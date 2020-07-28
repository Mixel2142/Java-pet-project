package portal.education.GraphQLR.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@org.springframework.data.relational.core.mapping.Table("users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Persistable<UUID> {

    @Id
    private UUID id;

    private String nickname;

    private String accountIdentification;

    private boolean accountConfirmed = false;

    private String password;

//    private String  avatarUrl;

    private boolean accountEnabled = true;

    private boolean online = false;

    private LocalDateTime lockoutEnd = null;

//    private Set<Role> roles = new HashSet<>();

    private LocalDateTime createdOn;

    @Override
    public UUID getId() {
        return id;
    }

    @Transient
    private boolean newData = false;

    @Override
    public boolean isNew() {
        return this.newData;
    }

    public User asNew() {
        this.newData = true;
        return this;
    }
}
