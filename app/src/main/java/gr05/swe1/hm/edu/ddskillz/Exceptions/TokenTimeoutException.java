package gr05.swe1.hm.edu.ddskillz.Exceptions;

/**
 * Created by Peter on 28.11.2014.
 */
public class TokenTimeoutException extends Exception {
    public TokenTimeoutException(String message){
        super(message);
    }
    public TokenTimeoutException(){}
}
