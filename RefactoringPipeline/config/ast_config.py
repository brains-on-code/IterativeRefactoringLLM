import javalang

from config.utils import camel_to_snake, snake_to_camel

################################################################################################
# *** Section 7: Code Wrapping and AST Parsing Properties ***
################################################################################################

# We need to wrap the code lines from the diff, since they are often syntactically incomplete.
# (We do basic syntax completion first, like closing brackets.
# But javalang can not parse single statements, they have to be wrapped in proper classes/methods.

class_decl = "class_declaration"  # --> Basic syntax completion is enough (result: mostly an empty class)
declaration = "declaration"  # --> Wrap it in a class (but NOT inside another method)
incomplete_else = "incomplete_else"  # --> Wrap it in a class and method + if (true) {}
incomplete_switch_case = "incomplete_switch_case"
lonely_break = "break_statement"  # --> Wrap it in a class and method + inside while loop"
single_statement = "single_statement"  # --> wrap in class and method
# Subclass of single_statements
return_statement = "return_statement"

probably_comment = "probably_comment"
probably_string_fragment = "probably_string_fragment"


dummy_class = "class Dummy"
dummy_method = "void dummy()"
dummy_condition = "true"
dummy_if = f"if ({dummy_condition})"
dummy_while = f"while ({dummy_condition})"
dummy_switch = "switch(0)"
dummy_string = "String dummy"

# --- Node Groups ---

declaration_nodes = (
    javalang.tree.ClassDeclaration,
    javalang.tree.InterfaceDeclaration,
    javalang.tree.EnumDeclaration,
    javalang.tree.MethodDeclaration,
    javalang.tree.ConstructorDeclaration,
    javalang.tree.FieldDeclaration,
    javalang.tree.VariableDeclarator,
    javalang.tree.LocalVariableDeclaration,
    javalang.tree.FormalParameter
)

declaration_node_names = [camel_to_snake(cls.__name__) for cls in declaration_nodes]

modifiers = "Modifiers"

control_flow_nodes = (
    javalang.tree.IfStatement,
    javalang.tree.ForStatement,
    javalang.tree.WhileStatement,
    javalang.tree.DoStatement,
    javalang.tree.SwitchStatement,
    javalang.tree.SwitchStatementCase,
    javalang.tree.BreakStatement,
    javalang.tree.ContinueStatement,
    javalang.tree.ReturnStatement,
    javalang.tree.ThrowStatement,
    javalang.tree.TryStatement,
    javalang.tree.SynchronizedStatement,
)


control_node_names = [incomplete_else, lonely_break] + [camel_to_snake(cls.__name__) for cls in control_flow_nodes]

expression_nodes = (
    javalang.tree.MethodInvocation,
    javalang.tree.SuperMethodInvocation,
    javalang.tree.ClassCreator,
    javalang.tree.Assignment,
    javalang.tree.BinaryOperation,
    javalang.tree.TernaryExpression,
    javalang.tree.Cast,
    javalang.tree.MemberReference,
    javalang.tree.ArraySelector,
    javalang.tree.ArrayCreator,
    javalang.tree.StatementExpression,
    javalang.tree.This,
    javalang.tree.ExplicitConstructorInvocation,
)

expression_node_names = [camel_to_snake(cls.__name__) for cls in expression_nodes]

literal_nodes = (
    javalang.tree.Literal,
)

literal_tokens = ["literal", "true", "false"]

# TODO: Werden aktuell nicht als Gruppe angesprochen und sind wsl auch nicht vollständig
other_nodes = (
    javalang.tree.Annotation,
    javalang.tree.BlockStatement,
    javalang.tree.ReferenceType,
    javalang.tree.BasicType,
)

java_operators = [
    # Arithmetic
    "+", "-", "*", "/", "%",

    # Unary
    "++", "--", "+", "-", "!", "~",

    # Assignment
    "=", "+=", "-=", "*=", "/=", "%=", "&=", "|=", "^=", "<<=", ">>=", ">>>=",

    # Relational
    "==", "!=", ">", "<", ">=", "<=",

    # Logical
    "&&", "||",

    # Bitwise
    "&", "|", "^",

    # Shift
    "<<", ">>", ">>>",

    # Ternary
    "?", ":",

    # Other special
    "->",    # lambda
    "::"     # method reference
]


def get_node_category(name):
    if name in declaration_node_names:
        return "DeclarationNode"
    elif name in control_node_names:
        return "ControlNode"
    elif name in java_operators:
        return "OperatorNode"
    elif name in literal_tokens or "literal" in name:
        return "LiteralNode"
    elif name in expression_node_names or "expression" in name:
        return "ExpressionNode"
    elif "access" in name:
        return "AccessNode"
    else:
        return snake_to_camel(name)
