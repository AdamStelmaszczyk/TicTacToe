package model.ai.precomputed;

import model.Board;
import model.Cords;
import model.ai.AI;

/**
 * Zwraca optymalny ruch z wcześniej przygotowanej tablicy optymalnych ruchów.
 *
 * @author Adam Stelmaszczyk
 * @version 2015-02-22
 */
public class Precomputed extends AI {

    /**
     * Stablicowane ruchy dla komputera.
     */
    private int[] moves = new int[MovesTable.SIZE];

    public Precomputed() {
        MovesTable movesTable = new MovesTable();
        moves = movesTable.getMovesTable();
    }

    /**
     * Szybko zwraca wcześniej obliczony ruch dla danej planszy.
     *
     * @param board Plansza.
     * @return Ruch komputera.
     */
    public Cords computeMove(Board board) {
        int boardNumber = board.getNumber();
        int moveNumber = moves[boardNumber];
        Cords move = new Cords(moveNumber);
        return move;
    }

    @Override
    public int getCounter() {
        return 0;
    }
}
