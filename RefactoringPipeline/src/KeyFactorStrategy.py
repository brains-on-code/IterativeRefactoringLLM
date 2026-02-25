import re
from abc import ABC, abstractmethod
import javalang

import CommentMeaninglessReplacer
from config.utils import setup_logger

# Setup logging
logger, handler = setup_logger("KeyFactorStrategy")

class KeyFactorStrategy(ABC):

    @abstractmethod
    def get_label(self) -> str:
        pass

    @abstractmethod
    def apply(self, code: str) -> str:
        """Applies the key factor modification to the code."""
        pass

    @abstractmethod
    def get_kf_specific_prompt(self) -> dict:
        """Return the prompt for the LLM with respect to the specific KeyFactos."""
        pass

class KF0_Baseline(KeyFactorStrategy):

    def get_label(self) -> str:
        return "KF0"

    def get_kf_specific_prompt(self) -> dict:
        return {'id': 'pKF0',
               'content': "Refactor this code for improved readability:\n"}

    def apply(self, code: str) -> str:
        return code


class KF1_NameRemover(KeyFactorStrategy):

    def get_label(self):
        return "KF1"

    def get_kf_specific_prompt(self) -> dict:
        return {'id' : 'pKF1',
                'content': "Refactor this code for improved readability, especially with respect to identifier naming.\n"
                }

    def apply(self, code: str) -> str:

        try:
            tree = javalang.parse.parse(code)
        except javalang.parser.JavaSyntaxError as e:
            logger.warning(f"[SKIP] JavaSyntaxError during KF1 parsing/variant creation: {e}")
            return "JavaSyntaxError"

        except Exception as e:
            logger.error(f"[SKIP] Exception during KF1 parsing/variant creation – {type(e).__name__}: {e}")
            return "JavaSyntaxError"

        class_counter = 1
        method_counter = 1
        variable_counter = 1

        name_map = {}

        # Part I: Find all identifiers
        # 1. Klassen
        for path, node in tree.filter(javalang.tree.ClassDeclaration):
            original = node.name
            new_name = f'Class{class_counter}'
            name_map[original] = new_name
            logger.debug(f"Renamed class '{original}' → '{new_name}'")
            class_counter += 1

        # 2. Methoden
        for path, node in tree.filter(javalang.tree.MethodDeclaration):
            original = node.name
            new_name = f'method{method_counter}'
            name_map[original] = new_name
            logger.debug(f"Renamed method '{original}' → '{new_name}'")
            method_counter += 1

        # 3. Variablen: Parameter
        for _, node in tree.filter(javalang.tree.FormalParameter):
            original = node.name
            if original not in name_map:
                new_name = f'var{variable_counter}'
                name_map[original] = new_name
                logger.debug(f"Renamed variable (parameter) '{original}' → '{new_name}'")
                variable_counter += 1

        # 4. Variablen: lokale Deklarationen und Felder
        for _, node in tree.filter(javalang.tree.VariableDeclarator):
            original = node.name
            if original not in name_map:
                new_name = f'var{variable_counter}'
                name_map[original] = new_name
                logger.debug(f"Renamed variable '{original}' → '{new_name}'")
                variable_counter += 1

        # Part II: Replace all identifiers with a meaningless replacement
        # Schritt 1: Alle String-Literale temporär ersetzen
        string_literals = []

        def string_replacer(match):
            string_literals.append(match.group(0))
            return f"__STRING_LITERAL_{len(string_literals) - 1}__"

        code_without_strings = re.sub(r'"(?:\\.|[^"\\])*"', string_replacer, code)

        # Schritt 2: Nur im code_without_strings Variablen ersetzen
        # Ersetze nur ganze Wörter im Code!
        # Vorsicht: Reihenfolge beachten, längere Namen zuerst ersetzen, um Teiltreffer zu vermeiden
        for original in sorted(name_map, key=len, reverse=True):
            replacement = name_map[original]
            code_without_strings = re.sub(rf'\b{re.escape(original)}\b', replacement, code_without_strings)

        # Schritt 3: Platzhalter wieder durch die ursprünglichen Strings ersetzen
        def restore_strings(match):
            index = int(match.group(1))
            return string_literals[index]

        code_with_renamed_identifiers = re.sub(r'__STRING_LITERAL_(\d+)__', restore_strings, code_without_strings)

        # Part 3: Remove semantic information from comments by replacing them with a similar structure
        code_final = CommentMeaninglessReplacer.obfuscate_java_comments(code_with_renamed_identifiers)

        return code_final


class KF2_CommentRemover(KeyFactorStrategy):

    def get_label(self) -> str:
        return "KF2"

    def get_kf_specific_prompt(self) -> dict:
        return {'id': 'pKF2',
                'content': "Refactor this code for improved readability, especially with respect to comments.\n"
                }

    def apply(self, code: str) -> str:
        # Remove multiline comments (incl. JavaDoc)
        code = re.sub(r'/\*.*?\*/', '', code, flags=re.DOTALL)

        cleaned_lines = []
        for line in code.split('\n'):
            original = line  # to recognize empty lines correctly
            stripped = line.strip()

            # Remove entire comment lines (//...)
            if stripped.startswith("//"):
                continue

            # Remove inline comments with consideration of string literals
            new_line = ''
            in_string = False
            i = 0
            while i < len(line):
                if line[i] == '"' and (i == 0 or line[i - 1] != '\\'):
                    in_string = not in_string
                if not in_string and line[i:i + 2] == '//':
                    break
                new_line += line[i]
                i += 1

            if new_line.strip():
                cleaned_lines.append(new_line.rstrip())
            else:
                # Only keep if the original line was *really* empty
                if original.strip() == '':
                    cleaned_lines.append('')

        return '\n'.join(cleaned_lines)
