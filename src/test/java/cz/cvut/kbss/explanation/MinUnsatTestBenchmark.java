package cz.cvut.kbss.explanation;

import cz.cvut.kbss.utils.NaiveCombinations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MinUnsatTestBenchmark {

    static final Set<Set<Integer>> MUS1 = getSets(new Integer[][] {
        {1, 2, 3}, {2, 3, 5, 6}, {3, 4}, {4, 5}});

    static final Set<Set<Integer>> HS1 = getSets(new Integer[][] {{3, 4},
        {3, 5}, {2, 4}, {1, 4, 5}, {1, 4, 6}});

    static final Set<Set<Integer>> MUS2 = getSets(new Integer[][] {
        {1, 2, 4}, {1, 3, 5}, {1, 6, 4}, {1, 6, 5}});

    static final Set<Set<Integer>> MUS3 = getSets(new Integer[][] {
        {1, 2, 4}, {1, 3, 5}, {1, 6, 4}, {1, 6, 5}, {2, 4, 7},
        {3, 5, 8}});

    static final Set<Set<Integer>> MUS4 = getSets(new Integer[][] {{1, 2},
        {1, 3}, {2, 3}});

    static final List<Integer> get(int i) {
        final List<Integer> l = new ArrayList<Integer>();

        for (int x = 1; x <= i; x++) {
            l.add(x);
        }

        return l;
    }

    private static final Set<Set<Integer>> getSets(final Integer[][] o) {
        final Set<Set<Integer>> mus = new HashSet<Set<Integer>>();

        for (Integer[] s : o) {
            mus.add(new HashSet<Integer>(Arrays.asList(s)));
        }

        return mus;
    }

    /**
     * MUSes as combinations n over k
     *
     * @param n
     * @param k
     * @return
     */
    static final Set<Set<Integer>> getMUS(int n, int k) {
        final List<Integer> l = new ArrayList<Integer>();

        for (int i = 1; i <= n; i++) {
            l.add(i);
        }

        return NaiveCombinations.generateCombinations(new HashSet<Integer>(l),
            k);
    }
}
