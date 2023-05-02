package Data.AnimalFood;

import Data.*;
import Data.AbstractClass.*;

public class CowFood extends Item
{
    public CowFood()
    {
        super(1, "cowFood", 100.00, new String[] {"wheat", "corn"}, new int[] {3, 2});
    }
}
