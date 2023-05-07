package Data;

import java.util.*;

public class Inventory
{
    private HashMap<String, Integer> inventory ;

    public Inventory()
    {
        inventory = new HashMap<>();
    }

    public boolean productInInventory(String key)
    {
        return inventory.containsKey(key);
    }

    public int getItemQuantity(String key)
    {
        if (inventory.get(key) == null)
        {
            return 0;
        }
        return inventory.get(key);
    }
    public void increaseProductQuantity(String key, int quantity)
    {
        int temp = inventory.get(key);
        inventory.put(key, temp + quantity);
    }

    public void decreaseProductQuantity(String key, int quantity)
    {
        if(productInInventory(key))
        {
            if (inventory.get(key) == 0 || inventory.get(key) < quantity)
            {
                removeProductFromInventory(key);
            }
            else
            {
                int temp = inventory.get(key);
                temp -= quantity;
                inventory.put(key,temp);
            }
        }
    }

    public void removeProductFromInventory(String key)
    {
        if(productInInventory(key) && inventory.get(key) == 0)
        {
            inventory.remove(key);
        }
    }

    public void addProductToInventory(String key, int quantity)
    {
        if (!productInInventory(key))
        {
            inventory.put(key,quantity);
        }
        else
        {
            increaseProductQuantity(key, quantity);
        }
    }

    public String toString()
    {
        if (inventory.isEmpty())
        {
            return "no item in the inventory";
        }
        String temp = "";
        for (Map.Entry<String, Integer> entry: inventory.entrySet())
        {
            temp += entry.getKey() + " : " + entry.getValue()  + "        ";

        }
        return temp;
    }
}
