package view;

import javax.swing.JButton;

import model.Cords;

/**
 * Przycisk z obrazkiem, który zna swoje stałe współrzędne na planszy.
 * 
 * @author Adam Stelmaszczyk
 * @version 2011-07-12
 */
class ButtonField extends JButton
{
    /** Numer wersji. */
    private static final long serialVersionUID = 1;
    /** Stałe współrzędne na planszy. */
    private final Cords cords;
    /** Obrazek na przycisku. */
    private ImageLoader image;

    /**
     * Konstruktor.
     * 
     * @param cords Współrzędne na planszy.
     */
    ButtonField(final Cords cords)
    {
        this.cords = cords;
    }

    /**
     * Zwraca współrzędne przycisku na planszy.
     * 
     * @return Współrzędne przycisku na planszy.
     */
    Cords getCords()
    {
        return cords;
    }

    /**
     * Ustawia obrazek na przycisku.
     * 
     * @param fieldType Typ pola.
     */
    void setImage(final String fieldType)
    {
        image = new ImageLoader(fieldType);
        setIcon(image.getImage());
    }

}
