package cz.cvut.kbss.explanation.reiter;

import cz.cvut.kbss.explanation.IncrementalTest;
import cz.cvut.kbss.explanation.IncrementalTestState;
import cz.cvut.kbss.explanation.SingleMUSAlgorithm;
import cz.cvut.kbss.explanation.Test;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class SingleIncrementalMUSOld<C> implements SingleMUSAlgorithm<C> {

    private final IncrementalTest<C> t;

    private final Test<C> tt;

    public SingleIncrementalMUSOld(final Test<C> tt, final IncrementalTest<C> t) {
        this.t = t;
        this.tt = tt;
    }

    public Set<C> find(List<C> list) {
        if (tt.test(new HashSet<C>(list))) {
            // test passed - no MUSes
            return null;
        }

        final ListIterator<C> li = list.listIterator();

        Set<C> k1 = new HashSet<C>();

        // this element should exist, otherwise the former test should not have
        // succeeded.

        boolean result = true;
        C last = null;
        Object state = null;

        while (result && li.hasNext()) {
            last = li.next();

            IncrementalTestState state2 = t.test(last, state);
            result = state2.result;
            state = state2.state;
            k1.add(last);
        }

        // minimizing
        for (final C c : new HashSet<C>(k1)) {
            if (!c.equals(last)) {
                k1.remove(c);
                if (tt.test(Collections.unmodifiableSet(k1))) {
                    k1.add(c);
                }
            }
        }

        return k1;
    }
}
