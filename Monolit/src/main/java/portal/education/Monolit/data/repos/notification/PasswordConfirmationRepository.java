package portal.education.Monolit.data.repos.notification;

import portal.education.Monolit.data.model.notification.PasswordConfirmation;
import portal.education.Monolit.data.model.person.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;


@Repository
public interface PasswordConfirmationRepository extends ConfirmNotificationMRepository<PasswordConfirmation> {
    PasswordConfirmation findByUser(User user);

    @Query("SELECT c.state FROM PasswordConfirmation c WHERE c.user = :user")
    boolean isAvailable(@Param("user") User user);


    @Query("SELECT c.attempts FROM PasswordConfirmation c WHERE c.user = :user")
    Short getAttempts(@Param("user") User user);

    @Query("SELECT c.nextAttemptTime FROM PasswordConfirmation c WHERE c.user = :user")
    Date getNextAttemptTime(@Param("user") User user);

    @Modifying
    @Query("UPDATE PasswordConfirmation c SET c.attempts = c.attempts-1 WHERE c.user = :user")
    void decrementAttempts(@Param("user") User user);

    @Modifying
    @Query("UPDATE PasswordConfirmation c SET c.nextAttemptTime = :time WHERE c.user = :user")
    void setNextAttemptTime(@Param("user") User user, @Param("time") Date time);

    @Modifying
    @Query("UPDATE PasswordConfirmation c SET c.confirm = :conf WHERE c.user = :user")
    void setConfirm(@Param("user") User user, @Param("conf") boolean conf);
}
