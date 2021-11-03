package cz.cvut.kbss.explanation;

import java.util.List;
import java.util.Set;

/**
 * Returns all minimal unsatisfiable subset - null in case that the given set is
 * satisfiable;
 *
 * @param <C> axiom class
 * @author kremen
 */
public interface AllMUSesAlgorithm<C> {

    /**
     * Returns all MUSes for given axioms.
     *
     * @param list all axioms
     * @return MUS
     */
    Set<Set<C>> find(final List<C> list);
}
