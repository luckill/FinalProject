package Data.AbstractClass;

import Data.*;
import timer.*;

public abstract class Animal
{
    private String name;
    private double purchasePrice;

    private boolean feedingNow;

    public Animal(String name, double price)
    {
        this.name = name;
        this.purchasePrice = price;
        this.feedingNow = false;
    }

    public String getName()
    {
        return name;
    }

    public double getPurchasePrice()
    {
        return purchasePrice;
    }

    public boolean isFeedingNow()
    {
        return feedingNow;
    }

    public void feedAnimal(long duration)
    {
        if(!feedingNow)
        {
            this.feedingNow = true;
            Timer timer = new Timer(duration);
            if(timer.isTimeUp())
            {
                this.feedingNow = false;
            }
        }
    }

    public void produceProduct(Inventory inventory, String key)
    {
        if (feedingNow)
        {
            Timer timer = new Timer();
            if(timer.getElapsedTimeInMinute() == 5)
            {
                inventory.increaseProductQuantity(key,1);
            }
        }
    }
}
