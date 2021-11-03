package cz.cvut.kbss.explanation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * Implementation of <code>min_unsat2</code> algorithm in "De la Banda et al. :
 * Finding All Minimal Unsatisfiable Subsets, ACM 2003, pp.3."
 * </p>
 *
 * <p>
 * This is an improvement to the min_unsat1, so that
 * <ul>
 * <li>all children and right siblings of a node (D,P) are pruned if D is a
 * superset of already found MUS A</li>
 * <li>tests are avoided whenever (D UNION P) is superset of already found MUS
 * A</li>
 * </ul>
 * </p>
 *
 * @param <C> axiom class/component class
 * @author kremen
 */
public class MinUnsat2<C> extends AbstractCSTreeMUSAlgorithm<C> implements
    RecursiveMinimalSubsetSearch<C> {

    private CSTreeMUSAlgorithm<C> recursion = this;

    private SimpleVertex<C> last = null;

    public MinUnsat2(final Test<C> t) {
        super(t);
    }

    /**
     * {@inheritDoc}
     */
    public void setRecursion(CSTreeMUSAlgorithm<C> r) {
        this.recursion = r;
    }

    /**
     * {@inheritDoc}
     */
    public SimpleVertex<C> getCurrentVertex() {
        return last;
    }

    /**
     * {@inheritDoc}
     */
    public void find(List<C> d, List<C> p, Set<Set<C>> a) {
        SimpleVertex<C> v = null;
        if (track) {
            v = new SimpleVertex<C>(new ArrayList<C>(d), new ArrayList<C>(p));
            addVertex(v);
            if ((last != null) && (last != v)) {
                addEdge(last, v);
            }
        }

        final List<C> dd = new ArrayList<C>(d);
        final List<C> pp = new ArrayList<C>(p);

        final List<C> all = new ArrayList<C>();
        all.addAll(dd);
        all.addAll(pp);

        boolean testForSureFails = false;

        for (final Set<C> l : a) {
            if (all.containsAll(l)) {
                testForSureFails = true;
                break;
            }
        }

        if (!testForSureFails) {
            final Set<C> ax = new HashSet<C>(all);
            final boolean r = getTest().test(ax);
            if (r) {
                return;
            }
        }

        while (!pp.isEmpty()) {
            final C c = pp.iterator().next();
            pp.remove(c);
            last = v;
            recursion.find(dd, pp, a);
            dd.add(c);
            for (final Set<C> l : a) {
                if (!l.isEmpty() && dd.containsAll(l)) {
                    return;
                }
            }
        }

        a.add(new HashSet<C>(dd));
    }
}
