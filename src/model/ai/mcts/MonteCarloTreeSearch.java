package model.ai.mcts;

import model.Board;
import model.Cords;
import model.FieldType;
import model.ai.AI;

import java.util.ArrayList;
import java.util.List;

/**
 * Monte Carlo tree search algorithm.
 *
 * @author Adam Stelmaszczyk
 * @version 2015-02-22
 */
public class MonteCarloTreeSearch extends AI {

    private final static int PLAY_OUTS = 200;
    private static int boardsCalculated;

    private final static double POINTS_FOR_WIN = 1;
    private final static double POINTS_FOR_DRAW = 0.9;
    private final static double POINTS_FOR_LOSS = 0;

    /**
     * Oblicza ruch komputera na danej planszy.
     *
     * @param board Plansza.
     * @return Ruch komputera.
     */
    @Override
    public Cords computeMove(Board board) {
        boardsCalculated = 0;

        Node root = new Node(board, null, FieldType.USER);

        for (int i = 0; i < PLAY_OUTS; i++) {
            fourSteps(root);
        }

        // Find the best move in terms of the highest probability of win
        Node best = root.getBestChild();

        // Visualization
//        TreeView tv = new TreeView(root);
//        tv.showTree("After " + PLAY_OUTS + " play outs");

        return best.getMove();
    }

    /**
     * Perform 4 steps of MCTS: selection, expansion, simulation and propagation.
     */
    public static void fourSteps(Node root) {
        // 1. Selection
        List<Node> visited = new ArrayList<Node>();
        Node current = root;
        visited.add(current);

        // Find the most promising leaf to extend
        while (!current.isLeaf()) {
            current = current.selectBestChild();
            visited.add(current);
        }

        // 2. Expansion
        current.expand();

        Node bestChild = current.selectBestChild();

        if (bestChild == null) {
            // Best child is a terminal state, can't roll out it further
            bestChild = current;
        } else {
            visited.add(bestChild);
        }

        // 3. Simulation
        double result = rollOut(bestChild);

        // 4. Propagation
        for (Node node : visited) {
            node.updateStats(result);
        }
    }

    /**
     * Ultimately a roll out will end in some result value.
     *
     * @param node Starting node state.
     * @return Payout from a roll out.
     */
    public static double rollOut(Node node) {
        boardsCalculated++;

        Board board = node.getBoard();
        FieldType player = node.getPlayer();
        FieldType nextPlayer = player.negate();

        if (board.isWinner(player)) {
            return getValueOfWinner(player); // current player wins
        }
        if (board.isWinner(nextPlayer)) {
            return getValueOfWinner(nextPlayer); // opponent wins
        }
        if (board.getNumberOfEmpty() == 0) {
            return POINTS_FOR_DRAW; // draw
        }

        // If the opponent has a winning move - he makes it.
        Cords move = board.getWinningMove(nextPlayer);
        if (move != null) {
            return getValueOfWinner(nextPlayer); // Current player loses
        }

        // If the opponent has a blocking move - he makes it.
        move = board.getBlockingMove(nextPlayer);
        if (move != null) {
            Board nextBoard = new Board(board);
            nextBoard.setField(move, nextPlayer);
            Node nextNode = new Node(nextBoard, move, nextPlayer);
            return rollOut(nextNode);
        }

        // Move randomly.
        Board nextBoard = new Board(board);
        Cords randomMove = nextBoard.setRandomEmptyField(nextPlayer);
        Node nextNode = new Node(nextBoard, randomMove, nextPlayer);
        return rollOut(nextNode);
    }

    /**
     * Zwraca licznik przeliczonych plansz.
     *
     * @return Licznik.
     */
    @Override
    public int getCounter() {
        return boardsCalculated;
    }

    /**
     * Zwraca wartość punktową zwycięstwa danego gracza.
     *
     * @param player Gracz.
     * @return Punkty.
     */
    public static double getValueOfWinner(FieldType player) {
        return (player.isAI()) ? POINTS_FOR_WIN : POINTS_FOR_LOSS;
    }

}
