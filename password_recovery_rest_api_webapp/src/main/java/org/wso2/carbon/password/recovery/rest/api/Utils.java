package org.wso2.carbon.password.recovery.rest.api;

import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.password.recovery.rest.api.core.UserManager;
import org.wso2.carbon.user.api.UserStoreException;

public class Utils {

    private Utils() {
    }

    public static UserManager getUserManager() {
        return (UserManager) PrivilegedCarbonContext.getThreadLocalCarbonContext()
                .getOSGiService(UserManager.class);
    }

    public static String getTenantDomain(int tenantId) throws UserCoreRestException {
        try {
            return org.wso2.carbon.password.recovery.rest.api.core.Utils.getTenantDomain(tenantId);
        }  catch (UserStoreException e) {
            throw new UserCoreRestException("Error while getting tenant domain of tenant id :"+tenantId, e);
        }
    }
}
