import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class TestClient2 {
    public static void main(String[] args) {
        System.out.println(args[0] + "  " + args[1]);
        WordNet wordnet = new WordNet(args[0], args[1]);
        // Outcast outcast = new Outcast(wordnet);
        // for (int t = 2; t < args.length; t++) {
        // In in = new In(args[t]);
        // String[] nouns = in.readAllStrings();
        // StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        // }
    }
}
