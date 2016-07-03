package org.wso2.carbon.password.recovery.rest.api.core.authentication;

import java.util.Map;

public interface AuthenticationHandler {

    /**
     * Returns whether the authenticator can handle the request
     *
     * @param httpHeaders
     * @return Return true if the request can be handled, false otherwise
     */
    public boolean canHandle(Map httpHeaders);

    /**
     * Process the request and  return the result
     *
     * @param httpHeaders
     * @return true if authentication successful, false otherwise
     */
    public boolean isAuthenticated(Map httpHeaders);
}
