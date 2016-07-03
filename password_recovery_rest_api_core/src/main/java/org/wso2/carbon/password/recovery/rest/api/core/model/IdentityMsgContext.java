
package org.wso2.carbon.password.recovery.rest.api.core.model;

public class IdentityMsgContext {
    private boolean validateExpiration = true;


    public IdentityMsgContext(boolean validateExpiration) {
        this.validateExpiration = validateExpiration;
    }

    public boolean isValidateExpiration() {
        return validateExpiration;
    }

    public void setValidateExpiration(boolean validateExpiration) {
        this.validateExpiration = validateExpiration;
    }
}
