package cz.cvut.kbss.explanation;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import lombok.extern.slf4j.Slf4j;
import org.jgraph.JGraph;
import org.jgraph.layout.JGraphLayoutAlgorithm;
import org.jgraph.layout.TreeLayoutAlgorithm;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedMultigraph;

@Slf4j
public class GeneralGraphView<V> extends JPanel implements Listener<V> {

    private final JGraph gg;

    private final Graph<V, DefaultEdge> g = new DefaultListenableGraph<>(
        new DirectedMultigraph<>(
            (sourceVertex, targetVertex) -> {
                final DefaultEdge e = new DefaultEdge() {
                    // Object source = sourceVertex;
                    //
                    // Object target = targetVertex;
                    // TODO
                    public String toString() {
                        return "";
                    }
                };
                return e;
            }));

    public GeneralGraphView() {

        gg = new JGraph(new JGraphModelAdapter<>(g));

        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(gg), BorderLayout.CENTER);
    }

    public void refresh() {
        SwingUtilities.invokeLater(new Runnable() {
            /**
             * {@inheritDoc}
             */
            public void run() {
                gg.getGraphLayoutCache().setVisible(gg.getRoots(), true);
                synchronized (gg.getGraphLayoutCache()) {
                    try {
                        TreeLayoutAlgorithm a = new TreeLayoutAlgorithm();

                        a.setCenterRoot(true);
                        a.setLevelDistance(150);

                        JGraphLayoutAlgorithm.applyLayout(gg, gg.getRoots(), a);

                    } catch (Exception e) {
                        log.debug(e.getMessage(), e);
                    }
                }
            }
        });
    }

    public void addEdge(V v, V v2) {
        log.debug("Adding edge : (" + v + " -> " + v2 + ")");
        g.addEdge(v, v2);
    }

    public void addVertex(V v) {
        log.debug("Adding vertex : " + v);
        g.addVertex(v);
    }

    public void removeEdge(V v, V v2) {
        log.debug("Removing edge : (" + v + " -> " + v2 + ")");
        g.removeEdge(v, v2);
    }

    public void removeVertex(V v) {
        log.debug("Removing vertex : " + v);
        g.removeVertex(v);
    }
}
