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
        boolean hasClauses = false;

        for (ComparisonStatement clause : getClauses()) {
            if (!hasClauses) {
                renderer.openParenthesis();
                hasClauses = true;
            } else {
                renderer.keyword(Keyword.AND);
            }

            renderer.statement(clause);
        }

        if (hasClauses) {
            renderer.closeParenthesis();
        }

        return renderer;
    }
}