package cz.cvut.kbss.explanation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Implementation of all_subsets algorithm in "De la Banda et al. : Finding All
 * Minimal Unsatisfiable Subsets", ACM 2003, pp.34.
 * <p>
 * This algorithm constructs the CS-tree in a naive manner and returns its
 * leaves corresponding to MUSes.
 *
 * @param <V> axioms/components
 * @author kremen
 */
public class AllSubsets<V> {

    private final Set<Listener<SimpleVertex<V>>> listeners =
        new HashSet<Listener<SimpleVertex<V>>>();

    private boolean track = false;

    /**
     * Returns all {X_i}, where each X_i SUBSETOF (d UNION p) and d SUBSETOF
     * X_i.
     *
     * @param d set of all necessary components/axioms
     * @param p set of all possible components/axioms
     * @param a collection of all subsets found so far. This collection
     *          contains all subsets at the end of the run.
     * @return
     */
    public void allSubsets(final Set<V> d, final Set<V> p, final Set<Set<V>> a) {
        SimpleVertex<V> v = null;
        if (track) {
            v = new SimpleVertex<V>(new ArrayList<V>(d), new ArrayList<V>(p));
            fireVertexAdded(v);
        }

        final Set<V> all = new HashSet<V>();
        all.addAll(d);
        all.addAll(p);
        a.add(all);

        final Set<V> dd = new HashSet<V>(d);
        final Set<V> pp = new HashSet<V>(p);

        while (!pp.isEmpty()) {
            final V c = pp.iterator().next();
            pp.remove(c);
            allSubsets(dd, pp, a);
            if (track) {
                fireEdgeAdded(v, new SimpleVertex<V>(new ArrayList<V>(dd),
                    new ArrayList<V>(pp)));
                dd.add(c);
            }
        }
    }

    private void fireVertexAdded(final SimpleVertex<V> v) {
        for (final Listener<SimpleVertex<V>> l : listeners) {
            l.addVertex(v);
        }
    }

    private void fireEdgeAdded(final SimpleVertex<V> v1,
                               final SimpleVertex<V> v2) {
        for (final Listener<SimpleVertex<V>> l : listeners) {
            l.addEdge(v1, v2);
        }
    }

    /**
     * Adds a listener to listen CS-tree construction.
     *
     * @param l listener
     */
    public void addListener(Listener<SimpleVertex<V>> l) {
        track = true;
        listeners.add(l);
    }

    /**
     * Removes a listener that listened CS-tree construction.
     *
     * @param l listener
     */
    public void removeListener(Listener<SimpleVertex<V>> l) {
        listeners.remove(l);
        if (listeners.isEmpty()) {
            track = false;
        }
    }
}
