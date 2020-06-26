package portal.education.Monolit.data.model.article;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.abstractModel.AbstractFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Blob;
import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class ArticleFile extends AbstractFile {

    @ManyToOne()
    @JoinColumn(name = "article_id")
    @JsonIgnoreProperties("files")
    @JsonView(JsonViews.Public.class)
    private Article article = null;

    private Date expiryDate; // срок жизни если не совершилась привязка к статье. после привязки к статье не изменять. т.к. если статья удалится, то и файл тоже должен удалиться

    public ArticleFile(String сontentType, Long size, Blob data, Date expiryDate) {
        super(сontentType, size, data);
        this.expiryDate = expiryDate;
    }
}
