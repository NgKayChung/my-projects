package Assignment.ATMClass;

import java.util.*;
import java.io.*;

public class TestATM
{
    public static void main(String args[])
    {
        ATM atm;
        
        //variable used for login or exit the system
        int choice = 0;
        
        //variable used for options of transaction
        int option = 0;
        
        try
        {
            //invoke ATM constructor - read files and initialize user
            atm = new ATM();
            
            do
            {
                //display ATM menu
                atm.start();
                
                try
                {
                    //prompt for choice - Login or Exit
                    choice = atm.promptStart();
                    
                    switch(choice)
                    {
                        case 1: //login
                        {   
                            try
                            {
                                //User login, prompt for account number(insert card) & pin number
                                atm.userLogin();

                                do
                                {
                                    //display options available 
                                    atm.displayATMOptions();

                                    try
                                    {
                                        //get option from user (1 - 7)
                                        option = atm.promptOptions();
                                    }
                                    catch(InvalidAccountNumberException aExp)
                                    {
                                        System.out.println(aExp);
                                    }
                                    catch(InvalidPinNumberException iExp)
                                    {
                                        System.out.println(iExp);
                                    }
                                    catch(InputMismatchException imExp)
                                    {
                                        System.out.println();
                                        System.out.println("Error. Invalid input value");
                                        atm.console.nextLine();
                                    }
                                    System.out.println();
                                }while(option != 7);
                            }
                            catch(InvalidAccountNumberException iExp)
                            {
                                System.out.println(iExp);
                                System.out.println();
                            }
                            catch(InvalidPinNumberException pExp)
                            {
                                System.out.println(pExp);
                                System.out.println();
                            }
                            catch(Exception e)
                            {
                                if(e instanceof InputMismatchException)
                                {
                                    System.out.println();
                                    System.out.println("Error. Invalid value entered");
                                    atm.console.nextLine();
                                    System.out.println();
                                }
                                else if(e instanceof NoSuchElementException)
                                {
                                    System.out.println("Error. No more data in storage");
                                    System.out.println();
                                }
                                else
                                {
                                    System.out.println(e);
                                    System.out.println();
                                }
                            }
                            break;
                        }

                        case 2:
                            //print all users data to file storage, close files and terminates system
                            atm.exit();
                            break;
                            
                        default:
                            System.out.println();
                            System.out.println("Error. Wrong choice entered");
                            System.out.println();
                    }
                }
                catch(InputMismatchException mExp)
                {
                    System.out.println();
                    System.out.println("Error. Invalid choice value entered, should be numeric character");
                    atm.console.nextLine();
                    System.out.println();
                }
            }while(choice != 2);
        }
        catch(FileNotFoundException fExp)
        {
            System.out.println("Error.  File not found");
        }
        catch(NumberFormatException nExp)
        {
            System.out.println("Error. Invalid data in storage" + '\n' + nExp);
        }
        catch(Throwable t)
        {
            System.out.println(t);
        }
    }
}