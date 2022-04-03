package tr.edu.duzce.mf.bm.api.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tr.edu.duzce.mf.bm.business.abstracts.GenderService;
import tr.edu.duzce.mf.bm.business.concretes.GenderManager;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCGenderDao;
import tr.edu.duzce.mf.bm.entities.concretes.Gender;

import java.util.List;

@Path("/genders")
public class GenderController {
    private GenderService genderService;

    public GenderController() {
        genderService = new GenderManager(new JDBCGenderDao());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<Gender>> getAll() {
        return this.genderService.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<Gender> getById(@PathParam("id") int id) {
        return this.genderService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Result add(Gender gender) {
        return this.genderService.add(gender);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Result update(Gender gender) {
        return this.genderService.update(gender);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Result delete(Gender gender) {
        return this.genderService.delete(gender);
    }
}
