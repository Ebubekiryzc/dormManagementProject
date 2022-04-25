package tr.edu.duzce.mf.bm.api.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tr.edu.duzce.mf.bm.business.abstracts.DayOffService;
import tr.edu.duzce.mf.bm.business.concretes.DayOffManager;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCDayOffDao;
import tr.edu.duzce.mf.bm.entities.concretes.DayOff;

import java.util.List;

@Path("/dayoffs")
public class DayOffResource {
    private DayOffService dayoffService;

    public DayOffResource() {
        dayoffService = new DayOffManager(new JDBCDayOffDao());
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
