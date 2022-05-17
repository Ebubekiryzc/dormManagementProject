package tr.edu.duzce.mf.bm.api.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tr.edu.duzce.mf.bm.business.abstracts.IndividualUserService;
import tr.edu.duzce.mf.bm.business.concretes.IndividualUserManager;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCIndividualUserDao;
import tr.edu.duzce.mf.bm.entities.concretes.IndividualUser;

import java.util.List;

@Path("/individual_users")
public class IndividualUserResource {
    private IndividualUserService individualUserService;

    public IndividualUserResource() {
        individualUserService = new IndividualUserManager(new JDBCIndividualUserDao());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<IndividualUser>> getAll() {
        return this.individualUserService.getAll();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<IndividualUser> getById(@PathParam("id") int id) {
        return this.individualUserService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Result add(IndividualUser individualUser) {
        return this.individualUserService.add(individualUser);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Result update(IndividualUser individualUser) {
        return this.individualUserService.update(individualUser);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Result delete(IndividualUser individualUser) {
        return this.individualUserService.delete(individualUser);
    }
}
