from collections import OrderedDict
from difflib import SequenceMatcher

import config.constants
import config.fields
import config.utils
import src.DiffParserUtils as dpUtils

'''
Diff Format: 

@@ -<start1>,<count1> +<start2>,<count2> @@

Meaning of the components:

1. @@:
    * Marks the beginning and end of the header.

2. -<start1>,<count1>:
    * Start line and number of lines in the original text (before the change).
    * Example:
        * -3,3: Changes start from line 3 in the original text and affect 3 lines.
        
3. +<start2>,<count2>:
    * Start line and number of lines in the changed text (after the change).
    * Example:
        * +3,9: Changes start from line 3 in the new text and affect 9 lines.
        
4. additional text (optional):
    * After the second @@ there can be a comment or context info (not present in your example).
    
'''



class DiffParser:

    def __init__(self, filename, log_dir=""):
        self.filename = filename
        logger, handler = config.utils.setup_logger(filename, dir=log_dir)
        self.logging = logger
        self.handler = handler


    def process_diff_block(self, relevant_indexed_lines):

        """
        Processes a diff block.

        :param diff_block: List of lines from a diff block
        :param relevant_indexed_lines: The relevant indexed lines from this diff block.
        :return: {
            'modifications': mapping of removed to added lines (as indexes),
            'deletions': unmatched lines prefixed with '-' (as indexes),
            'insertions': unmatched lines prefixed with '+' (as indexes)
        }
        """

        modifications = {}  # Two-way mapping of removed to added lines (as INDEXED lines) = "modifications'
        removed_lines = set()  # Lines prefixed with '-'
        added_lines = set()  # Lines prefixed with '+'

        total_number_of_lines = 0

        for idx, line in relevant_indexed_lines.items():
            total_number_of_lines += 1
            if line.startswith('-'):
                removed_lines.add(idx)
            elif line.startswith('+'):
                added_lines.add(idx)
            else:
                self.logging.warning(f"Diff Line does not start with - or +! ({line}).")
                if line.startswith("@"):
                    self.logging.warning("Line is header-->Stop the process, something went wrong.")
                    raise Exception("Unified diff seems to be in invalid format!")

        # IMPORTANT:
        # Why do we store indices (a_idx) instead of lines?
        #
        # --> The added_lines list potentially contains several identical or similar lines.
        #
        # If we store only the lines themselves, multiple - lines could be assigned to
        # one + line, because the same or similar lines could be in the diff multiple times.
        #
        # By saving the indices, we ensure that each + line is only assigned once to one - line.

        matched_insertions = set()
        matched_deletions = set()

        matches_for_r_lines = {}

        # Try to pair similar removed and added lines
        for r_idx in sorted(removed_lines):

            # Initialize dict that collects all matched lines in the process
            if r_idx not in matches_for_r_lines.keys():
                matches_for_r_lines[
                    r_idx] = {}  # keys = a_idx , values = ratio of the matched lines of (r_idx, a_idx), change type classification

            if r_idx in matched_deletions:
                continue  # Skip already matched deletions

            # Cut the prefix "-"
            r_line_content = relevant_indexed_lines[r_idx][1:].strip()

            self.logging.info(f"\n#############################################"
                              f"\n==> Start to find match for r_line: {r_line_content}")

            match_found = False

            # Don't try to match empty lines
            # --> This  needs to be done here for r_line already,
            # because a "continue" when we check for empty lines inside the a_line for-loop
            # is too late. So the 'duplicate' code here is necessary.

            if r_line_content == "" or r_line_content == "}" or r_line_content == "{":
                # self.logging.info("Skip empty r_line...\n")
                continue
            # */

            # Split line in code and comment part, because extensive changes in comment part would throw the SequenceMatcher off
            r_line_only_code = dpUtils.strip_comments_from_line(r_line_content)
            r_line_comments = dpUtils.extract_comments_from_line(r_line_content)

            tokens_r_line_only_code = dpUtils.filter_irrelevant_tokens(dpUtils.tokenize_code_line(r_line_only_code))
            tokens_r_line_comments = dpUtils.tokenize_code_line(r_line_comments)

            for a_idx in sorted(added_lines):

                # Note: We don't skip already matched additions, because maybe we haven't found the best one yet in this block!

                a_line_content = relevant_indexed_lines[a_idx][1:].strip()
                self.logging.info(f"\na_line: {a_line_content}\n")

                # Don't try to match empty lines
                if a_line_content == "" or a_line_content == "}" or a_line_content == "{":
                    # self.logging.info(f"Skip empty a_line...{a_line}\n")
                    continue

                # Again, plit line in code and comment part, because extensive changes in comment part would throw the SequenceMatcher off
                a_line_only_code = dpUtils.strip_comments_from_line(a_line_content)
                a_line_comments = dpUtils.extract_comments_from_line(a_line_content)

                tokens_a_line_only_code = dpUtils.filter_irrelevant_tokens(dpUtils.tokenize_code_line(a_line_only_code))
                tokens_a_line_comments = dpUtils.tokenize_code_line(a_line_comments)

                # Compare content of lines without trailing signs (code and comment part separately)
                sequence_ratio_code = SequenceMatcher(None, r_line_only_code, a_line_only_code).ratio()
                sequence_ratio_comments = SequenceMatcher(None, r_line_comments, a_line_comments).ratio()
                tokens_ratio_code = SequenceMatcher(None, tokens_r_line_only_code, tokens_a_line_only_code).ratio()
                tokens_ratio_comments = SequenceMatcher(None, tokens_r_line_comments, tokens_a_line_comments).ratio()

                self.logging.info(f"seq./tokens ratio (code): {sequence_ratio_code}/{tokens_ratio_code}")
                self.logging.info(f"seq./tokens ratio (comment): {sequence_ratio_comments}/{tokens_ratio_comments}")

                if not r_line_only_code:
                    # If line is a comment line, of course we can only compare comment part ratios
                    # TODO /Thought: Auskommentierte Zeilen würden so dann nicht erkannt werden,
                    #  aber sollten meiner Meinung nach eh nicht relevant sein (weil ChatGPT wohl kaum
                    #  Zeilen auskommentiert sondern einfach neuen, 'guten' Code ausspuckt)
                    weighted_combined_ratio = 0.5 * (sequence_ratio_comments + tokens_ratio_comments)

                else:
                    # TODO: we'll test if making the closeness of indeces a factor improves something
                    # Therefore, we'll add in that -/+ lines that are at the same index in their respective list are probably the same line
                    # So if we have:
                    # - int x = 0;
                    # - int y = 0;
                    # + int start = 0;
                    # + int end = 0;
                    # --> Then the first - and the first + should be matched rather than the second with the first one.

                    # If AST node types are same, that should be taken into account
                    ast_similarity = dpUtils.compare_ast_structure(r_line_only_code, a_line_only_code)

                    self.logging.info(f"AST similarity: {ast_similarity}")

                    if ast_similarity == -1:
                        # means there was a parsing error or some issue with the tree and no real comparison possible
                        weighted_combined_ratio = (0.4 * sequence_ratio_code +
                                                   0.5 * tokens_ratio_code +
                                                   0.1 * sequence_ratio_comments
                                                   )


                    else:
                        weighted_combined_ratio = (0.2 * sequence_ratio_code +
                                                   0.25 * tokens_ratio_code +
                                                   0.5 * ast_similarity +
                                                   0.05 * sequence_ratio_comments
                                                   )


                if weighted_combined_ratio >= config.constants.similarity_threshold:
                    match_found = True
                    # Add current match to list of all matches for r_line
                    matches_for_r_lines[r_idx][a_idx] = {config.fields.ratio: weighted_combined_ratio}

                    self.logging.info(
                        f"\nMatch found: {a_line_content} with weighted combined ratio: {weighted_combined_ratio}")

                else:

                    self.logging.info(f"No match. Weighted combined ratio = {weighted_combined_ratio}")


            self.logging.info(f"\n====================================\n--> Matching process done for {r_line_content}!")

            if not match_found:
                # Line stays in removed_lines, nothing to do here
                self.logging.info(f"\n--> Crossmatching candidate!\n")

            else:

                # At least one match was found --> take best match (or the one that makes most sense if the a_line had a better match before)

                sorted_matches = OrderedDict(sorted(
                    matches_for_r_lines[r_idx].items(),
                    # -item[1][config.ratio]: higher ratio first
                    # TODO: testen, ob das so geht und sinnvoll ist
                    # abs(item[0] - r_idx): When same ratio, prefer the index that is closer to r_idx
                    key=lambda item: (-item[1][config.fields.ratio], abs(item[0] - r_idx)))
                )

                self.logging.info(f"Sorted matches: {sorted_matches}, r_idx = {r_idx}")

                matches_for_r_lines[r_idx] = sorted_matches
                match_complete = False  # If all matched a_lines have better previous matches --> r_line is crossmatching candidate because it couldn't be matched within its block

                # Try to complete the matching process
                for best_match_a_idx, best_match_stats in sorted_matches.items():

                    best_match_content = relevant_indexed_lines[best_match_a_idx][1:].strip()
                    best_match_ratio = best_match_stats[config.fields.ratio]

                    self.logging.info(
                        f"--> Current Best Sequence Match:\n{best_match_content}\nRatio: Seq({best_match_ratio}), a_idx = {best_match_a_idx}")

                    # Check if a (better) match for a_line had already been found
                    if best_match_a_idx in modifications:
                        previously_matched_r_idx = modifications[best_match_a_idx][config.fields.match]
                        previous_match_ratio = modifications[best_match_a_idx][config.fields.ratio]

                        if best_match_ratio <= previous_match_ratio:

                            # If this match is worse than the one before --> keep the previous one and check if there is another match for this r_line
                            self.logging.info(
                                f"Previous match was better or equal (previous: {previous_match_ratio}, current: {best_match_ratio}). Continue.")

                        else:
                            # If this matching pair is better --> delete old match and add the new one
                            # TODO: eine Art "Vorrücken" von anderen r_line matches wäre sinnvoll. list.pop() und so.
                            self.logging.info(
                                f"Previous match was worse (previous: {previous_match_ratio}, current: {best_match_ratio})\n->Delete old match and add the new!")

                            # Delete old match
                            del modifications[previously_matched_r_idx]
                            matched_deletions.remove(previously_matched_r_idx)

                            # Add this r_line to modifications with its match.
                            self.logging.info(f"Adding to mods r_line: {r_line_content}")

                            modifications[r_idx] = {config.fields.match: best_match_a_idx, config.fields.ratio: best_match_ratio}
                            self.logging.info(f"Updating mod entry of a_line: {best_match_content}")
                            modifications[best_match_a_idx] = {config.fields.match: r_idx, config.fields.ratio: best_match_ratio}  # mapping for both ways. Not sure if needed.

                            # Add this r_line to matched deletions.
                            matched_deletions.add(r_idx)

                            # (Since a_line had already been matched and now even has a better match, it is already in "matched_insertions".)

                            # Added our match --> finish loop
                            match_complete = True
                            break



                    else:
                        # There was no match before --> add this matching pair to modifications and to matched insertions/deletions respectively
                        self.logging.info("Hooray! This a_line was unmatched before! Adding match now...")
                        self.logging.info(f"Adding to mods r_line: {r_line_content}")

                        modifications[r_idx] = {config.fields.match: best_match_a_idx, config.fields.ratio: best_match_ratio}
                        self.logging.info(f"Adding to mods a_line: {best_match_content}")
                        modifications[best_match_a_idx] = {config.fields.match: r_idx, config.fields.ratio: best_match_ratio}  # mapping for both ways. Not sure if needed.
                        matched_insertions.add(best_match_a_idx)
                        matched_deletions.add(r_idx)

                        # Added our match --> finish loop
                        match_complete = True
                        break

                if not match_complete:
                    self.logging.info(f"All a_lines had better matched before.\n--> Crossmatching candidate!")
                    # Nothing to do here, line stays in removed/added_lines

            self.logging.info("\n#############################################")

        # Identify removed and added lines without matches.
        unmatched_deletions = removed_lines - matched_deletions
        unmatched_insertions = added_lines - matched_insertions

        # Make sure that the counting isn't messed up
        sum_after = len(modifications.keys()) + len(unmatched_insertions) + len(unmatched_deletions)
        if sum_after != total_number_of_lines:
            self.logging.warning("Number of lines and count of mods/ins/dels differ!")
            self.logging.warning(
                f"Lines: {total_number_of_lines}: m({len(modifications.keys())})/i({len(unmatched_insertions)})/d({len(unmatched_deletions)})")

        return {
            config.fields.modifications: modifications,
            config.fields.total_deletions: unmatched_deletions,
            config.fields.total_insertions: unmatched_insertions
        }

    ##########################################################################################
    # MAIN METHOD, ENTRY POINT.
    ##########################################################################################
    def parse_line_diff(self, indexed_lines):

        self.logging.info(f"\nParsing {self.filename} to classify insertions, modifications, and deletions...")

        # TRY TO MATCH THE LINES IN THE DIFF

        crossmatching_candidates = []  # collects all unmatched lines for later crossmatching across diff blocks
        modifications = {}  # Mapping of removed to added indexed lines
        insertions = []  # Lines that prefix '+' and don't have a match count as "insertions"
        deletions = []  # Lines that prefix '-' and don't have a match count as "deletions"

        i = 0
        while i < len(indexed_lines.keys()):

            line = indexed_lines[i]
            self.logging.info("***************************************************************")
            self.logging.info(f"i={i}, Line= {line}")

            if line.startswith("@@"):  # Parse header

                header_data = dpUtils.parse_header(line.strip())

                count1 = header_data["count1"]
                count2 = header_data["count2"]

                block_size = count1 + count2 + 1 # Note: count1 is set to 1 if empty during header parsing, so this should work
                end_of_block = i + block_size

                relevant_indexes = {
                    k: indexed_lines[k] for k in range(i + 1, end_of_block) if k in indexed_lines
                }

                self.logging.info("***************************************************************")
                self.logging.info(f"Starting to process block at {line}:")
                for l in relevant_indexes.values():
                    self.logging.info(l.strip())
                self.logging.info("***************************************************************")

                classifications = self.process_diff_block(relevant_indexes)

                # Collect all newly matched lines from the results
                modifications |= classifications[config.fields.modifications]  # |= is the operator for merging dicts

                # Collect all crossmatching candidates for later matching.
                crossmatching_candidates += classifications[config.fields.total_deletions]
                crossmatching_candidates += classifications[config.fields.total_insertions]

                # This is only for counting/testing
                # TODO: might remove/delete later on
                insertions += classifications[config.fields.total_insertions]
                deletions += classifications[config.fields.total_deletions]

                i += block_size

            else:  # line is not header
                i += 1

        # ---> Matching within the diff blocks complete! Now try to crossmatch.

        sum_before = len(modifications.keys()) + len(crossmatching_candidates)
        # +len(insertions)
        # +len(deletions))

        # TODO: Stimmt, die kann auch gar nicht gleich sein, weil wir in Lines ja auch noch die Header haben
        # assert(sum_before == len(lines)), f"Assertion failed: sum of mods and crossmatching candidates ({sum_before}) is not equal to number of lines ({len(lines)})"

        self.logging.info("\n\n***************************************************************")
        self.logging.info(" ### Start of CROSSMATCHING... ### ")
        self.logging.info("***************************************************************")

        crossmatching_diff_block = {index: line for index, line in indexed_lines.items() if
                                    index in crossmatching_candidates}

        crossmatching_results = self.process_diff_block(crossmatching_diff_block)

        # Final results
        modifications |= crossmatching_results[config.fields.modifications]  # |= is the operator for merging dicts
        insertions = crossmatching_results[config.fields.total_insertions]  # Note: the leftovers are now the real ins/dels
        deletions = crossmatching_results[config.fields.total_deletions]

        self.logging.info("// End of CROSSMATCHING...\n")


        ### Important: Updating of results! ###
        sum_after = (len(modifications)
                     + len(insertions)
                     + len(deletions))

        assert (
                    sum_before == sum_after), f"[Crossmatching] Assertion failed: sum_before ({sum_before}) is not equal to sum_after ({sum_after})"


        # !!!!
        # VERY IMPORTANT: CLOSE FILEHANDLER FOR THIS SPECIFIC LOGGING FILE!!
        # !!!!
        config.utils.remove_logger(self.logging, self.handler)

        return {
            config.fields.modifications: modifications,
            config.fields.total_deletions: deletions,
            config.fields.total_insertions: insertions,
        }

################################################################################################
