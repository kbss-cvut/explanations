package cz.cvut.kbss.explanation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IntegerIncrementalTest implements IncrementalTest<Integer> {

    final Set<Set<Integer>> mus;
    public int n = 0;

    public IntegerIncrementalTest(final Set<Set<Integer>> mus) {
        this.mus = mus;
    }

    public Set<Integer> createEmptyState() {
        return new HashSet<>();
    }

    @SuppressWarnings("unchecked")
    public IncrementalTestState test(Integer v, Object state) {
        n++;
        if (state == null) {
            for (final Set<Integer> s : mus) {
                if ((s.size() == 1) && (s.contains(v))) {
                    Set<Integer> set = new HashSet<>();
                    set.add(v);
                    return new IncrementalTestState(false, set);
                }
            }
            Set<Integer> set = createEmptyState();
            set.add(v);
            return new IncrementalTestState(true, set);
        } else {
            final Set<Integer> sss = new HashSet<Integer>((Set<Integer>) state);
            sss.add(v);
            for (final Set<Integer> s : mus) {
                if (sss.containsAll(s)) {
                    final IncrementalTestState ssss = new IncrementalTestState(
                        false, sss);
                    return ssss;
                }
            }
            return new IncrementalTestState(true,
                sss);
        }
    }

    /**
     * {@inheritDoc}
     */
    public IncrementalTestState test(List<Integer> component, Object state) {

        IncrementalTestState newState = (IncrementalTestState) state;

        for (final Integer i : component) {
            newState = test(i, newState.state);
        }

        return newState;
    }

    /**
     * {@inheritDoc}
     */
    public Object copyState(Object object) {
        return new HashSet<>((Set<Integer>) object);
    }
}