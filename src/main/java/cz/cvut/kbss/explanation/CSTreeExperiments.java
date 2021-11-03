package cz.cvut.kbss.explanation;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.JFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CSTreeExperiments<V> {

    /**
     * Shows a CS tree for a given set of integers.
     *
     * @param x set of integers to generate a CS tree for.
     */
    private static void showCSTree(final Set<Integer> x) {
        final AllSubsets<Integer> s = new AllSubsets<>();
        final GeneralGraphView<SimpleVertex<Integer>> v =
            new GeneralGraphView<>();
        s.addListener(v);

        final JFrame f = new JFrame();
        f.getContentPane().add(v);
        f.pack();
        f.setVisible(true);
        f.setSize(new Dimension(800, 600));
        f.setTitle("CS tree for " + x);

        s.allSubsets(new HashSet<>(), x, new HashSet<>());

        v.refresh();
    }

    private static void showCSTreeAndComputeMUS(
        final CSTreeMUSAlgorithm<Integer> a, final List<Integer> x) {
        final GeneralGraphView<SimpleVertex<Integer>> v =
            new GeneralGraphView<>();
        a.addListener(v);

        final JFrame f = new JFrame();
        f.getContentPane().add(v);
        f.pack();
        f.setVisible(true);
        f.setSize(new Dimension(800, 600));
        f.setTitle("CS tree for " + x + ", and algorithm " + a);

        log.info("Searching for all MUSes of " + x + " using " + a + " ... ");
        final Set<Set<Integer>> all = new HashSet<>();
        a.find(new ArrayList<>(), x, all);
        log.info("MUSes = " + all);

        v.refresh();

    }

    private static Test<Integer> create(final Set<Set<Integer>> musCollection) {
        return v -> musCollection.stream().noneMatch(v::containsAll);
    }

    public static void main(final String[] args) {

        final List<Set<Set<Integer>>> data = CSTreeSampleData.getMUSes();

        showCSTree(new HashSet<>(get(3)));

        showCSTreeAndComputeMUS(new MinUnsat3<>(new
                MinUnsat2<>(create(data.get(3)))),
            get(4));

        showCSTreeAndComputeMUS(new MinUnsat4<>(new
                MinUnsat2<>(
                create(data.get(3))), new IntegerPartitioner(CSTreeSampleData.getPartition())),
            get(4));
    }

    private static final List<Integer> get(int i) {
        return IntStream.rangeClosed(1, i).boxed().collect(Collectors.toList());
    }
}
