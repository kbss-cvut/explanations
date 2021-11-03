package cz.cvut.kbss.explanation.reiter;

import cz.cvut.kbss.explanation.IncrementalTest;
import cz.cvut.kbss.explanation.IncrementalTestState;
import cz.cvut.kbss.explanation.SingleMUSAlgorithm;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * Implementation of the single MUS algorithm described in "Kremen, Kouba :
 * Incremental Approach to Error Explanations in Ontologies, I-KNOW 2007."
 * </p>
 *
 * @param <C> axiom class/component class
 * @author kremen
 */
public class SingleIncrementalMUS<C> implements SingleMUSAlgorithm<C> {

    private final IncrementalTest<C> t;

    // binary split of the KB
    private boolean logarithmic;

    public SingleIncrementalMUS(final IncrementalTest<C> t,
                                final boolean logarithmic) {
        this.t = t;
        this.logarithmic = logarithmic;
    }

    /**
     * {@inheritDoc}
     */
    public Set<C> find(List<C> p) {
        return _find(p, 0, p.size());
    }

    private Set<C> _find(List<C> p, int lower, int upper) {
        if (t.test(p, null).result) {
            return Collections.emptySet();
        }

        if (logarithmic && !p.isEmpty()) {
            Set<C> s = find(p.subList(0, p.size() / 2));

            if (s == null || s.isEmpty()) {
                s = find(p.subList((p.size() / 2) + 1, p.size()));
            }

            if (s != null && !s.isEmpty()) {
                return s;
            }
        }

        final Set<C> d = new HashSet<C>();
        Object stateD = null;
        Object last = null;

        int inc = 1;
        int i = 0;

        while (lower <= upper) {
            if (i >= p.size()) {
                return null;
            }

            final C c = p.get(i);

            final IncrementalTestState state = t.test(c, last);

            if (state.result) {
                last = state.state;
                i += inc;
            } else {
                d.add(c);

                stateD = t.test(c, stateD).state;
                last = t.copyState(stateD);

                if (inc == 1) {
                    inc = -1;
                    upper--;
                } else {
                    inc = 1;
                    lower++;
                }
            }
        }

        return d;
    }
}
