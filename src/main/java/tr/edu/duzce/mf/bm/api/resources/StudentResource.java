package tr.edu.duzce.mf.bm.api.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tr.edu.duzce.mf.bm.business.abstracts.StudentService;
import tr.edu.duzce.mf.bm.business.concretes.StudentManager;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCStudentDao;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;
import tr.edu.duzce.mf.bm.entities.concretes.Student;

import java.util.List;

@Path("/students")
public class StudentResource {
    private StudentService studentService;

    public StudentResource() {
        studentService = new StudentManager(new JDBCStudentDao());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<Student>> getAll() {
        return this.studentService.getAll();
    }

    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<Student>> getByFirstName(@QueryParam("first_name") String firstName, @QueryParam("last_name") String lastName) {
        if (firstName != null && lastName != null) {
            return this.studentService.getByFullName(firstName, lastName);
        } else if (firstName != null) {
            return this.studentService.getByFirstName(firstName);
        } else if (lastName != null) {
            return this.studentService.getByLastName(lastName);
        } else return null;
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
