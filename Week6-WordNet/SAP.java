import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

public class SAP {
    private Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.graph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        jCheck(v);
        jCheck(w);
        Jbfs bfs = new Jbfs(graph, toIterable(v), toIterable(w));
        return bfs.getL();
    }

    // a common ancestor of v and w that participates in a shortest ancestral
    // path; -1 if no such path
    public int ancestor(int v, int w) {
        jCheck(v);
        jCheck(w);
        Jbfs bfs = new Jbfs(graph, toIterable(v), toIterable(w));
        return bfs.getA();
    }

    // length of shortest ancestral path between any vertex in v and any vertex
    // in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        jCheck(v);
        jCheck(w);
        Jbfs bfs = new Jbfs(graph, v, w);
        return bfs.getL();
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no
    // such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        jCheck(v);
        jCheck(w);
        Jbfs bfs = new Jbfs(graph, v, w);
        return bfs.getA();
    }

    private Iterable<Integer> toIterable(int v) {
        Queue<Integer> toQueue = new Queue<>();
        toQueue.enqueue(v);
        return toQueue;
    }

    private void jCheck(Iterable<Integer> vs) {
        for (int v : vs)
            jCheck(v);
    }

    private void jCheck(int v) {
        if (v >= graph.V())
            throw new IndexOutOfBoundsException();
    }
}

class Jbfs {
    private final int vertexes;
    private final BreadthFirstDirectedPaths v, w;
    private int a = -1;
    private int minL = -1;

    Jbfs(final Digraph g, final Iterable<Integer> v, final Iterable<Integer> w) {
        vertexes = g.V();
        this.v = new BreadthFirstDirectedPaths(g, v);
        this.w = new BreadthFirstDirectedPaths(g, w);
        getAncest();
    }

    private void getAncest() {
        for (int i = 0; i < vertexes; i++) {
            if (hasBothPath(i) && (minL == -1 || minL > getL(i))) {
                minL = getL(i);
                this.a = i;
            }
        }
    }

    private boolean hasBothPath(int t) {
        return (this.v.hasPathTo(t) && this.w.hasPathTo(t));
    }

    private int getL(final int t) {
        return this.v.distTo(t) + this.w.distTo(t);
    }

    public int getA() {
        return this.a;
    }

    public int getL() {
        return this.minL;
    }
}
