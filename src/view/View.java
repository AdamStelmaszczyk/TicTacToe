package view;

import event.BoardClickEvent;
import event.GameQueue;
import event.NewGameEvent;
import model.Board;
import model.Cords;
import model.FieldType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa widoku - wyświetla GUI.
 *
 * @author Adam Stelmaszczyk
 * @version 2011-07-13
 */
public class View {
    /**
     * Kolejka zdarzeń gry.
     */
    private final GameQueue gameQueue;
    /**
     * Okno gry.
     */
    private MainWindow mainWindow;
    /**
     * Guziki tworzące planszę.
     */
    private ArrayList<ButtonField> buttons = new ArrayList<ButtonField>();
    /**
     * Checkbox czy komputer ma zaczynać.
     */
    private JCheckBox computerStarts;
    /**
     * Radio if we should use precomputed moves table.
     */
    private JRadioButton precomputed;
    /**
     * Radio if we should use MinMax algorithm.
     */
    private JRadioButton minMax;
    /**
     * Radio if we should use MCTS algorithm.
     */
    private JRadioButton mcts;
    /**
     * Informacje o tym ile liczy komputer.
     */
    private JLabel computerLabel;
    /**
     * Mapa (słownik) odworowujący typ pola na nazwę pliku.
     */
    private final Map<FieldType, String> fieldTypeToName = new HashMap<FieldType, String>();
    /**
     * Nasłuchiwacz kliknięć w planszę.
     */
    private final ActionListener boardListener = new ActionListener() {
        public void actionPerformed(final ActionEvent e) {
            ButtonField button = (ButtonField) e.getSource();
            Cords click = button.getCords();
            BoardClickEvent boardClickEvent = new BoardClickEvent(click, FieldType.USER);
            gameQueue.put(boardClickEvent);
        }
    };
    /**
     * Nasłuchiwacz guzika nowej gry.
     */
    private final ActionListener newGameListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            gameQueue.put(new NewGameEvent());
        }
    };

    /**
     * Konstruktor. Tworzy skojarzenia nazw plików z typami pól na planszy.
     *
     * @param gameQueue
     */
    public View(GameQueue gameQueue) {
        this.gameQueue = gameQueue;

        // Pobiera nazwy typów pól.
        FieldType[] fieldTypes = FieldType.values();

        // Ich liczba musi się zgadzać z liczbą plików obrazków.
        if (fieldTypes.length != ViewConfig.FILENAME.length) {
            System.err.println(getClass() + " : Nazwy plików obrazków nie zgadzają się z typami pól.");
            throw new RuntimeException();
        }

        // Jeśli się zgadza, inicjalizujemy mapę odwzorowującą typy pól na nazwy plików graficznych.
        for (int i = 0; i < fieldTypes.length; i++) {
            fieldTypeToName.put(fieldTypes[i], ViewConfig.FILENAME[i]);
        }

        // Przekazuje wątkowi Swinga parę rzeczy do zrobienia.
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                mainWindow = new MainWindow("TicTacToe");
            }
        });
    }

    /**
     * Rysuje wstępnie to, co może na początku. Zarys planszy, guziki.
     */
    public void showInitially() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Dodaje panel planszy do okna.
                JPanel board = new JPanel(new GridLayout(3, 3));
                board.setBounds(ViewConfig.BOARD_X, ViewConfig.BOARD_Y, ViewConfig.BOARD_WIDTH,
                        ViewConfig.BOARD_HEIGHT);
                mainWindow.add(board);

                // Dodaje guziki do planszy.
                for (int i = 0; i < 9; i++) {
                    Cords cords = new Cords(i);
                    ButtonField button = new ButtonField(cords);
                    button.addActionListener(boardListener);
                    buttons.add(button);
                    board.add(button);
                }

                // New game
                JButton newGame = new JButton("New game");
                newGame.setBounds(2 * ViewConfig.BOARD_X + ViewConfig.BOARD_WIDTH,
                        ViewConfig.BOARD_Y,
                        ViewConfig.BOARD_WIDTH,
                        30);
                newGame.addActionListener(newGameListener);
                mainWindow.add(newGame);

                // Computer starts
                computerStarts = new JCheckBox("Computer, you start", true);
                computerStarts.setBounds(236,
                        ViewConfig.BOARD_Y + 40,
                        ViewConfig.BOARD_WIDTH,
                        30);
                mainWindow.add(computerStarts);

                precomputed = new JRadioButton("Precomputed");
                precomputed.setBounds(236,
                        ViewConfig.BOARD_Y + 70,
                        ViewConfig.BOARD_WIDTH,
                        30);
                mainWindow.add(precomputed);

                minMax = new JRadioButton("MinMax");
                minMax.setBounds(236,
                        ViewConfig.BOARD_Y + 100,
                        ViewConfig.BOARD_WIDTH,
                        30);
                mainWindow.add(minMax);

                mcts = new JRadioButton("MCTS", true);
                mcts.setBounds(236,
                        ViewConfig.BOARD_Y + 130,
                        ViewConfig.BOARD_WIDTH,
                        30);
                mainWindow.add(mcts);

                ButtonGroup group = new ButtonGroup();
                group.add(precomputed);
                group.add(minMax);
                group.add(mcts);

                JPanel radioPanel = new JPanel(new GridLayout(3, 1));
                radioPanel.setBorder(BorderFactory.createTitledBorder("AI algorithm"));
                radioPanel.setBounds(236,
                        ViewConfig.BOARD_Y + 80,
                        ViewConfig.BOARD_WIDTH,
                        100);

                radioPanel.add(precomputed);
                radioPanel.add(minMax);
                radioPanel.add(mcts);
                mainWindow.add(radioPanel);

                // Informacja o tym, ile liczy komputer.
                computerLabel = new JLabel();
                computerLabel.setBounds(2 * ViewConfig.BOARD_X + ViewConfig.BOARD_WIDTH,
                        ViewConfig.BOARD_Y + 180,
                        ViewConfig.BOARD_WIDTH + 30,
                        30);
                mainWindow.add(computerLabel);
            }
        });
    }

    /**
     * Wyświetla informację w okienku z przyciskiem ok.
     *
     * @param msg   Informacja.
     * @param title Napis tytułowy na belce okienka.
     */
    void showMessageDialog(final String msg, final String title) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JOptionPane.showMessageDialog(mainWindow, msg, title, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * Wyświetla informację o zwycięzcy partii.
     *
     * @param winner Kolor zwycięzcy.
     */
    public void showWinner(final FieldType winner) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                String text = null;
                if (winner == FieldType.USER) {
                    text = "You won.";
                }
                if (winner == FieldType.AI) {
                    text = "You lost.";
                }
                if (winner == FieldType.EMPTY) {
                    text = "Draw.";
                }
                JOptionPane.showMessageDialog(mainWindow, text, "Game over", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * Rysuje planszę na podstawie kopii.
     *
     * @param board Kopia planszy do narysowania.
     */
    public void showBoard(final Board board) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                for (ButtonField button : buttons) {
                    FieldType fieldType = board.getField(button.getCords());
                    button.setImage(fieldTypeToName.get(fieldType));
                }
            }
        });
    }

    /**
     * Wł/wył guziki na planszy.
     *
     * @param enable True, jeśli guziki na planszy mają być włączone.
     */
    public void setEnabledBoard(final boolean enable) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                for (ButtonField button : buttons) {
                    button.setEnabled(enable);
                }
            }
        });
    }

    public boolean isComputerFirst() {
        if (computerStarts == null) {
            return false;
        }
        return computerStarts.isSelected();
    }

    public boolean isPrecomputed() {
        if (precomputed == null) {
            return false;
        }
        return precomputed.isSelected();
    }

    public boolean isMinMax() {
        if (minMax == null) {
            return false;
        }
        return minMax.isSelected();
    }

    public boolean isMcts() {
        if (mcts == null) {
            return false;
        }
        return mcts.isSelected();
    }

    /**
     * Ustawia liczbę w komunikacie o przeliczonych planszach.
     *
     * @param number Liczba plansz do pokazania.
     */
    public void setComputerLabel(final int number) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                computerLabel.setText("Calculated boards: " + number);
            }
        });
    }

}
