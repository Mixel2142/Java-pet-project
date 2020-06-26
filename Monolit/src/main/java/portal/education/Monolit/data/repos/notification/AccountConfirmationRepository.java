package portal.education.Monolit.data.repos.notification;

import portal.education.Monolit.data.model.notification.AccountConfirmation;
import portal.education.Monolit.data.model.person.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface AccountConfirmationRepository extends ConfirmNotificationMRepository<AccountConfirmation> {
    AccountConfirmation findByUser(User user);

    @Query("SELECT c.state FROM AccountConfirmation c WHERE c.user = :user")
    boolean isAvailable(@Param("user") User user);


    @Query("SELECT c.attempts FROM AccountConfirmation c WHERE c.user = :user")
    Short getAttempts(@Param("user") User user);

    @Query("SELECT c.nextAttemptTime FROM AccountConfirmation c WHERE c.user = :user")
    Date getNextAttemptTime(@Param("user") User user);

    @Modifying
    @Query("UPDATE AccountConfirmation c SET c.attempts = c.attempts-1 WHERE c.user = :user")
    void decrementAttempts(@Param("user") User user);

    @Modifying
    @Query("UPDATE AccountConfirmation c SET c.nextAttemptTime = :time WHERE c.user = :user")
    void setNextAttemptTime(@Param("user") User user, @Param("time") Date time);

    @Modifying
    @Query("UPDATE AccountConfirmation c SET c.confirm = :conf WHERE c.user = :user")
    void setConfirm(@Param("user") User user, @Param("conf") boolean conf);
}
