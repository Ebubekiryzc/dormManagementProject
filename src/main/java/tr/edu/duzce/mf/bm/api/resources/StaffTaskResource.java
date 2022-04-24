package tr.edu.duzce.mf.bm.api.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tr.edu.duzce.mf.bm.business.abstracts.StaffTaskService;
import tr.edu.duzce.mf.bm.business.concretes.StaffTaskManager;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCStaffTaskDao;
import tr.edu.duzce.mf.bm.entities.concretes.StaffTask;

import java.util.List;

@Path("/lnkStaffTasks")
public class StaffTaskResource {
    private StaffTaskService staffTaskService;

    public StaffTaskResource() {
        staffTaskService = new StaffTaskManager(new JDBCStaffTaskDao());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<StaffTask>> getAll() {
        return this.staffTaskService.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<StaffTask> getById(@PathParam("id") int id) {
        return this.staffTaskService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Result add(StaffTask staffTask) {
        return this.staffTaskService.add(staffTask);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Result update(StaffTask staffTask) {
        return this.staffTaskService.update(staffTask);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Result delete(StaffTask staffTask) {
        return this.staffTaskService.delete(staffTask);
    }
}
