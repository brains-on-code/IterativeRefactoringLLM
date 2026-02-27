package com.invirgance.convirgance.jdbc.sql;

import com.invirgance.convirgance.jdbc.schema.DatabaseSchemaLayout;

/**
 * Represents a grouped set of comparison clauses within a WHERE statement.
 *
 * @param <P> the parent type, which must extend {@link WhereStatement}
 */
public class Class1<P extends WhereStatement> extends WhereStatement implements ComparisonStatement {

    public Class1(DatabaseSchemaLayout schemaLayout) {
        super(schemaLayout);
    }

    Class1(DatabaseSchemaLayout schemaLayout, P parent) {
        super(schemaLayout, parent);
    }

    @Override
    public SQLStatement method1() {
        return ((WhereStatement) getParent()).method1();
    }

    @Override
    public WhereStatement method2() {
        return (WhereStatement) getParent();
    }

    @Override
    public WhereStatement method3() {
        if (getParent() == null) {
            return this;
        }

        return ((WhereStatement) getParent()).method3();
    }

    @Override
    public SQLRenderer method4(SQLRenderer renderer) {
        boolean hasOpenedParenthesis = false;

        for (ComparisonStatement clause : getClauses()) {
            if (!hasOpenedParenthesis) {
                renderer.openParenthesis();
                hasOpenedParenthesis = true;
            } else {
                renderer.keyword(Keyword.AND);
            }

            renderer.var5(clause);
        }

        if (hasOpenedParenthesis) {
            renderer.closeParenthesis();
        }

        return renderer;
    }
}