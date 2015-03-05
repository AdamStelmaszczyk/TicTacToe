package model;

/**
 * Klasa modelu - samej logiki gry, zasad.
 * 
 * @author Adam Stelmaszczyk
 * @version 2011-07-12
 */
public class Model
{
    /** Bieżąca sytuacja na planszy, rozmieszczenie pionków. */
    private final Board board;
    /** Czyja kolej. */
    private FieldType whoseTurn;

    /**
     * Konstruktor.
     */
    public Model(FieldType whoStarts)
    {
        this.board = new Board(0);
        this.whoseTurn = whoStarts;
    }

    /**
     * Zwraca planszę.
     * 
     * @return Kopia planszy.
     */
    public Board getBoard()
    {
        return board;
    }

    /**
     * Kto wygrał?
     * 
     * @return Gracz, który wygrał.
     */
    public FieldType getWinner()
    {
        if (!isGameOver())
        {
            throw new RuntimeException("Gra jeszcze się nie skończyła, nie wiadomo kto wygrał.");
        }
        
        if (board.isWinner(FieldType.USER))
        {
            return FieldType.USER;
        }
        
        if (board.isWinner(FieldType.AI))
        {
            return FieldType.AI;
        }
        
        if (board.isDraw())
        {
            return FieldType.EMPTY;
        }
        
        throw new RuntimeException("Błąd podczas określania kto wygrał.");
    }

    /**
     * Zwraca true, jeśli gra dobiegła końca.
     * Gra się kończy jeśli albo któryś z graczy nie ma pionków, albo aktualny gracz nie ma ruchu.
     * 
     * @return True, jeśli gra dobiegła końca.
     */
    public boolean isGameOver()
    {
        return board.isDraw() || board.isWinner(FieldType.USER) || board.isWinner(FieldType.AI);
    }

    /**
     * Informuje model o kliknięciu na planszy.
     * 
     * @param click Kliknięte współrzędne.
     */
    public void doClick(final Cords click, FieldType player)
    {
        if (!isPlayerTurn(player))
        {
            return;
        }
        board.setField(click, player);
        flipTurn();
    }
    
    /**
     * Czy tura należy do danego gracza?
     * 
     * @param player Gracz.
     * @return True, jeśli tura się zgadza.
     */
    boolean isPlayerTurn(FieldType player)
    {
        return player == whoseTurn;
    }
    
    /**
     * Zmienia kolej gracza na drugiego.
     */
    void flipTurn()
    {
        whoseTurn = whoseTurn.negate();
    }

    /**
     * Czy ruch w dane pole jest poprawny?
     * 
     * @param click Kliknięte pole.
     * @return True, jeśli jest poprawny.
     */
    public boolean isValidMove(Cords click)
    {
        return board.isEmpty(click);
    }

}
