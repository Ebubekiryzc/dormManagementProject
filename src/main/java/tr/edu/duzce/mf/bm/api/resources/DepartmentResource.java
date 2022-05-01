package tr.edu.duzce.mf.bm.api.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tr.edu.duzce.mf.bm.business.abstracts.DepartmentService;
import tr.edu.duzce.mf.bm.business.concretes.DepartmentManager;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCDepartmentDao;
import tr.edu.duzce.mf.bm.entities.concretes.Department;

import java.util.List;

@Path("/departments")
public class DepartmentResource {
    private DepartmentService departmentService;

    public DepartmentResource() {
        departmentService = new DepartmentManager(new JDBCDepartmentDao());
    }

    @GET
    @RolesAllowed({"admin", "staff"})
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<Department>> getAll() {
        return this.departmentService.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<Department> getById(@PathParam("id") int id) {
        return this.departmentService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Result add(Department department) {
        return this.departmentService.add(department);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Result update(Department department) {
        return this.departmentService.update(department);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Result delete(Department department) {
        return this.departmentService.delete(department);
    }
}
