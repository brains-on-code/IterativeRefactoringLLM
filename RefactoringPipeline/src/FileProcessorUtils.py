import json
import os
from typing import Dict, Any, Tuple, List, Union
import javalang
import pandas as pd

import config.constants
import config.fields
import config.utils
from config.constants import max_LOC
from config.utils import determine_method_ratio, setup_logger

# Setup logging
logging, handler = setup_logger("FileProcessor", mode='a')


def create_dataset_entry(content, original_interval):

    new_entry = count_metrics(content) | {config.fields.LOC_interval: original_interval}

    return new_entry

def create_dataset_from_files(files_to_analyze: str) -> Dict[str, Dict[str, Dict[str, Dict[str, Any]]]]:

    """
    Recursively traverses a directory to find Java source files and constructs a nested dataset
    representing metrics for each file.

    Files are identified by a naming scheme to extract algorithm, variant, and version information.
    Files that do not follow the naming scheme are treated as external base files.

    Args:
        files_to_analyze (str): Path to the root directory containing Java files to analyze.

    Returns:
        dict: Nested dictionary structured as {algorithm: {variant: {version: {prompt_id : {metrics}}}},
              where metrics is a dict of computed code metrics.
    """

    dataset = {}  # dict containing all information about the Java files
    already_seen = []  # ensures that files are not analyzed twice


    for root, dirs, files in os.walk(files_to_analyze):

        dirs.sort()  # ensures that os.walk does everything in alphabetical order
        files.sort()

        for file in files:

            if file.endswith(f".{config.constants.language}") and file not in already_seen:
                already_seen.append(file)

                print(f"Processing {file}...")

                file_path = os.path.join(root, file)

                # we may process a folder that already contains variants and versions
                # TODO: vlt umbauen...auch wenn es sehr, sehr unwahrscheinlich ist, dass eine random Java-file diesem Muster entspricht
                try:
                    algorithm, variant, version, prompt_id = config.utils.parse_filename(file)

                except ValueError:

                    # If file doesn't have our naming scheme it's a new base file ('external file')
                    filename = os.path.splitext(os.path.basename(file))[0]

                    algorithm = sanitize_external_filenames(
                        filename)  # replace '_' in external filenames to accomodate for our naming scheme
                    variant = config.fields.base_variant
                    version = config.fields.base_version
                    prompt_id = config.fields.no_prompt

                # Open and analyze file
                with open(file_path, 'r', encoding="utf-8") as f:
                    content = f.read()

                    # Analyze metrics
                    metrics = count_metrics(content)

                    # Build JSON entry
                    if algorithm not in dataset:
                        dataset[algorithm] = {}
                    if variant not in dataset[algorithm]:
                        dataset[algorithm][variant] = {}
                    if version not in dataset[algorithm][variant]:
                        dataset[algorithm][variant][version] = {}
                    if prompt_id not in dataset[algorithm][variant][version]:
                        dataset[algorithm][variant][version][prompt_id] = {}

                    dataset[algorithm][variant][version][prompt_id] = metrics

    return dataset


def save_dataset_to_json(dataset, output_dir, fileext='') -> None:

    """
    Saves the current dataset (raw or filtered) to a JSON file and generates an absolute values report.

    Args:
        fileext (str, optional): Suffix for the JSON filename to distinguish dataset types
                                 (e.g., raw/plain or filtered). Defaults to empty string.
    """

    filename = config.utils.get_filename_for_dataset(fileext)

    json_path = os.path.join(output_dir, f"{filename}")

    with open(json_path, "w", encoding="utf-8") as json_file:
        json.dump(dataset, json_file, indent=4, sort_keys=True)
        json_file.close()

    print(f"Saved dataset to {json_path}.")
    return

def count_metrics(content: str) -> Dict[str, Any]:

    """
    Analyze Java source code content to extract basic code metrics including lines of code,
    comment lines, empty lines, number of methods, and calculated ratios.

    Args:
        content (str): Raw Java source code as a string.

    Returns:
        dict: Dictionary containing counts and ratios:
            - total lines
            - code lines
            - comment lines
            - inline comment lines
            - empty lines
            - number of methods
            - raw code content
            - boolean code-comment ratio (code lines > comment lines)
            - boolean method ratio (determined by heuristic rules)
    """

    lines = content.splitlines()
    num_lines = len(lines)  # Number of lines
    num_code_lines = 0
    num_comment_lines = 0
    num_inline_comment_lines = 0
    num_empty_lines = 0
    num_methods = 0
    in_block_comment = False

    for line in lines:
        stripped_line = line.strip()

        if not stripped_line:
            num_empty_lines += 1
        elif in_block_comment:
            num_comment_lines += 1
            if "*/" in stripped_line:
                in_block_comment = False
        elif stripped_line.startswith("/*"):
            num_comment_lines += 1
            if not stripped_line.endswith("*/"):
                in_block_comment = True
        elif stripped_line.startswith("//"):
            num_comment_lines += 1
        elif "//" in stripped_line:
            num_code_lines += 1
            num_inline_comment_lines += 1
        else:
            num_code_lines += 1

    # Count methods using javalang
    try:
        tree = javalang.parse.parse(content)
        num_methods = sum(1 for _ in tree.filter(javalang.tree.MethodDeclaration))
    except javalang.parser.JavaSyntaxError:
        pass

    return {
        config.fields.total_lines: num_lines,
        config.fields.code_lines: num_code_lines,
        config.fields.comment_lines: num_comment_lines,
        config.fields.inline_comments: num_inline_comment_lines,
        config.fields.empty_lines: num_empty_lines,
        config.fields.methods: num_methods,
        config.fields.code: content,
        config.fields.code_comment_ratio: num_code_lines > num_comment_lines,
        config.fields.method_ratio: determine_method_ratio(num_lines, num_methods)
    }


def save_summary_to_file(summary: pd.DataFrame, output_dir: str) -> None:

    """
    Save a pandas DataFrame summary of analyzed data as CSV and Markdown files in the specified output directory.

    If files already exist, they are overwritten.

    Args:
        summary (pandas.DataFrame): DataFrame containing the summarized data to save.
        output_dir (str): Directory path where the files will be saved.

    Returns:
        None
    """

    loc_prefix = config.utils.create_loc_fileext_for_configured_interval()
    summary_path_csv = os.path.join(output_dir, f"{loc_prefix}{config.constants.SUMMARY_COUNTS}.csv")
    summary_path_md = os.path.join(output_dir, f"{loc_prefix}{config.constants.SUMMARY_COUNTS}.md")

    summary.to_csv(summary_path_csv, index=False)
    print(f"Saved CSV summary to {summary_path_csv}")

    summary.to_markdown(summary_path_md, index=False)
    print(f"Saved Markdown summary to {summary_path_md}")


def analyze_files_by_complexity_conditions(
    dataset: Dict[str, Dict[str, Dict[str, Dict[str, Any]]]]) -> Tuple[pd.DataFrame, pd.DataFrame]:

    """
    Analyze files within a dataset and classify them based on predefined complexity conditions:
    1. Similar lines of code (LOC) within intervals
    2. Reasonable number of methods relative to LOC (~1 method per 20 lines)
    3. Files with more code lines than comment lines

    Only files fulfilling all conditions are considered valid.

    Args:
        dataset (dict): Nested dataset containing code metrics by snippet, variant, and version.

    Returns:
        tuple: A tuple containing:
            - pandas.DataFrame: Detailed file information including LOC intervals and condition flags.
            - pandas.DataFrame: Summary aggregating counts of valid files per LOC interval.
    """

    assert(config.constants.min_LOC < config.constants.max_LOC)

    data = flatten_dict_structure(dataset)

    df = pd.DataFrame(data)

    # Construct LOC intervals based on biggest LOC of the dataset
    min_loc = config.constants.min_LOC
    max_loc = min(config.constants.max_LOC, df[config.fields.total_lines].max())

    if min_loc > max_loc:
        raise Exception("Configured min_LOC > max LOC occuring in dataset! Abort...")
        # TODO: einbauen, dass man per Konsoleneingabe entscheiden kann, wie es weiter geht

    loc_step = config.constants.interval_size
    loc_bins = list(range(min_loc, max_loc + loc_step, loc_step))

    if len(loc_bins) < 1:
        raise Exception(f"Interval size {loc_step} does not yield any bins."
                        f"\nConfigured min/max/locstep: {config.constants.min_LOC}/{config.constants.max_LOC}/{loc_step}"
                        f"\nActual maxLOC in dataset: {max_loc}")

    df[config.fields.LOC_interval] = pd.cut(df[config.fields.total_lines], bins=loc_bins, include_lowest=False)

    # Save LOC interval for better readability as "x-y"-string

    df[config.fields.LOC_interval] = df[config.fields.LOC_interval].map(
        lambda iv: f"LOC_{int(iv.left)}-{int(iv.right) - 1}" if pd.notna(iv) else None)

    df[config.fields.all_fulfilled] = df[config.fields.method_ratio] & df[config.fields.code_comment_ratio]

    # Create summary
    summary = df.groupby(config.fields.LOC_interval, observed=True).agg(
        # "The default of observed=False is deprecated and will be changed to True in a future version of pandas. Pass observed=False to retain current behavior or observed=True to adopt the future default and silence this warning."
        Total_Files=(config.fields.snippet, "count"),
        Method_Ratio=(config.fields.method_ratio, "sum"),
        Code_Comment_Ratio=(config.fields.code_comment_ratio, "sum"),
        Valid_Files=(config.fields.all_fulfilled, "sum"),
    ).reset_index()

    # Sortiere numerisch nach Startwert
    summary = summary.sort_values(
        by=config.fields.LOC_interval,
        key=lambda col: col.map(config.utils.interval_start),
        ascending=True  # oder False, je nachdem wie du sortieren willst
    )

    return df, summary


def reconstruct_dataset_from_df(df: pd.DataFrame) -> Dict[str, Dict[str, Dict[str, Dict[str, Any]]]]:
    """
    Reconstruct the nested dataset structure from a flattened pandas DataFrame.

    Args:
        df (pd.DataFrame): DataFrame containing flattened dataset rows with snippet, variant, and version columns.

    Returns:
        Nested dataset structured as {snippet: {variant: {version: metrics}}}.
    """

    new_dataset = {}

    for _, row in df.iterrows():
        snippet = row[config.fields.snippet]
        variant = row[config.fields.variant]
        version = "v" + row[
            config.fields.version]  # Add "v" of Version number for better readability in tables and stuff
        prompt_id = row[config.fields.prompt]

        data = {key: row[key] for key in config.fields.DATASET_KEYS}

        new_dataset.setdefault(snippet, {}).setdefault(variant, {}).setdefault(version, {})[prompt_id] = data

    return new_dataset

def flatten_dict_structure(data: Union[Dict[str, Any], pd.DataFrame]) -> List[Dict[str, Any]]:
    """
    Flatten the nested dataset dictionary structure into a list of dictionaries suitable for DataFrame conversion.
    LOC intervals are ignored in this flattening.

    Args:
        data (Union[Dict[str, Any], pd.DataFrame]): Nested dataset or DataFrame to flatten.

    Returns:
        List[Dict[str, Any]]: List of dictionaries representing flattened dataset entries.
    """
    if isinstance(data, pd.DataFrame):
        return data.to_dict(orient="records")

    rows = []

    first_key = next(iter(data))
    if first_key.startswith("LOC"):
        # oberste Ebene ist LOC → skip
        datasets = data.values()
    else:
        # oberste Ebene sind direkt Snippets
        datasets = [data]

    for d in datasets:
        for snippet, variants in d.items():
            for variant, versions in variants.items():
                for version, prompt_ids in versions.items():
                    for prompt_id, info in prompt_ids.items():
                        row = {
                            config.fields.snippet: snippet,
                            config.fields.variant: variant,
                            config.fields.version: version.lstrip("Vv"),
                            config.fields.prompt: prompt_id
                        }
                        row.update(info)
                        rows.append(row)
    return rows


def create_filename_with_namingScheme(algorithm_name: str, kf_key: str, v_key: str, p_key: str) -> str:
    """
    Create a filename string following the configured naming scheme for Java files.

    Args:
        algorithm_name (str): Name of the algorithm or snippet.
        kf_key (str): Variant key.
        v_key (str): Version key.
        p_key (str): Prompt key.

    Returns:
        str: Filename in the format '{algorithm_name}_{kf_key}_{v_key}.{language}'.
    """

    return f"{algorithm_name}_{kf_key}_{v_key}_{p_key}.{config.constants.language}"


def sanitize_external_filenames(filename: str) -> str:

    """
    Sanitize external filenames by replacing underscores with hyphens to match internal naming conventions.

    Args:
        filename (str): Original filename string.

    Returns:
        str: Sanitized filename string.
    """

    return filename.replace("_", "-")


