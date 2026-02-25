###########################################
# *** Base Keys ***#
###########################################
snippet = "Snippet"
variant = "Variant"
version = "Version"
prompt = 'Prompt'

base_variant = "KF0"
base_version = "v0"
no_prompt = "nop"

variant1 = variant + " 1"
variant2 = variant + " 2"
version1 = version + " 1"
version2 = version + " 2"

###########################################
# *** Absolute Values and Dataset Keys ***#
###########################################

total_lines = 'Total Lines'
code_lines = 'Code Lines'
comment_lines = 'Comment Lines'
inline_comments = 'Inline Comments'
empty_lines = 'Empty Lines'
methods = 'Methods'

# Condition Fields for grouping files by complexity / filtering relevant files
LOC_interval = 'LOC Interval'
method_ratio = 'Method Ratio'
code_comment_ratio = 'Code > Comment'
all_fulfilled = 'All Fulfilled'

# Basic Line Categories
code = 'Code' # Extra field for dataset such that we can work with the code more efficiently than to always have to read it from files -- Note: must be excluded during report generation, otherwise the tables would include all the code
comment = 'Comment'
empty = 'Empty'

###########################################
# *** Base Keys for Comparison Values *** #
###########################################
avg_similarity_score = "Avg Similarity Score"

# /* --- The four main categories of code changes on a line-level
unchanged = 'Unchanged'  # (= How much do the compared versions overlap)
modifications = 'Modifications'
total_insertions = 'Total Insertions'
total_deletions = 'Total Deletions'

MAIN_CHANGE_COLS = [unchanged, modifications, total_insertions, total_deletions]

# --- */

# /* --- The fine-granular categories for line-level changes
ins_code_lines = "Insertions (Code Lines)"
ins_comment_lines = "Insertions (Comment Lines)"
ins_empty_lines = "Insertions (Empty Lines)"

dels_code_lines = "Deletions (Code Lines)"
dels_comment_lines = "Deletions (Comment Lines)"
dels_empty_lines = "Deletions (Empty Lines)"

# Group them together
DETAILED_INS_COLS = [
    ins_code_lines, ins_comment_lines, ins_empty_lines
]

DETAILED_DELS_COLS = [
    dels_code_lines, dels_comment_lines, dels_empty_lines
]

DETAILED_INS_DELS_COLS = DETAILED_INS_COLS + DETAILED_DELS_COLS

# Attributes for modifications' dict entries:
match = "match"
ratio = "ratio"
classification_data = "change_type_data"

# Details of "change type data"
classification = "Classification" #merged this with former second field "classification" because they were the same
source_ast_type = "Source AST Type"
target_ast_type = "Target AST Type"

# Main Change Type Categories for Modifications
name_change = "Rename" # Rename of variable, method or class name
syntax_only = "SyntaxOnly"
comment_change = "CommentChange"
semantic_change = "CodeChange (Sum)"
mixed_change = "MixedChange"
other = "Other"

MODIFICATIONS_COLS = [
    name_change,
    syntax_only,
    comment_change,
    semantic_change,
    mixed_change,
    other
]

DETAILED_CHANGE_COLS = [unchanged] + MODIFICATIONS_COLS + DETAILED_INS_DELS_COLS  # Note: colors in VisualizeAnlysis.py for stacked_detailed_plots must be set manually based on these categories

# --- */


# Debug/'Not implemented yet' categories
identical_partial_ast = "IdenticalPartialAST"
value_error = "ValueError"

DEBUG_CATEGORIES = [
    identical_partial_ast,
    value_error
]

# Semantic SubCategories
access_change = "AccessChange" # Structural Access Change (modifiers, this)
call_change = "CallChange" # Structural Call Change (method_invocation)
control_change = "ControlChange" # Structural Control Change (if, loop, return)
#expression_wrap_change = "ExpressionWrapChange" # Could be structural if parentheses are set
literal_change = "LiteralChange"
operator_change = "OperatorChange"
other_structural_change = "OtherStructuralChange" #Structural Fallback Category

SEMANTIC_CHANGE_COLS = [
    access_change,
    call_change,
    control_change,
    #expression_wrap_change,
    literal_change,
    operator_change,
    other_structural_change
]

# We define alternative "AST types" for comment lines:
block_comment = 'BlockComment'
inline_comment = 'InlineComment'

# Field used in Report Generation
source_line = "Source Line"
target_line = "Target Line"
ast_type_combination = "AST Type Combination"
count = "Count"
subcount = "Subcount"


# Attributes of graph
DUMMY_ROOT = "__ROOT__"
DUMMY_DUMP = "__DUMP__"
change_type = "change_type"


###########################################
# *** METRICS DEFINITIONS ***#
###########################################

# --- Absolute Values Report
ABSOLUTE_VALUES_METRICS = [total_lines,
                           code_lines,
                           comment_lines,
                           inline_comments,
                           methods,
                           empty_lines
                           ] # If you change this list, you have to make sure that the fields in FileprocessorUtils.count_metrics() are correctly set!



COMPARISON_REPORT_METRICS = [avg_similarity_score] + MAIN_CHANGE_COLS + DETAILED_INS_DELS_COLS

EVAL_DIFF_METRICS = COMPARISON_REPORT_METRICS + ABSOLUTE_VALUES_METRICS + MODIFICATIONS_COLS

AVG_METRICS = ['avg_' + key for key in EVAL_DIFF_METRICS]
"""Note: As of Pandas 2.0, you must use named aggregation when aggregating with a dictionary that targets 
    named output columns, and this only works with .groupby(...).agg(**your_dict), not .agg(dict)."""
avg_dict = {f'avg_{metric}': (metric, 'mean') for metric in EVAL_DIFF_METRICS}

###########################################
# *** Other useful field groupings ***
###########################################

DATASET_KEYS = ABSOLUTE_VALUES_METRICS + [code, code_comment_ratio, method_ratio, LOC_interval] # => Needed when reconstructing dataset from dataframe and for absolute values report
ALL_PERCENTAGE_COLUMNS = MAIN_CHANGE_COLS + MODIFICATIONS_COLS + DETAILED_INS_DELS_COLS + SEMANTIC_CHANGE_COLS

###########################################
# *** REPORT HEADER DEFINITIONS ***
###########################################

# Absolute Values Report
ABSOLUTE_VALUES_REPORT_HEADER = [snippet, variant, version, prompt] + [k for k in DATASET_KEYS if k != code]  # for single files metrics = absolute values
# Comparison Report
COMPARISON_REPORT_BASE_HEADER = [snippet, variant1, variant2, version1, version2, prompt]  # for comparison data
COMPARISON_REPORT_COMPLETE_HEADER = COMPARISON_REPORT_BASE_HEADER + COMPARISON_REPORT_METRICS
# Graph Report
GRAPH_REPORT_HEADER = ["Line 1", "Line 2", "version_from", "version_to", ratio, classification_data]
# Change Types Report
CHANGE_TYPE_REPORT_COLUMNS = [source_ast_type, target_ast_type, classification, source_line, target_line]
CHANGE_TYPES_REPORT_COMPLETE_HEADER = CHANGE_TYPE_REPORT_COLUMNS + COMPARISON_REPORT_BASE_HEADER

