package cz.cvut.kbss.explanation;

import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * Implementation of <code>min_unsat8</code> algorithm in "De la Banda et al. :
 * Finding All Minimal Unsatisfiable Subsets, ACM 2003, pp.3."
 * </p>
 *
 * <p>
 * This is a bit more optimized implementation of min_unsat8 using
 * {@link BitSet}.
 * </p>
 *
 * @param <C> axiom class/component class
 * @author kremen
 */
public class MinUnsat8BS<C> implements AllMUSesAlgorithm<C> {

    private final IncrementalTest<C> test;

    private List<C> l;

    /**
     * Creates a new MinUnsat8 instance given an incremental test
     * implementation.
     *
     * @param test
     */
    public MinUnsat8BS(final IncrementalTest<C> test) {
        this.test = test;
    }

    /**
     * {@inheritDoc}
     */
    public Set<Set<C>> find(final List<C> cc) {
        final Set<BitSet> set = new HashSet<BitSet>();

        // P list to bit set
        final BitSet pp = new BitSet();
        pp.set(0, cc.size());

        find(cc, new BitSet(), null, pp, new BitSet(), null, set);

        final Set<Set<C>> s = new HashSet<Set<C>>();

        for (final BitSet b : set) {
            final Set<C> ss = new HashSet<C>();
            for (int i = b.nextSetBit(0); i >= 0; i = b.nextSetBit(i + 1)) {
                ss.add(cc.get(i));
            }
            s.add(ss);
        }
        return s;
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
    public void find(final List<C> axioms, final BitSet d, Object stateD,
                     final BitSet p, final BitSet t, Object stateT, final Set<BitSet> a) {
        this.l = axioms;
        find(d, stateD, p, t, stateT, a);
    }

    private void find(final BitSet d, Object stateD, final BitSet p,
                      final BitSet t, Object stateT, final Set<BitSet> a) {
        boolean result = true;

        final BitSet tt = (BitSet) t.clone();

        final BitSet ttIp = (BitSet) p.clone();
        ttIp.andNot(tt);

        Object lastT = null;

        // IT CANNOT HAPPEN THAT c remains -1
        int c = 0;
        while (result && !(ttIp.nextSetBit(0) == -1)) {
            c = ttIp.nextSetBit(c);
            ttIp.clear(c);
            tt.set(c);
            lastT = stateT;

            IncrementalTestState state = test.test(l.get(c), stateT);
            result = state.result;
            stateT = state.state;
        }

        if (result) {
            return;
        }

        final BitSet pp = (BitSet) p.clone();
        pp.clear(c);
        tt.clear(c);
        find(d, stateD, pp, tt, lastT, a);

        final BitSet dd = (BitSet) d.clone();
        dd.set(c);

        IncrementalTestState state = test.test(l.get(c), stateD);

        result = state.result;
        stateD = state.state;

        boolean toAdd = true;
        if (!result) {
            for (final BitSet ll : a) {
                final BitSet lll = new BitSet();
                lll.or(ll);
                lll.andNot(dd);
                if (lll.isEmpty()) {
                    toAdd = false;
                }
            }
            if (toAdd) {
                final BitSet bs = new BitSet();
                bs.or(dd);
                a.add(bs);
            }
        } else {
            pp.clear(c);
            find(dd, stateD, pp, dd, stateD, a);
        }
    }
}
