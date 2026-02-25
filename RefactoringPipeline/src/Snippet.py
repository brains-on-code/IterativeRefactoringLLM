from pathlib import Path

import config.fields
import config.utils

class Snippet:
    """
    Represents a code snippet uniquely identified by (name, variant, version).

    Attributes:
        name (str): The general name of the snippet (e.g. "BinarySearch").
        variant (str): A specific variant label (e.g. "KF1").
        version (int): Version number of this snippet variant.
        code (str): The actual code content.
    """

    def __init__(self, name: str, variant: str, version: str | int, code: str, prompt_id : str | None):

        self.name = name
        self.variant = variant
        self.version = int(version.lstrip("Vv")) if isinstance(version, str) else version # need to compare version numbers as ints
        self.code = code
        self.prompt_id = prompt_id if prompt_id else config.fields.no_prompt


    def __eq__(self, other):
        """
        Checks equality between two Snippet objects based on name, variant, version and prompt id.

        Args:
            other (object): Another object to compare.

        Returns:
            bool: True if both are Snippets with identical name, variant, and version.
        """

        if not isinstance(other, Snippet):
            return False

        return (
                self.name == other.name and
                self.variant == other.variant and
                self.version == other.version and
                self.prompt_id == other.prompt_id
        )

    def __hash__(self):
        """
        Returns the hash based on the snippet's identity.

        Returns:
            int: Hash value.
        """

        return hash((self.name, self.variant, self.version, self.prompt_id))

    def __str__(self):
        """
        String representation of the snippet in the format: name_variant_v<version>.

        Returns:
            str: Formatted snippet identifier.
        """

        return (
            f"{self.name}_{self.variant}_v{self.version}_{self.prompt_id}"
        )

    @staticmethod
    def from_file(filepath: Path) -> "Snippet | None":

        """
        Parses a snippet object from a file.

        Args:
            filepath (Path): Path to the file to parse.

        Returns:
            Optional[Snippet]: A Snippet instance if parsing is successful, otherwise None.

        Raises:
            ValueError: If the filename does not match the expected pattern.
        """

        try:
            name, variant, version, prompt_id = config.utils.parse_filename(filepath.name)

        except ValueError:
            print(f"(SnippetCreation) Something went wrong during parsing of filename {filepath.name}! No snippet is created.")
            return None

        code = filepath.read_text()
        if not code:
            print(f"(SnippetCreation) File must not be empty! {filepath.name}! No snippet is created.")
            return None

        else:
            return Snippet(name=name, variant=variant, version=version, code=code, prompt_id=prompt_id)

