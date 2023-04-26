package sounds;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;

public class Sound
{
    private String string;
    private Clip clip;
    private AudioFormat format;
    private DataLine.Info info;
    private AudioInputStream stream;

    public Sound (String fileName)
    {
        string = fileName;
        File file = new File(string);
        try
        {
            stream =AudioSystem.getAudioInputStream(file);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(stream);
            clip.stop();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Audio not detected or audip problem...Exiting.");
            System.exit(0);
        }
    }

    public String getSonFileName()
    {
        return string;
    }

    public boolean isFinished()
    {
        return clip.getMicrosecondPosition() >= clip.getMicrosecondLength();
    }

    public boolean isPlaying()
    {
        return clip.isRunning();
    }

    public void resumeWAV()
    {
        clip.start();
    }

    public long getAudioLength()
    {
        return clip.getMicrosecondLength();
    }

    public long getAudioPosition()
    {
        return clip.getMicrosecondPosition();
    }

    public void playWAV()
    {
        if(clip.isActive())
        {
            clip.stop();
            clip.setMicrosecondPosition(0);
        }

        if(clip.getMicrosecondPosition() >= clip.getMicrosecondLength())
        {
            clip.setMicrosecondPosition(0);
        }
        clip.start();
    }

    public void resetWAV()
    {
        clip.setMicrosecondPosition(0);
    }

    public void flush()
    {
        clip.stop();
        clip.flush();
        clip.close();
    }

    public void pauseWAV()
    {
        if(clip.isRunning())
        {
            clip.stop();
        }
    }

    public void setLoop()
    {
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }
}
