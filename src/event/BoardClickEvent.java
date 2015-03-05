package event;

import model.Cords;
import model.FieldType;

/**
 * Kliknięcie na planszy.
 * 
 * @author Adam Stelmaszczyk
 * @version 2011-07-12
 */
public class BoardClickEvent extends ProgramEvent
{
    /** Numer wersji. */
    private static final long serialVersionUID = 1;
    /** Współrzędne kliknięcia. */
    private final Cords click;
    /** Gracz. */
    private final FieldType player;

    /**
     * Konstruktor.
     * 
     * @param click Współrzędne kliknięcia.
     */
    public BoardClickEvent(final Cords click, final FieldType player)
    {
        this.click = click;
        this.player = player;
    }

    /**
     * Zwraca współrzędne kliknięcia.
     * 
     * @return Współrzędne kliknięcia.
     */
    public Cords getCords()
    {
        return click;
    }
    
    /**
     * Zwraca gracza.
     * 
     * @return Gracz.
     */
    public FieldType getPlayer()
    {
        return player;
    }

}
