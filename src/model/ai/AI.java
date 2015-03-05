package model.ai;

import model.Board;
import model.Cords;
import model.FieldType;

/**
 * Interfejs sztucznej inteligencji.
 *
 * @author Adam Stelmaszczyk
 * @version 2015-02-22
 */
abstract public class AI {

    /**
     * Oblicza ruch komputera na danej planszy.
     *
     * @param board Plansza.
     * @return Ruch komputera.
     */
    abstract public Cords computeMove(Board board);

    /**
     * Zwraca licznik przeliczonych plansz.
     *
     * @return Licznik.
     */
    abstract public int getCounter();
}
