package model;

/**
 * Typ pola na planszy. Gracza, komputera, puste.
 * 
 * @author Adam Stelmaszczyk
 * @version 2011-07-12
 */
public enum FieldType
{
    EMPTY
    {
        @Override
        public FieldType negate()
        {
            throw new RuntimeException("Puste pole nie ma negacji.");
        }

        @Override
        public boolean isUser()
        {
            return false;
        }

        @Override
        public boolean isAI()
        {
            return false;
        }

        @Override
        public boolean isEmpty()
        {
            return true;
        }
    },    
    USER
    {
        @Override
        public FieldType negate()
        {
            return FieldType.AI;
        }

        @Override
        public boolean isUser()
        {
            return true;
        }

        @Override
        public boolean isAI()
        {
            return false;
        }

        @Override
        public boolean isEmpty()
        {
            return false;
        }
    },   
    AI
    {
        @Override
        public FieldType negate()
        {
            return FieldType.USER;
        }

        @Override
        public boolean isUser()
        {
            return false;
        }

        @Override
        public boolean isAI()
        {
            return true;
        }

        @Override
        public boolean isEmpty()
        {
            return false;
        }
    };
    
    /**
     * Zwraca przeciwnego gracza. Gracz jest przeciwieństwem komputera i odwrotnie.
     * 
     * @return Jeśli ustawione było AI zwraca USER i odwrotnie.
     */
    public abstract FieldType negate();
    
    /**
     * Czy pole należy do człowieka?
     * 
     * @return True, jeśli ustawiony jest człowiek.
     */
    public abstract boolean isUser();
    
    /**
     * Czy pole należy do AI?
     * 
     * @return True, jeśli ustawiony jest komputer.
     */
    public abstract boolean isAI();
    
    /**
     * Czy pole jest puste?
     * 
     * @return True, jeśli jest puste.
     */
    public abstract boolean isEmpty();
}
