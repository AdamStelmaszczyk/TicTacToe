package view;

/**
 * Klasa przechowująca stałe wykorzystywane przy rysowaniu okna i interfejsu.
 * 
 * @author Adam Stelmaszczyk
 * @version 2011-07-12
 */
class ViewConfig
{
    /**
     * Nazwy plików obrazków. Odpowiednio dla:
     * gracza, komputera, pustego pola.
     */
    static final String[] FILENAME = { "empty.jpg", "user.jpg", "ai.jpg" };
    /** Szerokość okna w pikselach. */
    static final int WINDOW_WIDTH = 450;
    /** Wysokość okna w pikselach. */
    static final int WINDOW_HEIGHT = 270;
    /** Lewy górny róg planszy, x. */
    static final int BOARD_X = 30;
    /** Lewy górny róg planszy, y. */
    static final int BOARD_Y = 30;
    /** Szerokość pola w pikselach. */
    static final int FIELD_WIDTH = 60;
    /** Wysokość pola w pikselach. */
    static final int FIELD_HEIGHT = 60;
    /** Szerokość planszy. */
    static final int BOARD_WIDTH = 3 * FIELD_WIDTH;
    /** Wysokość planszy. */
    static final int BOARD_HEIGHT = 3 * FIELD_HEIGHT;

}
