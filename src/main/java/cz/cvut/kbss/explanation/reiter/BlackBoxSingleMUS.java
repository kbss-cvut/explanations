package cz.cvut.kbss.explanation.reiter;

import cz.cvut.kbss.explanation.SingleMUSAlgorithm;
import cz.cvut.kbss.explanation.Test;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * An implementation of the algorithm SINGLE_MUPS in "Aditya Kalyanpur
 * :Debugging and Repair of OWL Ontologies, University of Maryland, 2006".
 * <p>
 * Notice that the algorithm is polynomial.
 *
 * @param <C> axiom/component class
 * @author kremen
 */
public class BlackBoxSingleMUS<C> implements SingleMUSAlgorithm<C> {

    private final Test<C> t;

    public BlackBoxSingleMUS(final Test<C> t) {
        this.t = t;
    }

    /**
     * {@inheritDoc}
     */
    public Set<C> find(List<C> list) {
        if (list.isEmpty() || t.test(new HashSet<C>(list))) {
            // test passed - no MUSes
            return null;
        }

        final ListIterator<C> li = list.listIterator();

        final Set<C> k1 = new HashSet<C>();

        // this element should exist, otherwise the former test should not have
        // succeeded.
        C last = li.next();

        k1.add(last);

        while (t.test(Collections.unmodifiableSet(k1))) {
            last = li.next();
            k1.add(last);
        }

        // minimizing
        for (final C c : new HashSet<C>(k1)) {
            if (!c.equals(last)) {
                k1.remove(c);
                if (t.test(Collections.unmodifiableSet(k1))) {
                    k1.add(c);
                }
            }
        }

        return k1;
    }
}
