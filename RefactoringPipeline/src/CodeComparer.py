import argparse
import json
import os
from itertools import combinations
from pathlib import Path
from typing import List, Set
from tqdm import tqdm

import config.constants
import config.fields
import config.paths
import config.utils
import src.CodeComparerUtils as ccUtils
from src.CodeDiffGraph import CodeDiffGraph
from src.LineDiff import LineDiff
from src.Snippet import Snippet


# Setup logging
logging, handler = config.utils.setup_logger("CodeComparer")



class CodeComparer:
    """
    A utility class for comparing Java source files across different variants and versions
    within a structured dataset.

    The class traverses a given input directory, identifies groups of Java files representing
    code snippets with multiple variants and versions, and performs pairwise comparisons to
    detect and analyze line-based code differences. It generates detailed comparison reports
    in both Markdown and CSV format, and optionally constructs and merges diff graphs to
    visualize code evolution within variants.

    Key features:
    - Automatic grouping of Java files by snippet name, variant, and version.
    - Pairwise diff computation using the LineDiff abstraction.
    - Horizontal (within-variant, across versions) and vertical (within-version, across variants)
      comparison logic.
    - Integration with CodeDiffGraph for visualizing version-to-version transformations.
    - Export of results including structured diff reports and graph visualizations.

    Attributes:
        dataset_dir (str): Path to the input dataset directory containing Java files.
        output_dir (str): Path to the output directory for storing reports and graphs.
    """

    def __init__(self, input_dir: str) -> None:
        """
        Initializes the CodeComparer with input and output directories.

        Args:
            input_dir (str): Path to the root directory containing structured Java files to compare.
            output_dir (str): Path to the directory where reports and diff results will be stored.
        """

        logging.info("Initializing CodeComparer...")

        self.dataset_dir = input_dir
        self.output_dir = config.utils.get_output_path_based_on_input_dir(input_dir)

        self.snippets_outputDir = os.path.join(self.output_dir, config.paths.SNIPPETS_SUBDIR)
        config.utils.init_directories([self.snippets_outputDir])

        logging.info(f"Input dir: {self.dataset_dir}, Output dir: {self.output_dir}")


    def create_and_process_linediff_obj(self, snippet1, snippet2, subdir):

        """Create LineDiff object that can keep track of all the diff's metrics
        Returns None if snippets aren't eligible for a diff."""

        linediff_obj: LineDiff | None = LineDiff.from_snippets(snippet1, snippet2)

        if linediff_obj:

            horizontal = linediff_obj.isHorizontal()
            vertical = linediff_obj.isVertical()

            # Only process pairs within the same variant OR within the same version
            if horizontal or vertical:

                # Parse diff, construct modified diff and graph
                linediff_obj.parse_line_diff(snippets_subdir=subdir, details_option=True)

                # Save diffs and graph to file
                linediff_obj.save_diffs_to_file(subdir)
                linediff_obj.save_as_json(subdir)

                if config.constants.create_graphs:
                    linediff_obj.save_graph_to_file(subdir)

                return linediff_obj

            else:
                logging.info(
                    f'Not horizontal or vertical, skip linediff creation for: {str(snippet1)} and {str(snippet2)}')

        return None

    ################################################################################################
    # *** Main Functions - Usage with dataset data from JSON file ***
    ################################################################################################

    def compare_java_code_from_dataset_json(self):

        generated_dataset = config.utils.get_filename_for_dataset(config.constants.generated)

        filepath = os.path.join(self.dataset_dir, generated_dataset)

        if not os.path.exists(filepath):
            raise FileNotFoundError("Dataset file not found.")

        else:

            logging.debug(f"Loading file {filepath} ...")

            with open(filepath, 'r', encoding="utf-8") as file:
                dataset = json.load(file)

                complete_results, graphs = self.calculate_diffs(dataset)  # returns list of LineDiff objects

                # Generate final reports
                ccUtils.generate_diff_report(complete_results, self.output_dir)
                ccUtils.generate_change_type_report(complete_results, self.output_dir)

                # Save all "prompt line" graphs per algorithm and variant
                ccUtils.save_all_graph_data(graphs, output_path=self.snippets_outputDir)
                logging.info("✅ File comparison complete..\n")


    def calculate_diffs(self, dataset):

        logging.info("Starting Java file comparison from dataset...")

        results: List[LineDiff] = []
        group_graphs = {}

        for algorithm, variants in tqdm(dataset.items(), desc="Processing file comparison"):
            algo_subdir = os.path.join(self.snippets_outputDir, algorithm)

            # Initialize graph stuff for this snippet group
            group_graphs[algorithm] = {}
            snippets = []

            for variant, versions in variants.items():
                for version, prompt_ids in versions.items():
                    for prompt_id, data in prompt_ids.items():
                        snippets.append(
                            Snippet(name=algorithm, variant=variant, version=version, code=data[config.fields.code], prompt_id=prompt_id)
                        )

            # Create all snippet pairs (LineDiff object takes care of internal validation)
            for snippet1, snippet2 in combinations(snippets, 2):
                result = self.create_and_process_linediff_obj(snippet1, snippet2, algo_subdir)
                if result:
                    results.append(result)

                    if result.isDirectSuccessor():
                        prompt_id = result.prompt_id
                        variant = result.snippet1.variant
                        group_graphs[algorithm].setdefault(variant, {})
                        # Merge the new subgraph into the graph so far, create en emtpy graph if there is no graph to merge into yet
                        graph_so_far = group_graphs[algorithm][variant].get(prompt_id, CodeDiffGraph())

                        group_graphs[algorithm][variant][prompt_id] = graph_so_far.merge_with(result.graph)

                        logging.debug(f"Merged subgraph of {str(snippet1)} vs. {str(snippet2)}")

        logging.info("Finished calculating diffs.")
        return results, group_graphs

    # TODO : This was the first version and has been deprecated since we work with the data via the generated_dataset.json
    # TODO : But maybe this functionality is helpful in the future so I leave it in for now, but it is not tested.
    # ################################################################################################
    # # *** Main Functions - Usage by traversing all FOLDERS in dataset_dir ***
    # ################################################################################################

    def compare_java_files_traversing_folders(self) -> None:

        """
        Traverses the input dataset directory and performs pairwise comparisons
        on all valid Java files contained within snippet subdirectories.

        For each directory containing at least two Java files, the method computes
        line-based diffs, aggregates all comparison results, and generates final
        reports in Markdown and CSV formats.

        Returns:
            None: All results are saved directly to the output directory.
        """

        complete_results: List[LineDiff] = []
        group_graphs = {}

        for root, dirs, files in os.walk(self.dataset_dir):

            # makes sure that os.walk does everything in alphabetical order. Not necessary, but prettier.
            dirs.sort()
            files.sort()

            if ccUtils.has_at_least_2_java_files(root):

                results, subgraphs = self.calculate_diffs_from_files(root)  # returns list of LineDiff objects
                complete_results.extend(results)
                group_graphs |= subgraphs

            else:
                logging.info(f'[SKIP] {os.path.basename(root)} has less than 2 .java files in it.')

        # Generate final report
        ccUtils.generate_diff_report(complete_results, self.output_dir)
        ccUtils.generate_change_type_report(complete_results, self.output_dir)
        #ccUtils.merge_and_generate_full_graphs(group_graphs, self.output_dir)

        logging.info("Java file comparison complete.")

    ################################################################################################
    # *** Main Function to create the diffs ***
    ################################################################################################

    def calculate_diffs_from_files(self, folder: str):
        """
        Performs exhaustive pairwise comparison of Java files within a given folder.

        Java files are first grouped by their snippet identity. The method then performs
        comparisons either across different versions within the same variant (horizontal)
        or across different variants for the same version (vertical). For each valid pair,
        a LineDiff object is generated, parsed, and used to optionally construct a
        graph-based representation of the diff. Merged graphs are created per variant
        to visualize code evolution.

        Args:
            folder (str): Path to the folder containing Java files belonging to a single snippet group.

        Returns:
            List[LineDiff]: A list of LineDiff objects capturing all valid comparisons.
        """

        logging.info("Starting Java file comparison...")

        results: List[LineDiff] = []
        group_graphs = {}

        seen_pairs: Set[tuple] = set()

        # Grouping files in folder by snippet name (files are named with name, variant, version
        # e.g. BinarySearch_KF0_v0.java)

        logging.info("Grouping files in folder by snippet name...")
        grouped_snippets = ccUtils.group_snippets(folder)

        for algorithm, snippet_objects in tqdm(grouped_snippets.items(), desc="Grouping files"):

            algo_subdir = os.path.join(self.snippets_outputDir, algorithm)

            # Initialize graph stuff for this snippet group
            group_graphs[algorithm] = {}

            for snippet1, snippet2 in combinations(snippet_objects, 2):

                # Make sure the compared files are always compared from lower to higher version
                key = tuple(sorted([snippet1, snippet2], key=str))

                if key in seen_pairs:
                    continue

                else:
                    seen_pairs.add(key)

                    result: LineDiff | None = self.create_and_process_linediff_obj(snippet1, snippet2, algo_subdir)

                    if result:

                        results.append(result)

                        if result.isDirectSuccessor():
                            prompt_id = result.prompt_id
                            variant = result.snippet1.variant
                            group_graphs[algorithm].setdefault(variant, {})
                            # Merge the new subgraph into the graph so far, create en emtpy graph if there is no graph to merge into yet
                            graph_so_far = group_graphs[algorithm][variant].get(prompt_id, CodeDiffGraph())

                            group_graphs[algorithm][variant][prompt_id] = graph_so_far.merge_with(result.graph)

                            logging.debug(f"Merged subgraph of {str(snippet1)} vs. {str(snippet2)}")


            # Finished with one snippet group --> get infos from graph of this group
            output_path = os.path.join(algo_subdir, config.paths.GRAPHS_SUBDIR)
            os.makedirs(output_path, exist_ok=True)

            ccUtils.generate_groups_graph_report(group_graphs[algorithm], output_path)

        logging.info("Finished calculating diffs.")
        return results, group_graphs


def main():

    parser = argparse.ArgumentParser(description="Process dataset in a given folder")

    parser.add_argument(
        "folder",
        type=Path,
        help="Path to the folder containing dataset"
    )

    args = parser.parse_args()

    folder_path = args.folder

    # Überprüfen, ob der Pfad existiert und ein Verzeichnis ist
    if not folder_path.exists() or not folder_path.is_dir():
        raise ValueError(f"{folder_path} is not a valid directory")

    # CodeComparer initialisieren
    cc = CodeComparer(str(folder_path))
    cc.compare_java_code_from_dataset_json()

if __name__ == "__main__":
    print("Running CodeComparer.py ...")
    main()
