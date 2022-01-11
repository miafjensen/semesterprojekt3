package api;

import controller.LoginController;
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

    @POST
    public String postEKGData(String EKGdata) {

        //System.out.println(EKGdata);


        String[] split = EKGdata.split(",");

        for (int i = 0; i < split.length; i++) {



            System.out.println(split[i]);
            // System.out.println("Done");
        }
            return EKGdata;

    }


    }

