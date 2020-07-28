package portal.education.serialize;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class MyGrantedAuthority implements Serializable {

    private static final long serialVersionUID = 53014L;

    private Set<EnumRole> roles = new HashSet<>();

    public MyGrantedAuthority(String commaSeparatedRoles) {
        this.roles = arrayStrToEnum(EnumRole.class, commaSeparatedRoles.split(","));
    }

    public Collection<? extends GrantedAuthority> getAuthority() {
        return roles;
    }

    private static <T extends Enum<T>> Set<T> arrayStrToEnum(Class<T> clazz, String[] values) {
        Set<T> set = new HashSet<>();
        for (String level : values) {
            set.add(Enum.valueOf(clazz, level));
        }
        return set;
    }
}