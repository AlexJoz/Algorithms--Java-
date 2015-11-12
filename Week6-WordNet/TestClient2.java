import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class TestClient2 {
    public static void main(String[] args) {
        System.out.println(args[0] + "  " + args[1]);

        // wordnet testing
        WordNet wordnet = new WordNet(args[0], args[1]);

        // check .nouns
        for (String string : wordnet.nouns()) {
            // System.out.println(string);
        }

        // check isNoun: true;
        // System.out.println(wordnet.isNoun("a"));

        // illegal argument exception
        // System.err.println(wordnet.sap("b", "a"));

        Outcast outcast = new Outcast(wordnet);
        
        In outcastset = new In(args[2]);
        while (!outcastset.isEmpty()) {
            String[] nouns = outcastset.readAllStrings();
            StdOut.println(outcast.outcast(nouns));
        }
    }
}
