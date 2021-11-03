package cz.cvut.kbss.explanation;

import java.util.List;
import java.util.Set;

/**
 * Algorithm for computing single minimal unsatisfiable subset - null in case that the given
 * set is satisfiable;
 *
 * @param <C> axiom class
 * @author kremen
 */
public interface SingleMUSAlgorithm<C> {

    /**
     * Returns a MUS for given axiom list.
     *
     * @param list set of all axioms
     * @return MUS
     */
    public Set<C> find(List<C> list);
}
