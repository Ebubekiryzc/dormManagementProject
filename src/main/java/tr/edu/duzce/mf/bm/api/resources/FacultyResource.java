package tr.edu.duzce.mf.bm.api.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tr.edu.duzce.mf.bm.business.abstracts.FacultyService;
import tr.edu.duzce.mf.bm.business.concretes.FacultyManager;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCFacultyDao;
import tr.edu.duzce.mf.bm.entities.concretes.Faculty;

import java.util.List;

@Path("/faculties")
public class FacultyResource {
    private FacultyService facultyService;

    public FacultyResource() {
        facultyService = new FacultyManager(new JDBCFacultyDao());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<Faculty>> getAll() {
        return this.facultyService.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<Faculty> getById(@PathParam("id") int id) {
        return this.facultyService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Result add(Faculty faculty) {
        return this.facultyService.add(faculty);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Result update(Faculty faculty) {
        return this.facultyService.update(faculty);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Result delete(Faculty faculty) {
        return this.facultyService.delete(faculty);
    }
}
