package Data.AnimalFood;

import Data.*;
import Data.AbstractClass.*;

public class ChickenFood extends Item
{
    public ChickenFood()
    {
        super(1, "chickenFood", 100.00, new String[]{"wheat", "carrot"}, new int[]{2, 1});
    }
}
