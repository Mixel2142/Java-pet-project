package portal.education.Monolit.data.repos;

import portal.education.Monolit.data.model.RefreshToken;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.repos.abstractRepos.AbstractEntityRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;



@Repository

public interface RefreshTokenRepository extends AbstractEntityRepository<RefreshToken, Long> {
    RefreshToken findByToken(String token);
    void deleteByUserAndToken(User user, String token);

    @Modifying
    @Query("DELETE FROM RefreshToken t WHERE t.expiryDate <= :date")
    void deleteAllExpiredSince(@Param("date") Date date);

    @Modifying
    @Query("DELETE FROM RefreshToken t WHERE t.user = :theUser and t.expiryDate <= :date")
    void deleteByNickNameAllExpiredSince(@Param("theUser") User theUser, @Param("date") Date date);
}
