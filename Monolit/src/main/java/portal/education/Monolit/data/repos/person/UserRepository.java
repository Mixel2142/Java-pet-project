package portal.education.Monolit.data.repos.person;

import portal.education.Monolit.data.model.person.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository<E extends User> extends JpaRepository<E, UUID> {

    Optional<E> findByNickname(String nickname);

    Optional<E> findByAccountIdentification(String email);

    @Modifying
    @Query("UPDATE User u SET u.online = :active WHERE u.nickname = :nickName")
    void setActiveByNickName(@Param("nickName") String nickName, @Param("active") boolean active);

    Optional<List<E>> findByNicknameStartingWith(String partOfNickName, Pageable pageable);
}


