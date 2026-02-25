import difflib
import re
from collections import Counter
from typing import Dict

import javalang
import javalang.tree as jtree
import code_diff
from javalang.parser import JavaSyntaxError
from javalang.tokenizer import LexerError

import config.ast_config
import config.constants
import config.fields
import config.patterns
import config.utils

# Setup logging
logging, handler = config.utils.setup_logger("DiffParserUtils")
#errorlog = config.setup_logger("Errorlog")


def parse_header(header_line: str) -> dict:
    """
    Parses a @@ header line and extracts start1, count1, start2, and count2.
    """

    match = re.match(config.patterns.diffheader_pattern, header_line)

    if not match:
        raise ValueError(f"Invalid @@ header format: {header_line}")

    # Extract the values, default to 1 if count is missing
    start1 = int(match.group(1))
    count1 = int(match.group(2)) if match.group(2) else 1
    start2 = int(match.group(3))
    count2 = int(match.group(4)) if match.group(4) else 1

    return {
        "start1": start1,
        "count1": count1,
        "start2": start2,
        "count2": count2
    }

def parse_java_code(code):
    """
    Parses a Java code line by wrapping it properly into a parsable Java code structure.

    :param code: line that should be parsed/wrapped
    :return: tuple of (tree (if parsing was successful), wrap_type)
    """

    # TODO: den wrap_type brauchen wir nicht mehr, wenn wir jetzt doch nichts rausfiltern.
    # TODO: können ihn aber auch stehen lassen, mal gucken wofür er noch nützlich sein könnte

    code = code.strip()  # remove all trailing whitespaces

    code = strip_comments_from_line(code)  # remove inline comments

    if not code:
        # If code string is empty, i.e. it was a comment
        return None, config.ast_config.probably_comment

    else:
        logging.info(f"Parsing code: {code}")

        wrapped_code, wrap_type = wrap_code(code)

        try:
            tree = javalang.parse.parse(wrapped_code)
            return tree, wrap_type

        except JavaSyntaxError as e:
            logging.warning(f"JavaSyntaxError during parsing: {str(e)}")
            #errorlog.warning(f"JavaSyntaxError during parsing: {str(e)} in {self.filename}")
            return None, wrap_type

        except LexerError as e:
            logging.warning(f"LexerError during tokenizing: {str(e)}")
            #errorlog.warning(f"LexerError during tokenizing: {str(e)} in {self.filename}")
            return None, wrap_type

        except Exception as e:
            logging.warning(f"Some other error during parsing java code: {e}")
            return None, wrap_type


def wrap_code(code):

    # Basic syntax completion (like closing brakets etc.)
    code = basic_syntax_completion(code)

    # Case 1: If it's a full class, parse directly
    if "class" in code:
        logging.info("=> is class declaration --> leave as is")
        wrapped_code = code
        wrap_type = config.ast_config.class_decl

    # Case 2: If it contains a method/field/constructor declaration, wrap it in a class (but NOT inside another method)
    elif is_probably_constructor_declaration(code) or is_probably_method_declaration(
            code) or is_probably_field_declaration(code):
        logging.info(
            "=> is probably constructor, method, or field declaration --> Wrap it in a class (but NOT inside another method)")
        wrapped_code = f"{config.ast_config.dummy_class} {{ {code} }}"
        wrap_type = config.ast_config.declaration

    # Case 3:
    elif is_incomplete_else(code):
        logging.info("=> is incomplete else --> Wrap it in a class and method + if (true) {}}")
        else_body = "" if code.endswith("}") or code.endswith(";") else "{}"
        wrapped_code = f"{config.ast_config.dummy_class} {{ {config.ast_config.dummy_method} {{ {config.ast_config.dummy_if} {{}}  {code} {else_body} }} }}"
        wrap_type = config.ast_config.incomplete_else

    elif is_incomplete_switch_case(code):
        logging.info("=> is incomplete case --> Wrap it in a class and method + switch (0) {**}}")

        # Arrow-Syntax (case X -> ...; oder default -> ...;) -> unverändert übernehmen
        if "->" in code:
            case_body = code

        # Klassisch: Nur Label ohne Body -> Dummy-Break anhängen
        elif re.match(r"^\}?\s*(case\s+.+:|default\s*:)\s*$", code.strip()):
            # reines Label ohne Body -> Dummy-Break einfügen
            case_body = f"{code} break;"
        else:
            # bereits ein Statement vorhanden
            case_body = code

        wrapped_code = f"{config.ast_config.dummy_class} {{ {config.ast_config.dummy_method} {{ {config.ast_config.dummy_switch} {{ {case_body} }} }} }}"
        wrap_type = config.ast_config.incomplete_switch_case

    # Case 4:
    elif "break;" in code:
        logging.info("=> is break statement --> Wrap it in a class and method + inside while loop")
        wrapped_code = f"{config.ast_config.dummy_class} {{ {config.ast_config.dummy_method} {{ {config.ast_config.dummy_while} {code} }} }}"
        wrap_type = config.ast_config.lonely_break

    # Last case: If it's just a single statement, wrap it in a method inside a dummy class
    elif code.endswith(";") or code.endswith("}"):
        logging.info("=> is probably single statement --> wrap statement inside class and method")
        wrapped_code = f"{config.ast_config.dummy_class} {{ {config.ast_config.dummy_method} {{ {code} }} }}"
        wrap_type = config.ast_config.return_statement if code.startswith("return") else config.ast_config.single_statement

    else:
        logging.warning("=> No case was detected. Treat it as String Fragment.")
        wrapped_code = f"{config.ast_config.dummy_class} {{ {config.ast_config.dummy_method} {{ {config.ast_config.dummy_string} = {code} ; }} }}"
        wrap_type = config.ast_config.probably_string_fragment

    logging.info(f"Final wrapped Code: {wrapped_code} (wrap type: {wrap_type})")
    return wrapped_code, wrap_type


def parse_into_ast(line):

    # Wraps code if necessary
    tree1, wrap_type1 = parse_java_code(line)

    if not tree1:
        raise ValueError("No tree could be generated.")

    else:
        ast1 = extract_ast_structure(tree1, wrap_type1)
        return ast1


def tokenize_code_line(line: str) -> list:
    """
    Tokenizes a Java/C-like code line into meaningful tokens.
    Supports:
        - Identifiers
        - Numeric literals (integers and floats)
        - Common multi-character operators (==, !=, <=, >=, &&, ||, ++, --)
        - Single-character symbols
    """
    return re.findall(
        r"""
        [A-Za-z_]\w*                    | # identifiers
        \d+\.\d+|\d+                    | # numbers (float or int)
        \+\+|--|==|!=|<=|>=|&&|\|\|     | # multi-char operators
        [{}();=<>!+\-*/]                 # single-char symbols
        """, line, re.VERBOSE
    )


def filter_irrelevant_tokens(tokens: list) -> list:
    """
    Filters some java keywords from token sequence, since sequence matching is character based
    and keywords like modifiers shouldn't "weigh" during sequence matching.

    Example: "int x = 0;" and "private final int x = 0;" should be matched, but character-based matching
             takes into account the full length of the keywords 'private final' and therefore the ratio would not be
             too good.
    """

    java_keywords = {"private", "protected", "public", "final", "static", "abstract", "transient", "volatile",
                     "synchronized"}

    return [tok for tok in tokens if tok not in java_keywords]


def basic_syntax_completion(code: str) -> str:

    """ Basic (simplified) syntax completion """

    #logging.info(f"Basic syntax completion for: {code}")

    if code == "":
        return code

    if code.startswith("{") and not code.endswith("}"):
        #logging.info("code.startswith('{') and not code.endswith('}') --> stripped '{'")
        code = code[1:]  # += "};"

    if code.startswith("}"):
        #logging.info("code.startswith('}')  --> stripped '}'")
        code = code[
               1:]  # strip away opening bracket, since our further syntax completion will introduce complete dummy ifs or loops with {}

    if code.startswith("?"):
        code = "1 " + code # i.e. "1 ? [whatever the code says then]"
        if ":" not in code:
            code += " : 0;" # i.e. complete the ternary expression

    if code.startswith(":"):
        # it might be part of a ternary expression, but we cannot really keep track of the context lines at this point so it's easier to ignore for now
        code = code[1:]

    if code.startswith("(") and not code.endswith(")"):
        #logging.info("code.startswith('(') and not code.endswith(')') --> added ')'")
        code += ")"

    if code.endswith(")") and contains_control_keywords(code):
        #logging.info("code.endwith(')' --> added {}")
        code += "{}"  # this is most likely an if or loop statment

    elif code.endswith(("&&", "||")):
        code = code[:-2].strip() + ";"

    elif code.endswith("{"):
        #logging.info("code.endswith('{') --> added '}'")
        code += "}"

    # After all the open brackets were closed etc. the last case could be: single statement without semicolon
    # (Note: There might be cases left that would have to be handled beforehand.)
    # TODO: that also means that "invalid code" or some string that is not code would be finished with ";" here...
    # elif not code.endswith((";", "}", ":")) : # TODO: 07.09. wir testen mal was passiert, wenn wir den Bums hier weglassen. Weil jetzt treated wir das ja als String Fragment.
    #     code += ";" #just in case

    #logging.info(f"--> code after basic syntax completion: {code.strip()}")

    return code.strip()


def contains_control_keywords(code_line: str) -> bool:
    """ Returns True if the code line (or AST type) contains any of the defined control keywords."""

    control_keywords = ["if", "for", "while", "switch", "catch", "else", "do", "try", "synchronized", "return",
                        "throw", "else", "catch"]
    result = (any(re.match(rf"\b{k}\b", code_line) for k in control_keywords)
              or any(re.match(rf"\b{k}", code_line) for k in config.ast_config.control_node_names))

    return result

def contains_literal_tokens(ast_type: str) -> bool:
    """ Returns True if the code line (or AST type) contains any of the defined literal tokens."""

    return any(token in ast_type for token in config.ast_config.literal_tokens)

def contains_operator(ast_type: str) -> bool:
    """ Returns True if the code line (or AST type) contains any of the defined operators."""

    return ast_type in config.ast_config.java_operators or "operator" in ast_type

def is_probably_constructor_declaration(code_line: str) -> bool:
    """ Recognize constructors such as `MyClass(...) {}`."""

    code_line = code_line.strip()

    # Early exit if obvious keywords indicate it's not a declaration
    if contains_control_keywords(code_line):
        return False

    pattern = re.compile(r"""
        ^\s*                                 # optional: any number of spaces
        (?:public|private|protected)?\s*     # optional: any modifier (word characters + spaces)
        ([A-Z][A-Za-z0-9_]*)\s*              # constructor name (capital letters at the beginning)
        \([^()]*\)\s*                        # parameter list (one line, no nested brackets)
        (?:\{\s*})?                          # optional: {} (empty body)
        """, re.VERBOSE)

    return bool(pattern.match(code_line))


def is_probably_method_declaration(code_line: str) -> bool:
    """Recognize pattern that is probably method declaration."""

    code_line = code_line.strip()

    # Early exit if obvious keywords indicate it's not a declaration
    if contains_control_keywords(code_line):
        return False

    method_decl_pattern = re.compile(r"""
        ^\s*
        (public|protected|private)?\s*                # optional: visibility
        (static\s+)?                                  # optional: static
        (<[^>]+>\s+)?                                 # optional: generics
        ([\w\.\<\>\[\],\s]+)\s+                       # return type (e.g. T[], List<T>, Map<K,V>)
        [a-zA-Z_][\w]*\s*                             # method name
        \(\s*[^\)]*\s*\)\s*                           # parameter list
        (throws\s+[^{]+)?                             # optional: throws-clause
        \{?                                           # optional: opening braket
        """, re.VERBOSE)

    return bool(method_decl_pattern.match(code_line))


def is_probably_field_declaration(code_line: str) -> bool:
    """Recognize pattern that is probably field declaration."""

    code_line = code_line.strip()

    # Early exit if obvious keywords indicate it's not a declaration
    if contains_control_keywords(code_line):
        return False

    modifiers = r"(public|protected|private|static|final|transient|volatile|synchronized)"
    modifier_pattern = fr"(?:{modifiers}\s+)*"

    type_pattern = r"[\w<>\[\],\s]+\s+"  # e.g., int, List<T>, Map<K,V>[], etc.
    name_pattern = r"[A-Za-z_][\w]*"  # variable name
    init_pattern = r"(=\s*[^;]+)?"  # optional initializer

    field_decl_pattern = re.compile(fr"""
        ^\s*
        {modifier_pattern}                    # optional: modifiers
        {type_pattern}                        # type
        {name_pattern}                        # name
        \s*{init_pattern}                     # optional: initializer
        ;\s*$                                 # must end with semicolon
        """, re.VERBOSE)

    return bool(field_decl_pattern.match(code_line))


def is_incomplete_else(code_line):
    """
    Recognize lines that are probably `else` or `else if` statements in Java.

    The regex matches:
    - An optional closing brace `}` (from the end of the previous block)
    - Optional whitespace
    - The keyword `else` (as a whole word)
    - Optionally followed by `if` (i.e., an `else if` construct)
    - Optional whitespace
    - An optional opening parenthesis `(` and closing parenthesis `)`
      around any characters (i.e., the condition in `else if`)
    - Optional opening brace `{` (for the start of the new block)

    Regex breakdown:
        ^           : Start of line
        \}?         : Optional closing brace
        \s*         : Optional whitespace
        \belse\b    : The word `else` (word boundary to avoid partial matches)
        (\s+if\b)?  : Optional `if` following `else`, with at least one space before it
        \s*         : Optional whitespace
        \(?         : Optional opening parenthesis `(`
        .*          : Any characters (condition or none)
        \)?         : Optional closing parenthesis `)`
        \s*         : Optional whitespace
        \{?         : Optional opening brace `{`

    Returns:
        True if the line matches a typical Java `else` or `else if` statement structure.
    """
    code_line = code_line.strip()
    return re.match(r"^\}?\s*\belse\b(\s+if\b)?\s*\(?.*\)?\s*\{?", code_line) is not None


def is_incomplete_switch_case(code_line):
    """
    Recognize lines that are probably Java `case` or `default` labels
    (with or without a following body).

    The regex matches:
    - An optional closing brace `}` (from the end of the previous block)
    - Optional whitespace
    - Either:
        - The keyword `case` followed by some expression and a colon `:`
        - Or the keyword `default` followed by a colon `:`
    - Optionally followed by any characters (body statements)

    Regex breakdown:
        ^           : Start of line
        \}?         : Optional closing brace
        \s*         : Optional whitespace
        (           : Start group
          case\s+.+:   : `case` + expression + colon
          |            : OR
          default\s*:  : `default` + colon
        )           : End group
        .*          : Optional trailing code (the case body)

    Returns:
        True if the line matches a `case` or `default` statement.
    """
    code_line = code_line.strip()
    return re.match(r"^\}?\s*(case\s+.+:|case\s+.+->.+;|default\s*:|default\s*->.+;).*", code_line) is not None



def is_if_statement(code_line: str) -> bool:
    """
    Recognize lines that are probably `if` statements in Java.

    The regex matches:
    - An optional closing brace `}` at the beginning (e.g. from a previous block)
    - Optional whitespace
    - The keyword `if`
    - A condition enclosed in parentheses `(...)`, not containing `)` inside
    - Optional whitespace
    - An optional opening brace `{`
    - Nothing else after that (end of line)

    Regex breakdown:
        ^               : Start of line
        \}?             : Optional closing brace
        \s*             : Optional whitespace
        if              : The keyword `if`
        \s*             : Optional whitespace
        \(              : Opening parenthesis `(` for condition
        [^)]*           : Any characters except closing parenthesis `)` (i.e., the condition)
        \)              : Closing parenthesis `)`
        \s*             : Optional whitespace
        (\{)?           : Optional opening brace `{`
        \s*$            : Optional whitespace until end of line

    This pattern is designed to match clean one-line `if` statements like:
        "} if (x > 0) {"
        "if (ready)"
        "if (a == b) {"
        "} if (count < 10)"

    Returns:
        True if the line looks like an `if` statement header (with or without `{`).
    """

    code_line = code_line.strip()
    return re.match(r"^\}?\s*if\s*\([^)]*\)\s*(\{)?\s*$", code_line) is not None

def is_comment_line(line : str) -> bool:
    """Determines if whole line is a comment."""
    if line.startswith('*') or line.startswith("/"):
        return True
    else:
        return False


def has_inline_comment_change(line1, line2):
    """Determines inline comment changes."""

    comment_part1 = extract_comments_from_line(line1)
    comment_part2 = extract_comments_from_line(line2)

    return comment_part1!=comment_part2


def strip_comments_from_line(line: str) -> str:
    """ Removes all comments from a line. """

    # If whole line is a comment:
    if is_comment_line(line):
        return ''

    else:
        # Remove inline block comments
        line = re.sub(r'/\*.*?\*/', '', line)

        # Remove single-line comments
        line = re.sub(r'//.*', '', line)

        return line.strip()


def extract_comments_from_line(line: str) -> str:
    """ Removes all code and returns only comment part of a line. """

    if line.startswith('*') or line.startswith('/'):
        return line.strip()

    else:

        # First, extract all block comments
        block_comments = re.findall(r'/\*.*?\*/', line)

        # Then extract everything after `//` as a line comment
        line_comment = re.search(r'//.*', line)
        comments = block_comments

        if line_comment:
            comments.append(line_comment.group())

        return ' '.join(comments).strip()


def extract_ast_structure(tree: jtree, wrap_type: str):
    """Extracts a simplified AST structure."""

    #TODO: eig müsste noch wrap_type eingebaut werden...
    #Note: ggf weichen die Node Names aus der code_diff library ab, aber grundsätzlich sind die CamelCase

    nodes = []

    for _, node in tree:

        # Declarations
        if isinstance(node, config.ast_config.declaration_nodes):

            if isinstance(node, jtree.FieldDeclaration):
                # Special case: the "Field" declaration node doesn't have the attribute "name", but a list of variable declarators
                for _ in node.declarators:
                    nodes.append((config.ast_config.modifiers, getattr(node, "modifiers", None)))

            elif isinstance(node, jtree.FormalParameter):
                nodes.append(("formal_parameter", getattr(node, "name", None)))
                nodes.append((config.ast_config.modifiers, getattr(node, "modifiers", None)))
            else:
                nodes.append((config.utils.camel_to_snake(type(node).__name__), getattr(node, "name", None)))

        # Control Flow
        elif isinstance(node, config.ast_config.control_flow_nodes):
            nodes.append((config.utils.camel_to_snake(type(node).__name__), None))

        # Expressions
        elif isinstance(node, jtree.MethodInvocation):
            nodes.append(("method_invocation", node.member))
            nodes.append(("method_invocation_qualifier", getattr(node, "qualifier", None)))
        elif isinstance(node, jtree.SuperMethodInvocation):
            nodes.append(("super_method_invocation_qualifier", getattr(node, "qualifier", None)))
            nodes.append(("super_method_invocation", node.member))
        elif isinstance(node, jtree.ClassCreator):
            nodes.append(("class_creator", getattr(node.type, 'name', 'TYPE_UNKNOWN')))
        elif isinstance(node, jtree.Assignment):
            nodes.append(("assignment_expression", node.type)) # assignments only exist if variable was declared before separately
        elif isinstance(node, jtree.BinaryOperation):
            nodes.append(("binary_operator", node.operator))
        elif isinstance(node, jtree.TernaryExpression):
            nodes.append(("ternary_expression", None))
        elif isinstance(node, jtree.Cast):
            nodes.append(("cast_expression", getattr(node.type, 'name', None)))
        elif isinstance(node, jtree.MemberReference):
            nodes.append(("MemberRef", node.member))
            nodes.append(("MemberRefQualifier", getattr(node, "qualifier", None)))
        elif isinstance(node, jtree.ArraySelector):
            nodes.append(("array_access", None))
        elif isinstance(node, jtree.ArrayCreator):
            nodes.append(("ArrayCreator", None))
        elif isinstance(node, jtree.StatementExpression):
            nodes.append(("statement_expression", None))
        elif isinstance(node, jtree.This):
            nodes.append(("field_access", None))
        elif isinstance(node, jtree.ExplicitConstructorInvocation):
            nodes.append(("ConstructorCall", None))

        # Literals
        elif isinstance(node, jtree.Literal):
            nodes.append(("literal", node.value))

        # Other nodes
        else:

            if (isinstance(node, jtree.BasicType)):
                nodes.append(("BasicType", node.name))
            elif (isinstance(node, jtree.ReferenceType)):
                nodes.append(("ReferenceType", node.name))

            else:
                # Optional: skip all unknown nodes or collect them?
                nodes.append((type(node).__name__, "NOT IMPLEMENTED"))

    return nodes


def compare_ast_structure(line1, line2):
    """Compares two Java code lines using AST structure matching."""

    try:
        ast1 = parse_into_ast(line1)
        ast2 = parse_into_ast(line2)

        # Compare the two AST structures by node types only (ignoring names, literals, etc.).
        structure1 = [node_type for node_type, _ in ast1]
        structure2 = [node_type for node_type, _ in ast2]
        matcher = difflib.SequenceMatcher(None, structure1, structure2)
        ast_similarity = matcher.ratio()

        return ast_similarity

    except ValueError:
        return -1


def validate_ast_types(code_part1, code_part2):

    # Generate AST diff
    # Note: tested basic syntax completion first, but it only makes things worse.
    # This code_diff library assigns more fitting AST types w/o the basic syntax completion.

    diff = code_diff.difference(code_part1, code_part2, lang=config.constants.language)

    source_ast = diff.source_ast
    target_ast = diff.target_ast

    if source_ast.type != 'ERROR':
        source_ast_type = source_ast.type
    else:
        _, source_ast_type = wrap_code(code_part1)

    if target_ast.type != 'ERROR':
        target_ast_type = target_ast.type
    else:
        _, target_ast_type = wrap_code(code_part2)


    return source_ast_type, target_ast_type


def classify_line(line):

    if not line.strip():
        return config.fields.empty
    elif line.startswith("/") or line.startswith("*"):
        return config.fields.comment
    else:
        return config.fields.code

def classify_ast_changes(line1, line2) -> Dict: #old_nodes: list[tuple], new_nodes: list[tuple]) -> str:
    """
    Classifies the change type between two code lines by comparing their AST structures.

    Classification follows a two-stage approach:
    1. High-level: Compare root AST node types (e.g., method invocation vs. variable access).
    2. Fine-grained: If root types match, check for Rename/Modify/Replace/SyntaxOnly.
    """

    # TODO: mit den "subtypes" machen wir aktuell nichts

    # Stage 0: Handle comment changes separately
    if is_comment_line(line1) or is_comment_line(line2):
        return {
            config.fields.source_ast_type: config.fields.block_comment,
            config.fields.target_ast_type: config.fields.block_comment,
            config.fields.classification: config.fields.comment_change
        }

    try:

        source_ast_type, target_ast_type = validate_ast_types(line1, line2)

        # Stage 1: Root AST node type differs → structural change
        if source_ast_type != target_ast_type:
            subtypes = classify_structural_ast_changes(source_ast_type, target_ast_type)

        else:

            # Stage 2: Same root AST node type → fine-grained change analysis
            old_nodes = parse_into_ast(line1)
            new_nodes = parse_into_ast(line2)

            # Stage 2.1: Identical Partical AST?
            if old_nodes == new_nodes:

                if has_inline_comment_change(line1, line2):
                    # --> either only inline comment change
                    return {
                        config.fields.source_ast_type: config.fields.inline_comment,
                        config.fields.target_ast_type: config.fields.inline_comment,
                        config.fields.classification: config.fields.comment_change
                    }

                else:
                    # --> or some part of AST extraction is not implemented yet in our extract_ast-method
                    logging.info("Extracted ASTs are identical, but maybe not complete -> IdenticalPartialAST")
                    subtypes = {config.fields.identical_partial_ast: 1}  # TODO: Das hier ist hauptsächlich zum debuggen bzw. erweitern unserer extract_ast-Methode

            else:
                # Stage 2.2: If nodes are added/deleted --> some structural semantic change
                if len(old_nodes) != len(new_nodes):
                    subtypes = classify_structural_ast_changes(source_ast_type, target_ast_type)  # "DelNodes" if len_old_nodes < len_new_nodes else "InsNodes"

                else:
                    # --> else it is most probably a rename or value change within a node
                    subtypes = classify_node_value_changes(old_nodes, new_nodes)

        # TODO: kann man auch noch leveragen um zu schauen welche change types gerne zusammen vorgenommen werden
        if len(subtypes.keys()) > 1:
            classification = config.fields.mixed_change

        else:
            first_key = next(iter(subtypes))
            classification = first_key

        return {
            config.fields.source_ast_type: source_ast_type,
            config.fields.target_ast_type: target_ast_type,
            config.fields.classification: classification
        }

    except ValueError as e:
        if str(e) == "Source and Target AST are identical.":
            return {
                config.fields.source_ast_type: "",
                config.fields.target_ast_type: "",
                config.fields.classification: config.fields.syntax_only
            }

        else:
            logging.warning("ValueError")
            return {
                config.fields.source_ast_type: "",
                config.fields.target_ast_type: str(e),
                config.fields.classification: config.fields.value_error
            }

def classify_structural_ast_changes(source_ast: str, target_ast: str):
    """
    Classifies a change where the root AST node type has changed.
    Specific categories: see config.py
    """

    # node_type can either be from jtree or from code_diff diff source_ast/target_ast
    # but we build the node names in code_diff snake_case in extract_ast_structure right away
    # (though that might be incomplete by now)

    s_ast = source_ast
    t_ast = target_ast

    subtypes = Counter()

    if "access" in s_ast or "access" in t_ast:
        subtypes[config.fields.access_change] += 1

    if "method_invocation" in s_ast or "method_invocation" in t_ast:
        subtypes[config.fields.call_change] += 1

    # TODO: don't know if we wanna keep this at all
    # if "parenthesized" in s_ast or "parenthesized" in t_ast:
    #     subtypes[config.expression_wrap_change] += 1

    if contains_control_keywords(s_ast) or contains_control_keywords(t_ast):
        subtypes[config.fields.control_change] += 1

    if contains_literal_tokens(s_ast) or contains_literal_tokens(t_ast):
        subtypes[config.fields.literal_change] += 1

    if contains_operator(s_ast) or contains_operator(t_ast):
        subtypes[config.fields.operator_change] += 1

    # Default if no specific category was detected so far
    if len(subtypes.keys()) < 1:
        subtypes[config.fields.other_structural_change] += 1

    return dict(subtypes)


def classify_node_value_changes(old_nodes, new_nodes):

    changes = Counter()

    # NOTE: zip schneidet halt einfach die Knoten ab wenn eine Liste länger ist
    for old_node, new_node in zip(old_nodes, new_nodes):

        if old_node == new_node:
            continue

        old_type, old_val = old_node
        new_type, new_val = new_node

        if old_type == new_type:
            if old_val != new_val and old_val is not None and new_val is not None:
                if old_type == config.ast_config.modifiers:
                    changes[config.fields.access_change] += 1
                else:
                    changes[config.fields.name_change] += 1
                    # Rename hat keine Subtypes, deswegen bleibt dict leer
        else:
            subtypes_result = classify_structural_ast_changes(old_type, new_type)

            # StructuralChange hochzählen
            for k, v in subtypes_result.items():
                changes[k] += v

    #assert (len(changes.keys()) > 0) # TODO: das ist hier nur sinnvoll, wenn wir diese Funktion nur an der Stelle aufrufen, wo es definitiv eine Änderung gibt, die dann auch definitiv detektiert werden soll. Möchte das lieber in Tests sicherstellen, dass die Funktion sich richtig verhält anstatt hier mit nem assert zu arbeiten.

    return dict(changes)


# --- Note: Dieser Teil wird jetzt zur Fertigstellung erstmal auskommentiert, da die code_diff library einen
# --- Nicht-Determnismus Bug hat, der nicht auf die Schnelle zu beheben ist und der mir die Ergebnisse bzgl,
# --- change types invalidiert.


# def _extract_op_type_and_node_types(op_str: str) -> Tuple[str, List[str]]:
#     """
#     Extracts operation type (Insert, Update, ...) and AST node types from a stringified edit_script() entry.
#
#     Handles:
#     - target_node=ASTNode(type=...)
#     - target_node=IN(type=...)
#     - node=ASTNode(type=...)
#     - node=IN(type=...)
#     - node=('node_type', ...)
#     """
#
#     op_type = op_str.split("(", 1)[0]  # e.g., "Insert", "Update", etc.
#     node_types = []
#
#     # Match 'target_node=ASTNode(type=...)' or 'target_node=IN(type=...)'
#     target_matches = re.findall(r'(?:target_node|node)=(?:ASTNode|IN)\(type=([\w_]+)', op_str)
#     node_types.extend(target_matches)
#
#     # Match node=('node_type', 'value')
#     node_matches = re.findall(r"node=\('([\w_]+)'", op_str)
#     node_types.extend(node_matches)
#
#     return op_type, node_types
#
#
# def _detect_change_types(ops: List[str]) -> Set[str]:
#     """
#     Maps stringified edit_script() operations to high-level change categories.
#     """
#     change_types = set()
#
#     for op_str in ops:
#         op_type, node_types = _extract_op_type_and_node_types(op_str)
#
#         for node_type in node_types:
#             category = config_ParserRules.CHANGE_TYPE_RULES.get((op_type, node_type))
#             if category:
#                 change_types.add(category)
#
#     return change_types
#
#
# def classify_change_types(old_line, new_line):
#
#     logging.info("*** Classify Change Types with code_diff ***")
#
#     if old_line.startswith(("-", "+")):
#         old_line = old_line[1:].strip()
#
#     if new_line.startswith(("-", "+")):
#         new_line = new_line[1:].strip()
#
#     logging.info(f"Old_Line -> New Line: {old_line}->{new_line}")
#
#     # If it's an empty line (though empty lines are ignored throughout matching process)
#     # then change would simply be a formatting change / syntax-only change
#     if not old_line or not new_line:
#         logging.info("Empty line(s) --> syntax_only")
#         return {config_ParserRules.syntax_only}
#
#     if is_comment_line(old_line) or is_comment_line(new_line):
#         logging.info("Comment line(s) --> Comment change")
#         return {config_ParserRules.comment_change}
#
#
#     try:
#
#         diff = code_diff.difference(old_line, new_line, lang=config.language)
#         edit_script = diff.edit_script()
#         ops = [str(op) for op in edit_script]
#
#         logging.info(f"\nDiff:\n{diff}\n\n{'\n'.join(ops)}") #ohne Listenklammern und Anführungszeichen
#
#         change_types = _detect_change_types(ops)
#
#         # if contains_control_keywords(old_line) or contains_control_keywords(new_line):
#         #     change_types.add(config.control_change)
#
#         if not change_types:
#             logging.debug("No change types detected.")
#             return {"tba"}
#
#         else:
#             logging.info(f"change_types:\n{change_types}")
#             return change_types
#
#     except ValueError as e:
#         if str(e) == "Source and Target AST are identical.":
#             logging.info(f"change_types: {config_ParserRules.syntax_only}")
#             return {config_ParserRules.syntax_only}
#
#         else:
#             logging.warning("ValueError")
#             return {"tba-ValueError"}



