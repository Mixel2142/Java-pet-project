package portal.education.Monolit.data.model.abstractModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.person.User;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class AbstractNotification extends AbstractEntity<Long>{

    @Temporal( TemporalType.TIMESTAMP )
    @CreationTimestamp
    private Date createdOn;

    @Temporal( TemporalType.TIMESTAMP )
    @UpdateTimestamp
    private Date updateOn;

    @Parameter(description = "Разрешить/Запретить отправлять уведомления")
    boolean state=true;
}
