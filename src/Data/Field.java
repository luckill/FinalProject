package Data;

public class Field
{
    private boolean readyForPlant;
    public Field()
    {
        this.readyForPlant = true;
    }
    public boolean isReadyForPlant()
    {
        return readyForPlant;
    }
    public void setReadyForPlant(boolean readyForPlant)
    {
        this.readyForPlant = readyForPlant;
    }
}
