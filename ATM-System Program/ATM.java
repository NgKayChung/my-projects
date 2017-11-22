package Assignment.ATMClass;

import java.util.*;
import java.io.*;

public class ATM
{
    private LinkedList<ATMAccount> users = new LinkedList<ATMAccount>(); //linked list for class ATMAccount(s)
    private ATMAccount currentUser = null; //ATMAccount instance that points to the currently logged in user in the list
    private ATMAccount transferringUser = null; //ATMAccount instance that points to the user that currentUser wants to transfer amount to
    
    //constant String array variable with size of 3 - questions for changing pin number
    private static final String[] QUESTIONS = new String[]{"What is the name of your hometown?", "What is the name of your pet?", "What is your favourite food?"};
    
    public static Scanner console = new Scanner(System.in);
    private Scanner inDataFile;      //file scanner that uses to read users' account data - "User Accounts.txt"
    private Scanner inSecurityFile;  //file scanner that uses to read users' initial answers for questions in changing pin number - "User Answers.txt"
    private PrintWriter outDataFile; //file writer that prints all the user data from the list to the file - "User Accounts.txt"
    
    //ATM constructor - read data from files and add each user to list
    public ATM() throws FileNotFoundException, NumberFormatException, Throwable
    {
        readFiles();
    }
    
    private void openDataFile() throws FileNotFoundException
    {
        inDataFile = new Scanner(new FileReader("src\\Assignment\\ATMClass\\ATM Database\\User Accounts.txt"));
    }
    
    private void openSecurityFile() throws FileNotFoundException
    {
        inSecurityFile = new Scanner(new FileReader("src\\Assignment\\ATMClass\\ATM Database\\User Answers.txt"));
    }
    
    private void readFiles() throws FileNotFoundException, NumberFormatException, Throwable
    {
        openDataFile();
        openSecurityFile();
        
        while(inDataFile.hasNext("\\w*")) //while not EOF / still has words(alphanumeric characters)
        {
            String tempFirst = inDataFile.next();
            String tempLast = inDataFile.next();
            long tempAcc = Long.parseLong(inDataFile.next());
            String tempPin = inDataFile.next();
            if(tempPin.length() != 6)
                throw new NumberFormatException(tempPin);
            double tempBal = Double.parseDouble(inDataFile.next());
            long securityTempAcc = Long.parseLong(inSecurityFile.nextLine()); //read user account number in securityFile
            if(securityTempAcc != tempAcc) //compare account numbers from data file and security file
                throw new Throwable("Error. Account number in security file not match with account number in data file");
            String ans1 = inSecurityFile.nextLine();
            String ans2 = inSecurityFile.nextLine();
            String ans3 = inSecurityFile.nextLine();
            
            initializeUser(tempFirst, tempLast, tempAcc, tempPin, tempBal, new String[]{ans1, ans2, ans3});
        }
    }
    
    //assign parameters value to ATMAccount and add to list
    private void initializeUser(String firstName, String lastName, long accountNumber, String pinNumber, double currentBalance, String[] answers)
    {
        users.add(new ATMAccount(firstName, lastName, accountNumber, pinNumber, currentBalance, answers));
    }
    
    public void start()
    {
        System.out.println("-------------------------- Welcome to Meybank ATM ---------------------------");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("                 1  ----------------------------  Login");
        System.out.println("                 2  ----------------------------  Quit");
        System.out.println("-----------------------------------------------------------------------------");
    }
    
    //prompt for choice
    //1 for user login
    //2 for exit the system(normally done by administrator)
    public int promptStart() throws InputMismatchException
    {
        int choice;
        
        System.out.print("Enter your choice : ");
        choice = console.nextInt();
            
        return choice;
    }
    
    //invoked after user chose to login
    public void userLogin() throws InputMismatchException, InvalidAccountNumberException, InvalidPinNumberException
    {
        System.out.println();
        promptAccount();      //user *inserts card* to the machine, reads account number
        promptPin();          //prompt for pin number to validate the user
    }
    
    //this method is the same as when the user inserting his/her ATM card
    //reads card account number
    public void promptAccount() throws InputMismatchException, InvalidAccountNumberException
    {
        String tempAccNo;
        
        System.out.print("(Card insert) Please enter your account number : ");
        tempAccNo = console.next();
        System.out.println();
        
        if(!tempAccNo.matches("^[0-9]*")) //if not whole numeric characters
            throw new InvalidAccountNumberException(tempAccNo);
        else if(tempAccNo.length() != 16)
            throw new InvalidAccountNumberException(tempAccNo, tempAccNo.length());
        
        long acc = Long.parseLong(tempAccNo);
        
        int uIndex = findInDatabase(acc); //find the entered account number in database(list)
        
        if(uIndex == -1) //if not found
            throw new InvalidAccountNumberException(acc);
        
        currentUser = users.get(uIndex); //currentUser points to the entered account
    }
    
    //after user inserted card, validates user by prompting for pin number
    public void promptPin() throws InvalidPinNumberException
    {
        int tries = 3; //set 3 tries for user to enter his/her pin number
        String tempPin;
        String currentPin = currentUser.getPin();
        boolean valid = false;
        
        do
        {
            try
            {
                System.out.print("Dear Mr/Miss " + currentUser.getLastName() + ", please enter your pin number : ");
                tempPin = console.next();
                System.out.println();
                
                if(!tempPin.matches("\\d{6}")) //if not whole numeric characters with length of 6
                {
                    tries--;
                    throw new InvalidPinNumberException(tempPin, -1);
                }
                else if(!tempPin.equals(currentPin)) //if not match with currentUser's pin number
                {
                    tries--;
                    throw new InvalidPinNumberException();
                }
                
                valid = true;
            }
            catch(InvalidPinNumberException pExp)
            {
                System.out.println(pExp);
                System.out.println();
                if(tries <= 0)
                    throw new InvalidPinNumberException(tries);
            }
        }while(valid == false);
    }
    
    public void displayATMOptions()
    {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("              1   ----------------------------   Deposit");
        System.out.println("              2   ----------------------------   Withdrawal");
        System.out.println("              3   ----------------------------   Transfer");
        System.out.println("              4   ----------------------------   Balance Enquiry");
        System.out.println("              5   ----------------------------   Print Statement");
        System.out.println("              6   ----------------------------   Change Pin");
        System.out.println("              7   ----------------------------   Quit");
        System.out.println("-----------------------------------------------------------------------------");
    }
    
    //prompts and obtains user option for process to do in the system
    //based on the available options (1 - 7)
    public int promptOptions() throws InputMismatchException, InvalidAccountNumberException, InvalidPinNumberException
    {
        int option = 0;
        
        try
        {
            System.out.print("Enter your option : ");
            option = console.nextInt();
            System.out.println();

            if(option < 1 || option > 7)
                System.out.println("Wrong option entered, option range is 1 - 7");

            switch(option)
            {
                case 1:
                    deposit();
                    break;

                case 2:
                    withdraw();
                    break;

                case 3:
                    transfer();
                    break;

                case 4:
                    balanceEnquiry();
                    break;

                case 5:
                    printStatement();
                    break;

                case 6:
                    changePinNumber();
                    break;

                case 7:
                    userLogout();
                    break;

                default:
            }
        }
        catch(InputMismatchException iExp)
        {
            System.out.println();
            System.out.println("Error. Invalid input, should be only digit number");
            console.nextLine();
        }
        
        return option;
    }
    
    protected ATMAccount getUser(int index)
    {
        return users.get(index);
    }
    
    protected int getNumberOfUsers()
    {
        return users.size();
    }
    
    private void deposit()
    {
        double deposit;
        
        try
        {
            //prompts for deposit amount
            System.out.print("Enter amount to be deposited : RM ");
            deposit = console.nextDouble();
            
            if(deposit < 0.0)
                throw new IllegalArgumentException("Error. Negative number entered");
            else if(deposit == 0.0)
                throw new IllegalArgumentException("Error. Zero value entered");

            currentUser.addDeposit(deposit);
        }
        catch(InputMismatchException mExp)
        {
            System.out.println();
            System.out.println("Error input. Number value is expected");
            console.nextLine();
        }
        catch(IllegalArgumentException iaExp)
        {
            System.out.println();
            System.out.println(iaExp);
        }
    }
    
    private void withdraw()
    {
        double withdraw;
        
        try
        {
            //prompts for withdrawal amount
            System.out.print("Enter amount to be withdrawn : RM ");
            withdraw = console.nextDouble();
            
            if(withdraw < 0.0)
                throw new IllegalArgumentException("Error. Negative number entered");
            else if(withdraw == 0.0)
                throw new IllegalArgumentException("Error. Zero value entered");

            double balance = currentUser.getCurrentBalance();
            
            //current balance after withdrawn should more than or equals to RM100
            if((balance - withdraw) < 100)
            {
                System.out.printf("Error, maximum withdrawal amount is RM %.2f", (balance - 100));
                System.out.println();
            }
            else
                currentUser.addWithdrawal(withdraw);
        }
        catch(InputMismatchException imExp)
        {
            System.out.println();
            System.out.println("Error input. Number value is expected");
            console.nextLine();
        }
        catch(IllegalArgumentException iaExp)
        {
            System.out.println();
            System.out.println(iaExp);
        }
    }
    
    private void transfer() throws InvalidAccountNumberException
    {
        String tempOtherAcc;
        int tries = 3;
        boolean success = false;
        
        do
        {
            try
            {
                //prompt for other user's account no
                System.out.print("Enter account number of the user to be transferred to : ");
                tempOtherAcc = console.next();
                
                if(!tempOtherAcc.matches("\\d*"))
                    throw new InvalidAccountNumberException(tempOtherAcc);
                else if(tempOtherAcc.length() != 16)
                    throw new InvalidAccountNumberException(tempOtherAcc, tempOtherAcc.length());
                
                long otherAcc = Long.parseLong(tempOtherAcc);
                
                if(otherAcc == currentUser.getAccountNumber())
                    throw new IllegalArgumentException("Account number entered is the same as your account number\nPlease enter a valid account number");
                
                int uIndex = findInDatabase(otherAcc);

                if(uIndex == -1)
                    throw new InvalidAccountNumberException(otherAcc);

                transferringUser = users.get(uIndex);

                double balance = currentUser.getCurrentBalance();
                double amountToBeTransferred;

                //prompt for transfering amount
                System.out.println();
                System.out.print("Enter amount to transfer : RM ");
                amountToBeTransferred = console.nextDouble();
                
                if(amountToBeTransferred < 0.0)
                    throw new IllegalArgumentException("Negative value entered\nPlease enter value more than 0");
                else if(amountToBeTransferred == 0.0)
                    throw new IllegalArgumentException("Zero value entered\nPlease enter value more than 0");
                
                //current balance should more than or equals to RM100
                if((balance - amountToBeTransferred) < 100)
                {
                    System.out.println();
                    System.out.printf("Error. Maximum transfer amount is RM %.2f%n", (balance - 100));
                    System.out.println();
                }
                else
                {
                    success = true;
                    currentUser.addTransfer(amountToBeTransferred);
                    transferringUser.setCurrentBalance(transferringUser.getCurrentBalance() + amountToBeTransferred);
                }
            }
            catch(InvalidAccountNumberException anExp)
            {
                tries--;
                System.out.println();
                System.out.println(anExp);
                System.out.println();
                if(tries <= 0)
                    throw new InvalidAccountNumberException(tries);
            }
            catch(InputMismatchException imExp)
            {
                System.out.println();
                System.out.println("Error. Invalid input, number value is expected");
                console.nextLine();
                
                System.out.print("Or do you want to cancel transferring?(y/n) ");
                char c = console.next().charAt(0);
                console.nextLine();
                System.out.println();
                
                if(c == 'y' || c == 'Y')
                    return;
            }
            catch(IllegalArgumentException iaExp)
            {
                System.out.println();
                System.out.println(iaExp);
                
                char c;
                
                do
                {
                    System.out.print("Or do you want to cancel transferring?(y/n) ");
                    c = console.next().charAt(0);
                    console.nextLine();
                    System.out.println();

                    if(c == 'y' || c == 'Y')
                        return;
                }while(c != 'y' && c != 'Y' && c != 'n' && c != 'N');
            }
        }while(success == false);
    }
    
    private void balanceEnquiry()
    {
        System.out.printf("Your current balance is RM %.2f", currentUser.getCurrentBalance());
        System.out.println();
    }
    
    private void printStatement()
    {
        double tDeposit = currentUser.getTotalDeposit();
        double tWithdrawal = currentUser.getTotalWithdrawal();
        double tTransferred = currentUser.getTotalTransfer();
        
        System.out.println("----------------------------   ATM Statement   -------------------------------");
        System.out.println();
        System.out.println("   Name  :  -------------------------------------------  " + currentUser.getName());
        System.out.println("   Account number  :   --------------------------------  " + currentUser.getAccountNumber());
        System.out.printf("   Current balance  :  --------------------------------  RM %.2f%n", currentUser.getCurrentBalance());
        if(tDeposit > 0.0)
            System.out.printf("   Total deposited amount  :  -------------------------  RM %.2f%n", tDeposit);
        
        if(tWithdrawal > 0.0)
            System.out.printf("   Total withdrawn amount  :  -------------------------  RM %.2f%n", tWithdrawal);
        
        if(tTransferred > 0.0)
            System.out.printf("   Total transferred amount  :  -----------------------  RM %.2f%n", tTransferred);
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
    }
    
    private void changePinNumber() throws InvalidPinNumberException
    {
        int correct = 0;
        
        System.out.println("Before typing new password, please answer several questions");
        System.out.println();
        console.nextLine();
        
        //ask questions, prompt for answers and compare entered answers with actual answers from the database(array)
        for(int i = 0;i < QUESTIONS.length;i++)
        {
            System.out.print(QUESTIONS[i] + " "); //ask questions - from QUESTIONS[]
            String tempAns = console.nextLine(); //get answers from user
            System.out.println();
            
            if(tempAns.equals(currentUser.getAnswer(i))) //compare with initial answers
                correct++;
        }
        
        if(correct < QUESTIONS.length) //if not all correct
        {
            System.out.println("Answers entered are not matched with your initial answers");
            System.out.println("Going back to menu");
        }
        else
        {
            String tempNewPin;
            int tries = 3;
            boolean valid = false;
            boolean again = false;
            
            do
            {
                try
                {
                    //prompt for new pin number
                    System.out.print("Enter your new 6-digit pin number : ");
                    tempNewPin = console.next();

                    if(tempNewPin.length() != 6)
                    {
                        tries--;
                        throw new InvalidPinNumberException(tempNewPin, -1);
                    }
                    else if(!tempNewPin.matches("\\d*"))
                    {
                        tries--;
                        throw new InvalidPinNumberException("Pin number should consists of only numeric characters");
                    }

                    String oldPin = currentUser.getPin();

                    if(oldPin.equals(tempNewPin))
                    {
                        tries--;
                        System.out.println();
                        System.out.println("There is no difference between the entered pin number and the old pin number");
                        
                        if(again == false)
                        {
                            char choice;
                            
                            do
                            {
                                System.out.print("Do you want to enter again your new pin number ?(y/n) ");
                                choice = console.next().charAt(0);
                                console.nextLine();
                                System.out.println();

                                if(choice == 'y' || choice == 'Y') //user chose to enter again
                                {
                                    if(tries <= 0)
                                        tries++; //give user one more chance
                                    
                                    again = true; //indicates user already had one extra try to enter new pin
                                }
                                else if(choice == 'n' || choice == 'N')
                                    return;
                            }while(choice != 'y' && choice != 'Y' && choice != 'n' && choice != 'N');
                        }
                        else
                        {
                            System.out.println("Going back to menu");
                            return;
                        }
                    }
                    else
                    {
                        valid = true;
                        currentUser.setPin(tempNewPin);
                    }
                }
                catch(InvalidPinNumberException nExp)
                {
                    System.out.println();
                    System.out.println(nExp);
                    System.out.println();
                    if(tries <= 0)
                        throw new InvalidPinNumberException("You have exceeded 3 tries for changing your account pin number");
                }
            }while(valid == false);
        }
    }
    
    //user chose to logout
    //currentUser and transferringUser point to nothing(or stop pointing to accounts in the list)
    private void userLogout()
    {
        currentUser = null;
        transferringUser = null;
        System.out.println("Thank you very much for using our ATM service");
    }
    
    //finds the account number in the database
    //if found, return the index of the account in the list (index >= 0)
    //otherwise, return -1 (indicates not found)
    private int findInDatabase(long a)
    {
        for(int i = 0;i < users.size();i++)
        {
            if(users.get(i).getAccountNumber() == a)
                return i;
        }
        return -1;
    }
    
    private void openOutDataFile() throws FileNotFoundException
    {
        outDataFile = new PrintWriter("src\\Assignment\\ATMClass\\ATM Database\\User Accounts.txt");
    }
    
    private void printToFiles()
    {
        while(!users.isEmpty()) //while the list is not empty
            outDataFile.println(users.removeFirst()); //returns and removes the first(head) user from the list
    }
    
    private void closeFiles()
    {
        inDataFile.close();
        inSecurityFile.close();
        outDataFile.close();
    }
    
    public void exit() throws FileNotFoundException
    {
        openOutDataFile();
        printToFiles();
        closeFiles();
    }
}