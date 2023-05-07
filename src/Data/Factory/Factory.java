package Data.Factory;

import java.util.*;

import Data.AbstractClass.*;
import timer.Timer;

import static Main.Main.inventory;

public class Factory
{

   // public static Set<Receipt> receiptList = new HashSet<>();
    private boolean isFactoryRunning;
    private static Timer timer;
    private static Queue<Item> productionQueue;
    public static String currentProduct;
    public Factory()
    {
        this.isFactoryRunning = false;
        productionQueue = new ArrayDeque<>();
    }

    public void Production()
    {
        if (!productionQueue.isEmpty())
        {
            if (!isFactoryRunning)
            {
                isFactoryRunning = true;
                Item item = productionQueue.remove();
                currentProduct = item.getName();
                Receipt receipt = item.getReceipt();
                timer = new Timer(receipt.getTime());
            }

        }
        if (timer != null)
        {
            if (timer.isTimeUp())
            {
                isFactoryRunning = !isFactoryRunning;
                inventory.addProductToInventory(currentProduct, 1);
                currentProduct = "";
                timer = null;
            }
        }
    }

    public int getProductionQueueSize()
    {
        return productionQueue.size();
    }

    public void addProductToQueue(Item item)
    {
        Receipt receipt = item.getReceipt();

        if (haveSufficientInventory(receipt.getIngredients()))
        {
            productionQueue.add(item);
            for (Map.Entry<String, Integer> entry : receipt.getIngredients().entrySet())
            {
                inventory.decreaseProductQuantity(entry.getKey(), entry.getValue());
            }
        }
    }

    private boolean haveSufficientInventory(HashMap<String, Integer> map)
    {
        for (Map.Entry<String, Integer> entry : map.entrySet())
        {
            if (inventory.getItemQuantity(entry.getKey()) < entry.getValue())
            {
                return false;
            }
        }
        return true;
    }
    public String getProductionQueueContent()
    {
        if (productionQueue.isEmpty())
        {
            return "no items in production queue!!!";
        }
        Iterator<Item> iterator = productionQueue.iterator();
        StringBuilder string = new StringBuilder();
        String temp;
        while (iterator.hasNext())
        {
            Item item = iterator.next();
            if (iterator.next() == null)
            {
                temp = item.getName();
            }
            else
            {
                temp = item.getName() + " -> ";
            }
            string.append(temp);
        }
        return string.toString();
    }
}

