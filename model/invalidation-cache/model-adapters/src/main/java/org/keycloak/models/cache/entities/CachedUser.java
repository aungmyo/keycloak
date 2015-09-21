package org.keycloak.models.cache.entities;

import org.keycloak.models.OfflineClientSessionModel;
import org.keycloak.models.OfflineUserSessionModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserCredentialValueModel;
import org.keycloak.models.UserModel;
import org.keycloak.util.MultivaluedHashMap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class CachedUser implements Serializable {
    private String id;
    private String realm;
    private String username;
    private Long createdTimestamp;
    private String firstName;
    private String lastName;
    private String email;
    private boolean emailVerified;
    private List<UserCredentialValueModel> credentials = new LinkedList<UserCredentialValueModel>();
    private boolean enabled;
    private boolean totp;
    private String federationLink;
    private String serviceAccountClientLink;
    private MultivaluedHashMap<String, String> attributes = new MultivaluedHashMap<>();
    private Set<String> requiredActions = new HashSet<>();
    private Set<String> roleMappings = new HashSet<String>();
    private Map<String, OfflineUserSessionModel> offlineUserSessions = new HashMap<>();
    private Map<String, OfflineClientSessionModel> offlineClientSessions = new HashMap<>();

    public CachedUser(RealmModel realm, UserModel user) {
        this.id = user.getId();
        this.realm = realm.getId();
        this.username = user.getUsername();
        this.createdTimestamp = user.getCreatedTimestamp();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.attributes.putAll(user.getAttributes());
        this.email = user.getEmail();
        this.emailVerified = user.isEmailVerified();
        this.credentials.addAll(user.getCredentialsDirectly());
        this.enabled = user.isEnabled();
        this.totp = user.isOtpEnabled();
        this.federationLink = user.getFederationLink();
        this.serviceAccountClientLink = user.getServiceAccountClientLink();
        this.requiredActions.addAll(user.getRequiredActions());
        for (RoleModel role : user.getRoleMappings()) {
            roleMappings.add(role.getId());
        }
        for (OfflineUserSessionModel offlineSession : user.getOfflineUserSessions()) {
            offlineUserSessions.put(offlineSession.getUserSessionId(), offlineSession);
        }
        for (OfflineClientSessionModel offlineSession : user.getOfflineClientSessions()) {
            offlineClientSessions.put(offlineSession.getClientSessionId(), offlineSession);
        }
    }

    public String getId() {
        return id;
    }

    public String getRealm() {
        return realm;
    }

    public String getUsername() {
        return username;
    }

    public Long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public List<UserCredentialValueModel> getCredentials() {
        return credentials;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isTotp() {
        return totp;
    }

    public MultivaluedHashMap<String, String> getAttributes() {
        return attributes;
    }

    public Set<String> getRequiredActions() {
        return requiredActions;
    }

    public Set<String> getRoleMappings() {
        return roleMappings;
    }

    public String getFederationLink() {
        return federationLink;
    }

    public String getServiceAccountClientLink() {
        return serviceAccountClientLink;
    }

    public Map<String, OfflineUserSessionModel> getOfflineUserSessions() {
        return offlineUserSessions;
    }

    public Map<String, OfflineClientSessionModel> getOfflineClientSessions() {
        return offlineClientSessions;
    }
}
