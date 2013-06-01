package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import entities.User;

@Path("/user")
public class UserResource {

	 // This method is called if XMLis request
	 @GET
	 @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public User get(){
		//User user = new User(0, "stam", "stam@gmail.com", "secret", "stam", "someone", "nowhere", "somewhere", 0, "sss", 1, "sss", "sss");
		return new User();
	}
	
}
