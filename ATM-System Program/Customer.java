package Assignment.ATMClass;

public abstract class Customer implements Person
{
    private String firstName;
    private String lastName;
    private long accountNumber;
    
    public Customer(){}
    
    public Customer(String fName, String lName, long accNo)
    {
        firstName = fName;
        lastName = lName;
        accountNumber = accNo;
    }
    
    public void setName(String fName, String lName)
    {
        firstName = fName;
        lastName = lName;
    }
    
    public void setAccountNumber(long accNo)
    {
        accountNumber = accNo;
    }
    
    public String getFirstName()
    {
        return firstName;
    }
    
    public String getLastName()
    {
        return lastName;
    }
    
    public String getName()
    {
        return (firstName + " " + lastName);
    }
    
    public long getAccountNumber()
    {
        return accountNumber;
    }
    
    public abstract void setCurrentBalance(double balance);
    public abstract double getCurrentBalance();
}