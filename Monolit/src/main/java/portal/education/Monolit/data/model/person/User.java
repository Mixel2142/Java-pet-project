package portal.education.Monolit.data.model.person;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.dto.UserDto;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.*;
import portal.education.Monolit.data.model.article.ArticleLike;
import portal.education.Monolit.data.model.article.ArticleView;
import portal.education.Monolit.data.model.comment.Comment;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User implements UserDetails {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({JsonViews.Public.class, JsonViews.Short.class, JsonViews.Internal.class, JsonViews.Author.class})
    @EqualsAndHashCode.Include
    public UUID id;

    @Column(unique = true, nullable = false)
    @JsonView({JsonViews.Public.class, JsonViews.Short.class, JsonViews.Internal.class})
    @EqualsAndHashCode.Include
    private String nickname;

    @Column(unique = true, nullable = true)
    @JsonView({JsonViews.Internal.class})
    private String accountIdentification;

    @JsonView(JsonViews.Internal.class)
    private boolean accountConfirmed = false;

    @JsonIgnore
    @Column(length = 63, nullable = false)
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserFile avatar;

    @JsonView(JsonViews.Internal.class)
    private boolean accountEnabled = true;

    @JsonView({JsonViews.Public.class, JsonViews.Short.class, JsonViews.Internal.class})
    private boolean online = false;

    @JsonView(JsonViews.Internal.class)
    private Date lockoutEnd = null;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonView(JsonViews.Internal.class)
    @JsonIgnoreProperties("users")
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Collection<RefreshToken> refreshTokens;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Collection<Comment> comments;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Collection<ArticleLike> likes;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    @JsonIgnore
    private Collection<ArticleView> views;


    @JsonView({JsonViews.Internal.class})
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_on")
    private Date createdOn;

    public User(UserDto data) {
        this(data.getNickname(), data.getPassword());
    }

    public void patch(UserDto user) {
        this.nickname = user.getNickname();
        this.password = user.getPassword();
    }

    public User(User user) {
        this.nickname = user.getNickname();
        this.accountIdentification = user.getAccountIdentification();
        this.accountConfirmed = user.isAccountConfirmed();
        this.password = user.getPassword();
        this.accountEnabled = user.isAccountEnabled();
        this.online = user.isOnline();
        this.lockoutEnd = user.getLockoutEnd();
        this.roles = user.getRoles();
        this.refreshTokens = user.getRefreshTokens();
        this.comments = user.getComments();
    }

    public User(String nickname, String password) {
        this();
        this.nickname = nickname;
        this.password = password;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.getNickname();
    }

    @JsonIgnore
    @Override//срок действия аккаунта не истек
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override//аккаунт не заблокирован
    public boolean isAccountNonLocked() {
        return this.accountEnabled;
    }

    @JsonIgnore
    @Override //Учетные данные не истеки
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}
