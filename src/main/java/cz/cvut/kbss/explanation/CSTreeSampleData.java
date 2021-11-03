package cz.cvut.kbss.explanation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CSTreeSampleData {

    private static final List<Set<Set<Integer>>> muses = new ArrayList<>();
    /**
     * map to variables
     */
    private static final Map<Integer, List<Integer>> PARTITION_4 =
        new HashMap<>();

    static {
        muses.add(getMUS(new Integer[][] {{1, 5}, {2, 5}, {3, 5},
            {4, 5}}));

        muses.add(getMUS(new Integer[][] {{3, 4}, {1, 2}, {5, 10}}));

        muses
            .add(getMUS(new Integer[][] {{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}}));
        muses.add(getMUS(new Integer[][] {{1}, {2, 3}, {2, 4}}));
        muses.add(getMUS(new Integer[][] {{1, 5, 8, 9}}));
        muses
            .add(getMUS(new Integer[][] {{1}, {2, 3}, {2, 4},
                {2, 5}}));
        muses
            .add(getMUS(new Integer[][] {{1, 2, 3}, {2, 3, 4},
                {4, 5}}));
        muses.add(getMUS(new Integer[][] {{1, 2}, {2, 3}, {1, 3}}));
        muses.add(getMUS(new Integer[][] {{1, 2}, {1, 3}, {2, 4}}));
        muses.add(getMUS(new Integer[][] {{1, 2, 4}, {3}}));
        muses.add(getMUS(new Integer[][] {{1, 2}, {1, 3}}));
        muses.add(getMUS(new Integer[][] {{2, 3, 4}}));
    }

    static {
        PARTITION_4.put(1, Arrays.asList(1, 2));
        PARTITION_4.put(2, Arrays.asList(2, 3, 4));
        PARTITION_4.put(3, Arrays.asList(3, 4, 5));
        PARTITION_4.put(4, Arrays.asList(4, 5));
    }

    public static List<Set<Set<Integer>>> getMUSes() {
        return muses;
    }

    public static Map<Integer, List<Integer>> getPartition() {
        return PARTITION_4;
    }

    private static Set<Set<Integer>> getMUS(final Integer[][] o) {
        return
            Arrays.stream(o).map(s -> new HashSet<>(Arrays.asList(s))).collect(Collectors.toSet());
    }
}
