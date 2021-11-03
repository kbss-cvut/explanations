package cz.cvut.kbss.explanation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * Implementation of <code>min_unsat1</code> algorithm in "De la Banda et al. :
 * Finding All Minimal Unsatisfiable Subsets, ACM 2003, pp.3."
 * </p>
 *
 * <p>
 * This algorithm constructs the CS-tree in a naive manner and returns its nodes
 * corresponding to MUSes. When finding a node that is satisfiable all of its
 * children are pruned.
 * </p>
 *
 * @param <C> axiom class/component class
 * @author kremen
 */
public class MinUnsat1<C> extends AbstractCSTreeMUSAlgorithm<C> implements
    RecursiveMinimalSubsetSearch<C> {

    private CSTreeMUSAlgorithm<C> recursion = this;
    private SimpleVertex<C> last = null;

    public MinUnsat1(final Test<C> t) {
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
    public void find(final List<C> d, final List<C> p, final Set<Set<C>> a) {
        SimpleVertex<C> v = null;

        if (track) {
            v = new SimpleVertex<C>(new ArrayList<C>(d), new ArrayList<C>(p));
            addVertex(v);
            if (last != null) {
                addEdge(last, v);
            }
        }

        final List<C> dd = new ArrayList<C>(d);
        final List<C> pp = new ArrayList<C>(p);

        final List<C> all = new ArrayList<C>();
        all.addAll(dd);
        all.addAll(pp);

        if (getTest().test(new HashSet<C>(all))) {
            return;
        }

        while (!pp.isEmpty()) {
            final C c = pp.iterator().next();
            pp.remove(c);
            last = v;
            recursion.find(dd, pp, a);
            dd.add(c);
        }

        boolean add = true;

        for (final Set<C> l : a) {
            if (!l.isEmpty() && dd.containsAll(l)) {
                add = false;
                break;
            }
        }

        if (add) {
            a.add(new HashSet<C>(dd));
        }

        return;
    }
}
