package cz.cvut.kbss.explanation;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class CSTreeTests {

    private static List<Integer> get(final int i) {
        return IntStream.rangeClosed(1, i).boxed().collect(Collectors.toList());
    }

    @Test
    public void computesMUSesCorrectly() {
        final List<Set<Set<Integer>>> data = CSTreeSampleData.getMUSes();
        final IntegerIncrementalTest t = new IntegerIncrementalTest(data
            .get(5));
        final AllMUSesAlgorithm<Integer> s = new CachedMinUnsat8<>(t);
        final List<Integer> ls = get(4);
        Collections.reverse(ls);
        final Set<Set<Integer>> l = s.find(ls);
        log.info("MUSes=" + l);
        Assertions.assertEquals(3, l.size());
    }
}
