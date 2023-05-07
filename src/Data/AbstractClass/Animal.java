package Data.AbstractClass;

import Data.*;
import timer.*;

public abstract class Animal
{
    private String name;

    private boolean feedingNow;

    public Animal(String name)
    {
        this.name = name;
        this.feedingNow = false;
    }

    public String getName()
    {
        return name;
    }

    public boolean isFeedingNow()
    {
        return feedingNow;
    }

    public void feedAnimal()
    {
        if(!feedingNow)
        {
            this.feedingNow = true;
            Timer timer = new Timer(10);
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
            if(timer.getElapsedTimeInMinute() == 2)
            {
                inventory.increaseProductQuantity(key,1);
                timer.resetWatch();
            }
        }
    }
}
