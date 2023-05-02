package Data.Factory;

import java.util.*;

import timer.*;
import timer.Timer;

public class Factory
{
    public static Set<Receipt> receiptList = new HashSet<>();
    private int conveyorsInUsed;
    private boolean isFactoryRunning;
    private Queue<Receipt> poductionQueue;

    public Factory()
    {
        this.conveyorsInUsed = 0;
        this.isFactoryRunning = false;
        this.poductionQueue = new LinkedList<>();
    }

    public void Production()
    {
        if (!poductionQueue.isEmpty())
        {
            if (!isFactoryRunning)
            {
                isFactoryRunning = true;
                Receipt receipt = poductionQueue.remove();
                Timer timer = new Timer(receipt.getTime());
                conveyorsInUsed++;
                if (timer.isTimeUp())
                {
                    conveyorsInUsed--;
                    isFactoryRunning = !isFactoryRunning;
                }
            }
        }
    }
}
