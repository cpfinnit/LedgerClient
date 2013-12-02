/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ledgerclient;


import com.ledgerapp.service.userservice.User;
import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 *
 * @author Jimmy
 */
public class LedgerRestfulClient {
    
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        

        
        Client client = ClientBuilder.newBuilder().build();
        
        ////*************************XML Media Type*************************////
        //Create user XML;
        WebTarget userTarget = client.target(getBaseURI());
        Form form = new Form().param("username", "Bruce Wayne");
        Response newResponse = userTarget.path("users").request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML).post(Entity.form(form));
        User user = newResponse.readEntity(User.class);
        System.out.println("New User: " + user.getUsername());
        
        //Get User XML
        WebTarget readUserTarget = client.target(getBaseURI());
        Response readResponse = readUserTarget.path("users").path("Bruce Wayne").request(MediaType.APPLICATION_XML).get();
        User newUser = readResponse.readEntity(User.class);
        System.out.println("Get User XML Username: " + newUser.getUsername());
        
        //Update User XML
        WebTarget updateUserTarget = client.target(getBaseURI());
        Form updateForm = new Form().param("_username", "Batman");
        Response updateResponse = updateUserTarget.path("users").path("Bruce Wayne").request(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_XML).put(Entity.form(updateForm));
        User updatedXMLUser = updateResponse.readEntity(User.class);
        System.out.println("Updated XML Username: " + updatedXMLUser.getUsername());
        
        //Delete User XML
        WebTarget deleteUser = client.target(getBaseURI());
        Response deleteResponse = deleteUser.path("users").path("Batman").request().delete();
        deleteResponse.readEntity(String.class);
        
        
////*************************Plain  Text Media Type*************************////
        //Create user String
        WebTarget newUserTarget = client.target(getBaseURI());
        Form newForm = new Form().param("username", "Clark Kent");
        Response newUserResponse = newUserTarget.path("users").request(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.TEXT_PLAIN).post(Entity.form(newForm));
        System.out.println("New User Plain Text: " + newUserResponse.readEntity(String.class));
        
        //Get User String
        WebTarget readUserTargetString = client.target(getBaseURI());
        Response readResponseString = readUserTargetString.path("users").path("Clark Kent").request(MediaType.TEXT_PLAIN).accept(MediaType.TEXT_PLAIN).get();
        System.out.println("Get Plain Text Username: " + readResponseString.readEntity(String.class));
        
        //Update User String
        WebTarget updateUserTargetString = client.target(getBaseURI());
        Form updateFormString = new Form().param("_username", "Superman");
        Response updateResponseString = updateUserTargetString.path("users").path("Clark Kent").request(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.TEXT_PLAIN).put(Entity.form(updateFormString));
        System.out.println("Updated Plain Text Username: " + updateResponseString.readEntity(String.class));

        //Delete User String
        WebTarget deleteUserString = client.target(getBaseURI());
        Response deleteResponseString = deleteUserString.path("users").path("Superman").request(MediaType.TEXT_PLAIN).accept(MediaType.TEXT_PLAIN).delete();
        deleteResponseString.readEntity(String.class);
        

        //This should invoke my custome exception and come back with a 404, but my UserNotFoundMapper is not being invoked. 
        WebTarget readUserTargetFail = client.target(getBaseURI());
        Response readResponseFail = readUserTargetFail.path("users").path("No One").request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML).get();
        System.out.println(readResponseFail.readEntity(String.class));
        
       
    }
    
    
    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/LedgerApp/rest").build();
    }
}
