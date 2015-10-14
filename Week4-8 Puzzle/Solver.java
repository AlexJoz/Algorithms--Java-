import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private JNode bNode, bTwinNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        int moves = 0;
        bNode = new JNode(initial, moves);
        bTwinNode = new JNode(initial.twin(), moves);
        MinPQ<JNode> jQueue = new MinPQ<>();
        
        //insert both
        jQueue.insert(bNode);
        jQueue.insert(bTwinNode);
        //choose nice one to solve
        bNode = jQueue.delMin();
        
        // till the very end...
        while (!bNode.isGoal()) {
            // make node neighbors stack
            Stack<Board> neighbors = new Stack<Board>();
            //and add all the neighbors for this node there
            for (Board b : bNode.neighbors()) {
                neighbors.push(b);
            }

            moves = bNode.moves + 1;

            // now check each neighbor
            for (Board b : neighbors) {
                Board bPrevious = null;

                // temp previous state
                if (bNode.previous != null) {
                    bPrevious = bNode.previous.getjBoard();
                }
                // when find really NEW one, 
                // add new node to Queue
                if (!b.equals(bPrevious)) {
                    JNode node = new JNode(b, moves);
                    node.previous = bNode;
                    jQueue.insert(node);
                }
            }
            // next node to proceed
            bNode = jQueue.delMin();
        }
    }

    // returns true if the initial board solvable
    public boolean isSolvable() {
        // get solved
        JNode firstNode = bNode;
        // and move backwards to the start
        while (firstNode.previous != null) {
            firstNode = firstNode.previous;
        }
        // compare init state
        // if solved one not twin, so exactly initial was solvable haha xD
        // or vise versa
        return firstNode.getjBoard().equals(bTwinNode.getjBoard()) ? false : true;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? bNode.moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        JNode sequenceNode = bNode;

        if (isSolvable()) {
            Stack<Board> solutionQueue = new Stack<Board>();

            solutionQueue.push(sequenceNode.getjBoard());
            while (sequenceNode.previous != null) {
                sequenceNode = sequenceNode.previous;
                solutionQueue.push(sequenceNode.getjBoard());
            }
            return solutionQueue;
        } else 
            return null;
    }

    private class JNode implements Comparable<JNode> {
        private JNode previous;
        private Board jBoard;
        private int moves;

        public JNode(Board jBoard, int moves) {
            this.jBoard = jBoard;
            this.moves = moves;
            previous = null;
        }

        public Board getjBoard() {
            return jBoard;
        }

        public boolean isGoal() {
            return jBoard.isGoal();
        }

        public Iterable<Board> neighbors() {
            return jBoard.neighbors();
        }

        // add moves to simple manhattan
        public int heuristic() {
            return jBoard.manhattan() + moves;
        }

        @Override
        public int compareTo(JNode that) {
            return this.heuristic() - that.heuristic();
        }

        @Override
        public String toString() {
            return jBoard.toString();
        }
    }

}