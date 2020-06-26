package portal.education.Monolit.data.model.person;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.abstractModel.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;


@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role implements GrantedAuthority {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({JsonViews.Public.class, JsonViews.Short.class, JsonViews.Internal.class, JsonViews.Author.class})
    @EqualsAndHashCode.Include
    public Short id;

    @JsonView({JsonViews.Internal.class, JsonViews.Public.class})
    @EqualsAndHashCode.Include
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties("roles")
    @JsonIgnore
    private Set<User> users;

    public Role(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\"").append(name).append("\"");
        return builder.toString();
    }

    @Override
    @JsonIgnore
    public String getAuthority() {
        return this.name;
    }
}

