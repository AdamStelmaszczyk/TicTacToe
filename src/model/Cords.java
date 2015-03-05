package model;

import java.io.Serializable;

/**
 * Przechowuje współrzędną pola na planszy.
 * 
 * @author Adam Stelmaszczyk
 * @version 2011-07-12
 */
public class Cords
{
    /** Numer pola. */
    final private int number;

    /**
     * Konstruktor współrzędnych.
     * 
     * @param number Numer pola.
     */
    public Cords(final int number)
    {
        this.number = number;
    }
    
    /**
     * Zwraca numer pola.
     * 
     * @return Numer pola.
     */
    public int getIndex()
    {
        return number;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return Integer.toString(number);
    }

}