package cz.cvut.kbss.explanation;

public interface Listener<T> {

    void addVertex(final T v);

    void removeVertex(final T v);

    void addEdge(final T v, final T v2);

    void removeEdge(final T v, final T v2);
}