package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Linia długości 3 pól.
 * 
 * @author Adam Stelmaszczyk
 * @version 2011-07-12
 */
public class Line implements Iterable<Cords>
{
    private final ArrayList<Cords> line = new ArrayList<Cords>();
    
    Line(int first, int second, int third)
    {
        line.add(new Cords(first));
        line.add(new Cords(second));
        line.add(new Cords(third));
    }

    public Iterator<Cords> iterator()
    {
        return line.iterator();
    }
    
    public String toString()
    {
        return line.toString();
    }

}
