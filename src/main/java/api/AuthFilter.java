/**

 * @author ${IT3 grp4 + tilpasset filter Magnus & Mia}

 * @Date ${jan 2022}

 */
package api;

import controller.JWTHandler;
import model.User;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;


@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        //System.out.println(containerRequestContext.getUriInfo().getPath());
        // Kontrol af private key på aftaler endpoint
        // Filter til tilladelse af import af aftaler udefra
        if ("aftaler".equals(containerRequestContext.getUriInfo().getPath())) {
            System.out.println("tilgår aftaler");
            if (!containerRequestContext.getHeaderString("Authorization").equals("hemmeliglogin")) {
                throw new WebApplicationException("psst hvad er kodeordet?", 401);
            }
            return;
        }
        // Filter til tilladelse af import af sessionID udefra
        if ("ekgSessions".equals(containerRequestContext.getUriInfo().getPath())) {
            System.out.println("tilgår aftaler");
            if (!containerRequestContext.getHeaderString("Authorization").equals("hemmeliglogin")) {
                throw new WebApplicationException("psst hvad er kodeordet?", 401);
            }
            return;
        }
        // Filter til tilladelse af import af ekg-data udefra
        if ("ekgSessions/measurements".equals(containerRequestContext.getUriInfo().getPath())) {
            System.out.println("tilgår aftaler");
            if (!containerRequestContext.getHeaderString("Authorization").equals("hemmeliglogin")) {
                throw new WebApplicationException("psst hvad er kodeordet?", 401);
            }
            return;
        }

        // path: login og ekgSessions/EKGdata er undtaget for kontrol token,
        // ekgSessions/EKGdata er pythons path ind i systemet til import af EKG data til databasen
        if (!"login".equals(containerRequestContext.getUriInfo().getPath()) && !"ekgSessions/EKGdata".equals(containerRequestContext.getUriInfo().getPath())) {
            if (containerRequestContext.getHeaderString("Authorization") == null) {
                throw new WebApplicationException("Ingen Token", 401);
            }
            try {
                User user = JWTHandler.validate(containerRequestContext.getHeaderString("Authorization"));
            } catch (Exception e) {
                throw new WebApplicationException("Invalid Token", 401);
            }

        }

    }
}
