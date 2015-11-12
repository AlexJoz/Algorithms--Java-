import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Outcast {
    private WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns) {
        // permutations of 2
        return choose(nouns, 2);
    }

    // lol, just simple try of array to stack conversion =)
    // u can use simple nested loops instead
    private String choose(String[] nouns, int i) {
        int maxD = 0;
        String outcast = null;
        List<String> list = Arrays.asList(nouns);
        Stack<String> nStack = new Stack<>();
        nStack.addAll(list);
        while (!nStack.empty()) {
            String out = nStack.pop();
            int d = getD(nouns, out);
            if (d > maxD) {
                maxD = d;
                outcast = out;
            } 
        }
        return outcast;
    }

    private int getD(String[] nouns, String one) {
        int d = 0;
        for (String noun : nouns) {
            if (!one.equals(noun))
                d += wordnet.distance(one, noun);
        }
        return d;
    }

}