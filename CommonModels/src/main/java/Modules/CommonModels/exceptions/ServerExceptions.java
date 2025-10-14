package Modules.CommonModels.exceptions;

public class ServerExceptions extends Exception{
    public ServerExceptions(String message) {
        super(message);
    }

    public ServerExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerExceptions(Throwable cause) {
        super(cause);
    }

    public ServerExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
