package cz.cvut.kbss.explanation;

import java.util.List;

/**
 * Incremental Test to use them in incremental testers, like MinUnsat8, etc.
 *
 * @param <T> axiom/component class
 * @author kremen
 */
public interface IncrementalTest<T> {

    /**
     * Creates a copy of the internal reasoner state. If null is given, an empty state is created.
     *
     * @param object state to copy
     * @return a copy
     */
    public Object copyState(final Object object);

    /**
     * Performs and incremental test using given reasoner <code>state</code> and
     * an incremental addition of axiom/component <code>component</code> .
     *
     * @param component added component
     * @param state     reasoner state
     * @return IncrementalTestState after performing the test
     */
    public IncrementalTestState test(final T component, final Object state);

    /**
     * Performs and incremental test using given reasoner <code>state</code> and
     * an incremental addition of several axioms/components
     * <code>component</code> .
     *
     * @param components added components
     * @param state      reasoner state
     * @return IncrementalTestState after performing the test
     */
    public IncrementalTestState test(final List<T> component, final Object state);
}
