package tr.edu.duzce.mf.bm.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import tr.edu.duzce.mf.bm.core.utilities.results.ErrorResult;

@Provider
public class ExceptionMapper implements jakarta.ws.rs.ext.ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        ErrorResult errorResult = new ErrorResult(exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
    }
}
