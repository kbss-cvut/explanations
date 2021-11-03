package cz.cvut.kbss.explanation;

public interface RecursiveMinimalSubsetSearch<C> extends CSTreeMUSAlgorithm<C> {

    void setRecursion(CSTreeMUSAlgorithm<C> r);

    SimpleVertex<C> getCurrentVertex();
}