package org.wso2.carbon.password.recovery.rest.api.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.captcha.mgt.beans.CaptchaInfoBean;
import org.wso2.carbon.identity.mgt.IdentityMgtServiceException;
import org.wso2.carbon.identity.mgt.beans.VerificationBean;
import org.wso2.carbon.identity.mgt.services.UserInformationRecoveryService;
import org.wso2.carbon.user.api.UserStoreException;

public class UserManager {

    private static final Log LOG = LogFactory.getLog(UserManager.class);
    private static UserManager userManager = new UserManager();

    private UserInformationRecoveryService userInformationRecoveryService = new UserInformationRecoveryService();

    public static UserManager getInstance() {
        return userManager;
    }


    public CaptchaInfoBean getCaptcha() throws IdentityMgtServiceException {
        return userInformationRecoveryService.getCaptcha();
    }


    public VerificationBean verifyUser(String username, CaptchaInfoBean captchaInfoBean, String tenantDomain)
            throws IdentityMgtServiceException, UserStoreException {
        try {
            Utils.startTenantFlow(tenantDomain);
            return userInformationRecoveryService.verifyUser(username, captchaInfoBean);
        } finally {
            Utils.endTenantFlow();
        }
    }

    public VerificationBean sendRecoveryNotification(String username, String key, String notificationType, String tenantDomain)
            throws IdentityMgtServiceException, UserStoreException {
        try {
            Utils.startTenantFlow(tenantDomain);
            return userInformationRecoveryService.sendRecoveryNotification(username, key, notificationType);
        } finally {
            Utils.endTenantFlow();
        }
    }

    public VerificationBean verifyConfirmationCode(String username, String code, CaptchaInfoBean captcha, String tenantDomain)
            throws IdentityMgtServiceException, UserStoreException {
        try {
            Utils.startTenantFlow(tenantDomain);
            return userInformationRecoveryService.verifyConfirmationCode(username, code, captcha);
        } finally {
            Utils.endTenantFlow();
        }
    }

    public VerificationBean updatePassword(String username, String confirmationCode, String newPassword, String tenantDomain)
            throws IdentityMgtServiceException, UserStoreException {
        try {
            Utils.startTenantFlow(tenantDomain);
            return userInformationRecoveryService.updatePassword(username, confirmationCode, newPassword);
        } finally {
            Utils.endTenantFlow();
        }
    }

}