package com.invirgance.convirgance.jdbc.sql;

import com.invirgance.convirgance.jdbc.schema.DatabaseSchemaLayout;

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
        WhereStatement parent = (WhereStatement) getParent();
        return (parent == null) ? this : parent.method3();
    }

    @Override
    public SQLRenderer method4(SQLRenderer renderer) {
        boolean hasClauses = false;

        for (ComparisonStatement clause : getClauses()) {
            if (!hasClauses) {
                renderer.openParenthesis();
                hasClauses = true;
            } else {
                renderer.keyword(Keyword.AND);
            }

            renderer.var5(clause);
        }

        if (hasClauses) {
            renderer.closeParenthesis();
        }

        return renderer;
    }
}