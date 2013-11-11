/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ledgerclient.handlers;


import static com.sun.org.apache.xerces.internal.impl.xpath.XPath.NodeTest.QNAME;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 *
 * @author Jimmy
 */
public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        
        System.out.println("#####STARTING HANDLER#####");
        
        //checks Message Context to see if the message is a request
        boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        
        //If the message is a request
        if(isRequest)
        {
            String message = "This is a header message";
            
            try
            {   //Gets the SOAPMessage
                SOAPMessage msg = context.getMessage();
                //Gets the SoapEnvelope
                SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
                //Get SOAPHeader
                SOAPHeader header = env.getHeader();
                
                //checks to see if a header is present, if it is not, adds a header
                if (header == null)
                    header = env.addHeader();
                
                //Creates new XML Qualified Name
                QName name = new QName("http://service.ledgerapp", "message");
                
                //Adds QName element to header
                SOAPHeaderElement el = header.addHeaderElement(name);
                
                //Adds the message to the header element as a text node
                el.setActor(SOAPConstants.URI_SOAP_ACTOR_NEXT);
                el.addTextNode(message);
                
                //Saves the above changes to the element. 
                msg.saveChanges();
                
                System.out.println("#####ENDING HANDLER#####");
            }
            catch (SOAPException ex)
            {
                System.out.println("IMPLEMENT LOGGING");
            }
        }
        
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        
       System.out.println("IMPLEMENT LOGGING");
       return true;
    }

    @Override
    public void close(MessageContext context) {
        System.out.println("IMPLEMENT LOGGING");
    }
    
}
