package cz.cvut.kbss.explanation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * Implementation of <code>min_unsat8</code> algorithm in "De la Banda et al. :
 * Finding All Minimal Unsatisfiable Subsets, ACM 2003, pp.3."
 * </p>
 *
 * <p>
 * This is the simpliest incremental version of the CS tree search.
 * </p>
 *
 * @param <C> axiom class/component class
 * @author kremen
 */
public class MinUnsat8<C> implements AllMUSesAlgorithm<C> {

    private static final Logger LOG = Logger.getLogger(MinUnsat8.class.getName());

    private final IncrementalTest<C> test;

    private int level = 0;

    /**
     * Creates a new MinUnsat8 instance given an incremental test
     * implementation.
     *
     * @param test
     */
    public MinUnsat8(final IncrementalTest<C> test) {
        this.test = test;
    }

    private String printLevel(int l) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < l; i++) {
            sb.append("*");
        }
        sb.append(" ");
        return sb.toString();
    }

    public IncrementalTest<C> getTest() {
        return test;
    }

    public Set<Set<C>> find(final List<C> cc) {
        final Set<Set<C>> set = new HashSet<Set<C>>();
        find(new ArrayList<C>(), null, cc, new ArrayList<C>(), null, set);
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
     */
    public void find(final List<C> d, Object stateD, final List<C> p,
                     final List<C> t, Object stateT, final Set<Set<C>> a) {

        if (LOG.isLoggable(Level.FINE)) {
            level++;
            LOG.fine(printLevel(level) + "d=" + d + ", stateD = " + stateD
                + ", p=" + p + ", t=" + t + ", stateT=" + stateT + ", a="
                + a);
        }
        boolean result = true;

        final List<C> tt = new ArrayList<C>(t);

        final List<C> ttIp = new ArrayList<C>(p);
        ttIp.removeAll(tt);

        Object lastT = null;

        // CANNOT HAPPEN THAT c remains null
        C c = null;
        while (result && !ttIp.isEmpty()) {
            c = ttIp.iterator().next();
            ttIp.remove(c);
            tt.add(c);
            lastT = stateT;

            if (LOG.isLoggable(Level.CONFIG)) {
                LOG.config(printLevel(level) + "TEST : c=" + c + ", stateT="
                    + stateT);
            }

            IncrementalTestState state = test.test(c, stateT);

            if (LOG.isLoggable(Level.CONFIG)) {
                LOG.config(printLevel(level) + "RESULT : result=" + state.result
                    + ", state=" + state.state);
            }
            result = state.result;
            stateT = state.state;
        }

        if (result) {
            if (LOG.isLoggable(Level.FINE)) {
                level--;
            }
            return;
        }

        final List<C> pp = new ArrayList<C>(p);
        pp.remove(c);
        tt.remove(c);
        find(d, stateD, pp, tt, lastT, a);

        final List<C> dd = new ArrayList<C>(d);
        dd.add(c);

        if (LOG.isLoggable(Level.CONFIG)) {
            LOG.config(printLevel(level) + "TEST : c=" + c + ", stateT="
                + stateT);
        }
        IncrementalTestState state = test.test(c, stateD);
        if (LOG.isLoggable(Level.CONFIG)) {
            LOG.config(printLevel(level) + "RESULT : result=" + state.result
                + ", state=" + state.state);
        }

        result = state.result;
        stateD = state.state;

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
            find(dd, stateD, pp, dd, stateD, a);
        }
        if (LOG.isLoggable(Level.FINE)) {
            level--;
        }
    }
}
