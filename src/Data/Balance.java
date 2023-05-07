package Data;

import java.text.*;

public class Balance
{
    private double balance;
    public Balance()
    {
        this.balance = 0;
    }

    public double getBalance()
    {
        return balance;
    }

    public void addBalance(double price)
    {
        if (price >= 0)
        {
            this.balance += price;
        }
    }

    public void subtractBalance(double price)
    {
        this.balance -= price;
    }

    @Override
    public String toString()
    {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(this.balance);
    }
}
