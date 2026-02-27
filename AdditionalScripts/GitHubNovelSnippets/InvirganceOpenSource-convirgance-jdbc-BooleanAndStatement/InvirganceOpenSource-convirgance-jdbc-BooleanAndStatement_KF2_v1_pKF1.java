package com.invirgance.convirgance.jdbc.sql;

import com.invirgance.convirgance.jdbc.schema.DatabaseSchemaLayout;

public class AndConditionGroup<P extends WhereStatement> extends WhereStatement implements ComparisonStatement {

    public AndConditionGroup(DatabaseSchemaLayout schemaLayout) {
        super(schemaLayout);
    }

    AndConditionGroup(DatabaseSchemaLayout schemaLayout, P parentStatement) {
        super(schemaLayout, parentStatement);
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
        if (getParent() == null) {
            return this;
        }

        return ((WhereStatement) getParent()).where();
    }

    @Override
    public SQLRenderer render(SQLRenderer renderer) {
        boolean hasWrittenClause = false;

        for (ComparisonStatement clause : getClauses()) {
            if (!hasWrittenClause) {
                renderer.openParenthesis();
                hasWrittenClause = true;
            } else {
                renderer.keyword(Keyword.AND);
            }

            renderer.statement(clause);
        }

        if (hasWrittenClause) {
            renderer.closeParenthesis();
        }

        return renderer;
    }
}