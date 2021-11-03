package cz.cvut.kbss.explanation;

import java.util.List;

/**
 * Partitioner for the components/axioms.
 *
 * @param <C> axiom/component class
 * @author kremen
 */
public interface Partitioner<C> {

    /**
     * Partitions the input list of components
     *
     * @param d list to partition
     * @return partitioning of the input list
     */
    List<List<C>> partition(final List<C> d);
}