package cz.cvut.kbss.explanation.reiter;

class StringVertex {

    private static int id = 0;

    private final String s;
    private final int id2;

    public StringVertex(String s) {
        this.id2 = id++;
        this.s = s;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (!(object instanceof StringVertex)) {
            return false;
        } else {
            StringVertex c = (StringVertex) object;
            return id2 == c.id2;
        }
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return id2;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return s;
    }

}
