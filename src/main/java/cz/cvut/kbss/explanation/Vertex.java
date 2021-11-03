package cz.cvut.kbss.explanation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Vertex<T> {
    private final List<T> d;

    private final List<T> p;

    private final Iterator<T> i;

    private List<T> childD;

    private List<T> childP;

    private T out;

    public Vertex(List<T> d, List<T> p) {
        this.d = d;
        this.p = p;
        i = p.iterator();
        // first
        childD = null;
        childP = null;
    }

    public List<T> getD() {
        return d;
    }

    public List<T> getP() {
        return p;
    }

    public Vertex<T> nextChild() {
        if (i.hasNext()) {
            T formerOut = out;
            out = i.next();
            if (childD == null) {
                childD = new ArrayList<T>(d);
                childP = new ArrayList<T>(p);
                childP.remove(out);
            } else {
                childD.add(formerOut);
                childP.remove(out);
            }
            return new Vertex<T>(new ArrayList<T>(childD), new ArrayList<T>(
                childP));
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (!(object instanceof Vertex)) {
            return false;
        } else {
            Vertex c = (Vertex) object;
            return d.equals(c.d) && (p.equals(c.p));
        }
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        int result = 7;
        result = 37 * result + d.hashCode();
        result = 37 * result + p.hashCode();
        return result;
    }

    public String toString() {
        return d.toString() + ", " + p.toString();
    }

}
