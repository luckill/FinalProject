package Data.AbstractClass;

import Data.*;
import timer.*;

public abstract class Animal
{
    private String name;
    protected int totalRevenueGenerated;
    protected boolean feedingNow;

    public Animal(String name)
    {
        this.name = name;
        this.totalRevenueGenerated = 0;
        this.feedingNow = false;
    }

    public String getName()
    {
        return name;
    }

    public int getTotalRevenueGenerated()
    {
        return totalRevenueGenerated;
    }

    public boolean isFeedingNow()
    {
        return feedingNow;
    }

    public abstract void feedAnimal();

    /*public void produceProduct(Inventory inventory, String key)
    {
        if (feedingNow)
        {
            Timer timer = new Timer();
            if(timer.getElapsedTimeInMinute() == 2)
            {
                inventory.increaseProductQuantity(key,1);
                timer.resetWatch();
            }
        }
    }*/
}
