package view;

import javax.swing.ImageIcon;

/**
 * Klasa wczytująca obrazki na podstawie nazwy pliku.
 * 
 * @author Adam Stelmaszczyk
 * @version 2011-07-12
 */
public class ImageLoader
{
    /** Wczytany obrazek. */
    private final ImageIcon image;

    /**
     * Konstruktor.
     * 
     * @param fileName Nazwa obrazka do wczytania.
     */
    ImageLoader(final String fileName)
    {
        image = new ImageIcon(getClass().getResource("images/" + fileName));
    }

    /**
     * Zwraca wczytany obrazek.
     * 
     * @return Wczytany obrazek.
     */
    ImageIcon getImage()
    {
        return image;
    }

}
