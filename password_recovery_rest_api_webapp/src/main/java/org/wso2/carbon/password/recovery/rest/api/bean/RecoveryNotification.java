package org.wso2.carbon.password.recovery.rest.api.bean;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "username",
        "userDomain",
        "key",
        "tenantId",
        "notificationType"
})
@XmlRootElement(name = "action")
public class RecoveryNotification {

    @XmlElement(required = true)
    private String username;

    @XmlElement(required = true)
    private String key;

    @XmlElement(required = false)
    private int tenantId;

    @XmlElement(required = false)
    private String userDomain;

    @XmlElement(required = true)
    private String notificationType;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
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