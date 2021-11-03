package cz.cvut.kbss.explanation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * <p>
 * Implementation of <code>min_unsat4</code> algorithm in "De la Banda et al. :
 * Finding All Minimal Unsatisfiable Subsets, ACM 2003, pp.5."
 * </p>
 *
 * <p>
 * This version of CS tree search makes use of independence of axioms. Axioms
 * are partitioned according to their variables.
 * </p>
 *
 * @param <C> axiom class/component class
 * @author kremen
 */
public class MinUnsat4<C> extends AbstractCSTreeMUSAlgorithm<C> {

    private final RecursiveMinimalSubsetSearch<C> s;

    private final Partitioner<C> par;

    public MinUnsat4(final RecursiveMinimalSubsetSearch<C> s,
                     final Partitioner<C> p) {
        super(s.getTest());
        this.s = s;
        s.setRecursion(this);
        this.par = p;
    }

    /**
     * {@inheritDoc}
     */
    public void find(final List<C> d, final List<C> p, final Set<Set<C>> a) {
        SimpleVertex<C> v = null;

        if (track) {
            v = new SimpleVertex<C>(new ArrayList<C>(d), new ArrayList<C>(p));
            addVertex(v);
            SimpleVertex<C> last = s.getCurrentVertex();
            if (last != null) {
                addVertex(last);
                addEdge(last, v);
            }
        }

        final List<C> all = new ArrayList<C>();
        all.addAll(d);
        all.addAll(p);

        final List<List<C>> partition = par.partition(all);

        for (final List<C> x : partition) {
            if (x.containsAll(d)) {
                final List<C> pp = new ArrayList<C>(x);
                pp.removeAll(d);

                s.find(d, pp, a);
                if (track) {
                    SimpleVertex<C> last = new SimpleVertex<C>(
                        new ArrayList<C>(d), new ArrayList<C>(pp));
                    if (!v.equals(last)) {
                        addVertex(last);
                        addEdge(v, last);
                    }
                }
            }
        }
    }

    @Override
    public void addListener(Listener<SimpleVertex<C>> l) {
        super.addListener(l);
        s.addListener(l);
    }

    @Override
    public void removeListener(Listener<SimpleVertex<C>> l) {
        super.removeListener(l);
        s.removeListener(l);
    }

}
