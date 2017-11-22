package Assignment.ATMClass;

import java.io.*;
import static java.lang.Math.*;

public class FixedDeposit extends ATM
{
    //static variable - interestRate which will be used by calcInterest()
    //same value for all class objects
    //range is 1.0 to 10.0
    private final static double INTEREST_RATE = 1.0 + random() * 9.0;
    
    //constructor for class FixedDeposit, invokes super class constructor - ATM()
    public FixedDeposit() throws FileNotFoundException, NumberFormatException, Throwable
    {
        super();
    }
    
    //method used by fixed deposit account, invoked once the system is terminated
    //interest will be added to all users' balance
    public void calcInterest()
    {
        for(int index = 0;index < getNumberOfUsers();index++)
        {
            ATMAccount tempUser = getUser(index);

            double oldBalance = tempUser.getCurrentBalance();

            double interest = oldBalance * (INTEREST_RATE / 100);

            tempUser.setCurrentBalance(oldBalance + interest);
        }
    }
    
    //override the exit() method in ATM class
    // - calcInterest() will be invoked
    // - write to file and close file
    public void exit() throws FileNotFoundException
    {
        calcInterest();
        super.exit();
    }
}