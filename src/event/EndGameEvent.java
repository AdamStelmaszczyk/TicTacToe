package event;

import model.FieldType;

/**
 * Zdarzenie końca gry.
 * 
 * @author Adam Stelmaszczyk
 * @version 2011-07-12
 */
public class EndGameEvent extends ProgramEvent
{
    /** Numer wersji. */
    private static final long serialVersionUID = 1;
    /** Gracz, który zwyciężył. */
    private final FieldType winner;

    /**
     * Konstruktor.
     * 
     * @param winner Gracz, który zwyciężył.
     */
    public EndGameEvent(final FieldType winner)
    {
        this.winner = winner;
    }

    /**
     * Zwraca kolor gracza, który zwyciężył.
     * 
     * @return Kolor gracza, który zwyciężył.
     */
    public FieldType getWinner()
    {
        return winner;
    }

}
