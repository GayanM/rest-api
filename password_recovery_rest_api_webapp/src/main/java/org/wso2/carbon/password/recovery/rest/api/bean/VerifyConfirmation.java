
package org.wso2.carbon.password.recovery.rest.api.bean;

import org.wso2.carbon.captcha.mgt.beans.CaptchaInfoBean;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "username",
        "tenantId",
        "userDomain",
        "code",
        "captcha"
})
@XmlRootElement(name = "verifyConfirmation")
public class VerifyConfirmation {
    @XmlElement(required = true)
    private String username;

    @XmlElement(required = false)
    private int tenantId;

    @XmlElement(required = true)
    private String code;

    @XmlElement(required = false)
    private CaptchaInfoBean captcha;

    public String getUsername() {
        return username;
    }

    @XmlElement(required = false)
    private String userDomain;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CaptchaInfoBean getCaptcha() {
        return captcha;
    }

    public void setCaptcha(CaptchaInfoBean captcha) {
        this.captcha = captcha;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public String getUserDomain() {
        return userDomain;
    }

    public void setUserDomain(String userDomain) {
        this.userDomain = userDomain;
    }
}