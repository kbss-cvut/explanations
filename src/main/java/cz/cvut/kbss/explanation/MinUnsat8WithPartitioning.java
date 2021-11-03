package cz.cvut.kbss.explanation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MinUnsat8WithPartitioning<C> {

    private final IncrementalTest<C> test;

    private final Partitioner<C> par;
    private int level = 0;

    public MinUnsat8WithPartitioning(final IncrementalTest<C> test,
                                     final Partitioner<C> p) {
        this.test = test;
        this.par = p;
    }

    // private String printLevel() {
    // String s = "";
    // for (int i = 0; i < level; i++) {
    // s += "*";
    // }
    // return s;
    // }

    public Set<Set<C>> find(final List<C> d, Object stateD, final List<C> p,
                            final List<C> t, Object stateT, final Set<Set<C>> a) {
        level++;
        // LOGGER.config(printLevel() + "d=" + d + ", stateD = " + stateD + ",
        // p="
        // + p + ", t=" + t + ", stateT=" + stateT + ", a=" + a);
        final List<C> all = new ArrayList<C>();
        all.addAll(d);
        all.addAll(p);

        final List<List<C>> prt = par.partition(all);

        for (final List<C> prtx : prt) {
            if (prtx.containsAll(d)) {

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

                    // LOGGER.config(printLevel() + "SAT TEST : c=" + c + ",
                    // stateT="
                    // + stateT);

                    IncrementalTestState state = test.test(c, stateT);
                    // LOGGER.config(printLevel() + "SAT RESULT : result=" +
                    // state.result
                    // + ", state=" + state.state);
                    result = state.result;
                    stateT = state.state;
                }

                if (result) {
                    level--;
                    return a;
                }

                final List<C> pp = new ArrayList<C>(p);
                pp.remove(c);
                tt.remove(c);
                Set<Set<C>> aa = find(d, stateD, pp, tt, lastT, a);

                final List<C> dd = new ArrayList<C>(d);
                dd.add(c);

                // LOGGER
                // .config(printLevel() + "SAT TEST : c=" + c + ", stateD="
                // + stateT);
                IncrementalTestState state = test.test(c, stateD);
                result = state.result;
                stateD = state.state;
                boolean toAdd = true;
                if (!result) {
                    for (final Set<C> l : aa) {
                        if (!l.isEmpty() && dd.containsAll(l)) {
                            toAdd = false;
                        }
                    }
                    if (toAdd) {
                        aa.add(new HashSet<C>(dd));
                    }
                    level--;
                    return aa;
                }
                pp.remove(c);
                aa = find(dd, stateD, pp, dd, stateD, aa);
                level--;
                return aa;
            }
        }

        return a;
        // LOGGER.config(printLevel() + "SAT RESULT : result=" + state.result
        // + ", state=" + state.state);
    }
}
