package tr.edu.duzce.mf.bm.core.utilities.security.filter;

import jakarta.annotation.Priority;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import oracle.jdbc.proxy.annotation.Pre;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.utilities.results.ErrorResult;
import tr.edu.duzce.mf.bm.core.utilities.security.jwt.TokenGenerator;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class OAuthFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Bearer";


    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        Method method = resourceInfo.getResourceMethod();
        boolean permittedToEveryOne = method.isAnnotationPresent(PermitAll.class);
        System.out.println("Permitted:" + permittedToEveryOne);

        if (!permittedToEveryOne) {
            if (method.isAnnotationPresent(DenyAll.class)) {
                //ErrorResult errorResult = new ErrorResult("Bu adres taşındı.");
                containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity("Adres Taşındı").build());
                return;
            }

            if (method.isAnnotationPresent(RolesAllowed.class)) {
                final MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();
                final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

                if (authorization == null || authorization.isEmpty()) {
                    //ErrorResult errorResult = new ErrorResult(Messages.MustLogin);
                    containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(Messages.MustLogin).build());
                    return;
                }

                final String accessToken = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

                if (method.isAnnotationPresent(RolesAllowed.class)) {
                    RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                    Set<String> roleSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

                    TokenGenerator tokenGenerator = new TokenGenerator();


                    if (!tokenGenerator.IsValid(accessToken, roleSet)) {
                        //ErrorResult errorResult = new ErrorResult(Messages.UnAuthorizedOrNotFound);
                        containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(Messages.UnAuthorizedOrNotFound).build());
                        return;
                    }
                }
            }
        }
    }
}
