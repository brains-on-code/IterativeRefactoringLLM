package com.invirgance.convirgance.jdbc.sql;

import com.invirgance.convirgance.jdbc.schema.DatabaseSchemaLayout;

public class AndConditionGroup<P extends WhereStatement> extends WhereStatement implements ComparisonStatement {

    public AndConditionGroup(DatabaseSchemaLayout schemaLayout) {
        super(schemaLayout);
    }

    AndConditionGroup(DatabaseSchemaLayout schemaLayout, P parentWhereStatement) {
        super(schemaLayout, parentWhereStatement);
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
        boolean hasRenderedAnyComparison = false;

        for (ComparisonStatement comparisonStatement : getClauses()) {
            if (!hasRenderedAnyComparison) {
                renderer.openParenthesis();
                hasRenderedAnyComparison = true;
            } else {
                renderer.keyword(Keyword.AND);
            }

            renderer.statement(comparisonStatement);
        }

        if (hasRenderedAnyComparison) {
            renderer.closeParenthesis();
        }

        return renderer;
    }
}