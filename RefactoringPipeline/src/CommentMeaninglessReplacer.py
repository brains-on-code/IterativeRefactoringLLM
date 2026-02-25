import re
import random
from wordfreq import top_n_list

# ---------------- CONFIG ----------------
RANDOM_SEED = 42
LANGUAGE = "en"
MAX_WORDS = 5000   # size of dictionary pool
LENGTH_TOLERANCE = 4
PRESERVE_CAPITALIZATION = False
# ----------------------------------------

random.seed(RANDOM_SEED)

# Load a real English dictionary
DICTIONARY = top_n_list(LANGUAGE, MAX_WORDS)

def dictionary_word(target_len):
    candidates = [
        w for w in DICTIONARY
        if abs(len(w) - target_len) <= LENGTH_TOLERANCE
    ]

    if candidates:
        return random.choice(candidates)

    # Very rare fallback
    return ''.join(random.choice("abcdefghijklmnopqrstuvwxyz") for _ in range(target_len))

def obfuscate_comment_text(text):
    def replace_word(match):
        word = match.group(0)
        replacement = dictionary_word(len(word))

        # possibly preserve capitalization style.
        if PRESERVE_CAPITALIZATION:
            if word.isupper():
                return replacement.upper()
            if word[0].isupper():
                return replacement.capitalize()

        return replacement

    return re.sub(r'[A-Za-z]+', replace_word, text)

def obfuscate_java_comments(java_code):
    comment_pattern = re.compile(
        r'//.*?$|/\*.*?\*/',
        re.DOTALL | re.MULTILINE
    )

    def replace_comment(match):
        return obfuscate_comment_text(match.group(0))

    return comment_pattern.sub(replace_comment, java_code)
