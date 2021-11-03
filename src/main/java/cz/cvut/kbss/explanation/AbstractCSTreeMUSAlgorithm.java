package cz.cvut.kbss.explanation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Common class to all minimal subset searching algorithms based on a CS tree.
 * It allows for listening the CS tree changes.
 *
 * @param <C>
 * @author kremen
 */
abstract class AbstractCSTreeMUSAlgorithm<C> implements CSTreeMUSAlgorithm<C>,
    AllMUSesAlgorithm<C> {
    private final Set<Listener<SimpleVertex<C>>> listeners =
        new HashSet<Listener<SimpleVertex<C>>>();

    private final Test<C> test;

    protected boolean track = false;

    public AbstractCSTreeMUSAlgorithm(final Test<C> test) {
        this.test = test;
    }

    /**
     * {@inheritDoc}
     */
    public abstract void find(final List<C> cc, final List<C> cp,
                              final Set<Set<C>> a);

    /**
     * {@inheritDoc}
     */
    public Test<C> getTest() {
        return test;
    }

    /**
     * {@inheritDoc}
     */
    public Set<Set<C>> find(final List<C> cc) {
        final Set<Set<C>> set = new HashSet<Set<C>>();
        find(new ArrayList<C>(), cc, set);
        return set;
    }

    protected void addVertex(final SimpleVertex<C> v) {
        for (final Listener<SimpleVertex<C>> l : listeners) {
            l.addVertex(v);
        }
    }

    protected void addEdge(final SimpleVertex<C> v1, final SimpleVertex<C> v2) {
        for (final Listener<SimpleVertex<C>> l : listeners) {
            l.addEdge(v1, v2);
        }
    }

    public void addListener(Listener<SimpleVertex<C>> l) {
        listeners.add(l);
        track = true;
    }

    public void removeListener(Listener<SimpleVertex<C>> l) {
        listeners.remove(l);
        if (listeners.isEmpty()) {
            track = true;
        }
    }
}