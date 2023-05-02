package Data.AbstractClass;

import Data.*;
import Main.*;
import timer.*;

public abstract class Crop
{
    private String name;
    private double sellPrice;
    private int minutesNeededToGrow;
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

    public void plantCrop(Field field, Inventory inventory)
    {
        if (field.isReadyForPlant())
        {
            field.setReadyForPlant(false);
            Timer timer = new Timer(this.minutesNeededToGrow);

            if(timer.isTimeUp())
            {
                this.readyForHarvest = true;
                field.setReadyForPlant(true);
                inventory.increaseProductQuantity(this.name,1);
            }
        }
    }
}

