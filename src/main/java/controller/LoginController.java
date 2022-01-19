/**

 * @author ${IT3 grp4 & Mia}

 * @Date ${DATE}

 */
package controller;

import dataAccesLayer.SQL;
import model.LoginData;
import model.User;
import javax.ws.rs.WebApplicationException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;

public class LoginController {

    private LoginController() {
    }

    static private final LoginController loginControllerOBJ = new LoginController();

    static public LoginController getLoginControllerOBJ() {
        return loginControllerOBJ;
    }

    public String doLogin(LoginData loginData) {
        try {
            // sql kald der kontrollere om brugeren eksisterer
            String brugerListe = SQL.getSqlOBJ().hentBrugerListe(loginData.getUsername());

            // kontrol af login og generer token
            if (loginVal(brugerListe, loginData.getPassword())) {
                User user = new User(loginData);
                return JWTHandler.generateJwtToken(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new WebApplicationException("fejl", 401);
    }

    public boolean loginVal(String brugerliste, String pass) {
        if (brugerliste.length() > 1) {
            String[] opdelt = brugerliste.split("\\|");
            int salt = Integer.parseInt(opdelt[2]);
            String hashcheck = generateHash(pass, salt);
            if (opdelt[1].equals(hashcheck)) {
                return true;
            }
        }
        return false;
    }

    public static String generateHash(String pass, int salt) { //metode til at genere hash
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            pass += String.valueOf(salt);
            md5.update(StandardCharsets.UTF_8.encode(pass));
            return String.format("%032x", new BigInteger(1, md5.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return pass;
    }

    public static int getSalt() { // metode til at genere salt
        byte[] salt = new byte[20];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(salt);
        int saltint = salt[4];
        return saltint;
    }
    /**

     * @author ${Mia}

     * @Date ${DATE}

     */

    // metode at oprette ny bruger hvor der genereres salt, kodeord hashes og indsættes i database
    public static void main(String[] args) {

        String brugernavn = "brugernavn"; // indtast ønsket brugernavn
        String password = "kodeord"; // indtast ønsket password

        int salt = getSalt();
        System.out.println("salt: " + getSalt());
        String hash = generateHash(password, salt);
        System.out.println("hash: " + hash);

        try{
            SQL.getSqlOBJ().createNewUser(brugernavn, hash, salt); // data indsættes i database

        }catch (SQLException throwables){
            throwables.printStackTrace();
            System.out.println("fejl i oprettelse af bruger");
        }


    }
}

