package cz.cvut.kbss.explanation.reiter;

import java.util.Set;

class MUSVertex<T> {
    private final Set<T> mups;

    public MUSVertex(Set<T> mups) {
        this.mups = mups;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (!(object instanceof MUSVertex)) {
            return false;
        } else {
            final MUSVertex<T> c = (MUSVertex<T>) object;
            return mups.equals(c.mups);
        }
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return mups.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return mups.toString();
    }

}
