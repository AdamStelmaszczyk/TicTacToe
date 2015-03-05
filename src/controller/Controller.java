package controller;

import java.util.HashMap;
import java.util.Map;

import model.*;
import model.ai.*;
import model.ai.mcts.MonteCarloTreeSearch;
import model.ai.minmax.MinMax;
import model.ai.precomputed.Precomputed;
import view.View;
import event.BoardClickEvent;
import event.ComputerTurnEvent;
import event.EndGameEvent;
import event.ProgramEvent;
import event.GameQueue;
import event.NewGameEvent;

/**
 * Kontroler.
 *
 * @author Adam Stelmaszczyk
 * @version 2011-07-12
 */
public class Controller implements Runnable {
    /**
     * AI algorithms.
     */
    private final AI precomputed = new Precomputed();
    private final AI minMax = new MinMax();
    private final AI mcts = new MonteCarloTreeSearch();
    /**
     * Referencja na widok.
     */
    private final View view;
    /**
     * Referencja na model dla kontrolera.
     */
    private Model model;
    /**
     * Kolejka zdarzeń gry.
     */
    private final GameQueue gameQueue;
    /**
     * Mapa odwzorowująca klasy zdarzeń gry w akcje, jakie należy podjąć, aby obsłużyc dany typ zdarzenia.
     */
    private final Map<Class<? extends ProgramEvent>, Action> gameEventToAction = new HashMap<Class<? extends ProgramEvent>, Action>();
    /**
     * Wątek kontrolera sprawdzający kolejkę.
     */
    private final Thread thread;
    /**
     * Czy koniec partii?
     */
    private boolean gameOver;

    /**
     * Klasa abstrakcyjna obsługi zdarzenia. Z niej dziedziczą wszystkie akcje.
     */
    abstract private class Action {
        /**
         * Wykonuje swoją akcję.
         */
        abstract void execute(ProgramEvent event);
    }

    /**
     * Akcja w odpowiedzi na zdarzenie na planszy.
     */
    private class BoardClickAction extends Action {
        @Override
        void execute(final ProgramEvent event) {
            if (gameOver) {
                return;
            }

            // Pobiera zdarzenie kliknięcia w planszę.
            BoardClickEvent boardClickEvent = (BoardClickEvent) event;

            // Pobiera współrzędne kliknięcia z obiektu zdarzenia.
            Cords click = boardClickEvent.getCords();

            // Pobiera gracza, który wykonał kliknięcie.
            FieldType player = boardClickEvent.getPlayer();

            // Jeśli ruch niepoprawny, ignorujemy go.
            if (!model.isValidMove(click)) {
                return;
            }

            // Informuje model o kliknięciu.
            model.doClick(click, player);

            // Wyświetla planszę.
            view.showBoard(model.getBoard());

            // Jeśli koniec gry, to wyślij informację o zwycięzcy.
            if (model.isGameOver()) {
                FieldType winner = model.getWinner();
                gameQueue.put(new EndGameEvent(winner));
                return;
            }

            // Jeśli ruszał się gracz - to teraz pora ruszyć komputer.
            if (player == FieldType.USER) {
                gameQueue.put(new ComputerTurnEvent());
            }
        }
    }

    /**
     * Akcja w odpowiedzi na koniec gry.
     */
    private class EndGameAction extends Action {
        @Override
        void execute(final ProgramEvent event) {
            gameOver = true;
            EndGameEvent endGameEvent = (EndGameEvent) event;
            view.showWinner(endGameEvent.getWinner());
        }
    }

    /**
     * Akcja w odpowiedzi na start gry.
     */
    private class NewGameAction extends Action {
        @Override
        void execute(final ProgramEvent event) {
            startNewGame();
        }
    }

    /**
     * Akcja w odpowiedzi na turę komputera.
     */
    private class ComputerTurnAction extends Action {
        @Override
        void execute(final ProgramEvent event) {
            Board board = model.getBoard();
            AI computerAI = precomputed;
            if (view.isMinMax()) {
                computerAI = minMax;
            }
            if (view.isMcts()) {
                computerAI = mcts;
            }
            BoardClickEvent boardClick = new BoardClickEvent(computerAI.computeMove(board), FieldType.AI);
            gameQueue.put(boardClick);
            int counter = computerAI.getCounter();
            view.setComputerLabel(counter);
        }
    }

    /**
     * Konstruktor. Inicjalizuje mapę odwzorowująca zdarzenia gry w akcje.
     *
     * @param gameQueue Kolejka zdarzeń gry.
     * @param view      Widok.
     */
    public Controller(final GameQueue gameQueue, View view) {
        this.gameQueue = gameQueue;
        this.view = view;
        this.thread = new Thread(this);
        thread.start();
        gameEventToAction.put(BoardClickEvent.class, new BoardClickAction());
        gameEventToAction.put(EndGameEvent.class, new EndGameAction());
        gameEventToAction.put(ComputerTurnEvent.class, new ComputerTurnAction());
        gameEventToAction.put(NewGameEvent.class, new NewGameAction());
    }

    /**
     * Główny wątek programu do końca życia sprawdza kolejkę zdarzeń. Jeśli kolejka jest pusta, wątek usypia.
     */
    public void run() {
        while (thread != null) {
            // Pobiera zdarzenie z kolejki, jak nic nie ma to usypia.
            ProgramEvent gameEvent = gameQueue.take();

            // Pobiera klasę tego zdarzenia.
            Class<?> eventClass = gameEvent.getClass();

            // Wybiera akcję związaną z pobraną klasą zdarzenia.
            Action gameAction = gameEventToAction.get(eventClass);

            // Wykonuje tą akcję, w argumencie przesyła zdarzenie, aby później wydobyć sobie szczegóły.
            gameAction.execute(gameEvent);
        }
    }

    /**
     * Startuje nową grę.
     */
    public void startNewGame() {
        gameOver = false;
        FieldType whoStarts = (view.isComputerFirst()) ? FieldType.AI : FieldType.USER;
        this.model = new Model(whoStarts);
        view.showBoard(model.getBoard());
        if (view.isComputerFirst()) {
            gameQueue.put(new ComputerTurnEvent());
        }
    }

}
