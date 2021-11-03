package cz.cvut.kbss.explanation;

import java.util.Set;

/**
 * A single test.
 *
 * @param <T> atomic component
 * @author kremen
 */
public interface Test<T> {

    /**
     * Performs a test (satisfiability, consistency, entailment, etc.)
     *
     * @param v set of components to test against
     * @return true if these components do not cause the inference to be valid.
     */
    public boolean test(final Set<T> v);

}