package portal.education.AuthService.domain;

import io.swagger.v3.oas.annotations.Parameter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Table("auth_data")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthData implements Persistable<UUID> {

    @Id
    @Column("user_id")
    @NotNull
    @Parameter(required = true)
    private UUID userId;

    @Column("login")
    @NotNull
    @Parameter(required = true)
    private String login;

    @Column("password")
    @NotNull
    @Parameter(required = true)
    private String password;

    @Column("roles")
    @Parameter(example = "ROLE_USER,ROLE_ADMIN,ROLE_WRITER",required = true)
    @NotNull
    private String roles;

    @Column("credentials")
    @Parameter(example = "ONE,TWO,THREE",hidden = true)
    private String credentials;

    @Column("access_token")
    //@Parameter(hidden = true)
    private String accessToken;

    @Column("refresh_token")
    //@Parameter(hidden = true)
    private String refreshToken;

    @Override
    public UUID getId() {
        return userId;
    }

    @Transient
    transient private boolean newAuthDataFlag = false;

    @Override
    @Transient
    public boolean isNew() {
        return this.newAuthDataFlag;
    }

    public AuthData asNew() {
        this.newAuthDataFlag = true;
        return this;
    }
}
