package com.invirgance.convirgance.jdbc.sql;

import com.invirgance.convirgance.jdbc.schema.DatabaseSchemaLayout;

public class AndWhereClause<P extends WhereStatement> extends WhereStatement implements ComparisonStatement
{
    public AndWhereClause(DatabaseSchemaLayout schemaLayout)
    {
        super(schemaLayout);
    }
    
    AndWhereClause(DatabaseSchemaLayout schemaLayout, P parentStatement)
    {
        super(schemaLayout, parentStatement);
    }

    @Override
    public SQLStatement getStatement()
    {
        return ((WhereStatement)getParent()).getStatement();
    }
    
    @Override
    public WhereStatement getWhereStatement()
    {
        return (WhereStatement)getParent();
    }
    
    @Override
    public WhereStatement getRootWhereStatement()
    {
        if (getParent() == null) return this;
        
        return ((WhereStatement)getParent()).getRootWhereStatement();
    }
    
    @Override
    public SQLRenderer render(SQLRenderer renderer)
    {
        boolean hasRenderedAnyClause = false;
        
        for (ComparisonStatement clause : getClauses())
        {
            if (!hasRenderedAnyClause)
            {
                renderer.openParenthesis();
                hasRenderedAnyClause = true;
            }
            else
            {
                renderer.keyword(Keyword.AND);
            }
            
            renderer.render(clause);
        }
        
        if (hasRenderedAnyClause) renderer.closeParenthesis();
        
        return renderer;
    }
}