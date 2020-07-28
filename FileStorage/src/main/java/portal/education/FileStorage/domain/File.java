package portal.education.FileStorage.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Table("file_storage")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File implements Persistable<UUID> {

    @Id
    private UUID fileId;

    @Column("article_id")
    private UUID articleId;

    @Column("user_id")
    private UUID userId;

    @Column("expiry_date")
    private LocalDateTime expiryDate;

    @Column("created_on")
    private LocalDateTime createdOn;

    @Column("сontent_type")
    private String сontentType;

    @Column("size")
    private long size;

    @Column("data")
    private byte[] data;

    @Override
    public UUID getId() {
        return userId;
    }

    @org.springframework.data.annotation.Transient
    transient private boolean newAuthDataFlag = false;

    @Override
    @Transient
    public boolean isNew() {
        return this.newAuthDataFlag;
    }

    public File asNew() {
        this.newAuthDataFlag = true;
        return this;
    }
}
