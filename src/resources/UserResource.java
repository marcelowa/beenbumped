package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import dao.UserDao;
import entities.User;

@Path("/user")
public class UserResource {

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public User get(@QueryParam("username") String username,
			@QueryParam("password") String password) {
		User user = UserDao.getInstance().authenticate(username, password);
		if (null == user) {
			throw new RuntimeException("User::get wrong username or password");
		}
		return user;
	}
}
