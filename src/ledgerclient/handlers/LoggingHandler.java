/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ledgerclient.handlers;



import com.ledgerapp.service.accountservice.Account;
import com.ledgerapp.service.accountservice.GetAccountResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

/**
 *
 * @author Jimmy
 */
public class LoggingHandler implements LogicalHandler <LogicalMessageContext> {

    @Override
    public boolean handleMessage(LogicalMessageContext c) {
        
        //Checks the message to see if it is an outbound message and sets the boolean outbound to the correct state.
        Boolean outbound = (Boolean) c.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        
        //If the message is not outbound we will handle it.
        if (!outbound)
        {
     
            LogicalMessage msg = c.getMessage();
            
            try
            {
                JAXBContext jaxb_ctx = JAXBContext.newInstance("com.ledgerapp.service.accountservice");
                
                //set payload object to the payload returned from the JAXBContext
                Object payload = msg.getPayload(jaxb_ctx);
                
                //If the payload is an instance of the JAXBElement
                if (payload instanceof JAXBElement)
                {
                    
                    //sets the object = to the payload by getting it using the getValue() method
                    Object obj = ((JAXBElement) payload).getValue();
                
                    //If the object is an instance of the GetAccountResponse service
                    if (obj instanceof GetAccountResponse)
                    {
                        //cast the response to an GetAccount Response object
                        GetAccountResponse response = (GetAccountResponse) obj;
                        //get an account from the getReturn method
                        Account account = response.getReturn();
                        //Log the account info using the getters in the account class
                        Logger.getLogger(LoggingHandler.class.getName()).log(Level.INFO,
                            "Account Number: " + account.getAccountNum() + "\n" +
                            "Account Name: " + account.getAccountName() + "\n" +
                            "Account Balance: " + account.getBankName() + "\n");
                    }
                }
            }
            catch (JAXBException jaxbe)
            {
                Logger.getLogger(LoggingHandler.class.getName()).log(Level.SEVERE, null, jaxbe);
            }
        }
            
            //continues the handler chain
            return true;
        
    }

    @Override
    public boolean handleFault(LogicalMessageContext c) {
        return true;
    }

    @Override
    public void close(MessageContext mc) {
    }
    
}
