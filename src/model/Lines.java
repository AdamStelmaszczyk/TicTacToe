package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 8 linii na planszy, dzięki którym szybko można sprawdzić bicia.
 * 
 * @author Adam Stelmaszczyk
 * @version 2011-07-13
 */
public class Lines implements Iterable<Line>
{
    /** Tablica linii. */
    private ArrayList<Line> lines = new ArrayList<Line>();
    
    /**
     * Konstruktor.
     */
    public Lines()
    {
        int[][] rawLines = { 
            { 0, 1, 2 }, 
            { 3, 4, 5 },
            { 6, 7, 8 },
            { 0, 3, 6 },
            { 1, 4, 7 },
            { 2, 5, 8 },
            { 0, 4, 8 },
            { 2, 4, 6 }
        };
        
        for (int[] rawLine : rawLines)
        {
            Line line = new Line(rawLine[0], rawLine[1], rawLine[2]);
            lines.add(line);
        }
    }

    /**
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<Line> iterator()
    {
        return lines.iterator();
    }
}
