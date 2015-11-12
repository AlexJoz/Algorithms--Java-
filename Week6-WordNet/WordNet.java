import java.util.HashMap;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

public class WordNet {
    private HashMap<Integer, String> idsyns;
    private HashMap<String, Bag<Integer>> nounids;
    private SAP jSap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In one = new In(synsets);
        In two = new In(hypernyms);
        idsyns = new HashMap<>();
        nounids = new HashMap<>();
        takeSynsets(one);
        jSap = new SAP(takeHypernyms(two));
    }

    // take care of Hypernyms and make digraph
    private Digraph takeHypernyms(In two) {
        Digraph G = new Digraph(idsyns.size());
        while (!two.isEmpty()) {
            String[] line = two.readLine().split(",");
            int v = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                int w = Integer.parseInt(line[i]);
                G.addEdge(v, w);
            }
        }
        checkCycle(G);
        return G;
    }

    // determining whether a digraph has a directed cycle
    private void checkCycle(Digraph g) {
        DirectedCycle cycled = new DirectedCycle(g);
        // also check multiroot
        int root = 0;
        for (int v = 0; v < g.V(); v++) {
            // check if theres no adjacent vertexes so it's root vertex
            if (!g.adj(v).iterator().hasNext())
                root++;
        }
        if (cycled.hasCycle() || root != 1)
            throw new java.lang.IllegalArgumentException();
    }

    // take care of all nouns
    private void takeSynsets(In one) {
        Bag<Integer> nounBag;
        while (!one.isEmpty()) {
            String[] ins = one.readLine().split(",");
            int id = Integer.parseInt(ins[0]);
            String nouns = ins[1];
            // meaning = ins[2];
            idsyns.put(id, nouns);
            for (String noun : ins[1].split(" ")) {
                nounBag = nounids.get(noun);
                if (nounBag == null) {
                    nounBag = new Bag<Integer>();
                    nounBag.add(id);
                    nounids.put(noun, nounBag);
                } else
                    nounBag.add(id);
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounids.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if(word == null) throw new java.lang.NullPointerException();
        return nounids.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new java.lang.IllegalArgumentException();
        return jSap.length(nounids.get(nounA), nounids.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of
    // nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new java.lang.IllegalArgumentException();
        return idsyns.get(jSap.ancestor(nounids.get(nounA), nounids.get(nounB)));
    }

}
