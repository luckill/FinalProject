package Data;

public class RECT
{
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private String tag;
    private String hoverLabel;
    private Frame graphicalHover;

    public Frame getGraphicalHover()
    {
        return graphicalHover;
    }

    public String getTag()
    {
        return tag;
    }

    public String getHoverLabel()
    {
        return hoverLabel;
    }

    public RECT(int x1, int y1, int x2, int y2, String tag)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.tag = tag;
        this.hoverLabel = "";
        this.graphicalHover = null;
    }

    //text hover
    public RECT(int x1, int y1, int x2, int y2,String tag, String hoverLabel)
    {
        this(x1, y1, x2, y2, tag);
        this.hoverLabel = hoverLabel;
    }

    //graphical hover
    public RECT(int x1, int y1, int x2, int y2, String tag, Frame graphicalHover)
    {
        this(x1,y1,x2,y2,tag);
        this.graphicalHover = graphicalHover;
    }

    //hybrid hover
    public RECT(int x1, int y1, int x2, int y2, String tag, String hoverLabel,  Frame graphicalHover)
    {
        this(x1,y1,x2,y2, tag, hoverLabel);
        this.graphicalHover = graphicalHover;
    }

    public boolean isCollision(int x, int y)
    {
        if(x >= x1 && x <= x2)
        {
            return y >= y1 && y <= y2;
        }
        return false;
    }

    public boolean isClicked(Click c, int buttonComparator)
    {
        if(c.getButton() != buttonComparator)
        {
            return false;
        }
        return isCollision(c.getX(),c.getY());
    }
}