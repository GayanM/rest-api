package org.wso2.carbon.password.recovery.rest.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class Configuration {

    private static final Log LOG = LogFactory.getLog(Configuration.class);

    private static List<String> AUTHENTICATION_HANDLERS = new ArrayList<String>();

    static {
        try {
            readAuthenticationHandlers();
        } catch (Exception e) {
            LOG.error("Failed to create the Configuration.", e);
        }
    }

    public static List<String> getAuthenticationHandlers() {
        return AUTHENTICATION_HANDLERS;
    }

    private static void readAuthenticationHandlers() {
        AUTHENTICATION_HANDLERS.add("org.wso2.carbon.password.recovery.rest.api.core.authentication.BasicAuthHandler");
    }


}
