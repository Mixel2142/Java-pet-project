package portal.education.serialize;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public enum EnumRole implements GrantedAuthority, Serializable {
    ROLE_USER("ROLE_USER"),
    ROLE_WRITER("ROLE_WRITER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_SUPER("ROLE_SUPER");


    private static final long serialVersionUID = 53013L;

    private String name;

    EnumRole(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

}
