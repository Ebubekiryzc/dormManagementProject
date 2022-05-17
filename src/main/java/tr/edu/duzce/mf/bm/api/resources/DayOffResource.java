package tr.edu.duzce.mf.bm.api.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jfree.data.time.Day;
import tr.edu.duzce.mf.bm.business.abstracts.DayOffService;
import tr.edu.duzce.mf.bm.business.concretes.DayOffManager;
import tr.edu.duzce.mf.bm.business.concretes.OperationClaimManager;
import tr.edu.duzce.mf.bm.core.dataAccess.concretes.JDBCOperationClaimDao;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.core.utilities.results.SuccessDataResult;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCDayOffDao;
import tr.edu.duzce.mf.bm.entities.concretes.DayOff;
import tr.edu.duzce.mf.bm.entities.dtos.DayOffDetailDto;

import java.util.List;

@Path("/day_offs")
public class DayOffResource {
    private DayOffService dayoffService;

    public DayOffResource() {
        dayoffService = new DayOffManager(new OperationClaimManager(new JDBCOperationClaimDao()), new JDBCDayOffDao());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<DayOff>> getAll() {
        return this.dayoffService.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<DayOff> getById(@PathParam("id") int id) {
        return this.dayoffService.getById(id);
    }

    @GET
    @Path("/with_access_level/{role}")
    @RolesAllowed({"admin", "staff"})
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<DayOffDetailDto>> getWithAccessLevel(@PathParam("role") String role) {
        return dayoffService.getAllWithRole(role);
    }

    @GET
    @Path("/get_all_active_day_offs/{role}")
    @RolesAllowed({"admin", "staff"})
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<DayOffDetailDto>> getActiveWithAccessLevel(@PathParam("role") String role) {
        return dayoffService.getAllActiveWithRole(role);
    }

    @GET
    @Path("/get_all_by_user_id/{userId}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<DayOff>> getAllByUserId(@PathParam("userId") String userId) {
        return dayoffService.getAllByUserId(userId);
    }

    @GET
    @Path("/get_all_active_by_user_id/{userId}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<DayOff>> getAllActivesByUserId(@PathParam("userId") String userId) {
        return this.dayoffService.getAllActivesByUserId(userId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Result add(DayOff dayoff) {
        return this.dayoffService.add(dayoff);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Result update(DayOff dayoff) {
        return this.dayoffService.update(dayoff);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Result delete(DayOff dayoff) {
        return this.dayoffService.delete(dayoff);
    }
}
