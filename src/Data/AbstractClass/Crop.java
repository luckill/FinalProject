package Data.AbstractClass;

import timer.*;

public abstract class Crop
{
    private String name;
    private double sellPrice;
    private final int minutesNeededToGrow;
    private boolean readyForHarvest;

    public Crop(String name, double sellPrice, int minutesNeededToGrow)
    {
        this.name = name;
        this.sellPrice = sellPrice;
        this.minutesNeededToGrow = minutesNeededToGrow;
        this.readyForHarvest = false;
    }

    public String getName()
    {
        return name;
    }

    public double getSellPrice()
    {
        return sellPrice;
    }

    public int getMinutesNeededToGrow()
    {
        return minutesNeededToGrow;
    }

    public void plantCrop()
    {
        Timer timer = new Timer(this.minutesNeededToGrow);
        if(timer.isTimeUp())
        {
            this.readyForHarvest = true;
        }
    }
}

