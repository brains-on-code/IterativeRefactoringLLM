package com.invirgance.convirgance.jdbc.sql;

import com.invirgance.convirgance.jdbc.schema.DatabaseSchemaLayout;

public class BooleanAndStatement<P extends WhereStatement> extends WhereStatement implements ComparisonStatement {

    public BooleanAndStatement(DatabaseSchemaLayout layout) {
        super(layout);
    }

    BooleanAndStatement(DatabaseSchemaLayout layout, P parent) {
        super(layout, parent);
    }

    @Override
    public SQLStatement done() {
        return ((WhereStatement) getParent()).done();
    }

    @Override
    public WhereStatement end() {
        return (WhereStatement) getParent();
    }

    @Override
    public WhereStatement where() {
        WhereStatement parent = (WhereStatement) getParent();
        return parent == null ? this : parent.where();
    }

    @Override
    public SQLRenderer render(SQLRenderer renderer) {
        boolean hasWrittenClause = false;

        for (ComparisonStatement statement : getClauses()) {
            if (!hasWrittenClause) {
                renderer.openParenthesis();
                hasWrittenClause = true;
            } else {
                renderer.keyword(Keyword.AND);
            }

            renderer.statement(statement);
        }

        if (hasWrittenClause) {
            renderer.closeParenthesis();
        }

        return renderer;
    }
}