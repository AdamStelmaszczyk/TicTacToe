package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Klasa opisująca planszę.
 *
 * @author Adam Stelmaszczyk
 * @version 2011-07-12
 */
public class Board {
    /**
     * Tablica pól na planszy.
     */
    private final ArrayList<FieldType> fields = new ArrayList<FieldType>();
    /**
     * Linie na planszy.
     */
    private final Lines lines = new Lines();

    /**
     * Konstruktor generujący planszę na podstawie podanej liczby z zakresu [0; 3^9 - 1] = [0; 19682].
     * Liczba ta stanowi dziesiętny zapis liczby w systemie trójkowym, na 9 pozycjach.
     * Np. number = 15 dziesiętnie to 9 + 3*2 = 3^2*1 + 3^1*2 + 3^0*0 czyli 110 trójkowo. De facto 000000110.
     * Teraz to można przekształcić w planszę.
     *
     * @param number Liczba kodująca planszę.
     */
    public Board(int number) {
        String boardCode = getBoardCodeFromNumber(number);
        FieldType[] fieldTypes = FieldType.values();
        for (int i = 0; i < 9; i++) {
            Character c = boardCode.charAt(i);
            int code = Character.getNumericValue(c);
            FieldType field = fieldTypes[code];
            fields.add(field);
        }
    }

    private String getBoardCodeFromNumber(int number) {
        String boardCode = "";
        for (int i = 0; i < 9; i++) {
            int digit = number % 3;
            boardCode += digit;
            number = number / 3;
        }
        return boardCode;
    }

    /**
     * Konstruktor generujący planszę na podstawie kodu planszy.
     *
     * @param boardCode Kod planszy.
     */
    public Board(String boardCode) {
        this(getNumberFromBoardCode(boardCode));
    }

    /**
     * Zwraca numer planszy na podstawie kodu planszy.
     *
     * @param boardCode Kod planszy.
     * @return Numer planszy.
     */
    static int getNumberFromBoardCode(String boardCode) {
        int number = 0;
        for (int i = 0; i < 9; i++) {
            Character c = boardCode.charAt(i);
            int digit = Character.getNumericValue(c);
            number += Math.pow(3, i) * digit;
        }
        return number;
    }

    /**
     * Zwraca numer planszy.
     *
     * @return Numer planszy.
     */
    public int getNumber() {
        String boardCode = "";
        for (FieldType field : fields) {
            boardCode += field.ordinal();
        }
        int number = getNumberFromBoardCode(boardCode);
        return number;
    }


    /**
     * Konstruktor kopiujący.
     *
     * @param board
     */
    public Board(Board board) {
        for (int i = 0; i < 9; i++) {
            Cords cords = new Cords(i);
            fields.add(board.getField(cords));
        }
    }

    /**
     * Zwraca typ pola o podanych wspołrzędnych.
     *
     * @param cords Współrzędne.
     * @return Pionek o podanych współrzędnych.
     */
    public FieldType getField(final Cords cords) {
        return fields.get(cords.getIndex());
    }

    /**
     * Ustawia dane pole.
     *
     * @param cords Numer pola.
     * @param field Pole na jakie ustawiamy.
     */
    public void setField(final Cords cords, final FieldType field) {
        fields.set(cords.getIndex(), field);
    }

    /**
     * Czy pole o podanym numerze jest puste?
     *
     * @param cords Numer pola.
     * @return True, jeśli jest puste.
     */
    public boolean isEmpty(final Cords cords) {
        int index = cords.getIndex();
        FieldType field = fields.get(index);
        return field.isEmpty();
    }

    /**
     * Czy linia jest zamknięta? Tzn. czy są na niej 3 takie same znaki?
     *
     * @param line Linia, którą sprawdzamy.
     * @return True, jeśli jest zamknięta.
     */
    public boolean isLineClosed(Line line, FieldType player) {
        int counter = 0;
        for (Cords cords : line) {
            if (getField(cords) == player) {
                counter++;
            }
        }
        return counter == 3;
    }

    /**
     * Zwraca 8 linii.
     *
     * @return Linie na planszy.
     */
    Lines getLines() {
        return lines;
    }

    /**
     * Zwraca liczbę pustych pól.
     *
     * @return Liczba pustych pól, od 0 do 9.
     */
    public int getNumberOfEmpty() {
        int numberOfEmpty = 0;
        for (FieldType field : fields) {
            if (field.isEmpty()) {
                numberOfEmpty++;
            }
        }
        return numberOfEmpty;
    }

    /**
     * Czy jest remis?
     *
     * @return True, jeśli remis.
     */
    public boolean isDraw() {
        if (isWinner(FieldType.AI) || isWinner(FieldType.USER)) {
            return false;
        }
        return getNumberOfEmpty() == 0;
    }

    /**
     * Ile jest pól na planszy?
     *
     * @return Rozmiar planszy.
     */
    public int size() {
        return fields.size();
    }

    /**
     * Czy player zwyciężył?
     *
     * @return True, jeśli podany gracz wygrał.
     */
    public boolean isWinner(FieldType player) {
        for (Line line : lines) {
            if (isLineClosed(line, player)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return fields.toString();
    }

    public Cords setRandomEmptyField(FieldType player) {
        List<Cords> empty = getCordsOfEmptyFields();
        Cords random = empty.get((int) (Math.random() * empty.size()));
        fields.set(random.getIndex(), player);
        return random;
    }

    public List<Cords> getCordsOfEmptyFields() {
        List<Cords> cords = new ArrayList<Cords>();
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).isEmpty()) {
                cords.add(new Cords(i));
            }
        }
        return cords;
    }

    /**
     * @param player Player to move.
     * @return Cords of the winning move or null if there is no winning move for given player.
     */
    public Cords getWinningMove(FieldType player) {
        List<Cords> emptyList = getCordsOfEmptyFields();
        for (Cords empty : emptyList) {
            Board testBoard = new Board(this);
            testBoard.setField(empty, player);
            if (testBoard.isWinner(player)) {
                return empty;
            }
        }
        return null;
    }

    /**
     * @param player Player to move.
     * @return Cords of the blocking move or null if there is no blocking move for given player.
     */
    public Cords getBlockingMove(FieldType player) {
        FieldType blockedPlayer = player.negate();
        List<Cords> emptyList = getCordsOfEmptyFields();
        for (Cords empty : emptyList) {
            Board testBoard = new Board(this);
            testBoard.setField(empty, blockedPlayer);
            if (testBoard.isWinner(blockedPlayer)) {
                return empty;
            }
        }
        return null;
    }

}
