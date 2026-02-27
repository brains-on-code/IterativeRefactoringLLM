
package com.invirgance.convirgance.jdbc.sql;

import com.invirgance.convirgance.jdbc.schema.DatabaseSchemaLayout;


public class BooleanAndStatement<P extends WhereStatement> extends WhereStatement implements ComparisonStatement
{
    public BooleanAndStatement(DatabaseSchemaLayout layout)
    {
        super(layout);
    }

    BooleanAndStatement(DatabaseSchemaLayout layout, P parent)
    {
        super(layout, parent);
    }

    @Override
    public SQLStatement done()
    {
        return ((WhereStatement)getParent()).done();
    }

    @Override
    public WhereStatement end()
    {
        return (WhereStatement)getParent();
    }

    @Override
    public WhereStatement where()
    {
        if(getParent() == null) return this;

        return ((WhereStatement)getParent()).where();
    }

    @Override
    public SQLRenderer render(SQLRenderer renderer)
    {
        boolean written = false;

        for(ComparisonStatement statement : getClauses())
        {
            if(!written)
            {
                renderer.openParenthesis();
                written = true;
            }
            else
            {
                renderer.keyword(Keyword.AND);
            }

            renderer.statement(statement);
        }

        if(written) renderer.closeParenthesis();

        return renderer;
    }
}
