package event;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Kolejka zdarzeń gry. Zdarzenia oczekują tutaj na obsłużenie.
 * 
 * @author Adam Stelmaszczyk
 * @version 2011-07-12
 */
public class GameQueue
{
    /** Kolejka FIFO usypiająca wątki. */
    private final BlockingQueue<ProgramEvent> queue;

    /**
     * Konstruktor.
     */
    public GameQueue()
    {
        queue = new LinkedBlockingQueue<ProgramEvent>();
    }

    /**
     * Wstawia do kolejki nowy obiekt.
     * 
     * @param event Zdarzenie gry.
     */
    public void put(final ProgramEvent event)
    {
        try
        {
            queue.put(event);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Pobiera z kolejki obiekt. Jesli nie ma żadnego, usypia wątek.
     * 
     * @return Zdarzenie gry.
     */
    public ProgramEvent take()
    {
        ProgramEvent ret = null;
        try
        {
            ret = queue.take();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return ret;
    }

}
