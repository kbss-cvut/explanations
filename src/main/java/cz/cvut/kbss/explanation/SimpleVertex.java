package cz.cvut.kbss.explanation;

import java.util.List;

public class SimpleVertex<T> {
    private final List<T> d;

    private final List<T> p;

    public SimpleVertex(final List<T> d, final List<T> p) {
        this.d = d;
        this.p = p;
    }

    public List<T> getD() {
        return d;
    }

    public List<T> getP() {
        return p;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (!(object instanceof SimpleVertex)) {
            return false;
        } else {
            SimpleVertex c = (SimpleVertex) object;
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
