import argparse
from pathlib import Path
from src.FileProcessor import FileProcessor
from src.CodeComparer import CodeComparer
from src.Evaluator import Evaluator

from config.utils import setup_logger

# Setup logging
logging, handler = setup_logger("Main")


def main(folder_path : str):

    # ------------------------------------------------------
    # TODO before you start:
    #  Set LOC interval parameters in config/constants.py as
    #  well as number of llm iterations.
    # ------------------------------------------------------

    # ------------------------------------------------------
    """
    The FileProcessor:

        - creates dataset dir in "input/[basedir]"
        - creates corresponding dir in "output/[basedir]/[interval]/" folder where all the comparison and analysis data should live
        
        - choses files based on our criteria 
          (+ generates _summary_counts.md file with overview of valid files that fulfill our criteria in each LOC interval)

        - generates all variants and versions for these valid files (in case they don't exist yet)
        - saves them as correctly named base files (..._KF0_v0_nop.java) in "../input/[basedir]/snippets/" (extra subdir per algorithm) 
        - analyzes all files of this generated dataset with respect to Absolute Values metrics 
            * saves AbsoluteValuesReport.csv in "../output/[basedir]/[interval]/"
            * saves all snippet data in a complete _generated_dataset.json file for the further steps of the experiment

    """
    fp = FileProcessor(input_dir = folder_path)
    fp.run()


    # ====================================================
    # => Now the dataset.json is finished!
    # ====================================================

    """
    The CodeComparer:
        - takes the _generated_dataset.json
        - calculates the diffs for all eligible snippets
            *includes LineDiff object creation for all diffs
            *parses the diff, i.e. classifies insertions, modifications, deletions and other metrics
            *constructs modified diff with classification comments and optional info on matches/ratio
            * ...
        - creates subdir in output/[basedir]/[interval]/snippets/ per algorithm
            *saves diffs, (sub-)graphs, logs, and all comparison data as JSON per LineDiff object
            *saves one complete code evolution graph per algorithm variant prompt line
        - generates the "Comparison_Data.csv" and "Change_Types_Report.csv"

    """

    cc = CodeComparer(input_dir=fp.dataset_dir)
    cc.compare_java_code_from_dataset_json()


    # ====================================================
    # => Now all comparison data are finished!
    # ====================================================

    """
    The Evaluator:
        - runs all analyses for the data (--> output/[basedir]/[interval]/analysis
        - plots everything (--> output/[basedir]/[interval]/plots)
    """

    evaluator = Evaluator(input_dir=cc.output_dir)
    evaluator.run_analysis()

    # ------------------------------------------------------


if __name__ == "__main__":
    logging.info("Running main.py ...")

    parser = argparse.ArgumentParser(description="Process Java files in a given folder")

    parser.add_argument(
        "folder",
        type=Path,
        help="Path to the folder containing Java source files"
    )

    args = parser.parse_args()

    folder_path = args.folder

    # Check if path exists and is a valid directory
    if not folder_path.exists() or not folder_path.is_dir():
        raise ValueError(f"{folder_path} is not a valid directory")

    main(str(folder_path))
