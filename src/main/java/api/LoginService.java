package api;

import controller.AftaleController;
import controller.LoginController;
import dataAccesLayer.SQL;
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
}




