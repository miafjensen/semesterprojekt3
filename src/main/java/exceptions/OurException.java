/**

 * @author ${tidligere kode fra IT3 grp4}

 * @Date ${DATE}

 */
package exceptions;

public class OurException extends Exception {

    @Override
    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    String Message = "Error 420";

}
