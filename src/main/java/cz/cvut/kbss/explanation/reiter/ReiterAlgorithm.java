package cz.cvut.kbss.explanation.reiter;

import cz.cvut.kbss.explanation.AllMUSesAlgorithm;
import cz.cvut.kbss.explanation.Listener;
import cz.cvut.kbss.explanation.SingleMUSAlgorithm;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * Implementation of the Reiter algorithm, as described in "Raymond Reiter : A
 * Theory of Diagnosis from First Principles, Artificial Intelligence, 1987."
 * </p>
 *
 * <p>
 * This algorithm takes as a black-box a function that is able to compute one
 * MUS. Notice that comparing to the algorithms based on CS trees, this
 * algorithm computes hitting sets as a side-product.
 * </p>
 *
 * @param <C> axiom/component class
 * @author kremen
 */
public class ReiterAlgorithm<C> implements AllMUSesAlgorithm<C> {

    private final Set<Listener<?>> listeners = new HashSet<Listener<?>>();

    private final SingleMUSAlgorithm<C> a;

    private final Set<Set<C>> hs = new HashSet<Set<C>>();

    private boolean trace = false;

    public ReiterAlgorithm(final SingleMUSAlgorithm<C> a) {
        this.a = a;
    }

    /**
     * {@inheritDoc}
     */
    public Set<Set<C>> find(List<C> cc) {
        hs.clear();
        final Set<Set<C>> set = new HashSet<Set<C>>();
        findR(null, cc, new HashSet<C>(), set, hs);
        return set;
    }

    public Set<Set<C>> getHittingSets() {
        return hs;
    }

    private void findR(Object last, List<C> cc, Set<C> path, Set<Set<C>> set,
                       Set<Set<C>> hittingSets) {

        for (final Set<C> hs : hittingSets) {
            if (path.containsAll(hs)) {
                // NOT TESTED
                if (trace) {
                    final Object v = new StringVertex("XXX");
                    addVertex(v);
                    addEdge(last, v);
                }
                return;
            }
        }

        final Set<C> mups = a.find(cc);

        if (mups == null) {
            boolean add = true;
            Set<Set<C>> toRemove = new HashSet<Set<C>>();
            for (final Set<C> c : hittingSets) {
                if (path.containsAll(c)) {
                    add = false;
                } else if (c.containsAll(path)) {
                    toRemove.add(c);
                }
            }
            hittingSets.removeAll(toRemove);
            if (add) {
                hittingSets.add(path);
            }

            // TEST SUCCEEDED
            if (trace) {
                final Object v = new StringVertex("SAT");
                addVertex(v);
                addEdge(last, v);
            }
            return;
        }

        set.add(mups);

        Object v = null;
        if (trace) {
            v = new MUSVertex<C>(mups);
            addVertex(v);
            if (last != null) {
                addEdge(last, v);
            }
        }

        for (final C c : mups) {
            final List<C> s = new ArrayList<C>(cc);
            final Set<C> newPath = new HashSet<C>(path);
            newPath.add(c);
            s.remove(c);
            findR(v, s, newPath, set, hittingSets);
        }
    }

    private void addVertex(Object s) {
        for (final Listener l : listeners) {
            l.addVertex(s);
        }
    }

    private void addEdge(Object s, Object s2) {
        for (final Listener l : listeners) {
            l.addEdge(s, s2);
        }
    }

    public void addListener(final Listener<?> l) {
        trace = true;
        this.listeners.add(l);
    }

    public void removeListener(final Listener<?> l) {
        this.listeners.remove(l);
        if (listeners.isEmpty()) {
            trace = false;
        }
    }
}