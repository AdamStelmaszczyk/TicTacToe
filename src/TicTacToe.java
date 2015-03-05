import view.View;
import controller.Controller;
import event.GameQueue;

/**
 * Klasa klienta gry.
 * 
 * @author Adam Stelmaszczyk
 * @see <a href="http://mion.elka.pw.edu.pl/~astelma1/">Strona domowa</a>
 * @version 2011-07-12
 */
public class TicTacToe
{
    /**
     * Uruchamia grę.
     * 
     * @param args Argumenty wywołania programu, nie używane.
     */
    public static void main(final String[] args)
    {
        GameQueue gameQueue = new GameQueue();
        View view = new View(gameQueue);
        view.showInitially();
        Controller controller = new Controller(gameQueue, view);
        controller.startNewGame();
    }
}
