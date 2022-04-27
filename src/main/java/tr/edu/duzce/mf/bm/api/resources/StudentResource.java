package tr.edu.duzce.mf.bm.api.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tr.edu.duzce.mf.bm.business.abstracts.StudentService;
import tr.edu.duzce.mf.bm.business.concretes.StudentManager;
import tr.edu.duzce.mf.bm.core.utilities.reporting.JasperReportHelper;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.ErrorResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCStudentDao;
import tr.edu.duzce.mf.bm.entities.concretes.Student;
import tr.edu.duzce.mf.bm.entities.dtos.StudentDetailDto;

import java.io.File;
import java.util.List;

@Path("/students")
public class StudentResource {
    private StudentService studentService;
    private static final JasperReportHelper<StudentDetailDto> reportHelper = new JasperReportHelper<>();
    private static final String REPORT_PATH = "C:/Users/EbubekirPC/Desktop/Projects/dormManagementProject/src/main/resources/StudentReport.jrxml";

    public StudentResource() {
        studentService = new StudentManager(new JDBCStudentDao());
    }

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<StudentDetailDto>> getAll() {
        DataResult<List<StudentDetailDto>> result = this.studentService.getAllStudentDetails();
        List<StudentDetailDto> entities = result.getEntity();
        reportHelper.setEntities(entities);
        return result;
    }

    @GET
    @PermitAll
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<StudentDetailDto>> getByFirstName(@QueryParam("first_name") String firstName, @QueryParam("last_name") String lastName) {
        DataResult<List<StudentDetailDto>> result = null;
        if (firstName != null && lastName != null) {
            result = this.studentService.getByFullName(firstName, lastName);
            List<StudentDetailDto> entities = result.getEntity();
            reportHelper.setEntities(entities);
        } else if (firstName != null) {
            result = this.studentService.getByFirstName(firstName);
            List<StudentDetailDto> entities = result.getEntity();
            reportHelper.setEntities(entities);
        } else if (lastName != null) {
            result = this.studentService.getByLastName(lastName);
            List<StudentDetailDto> entities = result.getEntity();
            reportHelper.setEntities(entities);
        }
        return result;
    }

    @GET
    @Path("/export_report")
    @PermitAll
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response exportReport() {
        try {
            String filePath = String.format("%s/student_report.pdf",System.getProperty("user.home"));
            System.out.println(filePath);
            reportHelper.createReport(REPORT_PATH, filePath);
            File file = new File(filePath);
            return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                    .build();
        } catch (Exception exception) {
            var result = new ErrorResult(exception.getMessage() + "/76 StudentResource");
            return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<Student> getById(@PathParam("id") int id) {
        return this.studentService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Result add(Student student) {
        return this.studentService.add(student);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Result update(Student student) {
        return this.studentService.update(student);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Result delete(Student student) {
        return this.studentService.delete(student);
    }
}
