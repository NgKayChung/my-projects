package Assignment.ATMClass;

public class InvalidPinNumberException extends Exception
{
    private String message;
    
    InvalidPinNumberException()
    {
        message = "Pin number entered is not match with your acccount's pin number";
    }
    
    InvalidPinNumberException(String msg)
    {
        message = msg;
    }
    
    InvalidPinNumberException(String temp, int len)
    {
        message = "Pin number should consists of only 6-digit number";
    }
    
    InvalidPinNumberException(int count)
    {
        message = "You have exceeded 3 tries for validating your pin number";
    }
    
    public String toString()
    {
        return message;
    }
}