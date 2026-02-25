import difflib
import json
import os

import config.constants
import config.fields
import config.utils
import config.paths
from src.CodeDiffGraph import CodeDiffGraph
from src.DiffParser import DiffParser
import src.DiffParserUtils as dpUtils
from src.Snippet import Snippet

# Setup logging
logging, handler = config.utils.setup_logger("CodeComparer", mode='a')

class LineDiff:

    def __init__(self, snippet1 : Snippet, snippet2 : Snippet):
        self.snippet1 = snippet1
        self.snippet2 = snippet2
        self.prompt_id = snippet2.prompt_id

        self.indexed_lines = self.getIndexedDiffLines()

        # Initialize fields -> get filled during parsing
        self.comparison_metrics = {config.fields.modifications: {},
                                   config.fields.total_insertions: set(),
                                   config.fields.total_deletions: set(),
                                   config.fields.avg_similarity_score: -1,
                                   config.fields.unchanged: len(snippet1.code.splitlines())
                                   }


        self.modified_diff = []
        self.graph = CodeDiffGraph()



    @staticmethod
    def from_snippets(snippet1: Snippet, snippet2: Snippet) -> "LineDiff | None":
        """
        Creates a LineDiff object from two snippets.

        Args:
            snippet1 (Snippet): The first snippet object.
            snippet2 (Snippet): The second snippet object.

        Returns:
            LineDiff: A new LineDiff instance

        Raises:
            (nothing yet, but it will skip snippets with "JavaSyntaxError" content)
        """

        if snippet1 == snippet2:
            logging.warning(f"[SKIP] Snippet object are identical -> no LineDiff is created.")
            return None

        if snippet1.name != snippet2.name:
            logging.warning(f"[SKIP] {snippet1.name} and {snippet2.name}... (Must be same name)")
            return None

        # TODO: make this more robust later maybe?
        if snippet1.code == "JavaSyntaxError" or snippet2.code == "JavaSyntaxError":
            logging.warning(f"[SKIP] {snippet1.name} and {snippet2.name}... (JavaSyntaxError)")
            return None

        if snippet1.version > snippet2.version:
            logging.warning(f"[SKIP] {snippet1.name} and {snippet2.name} ... (version ordering wrong)")
            return None

        if not config.utils.is_valid_prompt_id_combination(snippet1.prompt_id, snippet2.prompt_id):
            logging.warning(f"[SKIP] {snippet1.name} and {snippet2.name} ... (prompt_ids not identical)")
            return None

        # Create a LineDiff object the parser can work with:
        logging.info(f"[Constructor] Creating LineDiff object for files {str(snippet1)} and {str(snippet2)}...")
        #print(f"[Constructor] Creating LineDiff object for files {str(snippet1)} and {str(snippet2)}...")

        return LineDiff(snippet1, snippet2)

    def parse_line_diff(self, snippets_subdir, details_option=False):
        """
        Parses a line diff and creates a modified diff with classification comments.

             Example for a modified line diff with classification comments:

                     @@ -24,3 +25,3 @@
            -        BinarySearch binarySearch = new BinarySearch();  # --- modification
            -        int[] array = { -18, 12, 17, 23, 38, 54, 72 };  # --- modification
            -        int target = 23;  # --- modification
            +        BinarySearch binarySearchInstance = new BinarySearch();  # --- modification
            +        int[] sortedArray = { -18, 12, 17, 23, 38, 54, 72 };  # --- modification
            +        int targetValue = 23;  # --- modification

        :param snippets_subdir: Since these logs get HUGE, we save them per snippet group such that we can debug better.
        :param details_option: If set to True, the modified_diff will contain more details for modifications.
        :return: - (but the line_diff object will be manipulated)
        """
        sub_dir = self.getSnippetSubDir(snippets_subdir) # returns the subfolder based on the snippet group to which this linediff object belongs (e.g. "BinarySearch")

        data_filepath = os.path.join(sub_dir, "data", f"{str(self)}.json")

        if os.path.exists(data_filepath):
            logging.debug(f"Loading {data_filepath} into LineDiff object ...")

            # load the data if calculations have already been made
            # CAUTION: THIS SHOULD NOT BE APPLIED DURING DEVELOPMENT SINCE EXISTING FILES WITH OLD DATA WON'T BE OVERWRITTEN
            with open(data_filepath, "r", encoding="utf-8") as file:
                data = json.load(file, object_hook=config.utils.custom_decoder)
                # Convert keys in modifications from strings back to ints for further processing
                data[config.fields.modifications] = {
                    int(k): v for k, v in data[config.fields.modifications].items()
                }

                for key, data in data.items():
                    self.comparison_metrics[key] = data

        else:
            # else we need to create the data of course
            diffParser = DiffParser(filename=str(self), log_dir=sub_dir)
            self.comparison_metrics = diffParser.parse_line_diff(self.indexed_lines)

            # Parsing finished, metrics complete

            # After all parsing and matching is done, there are three collections
            # in which a line can be present:
            # (1) modifications = matched pairs
            # (2) deletions (- lines w/o matching + line)
            # (3) insertions (+ lines w/o matching - line)

            self.categorize_modifications()
            self.create_detailed_comparison_metrics()
            self.calcAverageSimilarityScore()
            self.calc_unchanged_code_part()


        self.construct_modified_diff(details_option=details_option)

        if config.constants.create_graphs:
            if self.isDirectSuccessor():
                self.construct_graph()

    def categorize_modifications(self):

        for id_x in self.comparison_metrics[config.fields.modifications]:


            if not self.indexed_lines[id_x].startswith("-"):
                continue # modifications-dict is symmetric, we only need the


            else:

                match_id = self.comparison_metrics[config.fields.modifications][id_x][config.fields.match]

                old_line = self.getStrippedLine(id_x)
                new_line = self.getStrippedLine(match_id)

                classification_data = dpUtils.classify_ast_changes(old_line, new_line)

                self.comparison_metrics[config.fields.modifications][id_x][config.fields.classification_data] = classification_data
                self.comparison_metrics[config.fields.modifications][match_id][config.fields.classification_data] = classification_data


    def calc_unchanged_code_part(self):

        # The old code should be divided into unchanged part + modifications + deletions
        # The new code should be divided into unchanged part + modifications + insertions

        mods = int(0.5 * len(self.comparison_metrics[config.fields.modifications].keys()))
        ins = len(self.comparison_metrics[config.fields.total_insertions])
        dels = len(self.comparison_metrics[config.fields.total_deletions])

        old_code = self.snippet1.code.splitlines()
        new_code = self.snippet2.code.splitlines()

        assert(mods + dels <= len(old_code))
        assert(mods + ins <= len(new_code))

        unchanged_1 = len(old_code) - mods - dels
        unchanged_2 = len(new_code) - mods - ins

        assert(unchanged_1 == unchanged_2)

        self.comparison_metrics[config.fields.unchanged] = unchanged_1



    def create_detailed_comparison_metrics(self):
        """
        Divides insertions and deletions into details (i.e. how many code lines,
        comment lines, emtpy lines are inserted/deleted)
        """


        # These will get filled at the end of parse_line_diff()
        # Note: because of object reference dynamics, we only add those fields AFTER parsing,
        # because the dict will be overwritten otherwise with the return dict of the parsing
        # process, that doesn't contain details
        for metric in config.fields.DETAILED_INS_DELS_COLS:
            self.comparison_metrics.setdefault(metric, set())


        # Iterate through insertions
        for key in self.comparison_metrics[config.fields.total_insertions]:

            line = self.getStrippedLine(key)
            category = dpUtils.classify_line(line)

            if category == config.fields.empty:
                self.comparison_metrics[config.fields.ins_empty_lines].add(key)
            elif category == config.fields.comment:
                self.comparison_metrics[config.fields.ins_comment_lines].add(key)
            else:
                self.comparison_metrics[config.fields.ins_code_lines].add(key)

        # Iterate through deletions
        for key in self.comparison_metrics[config.fields.total_deletions]:

            line = self.getStrippedLine(key)
            category = dpUtils.classify_line(line)

            if category == config.fields.empty:
                self.comparison_metrics[config.fields.dels_empty_lines].add(key)
            elif category == config.fields.comment:
                self.comparison_metrics[config.fields.dels_comment_lines].add(key)
            else:
                self.comparison_metrics[config.fields.dels_code_lines].add(key)


    def construct_modified_diff(self, details_option=False):
        """
        Reconstruct the diff with classification comments.
        :param details_option: If set to True, the modified_diff will contain more details for modifications.
        :return: - (sets self.modified_diff)
        """

        logging.info("*** Constructing modified diff...***")

        # Reset, if there is any yet
        modified_diff = []

        for line_idx, line in self.indexed_lines.items():

            if line_idx in self.comparison_metrics[config.fields.modifications].keys():

                match_id = self.comparison_metrics[config.fields.modifications][line_idx][config.fields.match]
                ratio = self.comparison_metrics[config.fields.modifications][line_idx][config.fields.ratio]
                classification_data = self.comparison_metrics[config.fields.modifications][line_idx][
                    config.fields.classification_data]
                match = self.getStrippedLine(match_id)

                modified_diff.append(
                    f"{line.strip()}  # --- modification"
                    # Split into sign at position [0] and content for more compact printing
                    f"{f' FROM {match} with {config.fields.ratio}: {ratio}, {config.fields.classification_data}: {classification_data}' if details_option else ''}"
                )

            elif line_idx in self.comparison_metrics[config.fields.total_deletions]:
                modified_diff.append(f"{line.strip()}  # --- deletion")
            elif line_idx in self.comparison_metrics[config.fields.total_insertions]:
                modified_diff.append(f"{line.strip()}  # --- insertion")
            else:  # line is header or something else
                modified_diff.append(f"{line.strip()}")

            # Set new modified diff
            self.modified_diff = modified_diff

        return

    def construct_graph(self):
        """
        Construct code change graph from matched lines.

        :return: the constructed graph.
        """

        logging.info("*** Constructing graph...***")

        version_from = self.snippet1.version
        version_to = self.snippet2.version  # LineDiff snippets are always sorted (snippet1.version < snippet2.version)

        # If this is a LineDiff object starting at the base version, add edges to a DUMMY-rootnode
        # such that we can merge the graphs better later
        if version_from == 0:

            for line in self.snippet1.code.splitlines():

                stripped_line = line.strip()

                if stripped_line:  # don't add empty lines
                    self.graph.add_edges(config.fields.DUMMY_ROOT, line.strip(), version_from=-1,
                                         version_to=self.snippet1.version,
                                         ratio=0.0)

        # Add modifications
        for mod_key in self.comparison_metrics[config.fields.modifications].keys():

            # matched_lines is a two-way dict --> going through "-" half of the lines suffices
            original_line = self.indexed_lines[mod_key]
            stripped_line = self.getStrippedLine(mod_key)

            if original_line.startswith("-"):
                match_id = self.comparison_metrics[config.fields.modifications][mod_key][config.fields.match]
                match = self.getStrippedLine(match_id)

                ratio = self.comparison_metrics[config.fields.modifications][mod_key][config.fields.ratio]
                change_type = self.comparison_metrics[config.fields.modifications][mod_key][
                    config.fields.classification_data][config.fields.classification]

                # Add code lines as nodes without trailing signs
                self.graph.add_edges(old_line=stripped_line, new_line=match, version_from=version_from, version_to=version_to, ratio=ratio, change_type=change_type) # save lines in graph without preceding "-" or "+" from the diff

        # Add insertions (we do need to track them if they are modified later on)
        for index in self.comparison_metrics[config.fields.total_insertions]:
            line = self.getStrippedLine(index) # strip the sign of the line
            if line: # don't add empty lines to graph
                line_type = dpUtils.classify_line(line)
                self.graph.add_edges(config.fields.DUMMY_ROOT, new_line=line, version_from=version_from, version_to=version_to, change_type=f"Insertion ({line_type})")

        # Don't add deletions for now, since we only want to measure modifications
        for index in self.comparison_metrics[config.fields.total_deletions]:
            line = self.getStrippedLine(index) # strip the sign of the line
            if line: # don't add empty lines to graph
                line_type = dpUtils.classify_line(line)
                self.graph.add_edges(old_line=line, new_line=config.fields.DUMMY_DUMP, version_from=version_from, version_to=version_to, change_type=f"Deletion ({line_type})")


    def getIndexedDiffLines(self):

        logging.info("Generating indexed line diff...")
        lines1 = self.snippet1.code.splitlines()
        lines2 = self.snippet2.code.splitlines()

        # Create unified diff
        diff = list(difflib.unified_diff(lines1, lines2, lineterm="", n=0))  # n=0 is important for more detailed diffs and no context lines in diff

        # We need uniquely indexed lines such that matched_lines keys won't get overwritten.
        return dict(enumerate(diff))

    def getStrippedLine(self, id_x):
        return self.indexed_lines[id_x][1:].strip()

    def calcAverageSimilarityScore(self):

        mods_values = self.comparison_metrics[config.fields.modifications].values()

        if len(mods_values) == 0:
            # If there are no modifications, the code is identical --> simscore = 1.0!
            self.comparison_metrics[config.fields.avg_similarity_score] = 1.0

        else:
            ratios = [v["ratio"] for v in mods_values]
            average_ratio = sum(ratios) / len(ratios)
            # Set new ratio (rounded) value in linediff object
            self.comparison_metrics[config.fields.avg_similarity_score] = round(average_ratio, 2)


    def isHorizontal(self):
        return self.snippet1.variant == self.snippet2.variant

    def isDirectSuccessor(self):
        return self.isHorizontal() and (self.snippet2.version - self.snippet1.version == 1)

    def isVertical(self):
        return self.snippet1.version == self.snippet2.version

    def save_diffs_to_file(self, output_dir):
        """
        Function to save to files:
        - linediff
        - our modified linediff

        :output_dir: where to save the file
        :return: - (files are written & saved)

        """

        sub_dir = self.getSnippetSubDir(output_dir)

        diff_output_dir1 = os.path.join(sub_dir, 'diffs', 'LineDiff')
        diff_output_dir2 = os.path.join(sub_dir, 'diffs', 'LineDiff_modified')

        horizontal_output_dir1 = os.path.join(diff_output_dir1, 'horizontal', self.prompt_id)
        vertical_output_dir1 = os.path.join(diff_output_dir1, 'vertical', self.prompt_id)

        horizontal_output_dir2 = os.path.join(diff_output_dir2, 'horizontal', self.prompt_id)
        vertical_output_dir2 = os.path.join(diff_output_dir2, 'vertical', self.prompt_id)

        os.makedirs(horizontal_output_dir1, exist_ok=True)
        os.makedirs(vertical_output_dir1, exist_ok=True)

        os.makedirs(horizontal_output_dir2, exist_ok=True)
        os.makedirs(vertical_output_dir2, exist_ok=True)

        # Save linediff
        filename_diff = f"LineDiff_{self.__str__()}.txt"
        # Save modified diff
        filename_modified_diff = f"LineDiff_modified_{self.__str__()}.txt"

        if self.isHorizontal():
            output_file1 = os.path.join(horizontal_output_dir1, filename_diff)
            output_file2 = os.path.join(horizontal_output_dir2, filename_modified_diff)
        elif self.isVertical():
            output_file1 = os.path.join(vertical_output_dir1, filename_diff)
            output_file2 = os.path.join(vertical_output_dir2, filename_modified_diff)
        else:
            # That case shouldn't really happen, but if we - for whatever reason - want to analyze
            # some other file combination that is neither horizontal nor vertical --> implement it yourself xD
            return


        # Write files
        with open(output_file1, "w", encoding="utf-8") as file:
            file.write("\n".join(self.indexed_lines.values()))

        with open(output_file2, "w", encoding="utf-8") as file:
            file.write("\n".join(self.modified_diff))

        logging.info(f"Diffs saved to {sub_dir}")

        return

    def save_as_json(self, output_dir):

        # Create dir and filepath
        sub_dir = os.path.join(self.getSnippetSubDir(output_dir), "data")
        os.makedirs(sub_dir, exist_ok=True)

        filepath = os.path.join(sub_dir, f"{str(self)}.json")

        if os.path.exists(filepath):
            logging.debug(f"[SKIP] {filepath} already exists.")


        else:
            with open(filepath, "w", encoding="utf-8") as file:
                json.dump(self.comparison_metrics, file, cls=config.utils.CustomEncoder, indent=4)

            logging.debug(f"Saved linediff obj as json: {filepath}")


    def save_graph_to_file(self, output_dir):
        """
        Function to save to graph of this linediff object to .graphml and .png

        :output_dir: where to save the file
        :return: - (files are written & saved)

        """

        sub_dir = self.getSnippetSubDir(output_dir)
        output_dir = os.path.join(sub_dir, config.paths.SUBGRAPHS_DIR)

        horizontal_output_dir = os.path.join(output_dir, 'horizontal', self.prompt_id)
        vertical_output_dir = os.path.join(output_dir, 'vertical', self.prompt_id)

        os.makedirs(horizontal_output_dir, exist_ok=True)
        os.makedirs(vertical_output_dir, exist_ok=True)

        # Save graph
        filename_graph = f"DiffGraph_{str(self)}"

        if self.isHorizontal():
            output_file = os.path.join(horizontal_output_dir, filename_graph)

        elif self.isVertical():
            output_file = os.path.join(vertical_output_dir, filename_graph)

        else:
            logging.warning("Graph not saved. LineDiff seems to be neither horizontal nor vertical. Go check that pls.")
            return


        self.graph.save_code_graph(output_file)
        self.graph.save_transition_graph(output_file)
        return

    def getSnippetSubDir(self, output_dir):
        if not self.snippet1.name in output_dir:
            return os.path.join(output_dir, str(self.snippet1.name))
        else:
            return output_dir

    def __str__(self):
        return f"{str(self.snippet1)}_vs_{str(self.snippet2)}"
