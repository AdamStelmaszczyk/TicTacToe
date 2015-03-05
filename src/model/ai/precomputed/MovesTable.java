package model.ai.precomputed;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Klasa wczytująca z pliku wcześniej obliczone ruchy dla każdej z możliwych 19683 (3^9) plansz.
 * 
 * @author Adam Stelmaszczyk
 * @version 2011-07-13
 */
public class MovesTable
{
    /** Liczba wszystkich możliwych plansz. Jest 9 pól, na każdym mogą być 3 symbole, stąd 3^9. */
    public final static int SIZE = (int) Math.pow(3, 9);
    /** Tablica liczb oznaczających numer kratki w którą się ruszyć na danej planszy. */
    private final int[] movesTable = new int[SIZE];

    /**
     * Konstruktor. Wczytuje liczby z przygotowanego pliku do tablicy.
     */
    public MovesTable()
    {
        InputStream stream = MovesTable.class.getResourceAsStream("/resources/movesTable.txt");
        Scanner scanner = new Scanner(stream);
        int i = 0;
        while (scanner.hasNext())
        {
            movesTable[i] = scanner.nextInt();
            i++;
        }
    }
    
    /**
     * Zwraca tablicę ruchów dla AI.
     * 
     * @return Tablica ruchów.
     */
    public int[] getMovesTable()
    {
        return movesTable;
    }
    
}