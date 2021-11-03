package cz.cvut.kbss.explanation;

/**
 * Represents a state for a reasoner after performing an incremental test.
 *
 * @author kremen
 */
public class IncrementalTestState {

    public Object state;

    public boolean result;

    /**
     * Creates new incremental test state.
     *
     * @param result result of the test
     * @param state  state of the reasoner
     */
    public IncrementalTestState(boolean result, Object state) {
        this.result = result;
        this.state = state;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return result + " : " + state.toString();
    }
}