package model.ai.minmax;

import model.Board;
import model.Cords;
import model.FieldType;
import model.ai.AI;

import java.util.ArrayList;
import java.util.List;

/**
 * Obsługuje sztuczną inteligencję. Generuje ruchy na podstawie kopii planszy.
 *
 * @author Adam Stelmaszczyk
 * @version 2011-07-13
 */
public class MinMax extends AI
{
    /** Wartość dla "nieskonczoności", musi być większa od wartości wygranej. */
    private final int INFINITY = 9;
    /** Licznik przeliczonych plansz dla 1 ruchu. */
    private int counter;
    
    /**
     * Oblicza ruch komputera na danej planszy.
     * 
     * @param board Plansza.
     * @return Ruch komputera.
     */
    public Cords computeMove(Board board)
    {
        counter = 0;
        int[] values = computeValues(board, FieldType.AI);
        int index = getIndexOfMax(values);
        Cords move = new Cords(index);
        return move;
    }

    /**
     * Zwraca tablicę z punktami opłacalności ruchu w każde pole.
     * 
     * @param board Plansza.
     * @return Tablicę z punktami.
     */
    private int[] computeValues(Board board, FieldType player)
    {
        int values[] = new int[board.size()];
        List<Cords> emptyCords = board.getCordsOfEmptyFields();

        for (int i = 0; i < board.size(); i++)
        {
            values[i] = -INFINITY * getValueOfWinner(player);
            Cords cords = new Cords(i);
            if (board.isEmpty(cords))
            {
                Board testBoard = new Board(board);
                testBoard.setField(cords, player);
                values[i] = miniMax(testBoard, player);
            }
        }
        return values;
    }
    
    /**
     * Zwraca siłę rażenia danego gracza na podanej planszy. AI maksymalizuje, USER minimalizuje punkty.
     * 
     * @param board Węzęł drzewa - stan planszy.
     * @param player Gracz.
     * @return Wartość liczbowa określająca siłę rażenia.
     */
    private int miniMax(Board board, FieldType player)
    {
        counter++;
        if (board.isDraw())
        {
            return 0;
        }
        if (board.isWinner(player))
        {
            return getValueOfWinner(player);
        }
        FieldType rival = player.negate();
        int[] values = computeValues(board, rival);
        int index = (rival.isAI()) ? getIndexOfMax(values) : getIndexOfMin(values);
        return values[index];
    }
    
    /**
     * Zwraca największą z podanych liczb.
     * 
     * @param numbers Tablica liczb.
     * @return Największą liczbę.
     */
    private int getMax(int[] numbers)
    {
        int maxValue = numbers[0];  
        for (int i = 1; i < numbers.length; i++)
        {  
            if (maxValue < numbers[i])
            {  
                maxValue = numbers[i];  
            }  
        }  
        return maxValue;  
    }
    
    /**
     * Zwraca najmniejszą z podanych liczb.
     * 
     * @param numbers Tablica liczb.
     * @return Największą liczbę.
     */
    private int getMin(int[] numbers)
    {
        int minValue = numbers[0];  
        for (int i = 1; i < numbers.length; i++)
        {  
            if (minValue > numbers[i])
            {  
                minValue = numbers[i];  
            }  
        }  
        return minValue;  
    }
    
    /**
     * Zwraca indeks największej z podanych liczb. Jeśli kilka jest największych, losuje indeks.
     * 
     * @param numbers Tablica liczb.
     * @return Indeks największej liczby.
     */
    private int getIndexOfMax(int[] numbers)
    {  
        int maxValue = getMax(numbers);
        ArrayList<Integer> maxIndex = new ArrayList<Integer>();
        for (int i = 0; i < numbers.length; i++)
        {  
            if (numbers[i] == maxValue)
            {  
                maxIndex.add(i);
            }  
        }  
        int randomIndex = (int) (Math.random() * maxIndex.size());
        return maxIndex.get(randomIndex);
    }
    
    /**
     * Zwraca indeks najmniejszej z podanych liczb. Jeśli kilka jest najmniejszych, losuje indeks.
     * 
     * @param numbers Tablica liczb.
     * @return Indeks najmniejszej liczby.
     */
    private int getIndexOfMin(int[] numbers)
    {  
        int minValue = getMin(numbers);
        ArrayList<Integer> minIndex = new ArrayList<Integer>();
        for (int i = 0; i < numbers.length; i++)
        {  
            if (numbers[i] == minValue)
            {  
                minIndex.add(i);
            }  
        }  
        int randomIndex = (int) (Math.random() * minIndex.size());
        return minIndex.get(randomIndex);
    }
    
    /**
     * Zwraca licznik przeliczonych plansz.
     * 
     * @return Licznik.
     */
    public int getCounter()
    {
        return counter;
    }

    /**
     * Zwraca wartość punktową zwycięstwa danego gracza.
     *
     * @param player Gracz.
     * @return Punkty.
     */
    public int getValueOfWinner(FieldType player) {
        return (player.isAI()) ? 1 : -1;
    }

}

