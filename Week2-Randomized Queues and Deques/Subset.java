import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {

    public static void main(String[] args) {
        RandomizedQueue<Object> fuckYouSedgewick = new RandomizedQueue<>();
        
        int k = Integer.parseInt(args[0]);
        String[] simpleStrings = new String[k];

        for (int i = 0; i < k; i++) {
            simpleStrings[i] = StdIn.readString();
            fuckYouSedgewick.enqueue(simpleStrings[i]);
        }
        StdRandom.shuffle(simpleStrings);
        for (String s : simpleStrings) {
            StdOut.println(s);
        }
    }

}
