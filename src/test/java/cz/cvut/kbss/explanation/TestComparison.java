package cz.cvut.kbss.explanation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestComparison {

    private static final Logger LOG = Logger.getLogger(TestComparison.class.getName());

    @Test
    public void testEvaluateCaching() {
        final int n = 10;

        final File f = new File("caching_eval_" + n + ".csv");
        final BufferedWriter w;
        try {
            w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                f)));
            w.write("# k\t withCaching\t noCaching\t ratio\n");

            for (int k = 1; k <= n; k++) {
                final IntegerIncrementalTest t = new IntegerIncrementalTest(
                    MinUnsatTestBenchmark.getMUS(n, k));
                final CachedMinUnsat8<Integer> s = new CachedMinUnsat8<Integer>(
                    t);
                int i = t.n;
                List<Integer> ls = MinUnsatTestBenchmark.get(n);
                Set<Set<Integer>> mus1 = s.find(ls);
                LOG.info("MUS5 - MinUnsat8Caching: mus= " + mus1 + ", with "
                    + (t.n - i) + " tests");

                int noTests = t.n - i;
                Assertions.assertEquals(MinUnsatTestBenchmark.getMUS(n, k), mus1);

                i = t.n;
                final MinUnsat8<Integer> s2 = new MinUnsat8<Integer>(t);
                Set<Set<Integer>> mus2 = s2.find(ls);
                ;
                LOG.info("MUS5 - MinUnsat8: mus= " + mus2 + ", with "
                    + (t.n - i) + " tests");

                int noTests2 = t.n - i;
                Assertions.assertEquals(MinUnsatTestBenchmark.getMUS(n, k), mus2);

                w.write(k + "\t " + noTests + "\t " + noTests2 + "\t"
                    + ((double) noTests2 - noTests) / ((double) (noTests2))
                    + "\n");
            }
            w.close();
        } catch (FileNotFoundException e) {
            LOG.log(Level.SEVERE, "Error creating file '" + f.getAbsolutePath() + "'", e);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error writing to file '" + f.getAbsolutePath() + "'", e);
        }
    }
}
