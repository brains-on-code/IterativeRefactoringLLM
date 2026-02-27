/*
 * The MIT License
 * 
 * Copyright 2025 jbanes.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.invirgance.convirgance.jdbc.sql;

import com.invirgance.convirgance.jdbc.schema.DatabaseSchemaLayout;

/**
 * Creates a logical AND grouping in SQL, combining multiple conditions that all must be true.
 * This wraps conditions in parentheses and joins them with AND keywords.
 *
 * <pre><code>
 * DatabaseSchemaLayout layout = getHSQLLayout();
 * Table table = layout.getCurrentSchema().getTable("customer");
 *
 * SQLStatement query = table
 *     .select()
 *     .column(table.getColumn("name"))
 *     .where()
 *         .equals(column, value)
 *         .and() // BooleanAndStatement
 *             .greaterThan(table.getColumn("height"), value)
 *             .lessThan(table.getColumn("maximum"), value)
 *         .end()
 *     .done();
 * </code></pre>
 *
 * @author jbanes
 * @param <P> The parent WhereStatement type this returns to when done.
 */
public class BooleanAndStatement<P extends WhereStatement> extends WhereStatement implements ComparisonStatement {

    public BooleanAndStatement(DatabaseSchemaLayout layout) {
        super(layout);
    }

    BooleanAndStatement(DatabaseSchemaLayout layout, P parent) {
        super(layout, parent);
    }

    @Override
    public SQLStatement done() {
        return parentWhere().done();
    }

    @Override
    public WhereStatement end() {
        return parentWhere();
    }

    @Override
    public WhereStatement where() {
        WhereStatement parent = parentWhere();
        return (parent == null) ? this : parent.where();
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

    private WhereStatement parentWhere() {
        return (WhereStatement) getParent();
    }
}