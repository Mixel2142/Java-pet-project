package portal.education.Monolit.data.repos.person;

import portal.education.Monolit.data.model.person.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Role, Short> {
    Role findByName(String role_name);
}

