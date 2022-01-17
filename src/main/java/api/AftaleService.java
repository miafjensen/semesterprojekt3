package api;

import controller.AftaleController;
import dataAccesLayer.SQL;
import exceptions.OurException;
import model.AftaleListe;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("aftaler")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML})
public class AftaleService {

    @GET
    public AftaleListe getPatient(@QueryParam("cpr") String cpr) throws SQLException{
        return AftaleController.getAftaleControllerOBJ().cprSearch(cpr);
    }

    @Path("aftalerSQL")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String makepatientSQL(@QueryParam("cpr") String cpr, @QueryParam("timestart")
            String timestart, @QueryParam("timeend") String timeend, @QueryParam("note") String notat) throws SQLException, OurException {
        return AftaleController.getAftaleControllerOBJ().createAftale(cpr, timestart, timeend, notat);
    }

    @Path("aftalerSQL")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public AftaleListe selectFromTime(@QueryParam("from") String from, @QueryParam("to") String to) throws SQLException {
         return SQL.getSqlOBJ().getAftaleListeDateTime(from, to);

    }


}
