import json
import os
import argparse
from pathlib import Path
import pandas as pd
from tqdm import tqdm

import config.constants
import config.fields
import config.paths
import config.utils
import src.FileProcessorUtils as fpUtils
from src.KeyFactorStrategy import KeyFactorStrategy, KF0_Baseline, KF1_NameRemover, KF2_CommentRemover
from src.APICaller import APICaller
from config.utils import ask_user, create_interval_label, setup_logger

# Setup logging
logging, handler = setup_logger("FileProcessor")


################################################################################################
# *** Class Definition ***
################################################################################################

class FileProcessor:

    """
    Handles the processing, analysis, filtering, variant creation, and versioning
    of Java source files within a given input directory.

    This includes loading or creating datasets from source files, filtering files based on
    complexity metrics, applying code transformations (variants), and generating
    iterative code versions via an external LLM-API.

    Attributes:
        path_to_original_files (str): Path to the directory containing the original Java files.
        original_dataset (dict): Raw dataset created from the original files.
        generated_dataset (dict): Filtered dataset containing only relevant files after filtering based on our conditions.
        dataset_dir (str): Directory path for storing datasets related to this input.
        output_dir (str): Directory path for storing output and reports.
    """

    def __init__(self, input_dir) -> None:

        """
        Initializes the FileProcessor with the given input directory.
        Sets up paths for dataset storage and output, and creates necessary directories.

        Args:
            input_dir (str): Path to the directory containing Java source files to process.
        """

        logging.info("Initializing FileProcessor...")

        self.path_to_original_files = input_dir

        self.original_dataset = {} # plain dataset that contains data from all files in a given directory (before filtering relevant ones)
        self.generated_dataset = {}  # generated dataset that only contains relevant files

        # Create and init directorires
        self.dataset_dir = os.path.join(config.paths.INPUT_DIR,
                                        str(os.path.basename(input_dir)))

        self.snippets_inputDir = os.path.join(self.dataset_dir, config.paths.SNIPPETS_SUBDIR)

        self.output_dir = config.utils.get_output_path_based_on_input_dir(input_dir)

        config.utils.init_directories([self.dataset_dir, self.snippets_inputDir])


    def access_base_field(self, algorithm, fieldname):
        """Accesses field in 'KF0 v0 nop' version of an algorithm """
        return self.generated_dataset[algorithm][config.fields.base_variant][config.fields.base_version][
            config.fields.no_prompt][fieldname]

    ################################################################################################
    # *** Main Function to analyze the single Java files ***
    ################################################################################################

    def analyze_java_files(self) -> None:

        """
        Analyzes Java files in the input directory to build a dataset of metrics.

        If a previously analyzed dataset JSON file exists, it loads the dataset
        instead of reprocessing the files. Otherwise, it creates a new plain
        dataset by analyzing each Java file.

        The plain dataset is saved to JSON after creation or loading.
        """

        files_to_analyze = self.path_to_original_files

        if len(os.listdir(files_to_analyze)) == 0:
            print("Empty dir, nothing to analyze!")
            return

        original_dataset = os.path.join(self.dataset_dir, config.constants.original + config.constants.DATASET_JSON)
        if os.path.exists(original_dataset):
            print(f"This directory has already been analyzed.\nLoading {original_dataset}")
            with open(original_dataset, "r", encoding="utf-8") as f:
                self.original_dataset = json.load(f)

        else:
            print(f"Create dataset from files...")
            self.original_dataset = fpUtils.create_dataset_from_files(files_to_analyze)
            fpUtils.save_dataset_to_json(self.original_dataset, self.dataset_dir, fileext=config.constants.original)


    def filter_relevant_files(self) -> None:

        """
        Filters the raw dataset to retain only files meeting predefined complexity conditions.

        The filtering is based on conditions and LOC intervals defined in the configuration.
        - Updates internal dataset to contain only relevant files.
        - Saves summary reports and restructures dataset/output directories accordingly.
        """

        # Analyze all files by complexity given our three conditions (defined in config)
        df, summary = fpUtils.analyze_files_by_complexity_conditions(self.original_dataset)

        # Save summary to main output folder
        fpUtils.save_summary_to_file(summary, self.dataset_dir)

        for interval_label in df[config.fields.LOC_interval].dropna().unique():
            relevant_files = df[(df[config.fields.LOC_interval] == interval_label) & (df[config.fields.all_fulfilled])]
            if relevant_files.empty:
                continue

            print(f"There are {len(relevant_files)} relevant files in {interval_label}!")

            intervals_dataset = fpUtils.reconstruct_dataset_from_df(relevant_files)
            self.generated_dataset |= intervals_dataset

        fpUtils.save_dataset_to_json(self.generated_dataset, self.dataset_dir, fileext=config.constants.filtered)
        return

    def create_variants(self):
        # First, create all variants of the original code
        logging.info(f"Creating all variants...")

        variants = [
            KF0_Baseline(),
            KF1_NameRemover(),
            KF2_CommentRemover()
        ]

        self._create_variants(variants)

    def create_dataset(self):
        """
        Creates the dataset json dict if it hasn't been created before.
        """
        generated_dataset = config.utils.get_filename_for_dataset(config.constants.generated)
        filepath = os.path.join(self.dataset_dir, generated_dataset)

        if os.path.exists(filepath):
            logging.info(f"Dataset already exists! Loading file {filepath} ...")

            with open(filepath, 'r', encoding="utf-8") as file:
                self.generated_dataset = json.load(file)

        else:
            # Finally, create all versions with a prompt tailored to the specific key factor
            variants = [
                KF0_Baseline(),
                KF1_NameRemover(),
                KF2_CommentRemover()
            ]

            for variant in variants:
                print(f"Creating all versions for variant {variant}...")

                specific_prompt = variant.get_kf_specific_prompt()
                self._create_versions(specific_prompt)


        logging.info("Save dataset to json and generate new absolute values report.")
        fpUtils.save_dataset_to_json(self.generated_dataset, self.dataset_dir, fileext=config.constants.generated)
        self.generate_absolute_values_report()


    def _create_variants(self, variants) -> None:
        """
        Creates code variants for each algorithm in a filtered dataset by applying
        provided KeyFactor strategies, i.e. for each algorithm
        - saves the base variant (original code)
        - generates new variants by applying transformation strategies.
        - analyzes and saves metrics for each variant.
        """
        for algorithm in tqdm(sorted(self.generated_dataset.keys()), desc="Creating code variants"):
            logging.info(f"----------- ### Creating variants for algorithm: {algorithm} ### ----------- ")

            # This has to exist at this point, because the generated dataset holds all KF0-v0 entries at this point.
            base_code = self.access_base_field(algorithm, config.fields.code)
            interval = self.access_base_field(algorithm, config.fields.LOC_interval)

            # Create and save all other variants
            for kf_strategy in variants:

                kf_key = kf_strategy.get_label()
                prompt_key = config.fields.no_prompt  # variants are created by us, not via prompted call to API

                if kf_key not in self.generated_dataset[algorithm]:
                    self.generated_dataset[algorithm][kf_key] = {}
                    self.generated_dataset[algorithm][kf_key].setdefault(config.fields.base_version, {})
                    self.generated_dataset[algorithm][kf_key][config.fields.base_version].setdefault(prompt_key, {})


                # Create new filename and check if it already exists
                new_filename = fpUtils.create_filename_with_namingScheme(algorithm, kf_key, config.fields.base_version, prompt_key)
                subdir = os.path.join(self.snippets_inputDir, algorithm)
                filepath = os.path.join(subdir, new_filename)

                if os.path.exists(filepath):
                    logging.info(f"File {filepath} already exists! Loading code...")
                    with open(filepath, encoding="utf-8") as file:
                        content = file.read()
                        # Analyze version code's metrics and save in dict
                        dataset_entry = fpUtils.create_dataset_entry(content, interval)
                        self.generated_dataset[algorithm][kf_key][config.fields.base_version][config.fields.no_prompt] = dataset_entry
                        file.close()

                else:
                    logging.info(f"Creating variant {kf_key} ...")
                    # Create variant code if file or entry doesn't exist yet
                    new_content = kf_strategy.apply(base_code)
                    self.save_code_to_file(new_content, new_filename)

                    # Analyze new code's metrics
                    dataset_entry = fpUtils.create_dataset_entry(new_content, interval)
                    self.generated_dataset[algorithm][kf_key][config.fields.base_version][config.fields.no_prompt] = dataset_entry

            logging.info(f"***Finished creating variants for algorithm: {algorithm}***")

        return


    def _create_versions(self, prompt) -> None:
        """
        Generates multiple iterative versions of each code variant by leveraging the external OpenAI API.

        For each algorithm and variant, uses the API to refactor the code iteratively for a number
        of rounds defined in the configuration. Saves each generated version and analyzes
        their metrics.

        After completing version creation, saves the finalized dataset and generates reports.
        """

        api_caller = APICaller()

        prompt_id = prompt.get('id')

        for algorithm in tqdm(sorted(self.generated_dataset.keys()), desc="Generating versions"):
            kf_keys = self.generated_dataset[algorithm]

            interval = self.generated_dataset[algorithm][config.fields.base_variant][config.fields.base_version][
                config.fields.no_prompt][config.fields.LOC_interval]
            subdir = os.path.join(self.snippets_inputDir, algorithm)

            for kf_key in kf_keys:

                logging.info(f"----------- ### Creating versions for algorithm variant: {algorithm} {kf_key} with prompt {prompt_id} ### -----------")
                rounds = config.constants.llm_iterations

                for i in range(1, rounds+1):
                    v_key = f"v{i}"

                    # Create new filename and check if it already exists
                    new_filename = fpUtils.create_filename_with_namingScheme(algorithm, kf_key, v_key, prompt_id)
                    new_filepath = os.path.join(subdir, new_filename)

                    if os.path.exists(new_filepath):
                        # Load code of version instead of making an API call to create a new one
                        logging.info(f"File {new_filename} already exists! Loading code...")
                        with open(os.path.join(subdir, new_filename), encoding="utf-8") as file:
                            content = file.read()

                            # Analyze version code's metrics and save in dict
                            self.generated_dataset[algorithm][kf_key].setdefault(v_key,{})[prompt_id] = fpUtils.create_dataset_entry(content, interval)
                            file.close()

                    else:
                        # Create new version via API call
                        logging.debug(f"Creating version {v_key}...")

                        v_key_prev = f"v{i - 1}"

                        if i == 1:
                            # for the first version to be created, we always need to use the base code
                            old_code = self.generated_dataset[algorithm][kf_key][v_key_prev][config.fields.no_prompt][
                                config.fields.code]
                        else:
                            old_code = self.generated_dataset[algorithm][kf_key][v_key_prev][prompt_id][
                                config.fields.code]

                        new_code = api_caller.refactor_code(old_code, prompt.get('content'))

                        # Analyze version code's metrics
                        self.generated_dataset[algorithm][kf_key].setdefault(v_key, {})[prompt_id] = fpUtils.create_dataset_entry(new_code, interval)

                        # Save code to file since it doesn't exist yet
                        self.save_code_to_file(new_code, new_filename)

                logging.info(f"***Finished creating {rounds} versions for algorithm: {algorithm} variant {kf_key} ***")

        logging.info("Finished version creation. => DATASET COMPLETE")

        return

    def save_code_to_file(self, content, new_filename, interval='') -> None:

        """
        Saves source code content to a file within the dataset directory,
        organized by algorithm-specific subdirectories.

        Args:
            content (str): Source code content to write.
            new_filename (str): Filename following the naming scheme.
            interval: the LOC interval folder
        """

        algorithm = new_filename.split("_")[0]

        # Save file, renamed after naming-scheme
        new_dir = os.path.join(self.dataset_dir, config.paths.SNIPPETS_SUBDIR, interval, algorithm)
        os.makedirs(new_dir, exist_ok=True)
        new_path = os.path.join(new_dir, new_filename)

        if os.path.exists(new_path):
            logging.info(f"File {new_path} already exists! Overwriting...")

        with open(new_path, "w", encoding="utf-8") as f:
            f.write(content)

        logging.info(f"Saved code to {new_path}.")
        return


    def generate_absolute_values_report(self, debug=False) -> None:
        """
        # TODO: wär cool, wenn das auch einfach nur ne JSON einlesen könnte

        Generate CSV and Markdown reports for the given dataset containing absolute code metrics.

        Saves reports in the specified output directory with standardized filenames.

        Returns:
            None
        """

        logging.info("Generating report for absolute values...")

        # Nehme das mal raus, weil das tatsächlich Probleme gibt wenn dieser Report mehrfach erzeugt wird.
        # Wenn der nämlich schon 'voll' ist, und das neue dataset leer ist, dann bleibt das volle einfach
        # in der Datei stehen.
        # if not data:
        #     logging.info("No data to generate reports for.")
        #     return

        filename = f"{config.constants.ABSOLUTE_VALUES}"

        if debug:
            data = self.original_dataset
        else:
            data = self.generated_dataset

        output_dir = self.output_dir

        csv_path = os.path.join(output_dir, f"{filename}.csv")
        markdown_path = os.path.join(output_dir, f"{filename}.md")

        # Now generate CSV and Markdown based on the JSON
        rows = fpUtils.flatten_dict_structure(data)

        # Header = Snippet, Variant, Version, [Metrics]
        header = config.fields.ABSOLUTE_VALUES_REPORT_HEADER

        # Filter rows to only include keys that are in the header
        filtered_rows = [{k: row.get(k, "") for k in header} for row in rows]

        # Convert to DataFrame
        df = pd.DataFrame(filtered_rows)

        # Sort data
        sorting_order_snippets = [config.fields.snippet, config.fields.variant, config.fields.version,
                                  config.fields.prompt]
        df_sorted = df.sort_values(by=sorting_order_snippets, ascending=[True, True, True, True])

        # Save CSV
        df_sorted.to_csv(csv_path, index=False, encoding='utf-8')
        logging.info(f"Saved CSV to {csv_path}")

        # Save Markdown
        markdown_table = df_sorted.to_markdown(index=False)
        with open(markdown_path, "w", encoding='utf-8') as md_file:
            md_file.write("# Absolute Values\n\n")
            md_file.write(markdown_table)

        logging.debug(f"Saved Markdown to {markdown_path}")

        logging.debug(f"Saved {csv_path}.")
        logging.debug(f"Saved {markdown_path}.")



    def run(self):
        """
        Run the full processing pipeline with optional CLI confirmations
        before each major step.

        Args:
            llm_iterations: Number of iterations for LLM code generation
        """

        logging.info("\n=== Java File Analysis ===")
        #if ask_user(f"Do you want to analyze the files in directory {self.path_to_original_files}?"):
        self.analyze_java_files()
        logging.info("✅ Finished base analysis.\n")
        # else:
            # print("⏭ Skipped base analysis.\n")
            # return

        loc_step = config.constants.interval_size
        min_loc = config.constants.min_LOC
        max_loc = config.constants.max_LOC

        if loc_step > max_loc - min_loc:
            raise Exception("Configured interval size exceeds interval boundaries! Abort...")
            # TODO: einbauen, dass man per Konsoleneingabe entscheiden kann, wie es weiter geht
        if loc_step < 10:
            raise Exception("Configured interval size < 10 --> Consider at least a LOC step of 10 lines.")
            # TODO: einbauen, dass man per Konsoleneingabe entscheiden kann, wie es weiter geht

        self.filter_relevant_files()  # uses LOC intervals from config
        logging.info("✅ Finished filtering.\n")

        logging.info("=== Variant Creation ===")
        self.create_variants()
        logging.info("✅ Finished variant creation.\n")

        logging.info("=== Dataset Creation ===")
        if ask_user("Do you want to proceed with the dataset creation (API key required)?"):
            self.create_dataset()
            logging.info("✅ Finished dataset creation.\n")
        else:
            logging.info("⏭ Skipped dataset creation.\n")

        logging.info("=== DONE ===")


def main():
    parser = argparse.ArgumentParser(description="Process Java files in a given folder")

    parser.add_argument(
        "folder",
        type=Path,
        help="Path to the folder containing Java source files"
    )

    args = parser.parse_args()
    folder_path = args.folder

    # Überprüfen, ob der Pfad existiert und ein Verzeichnis ist
    if not folder_path.exists() or not folder_path.is_dir():
        raise ValueError(f"{folder_path} is not a valid directory")

    # FileProcessor initialisieren
    fp = FileProcessor(str(folder_path))

    fp.run()


if __name__ == "__main__":
    logging.info("Running FileProcessor.py ...")
    main()
