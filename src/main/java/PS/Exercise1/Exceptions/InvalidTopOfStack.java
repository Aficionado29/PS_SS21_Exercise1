package PS.Exercise1.Exceptions;

public class InvalidTopOfStack extends Exception {

    public InvalidTopOfStack (String errorMessage) {
        super(errorMessage);
    }

}