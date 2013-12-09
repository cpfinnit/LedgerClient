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
        
        //Crate User XML
        WebTarget userTarget = client.target(getBaseURI());
        Form form = new Form().param("username", "Bruce Wayne");
        Response newResponse = userTarget.path("users").queryParam("reqFormat", "xml").request().post(Entity.form(form));
        User user = newResponse.readEntity(User.class);
        System.out.println("New User: " + user.getUsername());

        //Create user String
        WebTarget newUserTarget = client.target(getBaseURI());
        Form newForm = new Form().param("username", "Clark Kent");
        Response newUserResponse = newUserTarget.path("users").queryParam("reqFormat", "plain/text").request().post(Entity.form(newForm));
        System.out.println("New User Plain Text: " + newUserResponse.readEntity(String.class));
        
        //Get User XML
        WebTarget readUserTarget = client.target(getBaseURI());
        Response readResponse = readUserTarget.path("users").path("1").queryParam("reqFormat", "xml").request().get();
        User newUser = readResponse.readEntity(User.class);
        System.out.println("Get User XML Username: " + newUser.getUsername());
        
        //Get User String
        WebTarget readUserTargetString = client.target(getBaseURI());
        Response readResponseString = readUserTargetString.path("users").path("1").queryParam("reqFormat", "plain/text").request().get();
        System.out.println("Get Plain Text Username: " + readResponseString.readEntity(String.class));
       
        //Update User XML
        WebTarget updateUserTarget = client.target(getBaseURI());
        Form updateForm = new Form().param("_username", "Batman");
        Response updateResponse = updateUserTarget.path("users").path("Bruce Wayne").queryParam("reqFormat", "xml").request().put(Entity.form(updateForm));
        User updatedXMLUser = updateResponse.readEntity(User.class);
        System.out.println("Updated XML Username: " + updatedXMLUser.getUsername());
        
        //Update User String
        WebTarget updateUserTargetString = client.target(getBaseURI());
        Form updateForm1 = new Form().param("_username", "Superman");
        Response updateResponseString = updateUserTargetString.path("users").path("Clark Kent").queryParam("reqFormat", "plain/text").request().put(Entity.form(updateForm1));
        System.out.println("Updated Plain Text Username: " + updateResponseString.readEntity(String.class));
        
        //Delete User String
        WebTarget deleteUserString = client.target(getBaseURI());
        Response deleteResponseString = deleteUserString.path("users").path("Batman").queryParam("reqFormat", "plain/text").request().delete();
        deleteResponseString.readEntity(String.class);
 
        //Delete User XML
        WebTarget deleteUser = client.target(getBaseURI());
        Response deleteResponse = deleteUser.path("users").path("Superman").queryParam("reqFormat", "xml").request().delete();
        deleteResponse.readEntity(String.class);
        
    }
    
       
    
    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/LedgerApp/rest").build();
    }
}
