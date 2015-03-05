package model.ai.mcts;

import model.Board;
import model.Cords;
import model.FieldType;

import java.util.*;

public class Node {

    /**
     * Board state associated with this node. *
     */
    private final Board board;
    /**
     * Move which created this node.
     */
    private final Cords move;
    /**
     * Player who made the move to create this state.
     */
    private final FieldType player;
    /**
     * Children of this node.
     */
    private List<Node> children = new ArrayList<Node>();
    /**
     * How many times we visited this node?
     */
    private int visits;
    /**
     * How many points we won starting from this node (move)?
     */
    private double points;

    private static final double EPSILON = 10e-6;

    Node(Board board, Cords move, FieldType player) {
        this.board = board;
        this.move = move;
        this.player = player;
    }

    /**
     * Expand children of this node.
     */
    public void expand() {
        FieldType nextPlayer = player.negate();

        // If the opponent has a winning move - he makes it.
        Cords move = board.getWinningMove(nextPlayer);
        if (move != null) {
            Board nextBoard = new Board(board);
            nextBoard.setField(move, nextPlayer);
            children.add(new Node(nextBoard, move, nextPlayer));
            return;
        }

        // If the opponent has a blocking move - he makes it.
        move = board.getBlockingMove(nextPlayer);
        if (move != null) {
            Board nextBoard = new Board(board);
            nextBoard.setField(move, nextPlayer);
            children.add(new Node(nextBoard, move, nextPlayer));
            return;
        }

        // If not - we branch on all his possible movements.
        List<Cords> emptyCords = board.getCordsOfEmptyFields();
        children = new ArrayList<Node>(emptyCords.size());
        for (Cords empty : emptyCords) {
            Board nextBoard = new Board(board);
            nextBoard.setField(empty, nextPlayer);
            children.add(new Node(nextBoard, empty, nextPlayer));
        }
    }

    /**
     * Wybierz najlepsze dziecko.
     *
     * @return Zwraca dziecko o największym UCT.
     */
    public Node selectBestChild() {
        if (children.isEmpty()) {
            return null;
        }
        Node selected = null;
        double bestValue = Double.NEGATIVE_INFINITY;
        for (Node child : children) {
            double uctValue = child.points / (child.visits + EPSILON) +
                    Math.sqrt(2) * Math.sqrt(Math.log(visits + 1) / (child.visits + EPSILON)) +
                    Math.random() * EPSILON;
            // small random number to break ties randomly in unexpanded nodes
            if (uctValue > bestValue) {
                selected = child;
                bestValue = uctValue;
            }
        }
        return selected;
    }

    public void updateStats(double result) {
        visits++;
        points += result;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public List<Node> getChildren() {
        return children;
    }

    public double getWinProbability() {
        if (visits == 0) {
            return 0;
        }
        return points / visits;
    }

    public Cords getMove() {
        return move;
    }

    public Board getBoard() {
        return board;
    }

    public FieldType getPlayer() {
        return player;
    }

    public int getVisits() {
        return visits;
    }

    /**
     * @return The best move in terms of the highest probability of win.
     */
    public Node getBestChild() {
        Node best = children.get(0);
        for (int i = 1; i < children.size(); i++) {
            Node child = children.get(i);
            if (best.getWinProbability() < child.getWinProbability()) {
                best = child;
            }
        }
        return best;
    }
}