package cz.cvut.kbss.explanation;

import static cz.cvut.kbss.explanation.MinUnsatTestBenchmark.HS1;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;

public class TestMinUnsat8WithCachingAndHittingSets {

    private static final Logger LOG = Logger
        .getLogger(TestMinUnsat8WithCachingAndHittingSets.class.getName());

    @Test
    public void testMUS1() {
        final IntegerIncrementalTest t = new IntegerIncrementalTest(
            MinUnsatTestBenchmark.MUS1);
        final MinUnsat8WithCachingAndHittingSets<Integer> s =
            new MinUnsat8WithCachingAndHittingSets<Integer>(
                t);

        int i = t.n;
        List<Integer> ls = MinUnsatTestBenchmark.get(6);
        Set<Set<Integer>> mus = s.find(ls, new HashSet<Integer>());
        ;
        Set<Set<Integer>> hs = s.getHittingSets();
        LOG.info("MUS1: hs=" + hs + ", mus= " + mus + ", with " + (t.n - i)
            + " tests");

        assertEquals(MinUnsatTestBenchmark.MUS1, mus);
        assertEquals(HS1, hs);
    }

    @Test
    public void testMUS2() {
        final IntegerIncrementalTest t = new IntegerIncrementalTest(
            MinUnsatTestBenchmark.MUS2);
        final MinUnsat8WithCachingAndHittingSets<Integer> s =
            new MinUnsat8WithCachingAndHittingSets<Integer>(
                t);

        int i = t.n;
        List<Integer> ls = MinUnsatTestBenchmark.get(6);
        Set<Set<Integer>> mus = s.find(ls, new HashSet<Integer>());
        ;
        Set<Set<Integer>> hs = s.getHittingSets();
        LOG.info("MUS2: hs=" + hs + ", mus= " + mus + ", with " + (t.n - i)
            + " tests");

        assertEquals(MinUnsatTestBenchmark.MUS2, mus);
    }

    @Test
    public void testMUS3() {
        final IntegerIncrementalTest t = new IntegerIncrementalTest(
            MinUnsatTestBenchmark.MUS3);
        final MinUnsat8WithCachingAndHittingSets<Integer> s =
            new MinUnsat8WithCachingAndHittingSets<Integer>(
                t);

        int i = t.n;
        List<Integer> ls = MinUnsatTestBenchmark.get(8);
        Set<Set<Integer>> mus = s.find(ls, new HashSet<Integer>());
        ;
        Set<Set<Integer>> hs = s.getHittingSets();
        LOG.info("MUS3: hs=" + hs + ", mus= " + mus + ", with " + (t.n - i)
            + " tests");

        assertEquals(MinUnsatTestBenchmark.MUS3, mus);
    }

    @Test
    public void testMUS4() {
        final IntegerIncrementalTest t = new IntegerIncrementalTest(
            MinUnsatTestBenchmark.MUS4);
        final MinUnsat8WithCachingAndHittingSets<Integer> s =
            new MinUnsat8WithCachingAndHittingSets<Integer>(
                t);

        int i = t.n;
        List<Integer> ls = MinUnsatTestBenchmark.get(3);
        Set<Set<Integer>> mus = s.find(ls, new HashSet<Integer>());
        ;
        Set<Set<Integer>> hs = s.getHittingSets();
        LOG.info("MUS4: hs=" + hs + ", mus= " + mus + ", with " + (t.n - i)
            + " tests");

        assertEquals(MinUnsatTestBenchmark.MUS4, mus);
    }
}
