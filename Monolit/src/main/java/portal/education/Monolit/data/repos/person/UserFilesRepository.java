package portal.education.Monolit.data.repos.person;


import portal.education.Monolit.data.model.person.UserFile;
import portal.education.Monolit.data.repos.abstractRepos.AbstractFileRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserFilesRepository extends AbstractFileRepository<UserFile> {

}
