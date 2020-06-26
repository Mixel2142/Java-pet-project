package portal.education.Monolit.data.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.Category;
import portal.education.Monolit.data.model.Tag;
import portal.education.Monolit.data.model.comment.Comment;
import portal.education.Monolit.data.model.person.Author;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "articles")
@Getter
@Setter
@NoArgsConstructor
public class Article {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({JsonViews.Public.class, JsonViews.Short.class, JsonViews.Internal.class,JsonViews.Author.class})
    public UUID id;

    @OneToMany(mappedBy = "article")
    @JsonIgnore
    private Collection<Comment> comments;

    @JsonView({JsonViews.Short.class})
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateOn;

    @JsonView({JsonViews.Public.class, JsonViews.Short.class})
    private long rating;

    @Column(nullable = false)
    @JsonView({JsonViews.Public.class, JsonViews.Short.class})
    private Long viewsCounter = 0L;

    @JsonIgnore
    @OneToMany(mappedBy = "article")
    private Collection<ArticleView> views;

    @Column(nullable = false)
    @JsonView({JsonViews.Public.class, JsonViews.Short.class})
    private Long likesCounter = 0L;

    @JsonIgnore
    @OneToMany(mappedBy = "article")
    private Collection<ArticleLike> likes;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties(value = {"articles", "emailConfirmed", "online", "lockoutEnd", "authorities", "rating", "accountNonLocked"})
    @JsonView({JsonViews.Public.class, JsonViews.Short.class,})
    private Author author;

    @Column(unique = true, nullable = false)
    @JsonView({JsonViews.Public.class, JsonViews.Short.class, JsonViews.Author.class})
    private String title;

    @JsonView({JsonViews.Public.class, JsonViews.Author.class})
    @Column(columnDefinition = "TEXT NOT NULL")
    private String body;

    @OneToMany(mappedBy = "article")
    @JsonIgnoreProperties("article")
    @JsonIgnore
    private Collection<ArticleFile> files;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonView({JsonViews.Public.class, JsonViews.Short.class, JsonViews.Author.class})
    @JsonIgnoreProperties("articles")
    private Category category;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "articles_tags", joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonIgnoreProperties("articles")
    @JsonView({JsonViews.Public.class, JsonViews.Short.class, JsonViews.Author.class, JsonViews.Internal.class})
    private Set<Tag> tags;

    @JsonView({JsonViews.Public.class, JsonViews.Short.class})
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdOn;

    private boolean published = false;

    @JsonView({JsonViews.Public.class, JsonViews.Short.class})
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedDate;

    @JsonView({JsonViews.Public.class, JsonViews.Short.class})
    private portal.education.Monolit.data.model.article.ArticleStatus status;

    public Article(Author author, String title, String body, Category category, Tag... tags) {
        this.author = author;
        this.title = title;
        this.body = body;
        this.category = category;
        this.tags = Set.of(tags);
        this.status = ArticleStatus.IN_DEVELOPING;
    }
    public Article(Author author, String title, String body, Category category, Set<Tag> tags) {
        this.author = author;
        this.title = title;
        this.body = body;
        this.category = category;
        this.tags = tags;
        this.status = ArticleStatus.IN_DEVELOPING;
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
        this.published = ArticleStatus.PUBLISHED == status ? true : false;
        if (this.published)
            publishedDate = Date.from(Instant.now());

    }
}
