package Data.Animal;

import Data.*;
import Data.AbstractClass.*;
import timer.*;

public class Sheep extends Animal implements Revenue
{
    private Timer timer;

    public Sheep()
    {
        super("sheep", 250.00);
        this.timer = new Timer();
    }
    @Override
    public void generateRevenue(Balance balance)
    {
        if(timer.getElapsedTimeInMinute() == 20)
        {
            balance.addBalance(300.00);
        }
    }
}
