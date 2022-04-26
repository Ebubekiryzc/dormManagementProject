package tr.edu.duzce.mf.bm.api.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tr.edu.duzce.mf.bm.business.abstracts.StaffService;
import tr.edu.duzce.mf.bm.business.concretes.StaffManager;
import tr.edu.duzce.mf.bm.core.utilities.exceptions.NotExistInDatabase;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCStaffDao;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;

import java.util.List;

@Path("/staffs")
public class StaffResource {
    private StaffService staffService;

    public StaffResource() {
        staffService = new StaffManager(new JDBCStaffDao());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<Staff>> getAll() {
        return this.staffService.getAll();
    }

    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<Staff>> getByFirstName(@QueryParam("first_name") String firstName, @QueryParam("last_name") String lastName){
        if(firstName != null && lastName != null){
            return this.staffService.getByFullName(firstName, lastName);
        }else if(firstName != null){
            return this.staffService.getByFirstName(firstName);
        }else if(lastName != null){
            return this.staffService.getByLastName(lastName);
        }
        else return null;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<Staff> getById(@PathParam("id") int id) {
        return this.staffService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Result add(Staff staff) {
        return this.staffService.add(staff);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Result update(Staff staff) {
        return this.staffService.update(staff);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Result delete(Staff staff) {
        return this.staffService.delete(staff);
    }
}
