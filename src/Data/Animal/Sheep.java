package Data.Animal;

import Data.*;
import Data.AbstractClass.*;
import timer.*;

public class Sheep extends Animal implements Revenue
{
    private Timer timer;

    public Sheep()
    {
        super("sheep");
        this.timer = new Timer();
    }
    @Override
    public void generateRevenue(Balance balance)
    {
        if(timer.getElapsedTimeInMinute() == 2)
        {
            balance.addBalance(300.00);
            timer.resetWatch();
        }
    }
}
