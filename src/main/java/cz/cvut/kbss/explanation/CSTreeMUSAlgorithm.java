package cz.cvut.kbss.explanation;

import java.util.List;
import java.util.Set;


/**
 * An algorithm for computing all MUS.
 *
 * @param <C> axiom class
 * @author kremen
 */
public interface CSTreeMUSAlgorithm<C> {

    /**
     * Finds all minimal unsatisfiable sets (each as a set of axioms).
     *
     * @param d set of all necessary components/axioms
     * @param p set of all possible components/axioms
     * @param a collection of all subsets found so far. This collection IS
     *          MODIFIED during execution. It contains all subsets at the end
     *          of the run.
     */
    public void find(final List<C> d, final List<C> p, final Set<Set<C>> a);

    /**
     * Returns the testing function associated to this algorithm
     *
     * @return testing function
     */
    public Test<C> getTest();

    /**
     * Adds a listener to listen CS-tree construction.
     *
     * @param l listener
     */

    public void addListener(Listener<SimpleVertex<C>> l);

    /**
     * Removes a listener that listened to CS-tree construction.
     *
     * @param l listener
     */
    public void removeListener(Listener<SimpleVertex<C>> l);

}