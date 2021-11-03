package cz.cvut.kbss.explanation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class MinUnsat8WithCachingAndHittingSets<C> {

    private static final Logger LOGGER = Logger
        .getLogger(MinUnsat8WithCachingAndHittingSets.class.getName());

    private final IncrementalTest<C> test;
    private Result finalResult = null;

    public MinUnsat8WithCachingAndHittingSets(final IncrementalTest<C> test) {
        this.test = test;
    }

    public Set<Set<C>> find(final List<C> p, final Object emptyState) {
        finalResult = _find(new ArrayList<C>(), emptyState,
            new ArrayList<C>(p), new ArrayList<C>(), emptyState,
            new HashSet<Set<C>>(),
            // new Result(new HashSet<Set<C>>(), new HashSet<Set<C>>()),
            -1);

        return finalResult.muses;
    }

    public Set<Set<C>> getHittingSets() {
        return finalResult.hses;
    }

    private Result _find(final List<C> d, Object stateD, final List<C> p,
                         final List<C> t, Object stateT, Set<Set<C>> mus, int cached) {
        LOGGER.config("d=" + d + ", p=" + p + ", t=" + t + ", muses= "
            + ", cached=" + cached);

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
            return new Result(mus, new HashSet<Set<C>>());
        }

        final List<C> pp = new ArrayList<C>(p);
        pp.remove(c);
        tt.remove(c);

        Result r = _find(d, stateD, pp, tt, lastT, mus, -1);
        for (final Set<C> s : r.hses) {
            s.add(c);
        }
        LOGGER.config("hs=" + r.hses);

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
        if (r.hses.isEmpty()) {
            r.hses.add(new HashSet<C>(Collections.singleton(c)));
        }
        if (!result) {
            for (final Set<C> l : r.muses) {
                if (!l.isEmpty() && dd.containsAll(l)) {
                    toAdd = false;
                }
            }
            if (toAdd) {
                r.muses.add(new HashSet<C>(dd));
            }

            return r;
        }
        pp.remove(c);

        Result rr = _find(dd, stateD, pp, dd, stateD, r.muses, i - 1);
        LOGGER.config("hs3=" + rr.hses);

        // minimization of hitting sets
        final Set<Set<C>> x = new HashSet<Set<C>>(rr.hses);
        for (final Set<C> s : r.hses) {
            boolean add = true;
            for (final Set<C> s2 : rr.hses) {
                if (s2.containsAll(s)) {
                    x.remove(s2);
                } else if (s.containsAll(s2)) {
                    add = false;
                }
            }
            if (add) {
                x.add(s);
            }
        }

        return new Result(rr.muses, x);
    }

    private class Result {
        Set<Set<C>> muses;
        Set<Set<C>> hses;

        public Result(final Set<Set<C>> muses, final Set<Set<C>> hses) {
            this.muses = new HashSet<Set<C>>(muses);
            this.hses = new HashSet<Set<C>>(hses);
        }
    }
}
