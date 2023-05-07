package Data.Animal;

import Data.*;
import Data.AbstractClass.*;
import timer.*;

import static Main.Main.balance;

public class Sheep extends Animal implements Revenue
{
    private static Timer timer;
    private static Timer timer1;

    public Sheep()
    {
        super("sheep");
    }

    @Override
    public void generateRevenue(Balance balance)
    {
        if (timer1 == null)
        {
            timer1 = new Timer(2);
        }
        if(timer1.isTimeUp())
        {
            balance.addBalance(300.00);
            totalRevenueGenerated += 300.00;
            timer1.resetWatch();
        }
    }

    @Override
    public void feedAnimal()
    {
        if(!feedingNow)
        {
            this.feedingNow = true;
            timer = new Timer(10);
        }
    }

    public void checkTime()
    {
        if (timer != null)
        {
            generateRevenue(balance);
            if (timer.isTimeUp())
            {
                this.feedingNow = false;
                timer = null;
            }
        }
    }
}
