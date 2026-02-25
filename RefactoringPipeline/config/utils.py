import json
import logging
import os
import re

from config.constants import DATASET_JSON, filtered, generated, min_LOC, max_LOC
from config.fields import no_prompt
from config.paths import LOG_DIR, OUTPUT_DIR
from config.patterns import FILENAME_REGEX


def ask_user(prompt: str) -> bool:
    """
    Ask user a yes/no question in the CLI.
    Returns True for 'y', False for 'n'.
    """
    while True:
        answer = input(f"{prompt} [y/n]: ").strip().lower()
        if answer in ("y", "yes"):
            return True
        elif answer in ("n", "no"):
            return False
        else:
            print("Please provide y or n.")


def determine_method_ratio(loc: int, num_methods: int) -> bool:

    """
    Heuristically determine if the number of methods in a file is reasonable given its size.

    Rules:
    - Files with fewer than 20 lines should have at most 1 method.
    - Files with fewer than 50 lines should have 1 to 3 methods.
    - Larger files have a dynamic acceptable method range based on LOC.

    Args:
        loc (int): Total number of lines of code in the file.
        num_methods (int): Number of methods detected in the file.

    Returns:
        bool: True if the number of methods is within acceptable bounds, False otherwise.
    """

    if loc < 20:
        return num_methods <= 1

    elif loc < 50:
        return 1 <= num_methods <= 3

    else:
        # For larger files, check dynamically.
        # Example:
        # LOC 100 --> min_m would be 2, max_methods would be 5. I think that's a reasonable amount.
        min_m = max(1, loc // 50)
        max_m = max(1, loc // 20)

        return min_m <= num_methods <= max_m


def camel_to_snake(name: str) -> str:
    """
    Convert CamelCase (e.g., 'SuperMethodInvocation') to snake_case (e.g., 'super_method_invocation').

    Args:
        name (str): A CamelCase identifier.

    Returns:
        str: snake_case identifier.
    """
    # Trennt Großbuchstaben mit einem Unterstrich ab, außer am Anfang
    s1 = re.sub('(.)([A-Z][a-z]+)', r'\1_\2', name)
    snake = re.sub('([a-z0-9])([A-Z])', r'\1_\2', s1).lower()
    return snake


def snake_to_camel(name: str) -> str:
    """
    Convert snake_case (e.g., 'super_method_invocation') to CamelCase (e.g., 'SuperMethodInvocation').

    Args:
        name (str): A snake_case identifier.

    Returns:
        str: CamelCase identifier.
    """
    # Zerlegt bei Unterstrichen, macht jedes Teilwort groß, und verbindet wieder
    components = name.split('_')
    camel = ''.join(x.capitalize() for x in components if x)
    return camel


def init_directories(dir_list):
    """Ensure that all required directories exist."""
    for path in dir_list:
        os.makedirs(path, exist_ok=True)


def setup_logger(filename, dir="", mode='w'):
    log_dir = os.path.join(dir, LOG_DIR)
    # Erstelle einen Logger für das aktuelle Modul
    logger = logging.getLogger(filename)
    logger.setLevel(logging.DEBUG)

    # Erstelle einen Handler, der die Logs in eine Datei schreibt
    os.makedirs(log_dir, exist_ok=True)

    # FileHandler hängt standardmäßig an bestehende Log-Dateien an (mode='a' = append).
    # Um stattdessen die Datei bei jedem Start zu überschreiben -> Modus auf 'w' setzen.
    handler = logging.FileHandler(f'{log_dir}/{filename}.log', mode=mode, encoding="utf-8")
    #handler.setLevel(logging.ERROR)
    handler.setLevel(logging.DEBUG)

    # Erstelle ein Log-Format
    formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
    handler.setFormatter(formatter)

    # Füge den Handler zum Logger hinzu
    logger.addHandler(handler)
    logger.propagate = False  # wichtig: keine Weiterleitung an root-Logger (= Konsole)

    return logger, handler


def remove_logger(logger, handler):
    handler.close()
    logger.removeHandler(handler)


def sanitize_code_for_reports(code):
    return code.replace("|", "\\|").replace(",", "\\,")


def extract_interval_label(filename):
    """Extracts the LOC interval label (e.g. 'LOC_20-50') from a given string."""

    match = re.search(r"LOC_\d+-\d+", filename)
    if match:
        return match.group(0)
    return None


def interval_start(interval_str: str) -> int:
    if interval_str is None:
        return -1
    return int(interval_str.split("_")[1].split("-")[0])


class CustomEncoder(json.JSONEncoder):

    def default(self, obj):
        if isinstance(obj, set):
            # sets markieren, damit man sie später unterscheiden kann
            return {"__type__": "set", "items": list(obj)}
        return super().default(obj)


def custom_decoder(obj):
    if "__type__" in obj and obj["__type__"] == "set":
        return set(obj["items"])
    return obj


def create_interval_label(minLOC=min_LOC, maxLOC=max_LOC):
    """Uses configured LOC interval if no specific values are provided."""
    return f"LOC_{minLOC}-{maxLOC}"


def create_loc_fileext_for_configured_interval():
    return f"{create_interval_label()}_"


def get_output_path_based_on_input_dir(input_dir):
    output_dir = os.path.join(OUTPUT_DIR, str(os.path.basename(
        input_dir)), create_interval_label())

    os.makedirs(output_dir, exist_ok=True)
    return output_dir

def get_filename_for_dataset(fileext):

    if fileext in [generated, filtered]: # Quickfix to save one generated dataset per defined LOC interval
        return f"{create_loc_fileext_for_configured_interval()}{fileext}{DATASET_JSON}"
    else:
        return f"{fileext}{DATASET_JSON}"


def parse_filename(filename: str) -> tuple[str, str, str, str]:

    match = FILENAME_REGEX.match(filename)

    if not match:
        raise ValueError(f"Invalid filename format: {filename}")

    else:
        name = match.group(1)
        variant = match.group(2)
        version = match.group(3)
        prompt = match.group(4)

        if not prompt:
            # TODO: only allow specifiv prompt-version combinations (cause only v0 can be created with no prompt)
            prompt = no_prompt

        return name, variant, version, prompt


def is_valid_prompt_id_combination(id_1, id_2):

    # Valid combinations are same prompt ids or id_1 == no_prompt
    if id_1 == no_prompt:
        return True
    else:
        return id_1 == id_2
