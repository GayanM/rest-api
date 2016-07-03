package org.wso2.carbon.password.recovery.rest.api.resources;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.password.recovery.rest.api.Constants;
import org.wso2.carbon.password.recovery.rest.api.bean.StandardResponse;

import javax.ws.rs.core.Response;

public abstract class AbstractResource {

    private static final Log LOG = LogFactory.getLog(AbstractResource.class);


    public Response handleResponse(ResponseStatus responseStatus, String message) {
        Response response;
        StandardResponse standardResponse = getResponseMessage(responseStatus, message);
        switch (responseStatus) {
            case SUCCESS:
                response = Response.ok().entity(standardResponse).build();
                break;
            case FAILED:
                response = Response.serverError().entity(standardResponse).build();
                break;
            case INVALID:
                response = Response.status(400).entity(standardResponse).build();
                break;
            case FORBIDDEN:
                response = Response.status(403).entity(standardResponse).build();
                break;
            default:
                response = Response.noContent().build();
                break;
        }
        return response;
    }

    public Response handleResponse(StandardResponse standardResponse) {
        Response response;
        if (standardResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
            response = Response.ok().entity(standardResponse).build();

        } else if (standardResponse.getStatus().equalsIgnoreCase(Constants.FAILED)) {
            response = Response.serverError().entity(standardResponse).build();

        } else if (standardResponse.getStatus().equalsIgnoreCase(Constants.INVALID)) {
            response = Response.status(400).entity(standardResponse).build();

        } else if (standardResponse.getStatus().equalsIgnoreCase(Constants.FORBIDDEN)) {
            response = Response.status(403).entity(standardResponse).build();

        } else {
            response = Response.noContent().build();
        }
        return response;
    }

    private StandardResponse getResponseMessage(ResponseStatus status, String message) {
        StandardResponse standardResponse = new StandardResponse(status.toString());
        if (message != null) {
            standardResponse.setMessage(message);
        }
        return standardResponse;
    }

    public enum ResponseStatus {
        SUCCESS, FAILED, INVALID, FORBIDDEN
    }

}
