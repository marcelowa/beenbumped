package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import dao.UserDao;
import entities.User;

@Path("/user")
public class UserResource {

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public User get(@PathParam("id") int id) {
		User user = UserDao.getInstance().getById(id);
		if (null == user) {
			throw new RuntimeException("User::get with id " + id + " not found");
		}
		return user;
	}
}
