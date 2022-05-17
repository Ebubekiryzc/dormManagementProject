package tr.edu.duzce.mf.bm.api.resources;

import jakarta.annotation.Resource;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tr.edu.duzce.mf.bm.business.abstracts.OperationClaimService;
import tr.edu.duzce.mf.bm.business.concretes.OperationClaimManager;
import tr.edu.duzce.mf.bm.core.dataAccess.concretes.JDBCOperationClaimDao;
import tr.edu.duzce.mf.bm.core.entities.concrete.OperationClaim;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;

import java.util.List;

@Path("/operation_claims")
@Resource
public class OperationClaimResource {
    private OperationClaimService operationClaimService;

    public OperationClaimResource() {
        this.operationClaimService = new OperationClaimManager(new JDBCOperationClaimDao());
    }

    @GET
    @RolesAllowed({"admin", "staff"})
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<OperationClaim>> getAll() {
        return operationClaimService.getAll();
    }

    @GET
    @RolesAllowed({"admin", "staff"})
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<OperationClaim> getById(@PathParam("id") Integer id) {
        return operationClaimService.getById(id);
    }

    @GET
    @RolesAllowed({"admin", "staff"})
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<OperationClaim> getByName(@QueryParam("name") String name) {
        return operationClaimService.getByName(name);
    }
}
