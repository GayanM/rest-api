package org.wso2.carbon.password.recovery.rest.api.core.authentication;

import org.apache.axiom.om.util.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.util.ArrayUtil;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.user.api.UserRealm;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.core.service.RealmService;
import org.wso2.carbon.utils.multitenancy.MultitenantUtils;

import java.util.ArrayList;
import java.util.Map;

public class BasicAuthHandler implements AuthenticationHandler {
    private static final Log LOG = LogFactory.getLog(BasicAuthHandler.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public boolean canHandle(Map headers) {
        //get the value for Authorization Header
        ArrayList authzHeaders = (ArrayList) headers.get(AUTHORIZATION_HEADER);

        if(authzHeaders == null){
            return false;
        }

        String authzHeader = (String) authzHeaders.get(0);

        String type = authzHeader.split(" ")[0];

        if ("basic".equalsIgnoreCase(type)) {
            return true;
        }
        return false;
    }

    public boolean isAuthenticated(Map headers) {
        String userName = null;
        try {
            //get the value for Authorization Header
            ArrayList authzHeaders = (ArrayList) headers.get(AUTHORIZATION_HEADER);
            if (authzHeaders != null) {
                //get the authorization header value, if provided
                String authzHeader = (String) authzHeaders.get(0);

                //decode it and extract username and password
                byte[] decodedAuthHeader = Base64.decode(authzHeader.split(" ")[1]);
                String authHeader = new String(decodedAuthHeader);
                userName = authHeader.split(":")[0];
                String password = authHeader.split(":")[1];
                if (userName != null && password != null) {
                    String tenantDomain = MultitenantUtils.getTenantDomain(userName);
                    String tenantLessUserName = MultitenantUtils.getTenantAwareUsername(userName);

                    try {
                        //get super tenant context and get realm service which is an osgi service
                        RealmService realmService = (RealmService)
                                PrivilegedCarbonContext.getThreadLocalCarbonContext().
                                        getOSGiService(RealmService.class);
                        if (realmService != null) {
                            int tenantId = realmService.getTenantManager().getTenantId(tenantDomain);
                            if (tenantId == -1) {
                                LOG.error("Invalid tenant domain " + tenantDomain);
                                return false;
                            }
                            //get tenant's user realm
                            UserRealm userRealm = realmService.getTenantUserRealm(tenantId);
                            boolean authenticated = userRealm.getUserStoreManager().
                                    authenticate(tenantLessUserName, password);
                            if (authenticated) {
                                //authentication success. set the username for authorization header
                                //and proceed the REST call
                                authzHeaders.set(0, userName);
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } catch (UserStoreException e) {
                        LOG.debug("UserStoreException. Return Unauthorized.", e);
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            LOG.error("Error while trying to authenticate user", e);
            return false;
        }
    }
}
