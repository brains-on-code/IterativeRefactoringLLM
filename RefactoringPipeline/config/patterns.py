import re

diffheader_pattern = r'@@ -(\d+)(?:,(\d+))? \+(\d+)(?:,(\d+))? @@'
_filename_pattern = r'^(\w+)_([Kk][Ff]\d+)_([Vv]\d+)_([A-Za-z0-9]+)\.java$'  # \w = letters, ciphers, and '_'

# for performance reasons, file pattern get compiled once here and then used throughout the project
FILENAME_REGEX = re.compile(_filename_pattern)


# Not used right now but could become handy
algorithm_name_pattern = r'^(\w+)'
kf_name_pattern = r'([Kk][Ff]\d+)'
version_pattern = r'([Vv]\d+)'
prompt_key_pattern = r'([Pp\d+])'