package tr.edu.duzce.mf.bm.api.config;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import tr.edu.duzce.mf.bm.core.utilities.security.filter.CORSFilter;
import tr.edu.duzce.mf.bm.core.utilities.security.filter.OAuthFilter;


@ApplicationPath("/webapi/")
public class DormManagementApplication extends ResourceConfig {
    public DormManagementApplication() {
        packages("tr.edu.duzce.mf.bm");
        register(OAuthFilter.class);
        register(CORSFilter.class);
        //property("jersey.config.beanValidation.enableOutputValidationErrorEntity.server", "true");
    }
}
