package Data.Animal;

import Data.*;
import Data.AbstractClass.*;
import timer.*;

public class Cow extends Animal implements Revenue
{
    private Timer timer;
    public Cow()
    {
        super("cow");
        this.timer = new Timer();
    }


    @Override
    public void generateRevenue(Balance balance)
    {
        if(timer.getElapsedTimeInMinute() == 2)
        {
            balance.addBalance(200.00);
            timer.resetWatch();
        }
    }
}
