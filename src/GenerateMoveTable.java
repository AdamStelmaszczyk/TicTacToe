import model.Board;
import model.Cords;
import model.ai.AI;
import model.ai.minmax.MinMax;
import model.ai.precomputed.MovesTable;

/**
 * Generuje ruchy komputera dla wszystkich możliwych plansz. Wynik tej funkcji zapisany jest w movesTable.txt.
 *
 * @author Adam Stelmaszczyk
 * @version 2015-02-22
 */
public class GenerateMoveTable {

    public static void main(final String[] args) {
        for (int i = 0; i < MovesTable.SIZE; i++) {
            Board board = new Board(i);
            AI computerAI = new MinMax();
            Cords move = computerAI.computeMove(board);
            System.out.print(move + " ");
        }
    }
}
