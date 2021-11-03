package cz.cvut.kbss.explanation;

import java.util.Set;

public class IntegerTest implements Test<Integer> {

    final Set<Set<Integer>> mus;
    public int n = 0;

    public IntegerTest(final Set<Set<Integer>> mus) {
        this.mus = mus;
    }

    public boolean test(Set<Integer> v) {
        n++;
        for (final Set<Integer> s : mus) {
            if (v.containsAll(s)) {
                return false;
            }
        }
        return true;
    }
}