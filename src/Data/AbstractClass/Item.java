package Data.AbstractClass;

import Data.Factory.*;

import java.util.*;

public class Item
{
    private long time;
    private String name;
    private double purchasePrice;
    private Receipt receipt;
    private String ingredient;

    public Item(long time, String name, double purchasePrice , String[] ingredients, int[] quantity)
    {
        this.time = time;
        this.name = name;
        this.purchasePrice = purchasePrice;
        HashMap<String, Integer> map = new HashMap<>();
        for(int i = 0; i < 2; i++)
        {
            map.put(ingredients[i], quantity[i]);
        }

        Factory.receiptList.add(new Receipt(this.name, map, this.time));
    }

    public long getTime()
    {
        return time;
    }

    public String getName()
    {
        return name;
    }

    public double getPurchasePrice()
    {
        return purchasePrice;
    }
}
