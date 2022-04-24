package tr.edu.duzce.mf.bm.api.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tr.edu.duzce.mf.bm.business.abstracts.UserService;
import tr.edu.duzce.mf.bm.business.concretes.UserManager;
import tr.edu.duzce.mf.bm.core.dataAccess.concretes.JDBCUserDao;
import tr.edu.duzce.mf.bm.core.entities.concrete.User;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import java.util.List;

@Path("/users")
public class UserResource {
    private UserService userService;

    public UserResource() {
        userService = new UserManager(new JDBCUserDao());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<User>> getAll() {
        return this.userService.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<User> getById(@PathParam("id") int id) {
        return this.userService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Result add(User user) {
        return this.userService.add(user);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Result update(User user) {
        return this.userService.update(user);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Result delete(User user) {
        return this.userService.delete(user);
    }
}
