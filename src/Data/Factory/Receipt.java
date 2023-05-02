package Data.Factory;

import java.util.*;

public class Receipt
{
    private String name;
    private HashMap<String, Integer> ingredients;
    private long time;

    public Receipt(String name, HashMap<String, Integer> ingredients, long time)
    {
        this.name = name;
        this.ingredients = ingredients;
        this.time = time;
    }

    public String getName()
    {
        return name;
    }

    public long getTime()
    {
        return time;
    }

    public HashMap<String, Integer> getIngredients()
    {
        return ingredients;
    }
}
