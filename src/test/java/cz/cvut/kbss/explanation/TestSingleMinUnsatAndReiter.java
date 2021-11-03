package cz.cvut.kbss.explanation;

import static org.junit.jupiter.api.Assertions.assertEquals;


import cz.cvut.kbss.explanation.reiter.ReiterAlgorithm;
import cz.cvut.kbss.explanation.reiter.SingleIncrementalMUS;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestSingleMinUnsatAndReiter {

    private static final Logger LOG = Logger
        .getLogger(TestSingleMinUnsatAndReiter.class.getName());

    @Test
    @Disabled
    public void testMUS1() {
        final IntegerIncrementalTest t = new IntegerIncrementalTest(
            MinUnsatTestBenchmark.MUS1);
        final SingleIncrementalMUS<Integer> s = new SingleIncrementalMUS<Integer>(
            t, true);

        final ReiterAlgorithm<Integer> ra = new ReiterAlgorithm<Integer>(s);

        int i = t.n;
        List<Integer> ls = MinUnsatTestBenchmark.get(6);

        Set<Set<Integer>> mus = ra.find(ls);
        Set<Set<Integer>> hs = ra.getHittingSets();
        LOG.info("MUS1: hs=" + hs + ", mus= " + mus + ", with " + (t.n - i)
            + " tests");

        assertEquals(MinUnsatTestBenchmark.MUS1, mus);
        assertEquals(MinUnsatTestBenchmark.HS1, hs);
    }
}
