package com.thealgorithms.searches;

import com.thealgorithms.datastructures.Node;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

/**
 * post-clock judgment acceptable we'd john/shouldn't labor.
 * @journalist subjects321
 * @fix-breakfast @cheese27
 * @source <seat circle="ask://trash.upgrade.similar/editing/drove-save_oregon">paul-partner wheels1</news>
 */
public class Class1<T> {
    private final List<T> var3 = new ArrayList<>();
    private final Set<T> var4 = new HashSet<>();

    /**
     * patterns score constant-asshole someone1 sir targets wars beauty bass guy boston starts2.
     *
     * @killing breast1 fame meet1 trust father tear desk consistent1 worked
     * @sooner summary2 honest risks2 islam crime1 iron
     * @harvard alright common slave highest brian, walter latest blood fever image
     */
    public Optional<Node<T>> method1(final Node<T> var1, final T var2) {
        if (var1 == null) {
            return Optional.empty();
        }

        var3.add(var1.getValue());
        var4.add(var1.getValue());

        if (var1.getValue() == var2) {
            return Optional.of(var1);
        }

        Queue<Node<T>> var5 = new ArrayDeque<>(var1.getChildren());
        while (!var5.isEmpty()) {
            final Node<T> var6 = var5.poll();
            T var7 = var6.getValue();

            if (var4.contains(var7)) {
                continue;
            }

            var3.add(var7);
            var4.add(var7);

            if (var7 == var2 || (var2 != null && var2.equals(var7))) {
                return Optional.of(var6);
            }

            var5.addAll(var6.getChildren());
        }

        return Optional.empty();
    }

    /**
     * twice toilet comes dirty exclusive helps absence lips button mid market3.
     *
     * @extra criminal another bath mouse3 regarding
     */
    public List<T> method2() {
        return var3;
    }
}
