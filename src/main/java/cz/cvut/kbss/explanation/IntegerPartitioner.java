package cz.cvut.kbss.explanation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IntegerPartitioner implements Partitioner<Integer> {

    private final Map<Integer, List<Integer>> m;

    public IntegerPartitioner(final Map<Integer, List<Integer>> m) {
        this.m = m;
    }

    public List<List<Integer>> partition(List<Integer> d) {
        List<List<Integer>> p = new ArrayList<List<Integer>>();

        // final List<Set<Vertex>> sets = new ArrayList<Set<Vertex>>();
        //
        // final SimpleDirectedGraph<Vertex, DefaultEdge> g = new
        // SimpleDirectedGraph<Vertex, DefaultEdge>(
        // DefaultEdge.class);
        //
        // for (final Integer c : d) {
        // Vertex cc = new Vertex(c, true);
        // g.addVertex(cc);
        // final List<Integer> list = m.get(c);
        // for (final Integer v : list) {
        // Vertex vv = new Vertex(v, false);
        // if (!g.containsVertex(vv)) {
        // g.addVertex(vv);
        // }
        // g.addEdge(cc, vv);
        // }
        // }
        //
        // ConnectivityInspector<Vertex, DefaultEdge> ii = new
        // ConnectivityInspector<Vertex, DefaultEdge>(
        // g);
        //
        // List<Set<Vertex>> sets = ii.connectedSets();
        //
        // for (final Set<Vertex> s : sets) {
        // List<Integer> l = new ArrayList<Integer>();
        // for (final Vertex v : s) {
        // if (v.constraint) {
        // l.add(v.i);
        // }
        // }
        // p.add(l);
        // }

        return p;

    }

    static class Vertex {
        boolean constraint;

        int i;

        Vertex(Integer i, boolean constraint) {
            this.i = i;
            this.constraint = constraint;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Vertex)) {
                return false;
            }

            Vertex v = (Vertex) o;

            return v.i == i && v.constraint == constraint;
        }

        public int hashCode() {
            return 23 + 19 * i + 19 * Boolean.valueOf(constraint).hashCode();
        }
    }
}