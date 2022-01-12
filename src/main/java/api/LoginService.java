package api;

import controller.AftaleController;
import controller.LoginController;
import exceptions.OurException;
import model.LoginData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;





@Path("login")
@Produces({MediaType.TEXT_PLAIN})
public class LoginService {


    @GET
    public String loginKontrol(@QueryParam("username") String user, @QueryParam("password") String pass) {
        LoginData loginData = new LoginData(user, pass);
        return LoginController.getLoginControllerOBJ().doLogin(loginData);
    }


    //Henter data fra python til backend
    @POST
    public String postEKGData(String EKGdata) throws OurException {
        //System.out.println(EKGdata);
        String[] split = EKGdata.split(",");

        for (int i = 0; i < split.length; i++) {
            double str1= Double.parseDouble(split[i]);

           AftaleController.getAftaleControllerOBJ().insertEKGdataIDatabase("1111561112", String.valueOf(Float.parseFloat(split[i])));
            //System.out.println(split[i]);
            // System.out.println("Done");

        }

        return EKGdata;

    }
    }




