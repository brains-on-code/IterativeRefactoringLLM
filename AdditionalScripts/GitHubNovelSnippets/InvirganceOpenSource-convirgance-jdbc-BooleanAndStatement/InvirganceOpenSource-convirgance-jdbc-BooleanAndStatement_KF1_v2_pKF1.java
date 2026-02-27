package com.invirgance.convirgance.jdbc.sql;

import com.invirgance.convirgance.jdbc.schema.DatabaseSchemaLayout;

public class AndWhereClause<P extends WhereStatement> extends WhereStatement implements ComparisonStatement {

    public AndWhereClause(DatabaseSchemaLayout schemaLayout) {
        super(schemaLayout);
    }

    AndWhereClause(DatabaseSchemaLayout schemaLayout, P parentWhereStatement) {
        super(schemaLayout, parentWhereStatement);
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
        if (getParent() == null) {
            return this;
        }

        return ((WhereStatement) getParent()).getRootWhereStatement();
    }

    @Override
    public SQLRenderer render(SQLRenderer renderer) {
        boolean hasRenderedClause = false;

        for (ComparisonStatement comparisonClause : getClauses()) {
            if (!hasRenderedClause) {
                renderer.openParenthesis();
                hasRenderedClause = true;
            } else {
                renderer.keyword(Keyword.AND);
            }

            renderer.render(comparisonClause);
        }

        if (hasRenderedClause) {
            renderer.closeParenthesis();
        }

        return renderer;
    }
}