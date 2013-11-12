/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ledgerclient;


import com.ledgerapp.service.accountservice.Account;
import com.ledgerapp.service.accountservice.AddAccount;
import com.ledgerapp.service.accountservice.AddAccountImplService;
import com.ledgerapp.service.accountservice.GetAccount;
import com.ledgerapp.service.accountservice.GetAccountImplService;
import com.ledgerapp.service.accountservice.InvalidAccountException_Exception;
import com.ledgerapp.service.transactionservice.AddTransaction;
import com.ledgerapp.service.transactionservice.AddTransactionImplService;
import com.ledgerapp.service.transactionservice.GetTransactionInformation;
import com.ledgerapp.service.transactionservice.GetTransactionInformationImplService;
import com.ledgerapp.service.transactionservice.Transaction;
import com.ledgerapp.service.userservice.GetUserImpl;
import com.ledgerapp.service.userservice.GetUserImplService;
import com.ledgerapp.service.userservice.RegisterUser;
import com.ledgerapp.service.userservice.RegisterUserImplService;
import com.ledgerapp.service.userservice.User;

/**
 *
 * @author Jimmy
 */
public class LedgerClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Account account = null;
        User user = null;
        Transaction trans = null;
        Account newAccount = null;
        User newUser = null;
        Transaction newTrans = null;
        
        try 
        {
            account = getAccount("");
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }
        
        try
        {
            user = getUser("HomeSimpson");
        }
        catch (Exception e)
        {
            System.out.println("Invalid username");
        }
        
        try
        {
            trans = getTransaction();
        }
        catch (Exception e)
        {
            System.out.println("Invalid Transaction");
        }
                
        
        
        
        newUser = registerUser("New User");
        addAccount("New Account", "1234567890", "New Bank");
        newTrans = addTransaction("To/From", "11/03/2013", 99.99, "123456790");
        
        System.out.println("*******User Info************************");
        System.out.println("UserName: " + user.getUsername());
        System.out.println("****************************************");
        
        if (account != null)
        {
            System.out.println("*******Account Info********************");
            System.out.println("Account Name: " + account.getAccountName());
            System.out.println("Account Number: " + account.getAccountNum());
            System.out.println("Bank Name: " + account.getBankName());
            System.out.println("****************************************");
        }
        
        
        System.out.println("*******Transaction Info*****************");
        System.out.println("To/From: " + trans.getToFrom());
        System.out.println("Transaction Amount: " + trans.getTransAmount());
        System.out.println("Transaction Date: " + trans.getTransDate());
        System.out.println("Account Number: " + trans.getAccountNum());
        System.out.println("****************************************");
        
        
        System.out.println("*******User Info************************");
        System.out.println("UserName: " + newUser.getUsername());
        System.out.println("****************************************");
        
        
         System.out.println("*******Transaction Info*****************");
        System.out.println("To/From: " + newTrans.getToFrom());
        System.out.println("Transaction Amount: " + newTrans.getTransAmount());
        System.out.println("Transaction Date: " + newTrans.getTransDate());
        System.out.println("Account Number: " + newTrans.getAccountNum());
        System.out.println("****************************************");
        
    }
    
    private static Account getAccount(String accountNum) throws InvalidAccountException_Exception {
        
        GetAccountImplService service = new GetAccountImplService();
        GetAccount port = service.getGetAccountImplPort();
        return port.getAccount(accountNum); 
    }
    
    
    private static User getUser(String userName) {
        
        GetUserImplService service = new GetUserImplService();
        GetUserImpl port = service.getGetUserImplPort();
        return port.getUser(userName);
        
    }
    
    private static Transaction getTransaction() {
        
        GetTransactionInformationImplService service = new GetTransactionInformationImplService();
        GetTransactionInformation port = service.getGetTransactionInformationImplPort();
        return port.getTransaction();
    }
    
    private static User registerUser(String userName) {
        
        User user = null;
        RegisterUserImplService service = new RegisterUserImplService();
        RegisterUser port = service.getRegisterUserImplPort();
        return port.registerUser(userName);
    }
    
    private static void addAccount(String accountName, String accountNumber, String bankName) {
        
        Account account = null;
        AddAccountImplService service = new AddAccountImplService();
        AddAccount port = service.getAddAccountImplPort();
        port.addAccount(bankName, bankName, bankName);
        
        
    }
   
    private static Transaction addTransaction(String toFrom, String transDate, double transAmount, String accountNumber) {
        
        Transaction trans = null;
        AddTransactionImplService service = new AddTransactionImplService();
        AddTransaction port = service.getAddTransactionImplPort();
        return port.addTransaction(toFrom, toFrom, transAmount, toFrom);
        
    }
}
