import csv
import os
from collections import defaultdict
from pathlib import Path
from typing import Dict, List, Union

import config.constants
import config.fields
import config.paths
import config.utils
from src.CodeDiffGraph import CodeDiffGraph
from src.LineDiff import LineDiff
from src.Snippet import Snippet

# Setup logging (mode 'a' append logging output to CodeComparer.log!)
logging, handler = config.utils.setup_logger("CodeComparer", mode='a')


################################################################################################
# *** Helper Functions ***
################################################################################################

def has_at_least_2_java_files(directory: str) -> bool:

    """
    Checks whether a directory contains at least two `.java` files.

    If an input directory contains less than two .java files,
    there are no code snippets to compare.

    Args:
        directory (str): Path to the target directory.

    Returns:
        bool: True if the directory contains two or more Java files, False otherwise.
    """

    logging.info(f"Checking for at least 2 Java files in directory: {directory}")

    try:
        # Count number of java files in directory
        java_file_count = sum(
            1 for item in os.listdir(directory)
            if os.path.isfile(os.path.join(directory, item)) and item.endswith('.java')
        )
        return java_file_count >= 2
    except FileNotFoundError:
        logging.error(f"Folder {directory} doesn't exist.")
        return False
    except PermissionError:
        logging.error(f"No permission to access {directory}.")
        return False


################################################################################################
# *** Diff Helper Functions ***
################################################################################################

def group_snippets(folder) -> Dict[str, List[Snippet]]:
    """
    Groups Java files by algorithm/snippet name.

    Each group corresponds to a specific algorithm, with multiple variants and versions.
    Files are parsed into Snippet objects.

    Args:
        folder (str): Path to the folder containing Java source files.

    Returns:
        Dict[str, List[Snippet]]: A dictionary mapping snippet names to lists of Snippet objects.
    """


    logging.info(f"Loading files from folder \"{folder}\"...")

    grouped_snippets: Dict[str, List[Snippet]] = {}

    for file in sorted(Path(folder).iterdir()):
        if file.is_file() and file.suffix.lower() == f".{config.constants.language.lower()}":

            snippet_obj = Snippet.from_file(file)

            if not snippet_obj:
                logging.info(f"Snippet could not be created from file {file}.")
                continue

            if snippet_obj.name not in grouped_snippets:
                grouped_snippets[snippet_obj.name] = []  # collect all snippet objects that belong to one algorithm

            # Add new snippet object to the grouped snippets list
            grouped_snippets[snippet_obj.name].append(snippet_obj)

    logging.info(f"Grouped into {len(grouped_snippets)} group.")
    return grouped_snippets


################################################################################################
# *** Graph Report Generation ***
################################################################################################

def save_all_graph_data(groups_graphs, output_path: str) -> None:

    """
    Generates Markdown and CSV reports for graph-based snippet evolution per variant/prompt.
    + Saves the graphs as files to output/../snippets/../graphs/

    For each variant in a snippet group, the function extracts original lines and their
    change frequency across versions from the graph representation.

    Args:
        groups_subgraphs: A dictionary mapping variant names to their diff graphs.
        output_path (str): Directory where the reports and serialized graphs are saved.

    Returns:
        None: Outputs are written to disk.
    """

    if len(groups_graphs) == 0:
        logging.info("(generate_groups_graph_report) Group Graphs Dict is empty. Return ...")
        return

    logging.info("Generating graph reports and files...")

    for algorithm, variant_data in groups_graphs.items():

        output_subdir = os.path.join(output_path, algorithm, config.paths.GRAPHS_DIR)
        os.makedirs(output_subdir, exist_ok=True)

        for variant, prompts in variant_data.items():
            for prompt_id in prompts:
                graph_obj = prompts[prompt_id]
                # Save code and transition graphs to files
                filepath = os.path.join(output_subdir, f"{algorithm}_Graph_{variant}_{prompt_id}")
                graph_obj.save_code_graph(filepath)
                graph_obj.save_transition_graph(filepath)

                # Generate report for overview version count of all original lines
                _generate_version_count_report(algorithm, variant, graph_obj, output_subdir)

# --------------------------------------------------------------------------------------------------------

def _generate_version_count_report(algorithm, variant, graph_obj, output_path):

    # Generate graph report for this snippet group
    markdown_path = os.path.join(output_path,
                                 f"{algorithm}_VersionCount_{config.constants.GRAPH_REPORT}_{variant}" + ".md")

    csv_path = os.path.join(output_path,
                            f"{algorithm}_VersionCount_{config.constants.GRAPH_REPORT}_{variant}" + ".csv")

    header = [config.fields.variant, "Original Line", "Change Count",
              "Versions"]  # + [f"{config.version} {i}" for i in range(config.rounds)]

    report_data = []

    for node in graph_obj.get_all_original_nodes():
        # Get number of versions of this line
        change_count = graph_obj.get_change_count(node)

        # Sanitize code text for report data
        sanitized_node_name = config.utils.sanitize_code_for_reports(node)

        # Add these base values to row
        row = [variant, sanitized_node_name, change_count]

        # Fill in all version data

        # version_data = graph.get_version_data_as_dict(node) # TODO: Das funktioniert noch nicht so, s. Kommentare in der Funktion

        # Fill in versions
        version_data = [config.utils.sanitize_code_for_reports(v) for v in graph_obj.get_versions(node)]
        row.append(version_data)

        report_data.append(row)

    # Generate Markdown file
    with open(markdown_path, "w", encoding="utf-8") as md_file:
        md_file.write(f"# {config.constants.GRAPH_REPORT}: {output_path}\n\n")
        md_file.write("| " + " | ".join(header) + " |\n")
        md_file.write("| " + " | ".join(["-" * len(h) for h in header]) + " |\n")
        for row in report_data:
            md_file.write("| " + " | ".join(map(str, row)) + " |\n")

    # Generate CSV file
    with open(csv_path, 'w', newline='', encoding="utf-8") as csv_file:
        writer = csv.writer(csv_file)
        writer.writerow(header)  # Write header
        writer.writerows(report_data)  # Write data

    logging.debug(f"Version Count Reports generated: {markdown_path}, {csv_path}")


################################################################################################
# *** Diff Report Generation ***
################################################################################################

def generate_diff_report(results: List[LineDiff], output_dir: str) -> None:
    """
    Generates Markdown and CSV reports for all code snippet comparisons.

    Each row in the report contains metadata about the compared files as well as
    comparison metrics such as number of insertions, deletions, modifications,
    and average similarity scores.

    Args:
        results (List[LineDiff]): A list of LineDiff objects representing all comparisons.
        output_dir (str): Directory where the reports are saved.

    Returns:
        None: Results are written to disk.
    """

    logging.info("Generating diff reports and files...")

    if not results:
        logging.info("No results to generate reports for.")
        return

    # Generate table headers
    header = config.fields.COMPARISON_REPORT_COMPLETE_HEADER

    # Extract and process data
    report_data = []

    for res in results:
        # Dynamic generation of base values according to COMPARISON_REPORT_BASE_HEADER
        base_values = {
            config.fields.snippet: res.snippet1.name,
            config.fields.variant1: res.snippet1.variant,
            config.fields.variant2: res.snippet2.variant,
            config.fields.version1: res.snippet1.version,
            config.fields.version2: res.snippet2.version,
            config.fields.prompt: res.prompt_id
        }
        row = [base_values[key] for key in config.fields.COMPARISON_REPORT_BASE_HEADER]

        for key in config.fields.COMPARISON_REPORT_METRICS:
            value = res.comparison_metrics[key]
            # value is a list of lines in LineDiff object's modifications/insertions/deletions fields
            if key == config.fields.modifications:
                row.append(int(len(value) * 0.5))  # -/+ pairs count as "one" modified line
            elif key == config.fields.avg_similarity_score or key == config.fields.unchanged:
                row.append(value)
            else:
                row.append(len(value))

        report_data.append(row)

    markdown_path = os.path.join(output_dir,
                                 config.constants.COMPARISON_REPORT + ".md")

    csv_path = os.path.join(output_dir,
                            config.constants.COMPARISON_REPORT + ".csv")
    # Generate Markdown file
    with open(markdown_path, "w", encoding="utf-8") as md_file:
        md_file.write(f"# {config.constants.COMPARISON_REPORT}\n\n")
        md_file.write("| " + " | ".join(header) + " |\n")
        md_file.write("| " + " | ".join(["-" * len(h) for h in header]) + " |\n")
        for row in report_data:
            md_file.write("| " + " | ".join(map(str, row)) + " |\n")

    # Generate CSV file
    with open(csv_path, 'w', newline='', encoding="utf-8") as csv_file:
        writer = csv.writer(csv_file)
        writer.writerow(header)  # Write header
        writer.writerows(report_data)  # Write data

    print(f"Reports generated: {markdown_path}, {csv_path}")


def generate_change_type_report(results: List[LineDiff], output_dir: str) -> None:

    logging.info("Generating change types report ...")

    if not results:
        logging.info("No results to generate report for.")
        return

    # Generate table headers
    header = config.fields.CHANGE_TYPES_REPORT_COMPLETE_HEADER

    # Extract and process data
    report_data = []

    for res in results:

        base_values = {
            config.fields.snippet: res.snippet1.name,
            config.fields.variant1: res.snippet1.variant,
            config.fields.variant2: res.snippet2.variant,
            config.fields.version1: res.snippet1.version,
            config.fields.version2: res.snippet2.version,
            config.fields.prompt: res.prompt_id
        }

        for id_x in res.comparison_metrics[config.fields.modifications]:

            values = {}

            # matched_lines is a two-way dict --> going through "-" half of the lines suffices
            if res.indexed_lines[id_x].startswith("-"):
                match_id_x = res.comparison_metrics[config.fields.modifications][id_x][config.fields.match]

                values[config.fields.source_ast_type] = res.comparison_metrics[config.fields.modifications][id_x][
                    config.fields.classification_data][config.fields.source_ast_type]
                values[config.fields.target_ast_type] = res.comparison_metrics[config.fields.modifications][id_x][
                    config.fields.classification_data][config.fields.target_ast_type]
                values[config.fields.classification] = res.comparison_metrics[config.fields.modifications][id_x][
                    config.fields.classification_data][config.fields.classification]
                values[config.fields.source_line] = config.utils.sanitize_code_for_reports(res.getStrippedLine(id_x))
                values[
                    config.fields.target_line] = config.utils.sanitize_code_for_reports(res.getStrippedLine(match_id_x))

                report_data.append(
                    [values[key] for key in config.fields.CHANGE_TYPE_REPORT_COLUMNS] +
                    [base_values[key] for key in config.fields.COMPARISON_REPORT_BASE_HEADER]
                )


    markdown_path = os.path.join(output_dir,
                                 config.constants.CHANGE_TYPE_REPORT + ".md")

    csv_path = os.path.join(output_dir,
                            config.constants.CHANGE_TYPE_REPORT + ".csv")

    # Generate Markdown file
    with open(markdown_path, "w", encoding="utf-8") as md_file:
        md_file.write(f"# {config.constants.CHANGE_TYPE_REPORT}\n\n")
        md_file.write("| " + " | ".join(header) + " |\n")
        md_file.write("| " + " | ".join(["-" * len(h) for h in header]) + " |\n")
        for row in report_data:
            md_file.write("| " + " | ".join(map(str, row)) + " |\n")

    # Generate CSV file
    with open(csv_path, 'w', newline='', encoding="utf-8") as csv_file:
        writer = csv.writer(csv_file)
        writer.writerow(header)  # Write header
        writer.writerows(report_data)  # Write data

    print(f"Reports generated: {markdown_path}, {csv_path}")