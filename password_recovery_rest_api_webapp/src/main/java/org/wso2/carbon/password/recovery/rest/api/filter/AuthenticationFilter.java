package org.wso2.carbon.password.recovery.rest.api.filter;

import org.omg.CORBA.portable.ResponseHandler;
import org.wso2.carbon.password.recovery.rest.api.Configuration;
import org.wso2.carbon.password.recovery.rest.api.Constants;
import org.wso2.carbon.password.recovery.rest.api.bean.StandardResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.wso2.carbon.password.recovery.rest.api.core.authentication.AuthenticationHandler;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class AuthenticationFilter implements RequestHandler {

    private static Log log = LogFactory.getLog(AuthenticationFilter.class);
    List<AuthenticationHandler> authenticationHandlers;

    public AuthenticationFilter() {
        authenticationHandlers = new ArrayList<AuthenticationHandler>();
        try {
            this.loadAuthenticationHandlers(authenticationHandlers);
        } catch (Exception e) {
            log.error("Error occurred while initializing AuthenticationFilter.", e);
        }
    }

    public Response handleRequest(Message message, ClassResourceInfo classResourceInfo) {
        boolean authenticated = false;

        try {
            for (AuthenticationHandler handler : authenticationHandlers) {
                TreeMap protocolHeaders = (TreeMap) message.get(Message.PROTOCOL_HEADERS);
                if (handler.canHandle(protocolHeaders)) {
                    authenticated = handler.isAuthenticated(protocolHeaders);
                }
            }
            if (authenticated) {
                return null;
            } else {
                return handleError();
            }
        } finally {
        }
    }

    private Response handleError() {
        log.error("Failed authentication");
        return Response.status(401).
                entity(new StandardResponse(Constants.Status.FAILED,
                                            "failed authentication")).build();
    }

    private void loadAuthenticationHandlers(List<AuthenticationHandler> authenticationHandlers)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        List<String> authenticationHandlerClassNames = Configuration
                .getAuthenticationHandlers();
        for (String authenticationHandlerClassName : authenticationHandlerClassNames) {
            AuthenticationHandler authenticationHandler = (AuthenticationHandler) Class.forName
                    (authenticationHandlerClassName)
                    .newInstance();
            authenticationHandlers.add(authenticationHandler);
        }
    }
}
