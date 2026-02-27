package com.invirgance.convirgance.jdbc.sql;

import com.invirgance.convirgance.jdbc.schema.DatabaseSchemaLayout;

public class AndWhereClause<P extends WhereStatement> extends WhereStatement implements ComparisonStatement {

    public AndWhereClause(DatabaseSchemaLayout schemaLayout) {
        super(schemaLayout);
    }

    AndWhereClause(DatabaseSchemaLayout schemaLayout, P parentWhereClause) {
        super(schemaLayout, parentWhereClause);
    }

    @Override
    public SQLStatement getStatement() {
        return ((WhereStatement) getParent()).getStatement();
    }

    @Override
    public WhereStatement getWhereStatement() {
        return (WhereStatement) getParent();
    }

    @Override
    public WhereStatement getRootWhereStatement() {
        WhereStatement parentWhereClause = (WhereStatement) getParent();
        if (parentWhereClause == null) {
            return this;
        }
        return parentWhereClause.getRootWhereStatement();
    }

    @Override
    public SQLRenderer render(SQLRenderer renderer) {
        boolean hasRenderedClause = false;

        for (ComparisonStatement clause : getClauses()) {
            if (!hasRenderedClause) {
                renderer.openParenthesis();
                hasRenderedClause = true;
            } else {
                renderer.keyword(Keyword.AND);
            }

            renderer.render(clause);
        }

        if (hasRenderedClause) {
            renderer.closeParenthesis();
        }

        return renderer;
    }
}