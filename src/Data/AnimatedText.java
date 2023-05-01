package Data;

import timer.*;

public class AnimatedText
{
    private String srcString;
    private String destString;
    private stopWatchX timer;
    private boolean isFinished;
    private int cursor;

    public AnimatedText(String srcString, int delay)
    {
        this.srcString = srcString;
        timer = new stopWatchX(delay);
        destString = "";
        isFinished = false;
        cursor = 0;
    }

    public String getCurrentStr()
    {
        if (isFinished)
        {
            return destString;
        }
        if (timer.isTimeUp())
        {
            if (cursor < srcString.length())
            {
                destString += srcString.charAt(cursor++);
            }
            if (cursor >= srcString.length())
            {
                isFinished = true;

            }
            timer.resetWatch();
        }
        return destString;
    }

    public boolean isAnimationDone()
    {
        return isFinished;
    }

    public void resetAnimation()
    {
        isFinished = false;
        destString = "";
        cursor = 0;
        timer.resetWatch();
    }
}
