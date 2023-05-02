package Data;

import java.util.*;

public class Inventory
{
    private HashMap<String, Integer> inventory ;

    public Inventory()
    {
        inventory = new HashMap<>();
    }

    public HashMap<String, Integer> getInventory()
    {
        return inventory;
    }

    public boolean productInInventory(String key)
    {
        return inventory.containsKey(key);
    }

    public void increaseProductQuantity(String key, int quantity)
    {
        if (productInInventory(key))
        {
            int temp = inventory.get(key);
            inventory.put(key, temp + quantity);
        }
        else
        {
            addProductToInventory(key, quantity);
        }
    }

    public void decreaseProductQuantity(String key, int quantity)
    {
        if (inventory.get(key) == 0 || inventory.get(key) < quantity)
        {
            removeProductFromInventory(key);
        }

        if (productInInventory(key))
        {
            int temp = inventory.get(key);
            inventory.put(key,temp++);
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
    }
}
