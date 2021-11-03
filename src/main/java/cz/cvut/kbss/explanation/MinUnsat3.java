package cz.cvut.kbss.explanation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * <p>
 * Implementation of <code>min_unsat3</code> algorithm in "De la Banda et al. :
 * Finding All Minimal Unsatisfiable Subsets, ACM 2003, pp.4."
 * </p>
 *
 * <p>
 * This algorithm makes a preprocessing for another <code>min_unsatX</code>.
 * All axioms/components that necessarily belong to all MUSes are put to D in
 * advance.
 * </p>
 *
 * @param <C> axiom class/component class
 * @author kremen
 */
public class MinUnsat3<C> extends AbstractCSTreeMUSAlgorithm<C> {

    private final CSTreeMUSAlgorithm<C> algorithm;

    public MinUnsat3(final CSTreeMUSAlgorithm<C> alg) {
        super(alg.getTest());
        this.algorithm = alg;
    }

    /**
     * {@inheritDoc}
     */
    public void find(final List<C> d, final List<C> p, final Set<Set<C>> a) {
        final List<C> dd = inAllUnsat(d, p);

        final List<C> newDD = new ArrayList<C>(d);
        newDD.addAll(dd);

        final List<C> newPP = new ArrayList<C>(p);
        newPP.removeAll(dd);

        algorithm.find(newDD, newPP, a);
    }

    private List<C> inAllUnsat(final List<C> d, final List<C> p) {
        final List<C> dd = new ArrayList<C>();

        for (final C v : p) {
            final Set<C> s = new HashSet<C>(d);
            s.addAll(p);
            s.remove(v);

            if (getTest().test(s)) {
                dd.add(v);
            }
        }

        return dd;
    }

    /**
     * {@inheritDoc}
     */
    public void addListener(Listener<SimpleVertex<C>> l) {
        super.addListener(l);
        algorithm.addListener(l);
    }

    /**
     * {@inheritDoc}
     */
    public void removeListener(Listener<SimpleVertex<C>> l) {
        super.removeListener(l);
        algorithm.removeListener(l);
    }
}
