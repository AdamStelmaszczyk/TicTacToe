package view;

import javax.swing.JFrame;

/**
 * Główne okno gry.
 * 
 * @author Adam Stelmaszczyk
 * @version 2011-07-12
 */
class MainWindow extends JFrame
{
    /** Numer wersji. */
    private static final long serialVersionUID = 1;

    /**
     * Konstruktor.
     * 
     * @param title Tytuł na belce okna.
     */
    public MainWindow(final String title)
    {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(ViewConfig.WINDOW_WIDTH, ViewConfig.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);
        setResizable(false);
    }

}
