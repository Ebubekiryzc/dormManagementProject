package tr.edu.duzce.mf.bm.api.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tr.edu.duzce.mf.bm.business.abstracts.TaskService;
import tr.edu.duzce.mf.bm.business.concretes.TaskManager;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCTaskDao;
import tr.edu.duzce.mf.bm.entities.concretes.Task;

import java.util.List;

@Path("/tasks")
public class TaskResource {
    private TaskService taskService;

    public TaskResource() {
        taskService = new TaskManager(new JDBCTaskDao());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<Task>> getAll() {
        return this.taskService.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<Task> getById(@PathParam("id") int id) {
        return this.taskService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Result add(Task task) {
        return this.taskService.add(task);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Result update(Task task) {
        return this.taskService.update(task);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Result delete(Task task) {
        return this.taskService.delete(task);
    }
}
