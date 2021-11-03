package cz.cvut.kbss.explanation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * Modification of {@link MinUnsat8} that performs very cheap caching of the
 * reasoner results, as described in "Kremen, Kouba : Incremental Approach to
 * Error Explanations in Ontologies, I-KNOW 2007."
 * </p>
 *
 * @param <C> axiom class/component class
 * @author kremen
 */
public class CachedMinUnsat8<C> implements AllMUSesAlgorithm<C> {

    private final IncrementalTest<C> test;

    public CachedMinUnsat8(final IncrementalTest<C> test) {
        this.test = test;
    }

    /**
     * {@inheritDoc}
     */
    public Set<Set<C>> find(final List<C> cc) {
        final Set<Set<C>> set = new HashSet<Set<C>>();

        find(new ArrayList<C>(), null, new ArrayList<C>(cc),
            new ArrayList<C>(), null, set, -1);

        return set;
    }

    /**
     * Finds all minimal unsatisfiable sets.
     *
     * @param d      set of all necessary components/axioms
     * @param stateD reasoner state corresponding to d
     * @param p      set of all possible components/axioms
     * @param t      currently tested satisfiable set
     * @param stateT reasoner state corresponding to t
     * @param a      collection of all subsets found so far. This collection IS
     *               MODIFIED during execution. It contains all subsets at the end
     *               of the run.
     * @param cached determines until which position the result in t is cached
     */
    public void find(final List<C> d, Object stateD, final List<C> p,
                     final List<C> t, Object stateT, Set<Set<C>> a, int cached) {
        final List<C> tt = new ArrayList<C>(t);

        // CANNOT HAPPEN THAT c remains null
        C c = null;

        boolean result = true;
        Object lastT = null;

        for (int i = 0; i < p.size() && result; i++) {
            c = p.get(i);
            if (!t.contains(c)) {
                tt.add(c);

                if (i == cached) {
                    lastT = stateT;
                    result = false;
                    break;
                }
                lastT = stateT;
                final IncrementalTestState state = test.test(c, stateT);
                stateT = state.state;
                result = state.result;
            }
        }

        int i = p.indexOf(c);

        if (result) {
            return;
        }

        final List<C> pp = new ArrayList<C>(p);
        pp.remove(c);
        tt.remove(c);

        find(d, stateD, pp, tt, lastT, a, -1);

        final List<C> dd = new ArrayList<C>(d);
        dd.add(c);
        IncrementalTestState state;

        if (i == 0 && d == t) {
            result = false;
        } else {
            state = test.test(c, stateD);
            result = state.result;
            stateD = state.state;
        }

        boolean toAdd = true;
        if (!result) {
            for (final Set<C> l : a) {
                if (!l.isEmpty() && dd.containsAll(l)) {
                    toAdd = false;
                }
            }
            if (toAdd) {
                a.add(new HashSet<C>(dd));
            }
        } else {
            pp.remove(c);
            find(dd, stateD, pp, dd, stateD, a, i - 1);
        }
    }
}
