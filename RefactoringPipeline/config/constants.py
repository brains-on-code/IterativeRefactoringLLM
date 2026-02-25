
#######################################################
# *** LOC Constants***
#######################################################

interval_size = 100
min_LOC = 50
max_LOC = 200

llm_iterations = 5

create_graphs = False


#######################################################
# *** Fixed Filenames and file extensions we need *** #
#######################################################
DATASET_JSON = "dataset.json"

# filename prefixes
original = 'original_'
filtered = 'filtered_'
generated = 'generated_'

# since those will be both CSV and MD, we'll just define the base filenames here without file ending
SUMMARY_COUNTS = "Summary_Counts"
ABSOLUTE_VALUES = "Absolute_Values"
COMPARISON_REPORT = "Comparison_Data"
GRAPH_REPORT = "Graph_Report"
CHANGE_TYPE_REPORT = "Change_Types_Report"

#######################################################
# *** DANGER ZONE*** #
#######################################################
language = "java"
similarity_threshold = 0.6 # this has been empirically evaluated to be a good threshold for our purposes so far
