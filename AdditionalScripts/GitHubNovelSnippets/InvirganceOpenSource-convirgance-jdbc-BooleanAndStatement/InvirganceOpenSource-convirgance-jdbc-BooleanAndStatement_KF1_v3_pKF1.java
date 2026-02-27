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
        WhereStatement parentWhereStatement = (WhereStatement) getParent();
        if (parentWhereStatement == null) {
            return this;
        }
        return parentWhereStatement.getRootWhereStatement();
    }

    @Override
    public SQLRenderer render(SQLRenderer renderer) {
        boolean hasRenderedAnyClause = false;

        for (ComparisonStatement comparisonStatement : getClauses()) {
            if (!hasRenderedAnyClause) {
                renderer.openParenthesis();
                hasRenderedAnyClause = true;
            } else {
                renderer.keyword(Keyword.AND);
            }

            renderer.render(comparisonStatement);
        }

        if (hasRenderedAnyClause) {
            renderer.closeParenthesis();
        }

        return renderer;
    }
}