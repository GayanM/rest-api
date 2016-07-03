package org.wso2.carbon.password.recovery.rest.api;

public class UserCoreRestException extends Exception {
    public UserCoreRestException(String message) {
        super(message);
    }

    public UserCoreRestException(Throwable e) {
        super(e);
    }

    public UserCoreRestException(String message, Throwable e) {
        super(message, e);
    }
}
