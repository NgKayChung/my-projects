package Assignment.ATMClass;

public class InvalidAccountNumberException extends Exception
{
    private String message;
    
    InvalidAccountNumberException()
    {
        message = "Invalid account number entered";
    }
    
    InvalidAccountNumberException(String a, int len)
    {
        message = "Account number : " + a + " is a " + len + "-digit number, ATM card account number should be of 16-digit";
    }
    
    InvalidAccountNumberException(String a)
    {
        message = "Account number : " + a + " should contains only numeric characters";
    }
    
    InvalidAccountNumberException(long a)
    {
        message = "Account number : " + a + " is not existing in ATM records";
    }
    
    InvalidAccountNumberException(int t)
    {
        message = "You have exceeded 3 tries on entering other user's account number for transfer";
    }
    
    public String toString()
    {
        return message;
    }
}