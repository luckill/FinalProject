package scriptingEngine;

import java.util.*;

public class Command
{
    private String cmd;
    private String[] parameters;

    public Command(String raw)
    {
        StringTokenizer tokenizer = new StringTokenizer(raw, ":");
        if(tokenizer.countTokens() < 2)
        {
            return;
        }
        cmd = tokenizer.nextToken().trim();
        String rawParams = tokenizer.nextToken().trim();
        StringTokenizer tokenizer2 = new StringTokenizer(rawParams, ",");
        parameters = new String[tokenizer2.countTokens()];
        for (int i =0; i < parameters.length; i++)
        {
            parameters[i] = tokenizer2.nextToken().trim();
        }
    }

    public boolean isCommand(String input)
    {
        if(cmd == null)
        {
            return false;
        }
        return cmd.equalsIgnoreCase(input);
    }

    public int getNumberOfParameters()
    {
        if(parameters == null)
        {
            return 0;
        }
        return parameters.length;
    }

    public String getParametersByIndex(int index)
    {
        if (parameters == null)
        {
            return null;
        }

        if(index >= parameters.length)
        {
            return null;
        }
        return parameters[index];
    }
}
