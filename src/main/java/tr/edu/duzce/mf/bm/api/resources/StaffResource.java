package tr.edu.duzce.mf.bm.api.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tr.edu.duzce.mf.bm.business.abstracts.StaffService;
import tr.edu.duzce.mf.bm.business.concretes.StaffManager;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.utilities.exceptions.NotExistInDatabase;
import tr.edu.duzce.mf.bm.core.utilities.reporting.JasperReportHelper;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.ErrorResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.core.utilities.results.SuccessResult;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCStaffDao;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;
import tr.edu.duzce.mf.bm.entities.dtos.StaffDetailDto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Path("/staffs")
public class StaffResource {
    private StaffService staffService;
    private static final JasperReportHelper<StaffDetailDto> reportHelper = new JasperReportHelper<>();
    private static final String REPORT_PATH = "C:/Users/EbubekirPC/Desktop/Projects/dormManagementProject/src/main/resources/StaffReport.jrxml";

    public StaffResource() {
        staffService = new StaffManager(new JDBCStaffDao());
    }

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<StaffDetailDto>> getAll() {
        DataResult<List<StaffDetailDto>> result = this.staffService.getAllStaffDetails();
        List<StaffDetailDto> entities = result.getEntity();
        reportHelper.setEntities(entities);
        return result;
    }

    @GET
    @Path("/filter")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<StaffDetailDto>> getByFirstName(@QueryParam("first_name") String firstName, @QueryParam("last_name") String lastName) {
        DataResult<List<StaffDetailDto>> result = null;
        if (firstName != null && lastName != null) {
            result = this.staffService.getByFullName(firstName, lastName);
            List<StaffDetailDto> entities = result.getEntity();
            reportHelper.setEntities(entities);
        } else if (firstName != null) {
            result = this.staffService.getByFirstName(firstName);
            List<StaffDetailDto> entities = result.getEntity();
            reportHelper.setEntities(entities);
        } else if (lastName != null) {
            result = this.staffService.getByLastName(lastName);
            List<StaffDetailDto> entities = result.getEntity();
            reportHelper.setEntities(entities);
        }
        return result;
    }

    @GET
    @Path("/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<Staff> getById(@PathParam("id") int id) {
        DataResult<Staff> result = this.staffService.getById(id);
        return result;
    }

    @GET
    @Path("/export_report")
    @PermitAll
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response exportReport() {
        try {
            String filePath = String.format("%s/staff_report.pdf",System.getProperty("user.home"));
            System.out.println(filePath);
            reportHelper.createReport(REPORT_PATH, filePath);
            File file = new File(filePath);
            return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                    .build();
        } catch (Exception exception) {
            var result = new ErrorResult(exception.getMessage() + "/69 StaffResource");
            return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
        }
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
