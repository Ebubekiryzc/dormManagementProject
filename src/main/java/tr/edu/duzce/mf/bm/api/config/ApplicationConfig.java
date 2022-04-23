package tr.edu.duzce.mf.bm.api.config;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

@ApplicationPath("/")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        packages("tr.edu.duzce.mf.bm");

        register(RolesAllowedDynamicFeature.class);
    }
}
