package Assignment.ATMClass;

public class ATMAccount extends Customer
{
    private String pinNumber;
    private double currentBalance;
    private double totalDeposit = 0;
    private double totalWithdrawal = 0;
    private double totalTransfer = 0;
    private String[] answers;
    
    public ATMAccount(){}
    
    public ATMAccount(String fName, String lName, long accNo, String pin, double balance, String[] ans)
    {
        super(fName, lName, accNo);
        pinNumber = pin;
        currentBalance = balance;
        answers = ans;
    }
    
    public void setPin(String pin)
    {
        pinNumber = pin;
    }
    
    public void setCurrentBalance(double balance)
    {
        currentBalance = balance;
    }
    
    public void addDeposit(double depositAmount)
    {
        totalDeposit += depositAmount;
        currentBalance += depositAmount;
    }
    
    public void addWithdrawal(double withdrawalAmount)
    {
        totalWithdrawal += withdrawalAmount;
        currentBalance -= withdrawalAmount;
    }
    
    public void addTransfer(double transferAmount)
    {
        totalTransfer += transferAmount;
        currentBalance -= transferAmount;
    }
    
    public String getPin()
    {
        return pinNumber;
    }
    
    public double getCurrentBalance()
    {
        return currentBalance;
    }
    
    public double getTotalDeposit()
    {
        return totalDeposit;
    }
    
    public double getTotalWithdrawal()
    {
        return totalWithdrawal;
    }
    
    public double getTotalTransfer()
    {
        return totalTransfer;
    }
    
    public String[] getAnswer()
    {
        return answers;
    }
    
    public String getAnswer(int index)
    {
        return answers[index];
    }
    
    public String toString()
    {
        return (getName() + " " + getAccountNumber() + " " + getPin() + " " + String.format("%.2f", currentBalance));
    }
}