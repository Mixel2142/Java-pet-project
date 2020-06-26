package portal.education.Monolit.task;

import portal.education.Monolit.aspect.LogExecutionTime;
import portal.education.Monolit.data.repos.article.ArticleFileRepository;
import portal.education.Monolit.data.repos.RefreshTokenRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Service
@Transactional
@Log4j2(topic = "ASYNC__JSON__FILE__APPENDER")
public class AllTasks {

    @Autowired
    RefreshTokenRepository refreshTokenDao;

    @Autowired
    ArticleFileRepository fileModelDao;

    @Autowired
    StatisticsService statisticsService;

    @LogExecutionTime(writeErrorIfTimeLess = 100)
    //    @Scheduled(cron = "${task.purge.cron.refreshToken}")
    @Scheduled(fixedRate = 10000)// каждые 60 секунд
    public void purgeExpiredRefreshTokens() {
        try {
            Date now = Date.from(Instant.now());
            refreshTokenDao.deleteAllExpiredSince(now);
            log.info("Execute purgeExpiredRefreshTokens.");
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    @LogExecutionTime(writeErrorIfTimeLess = 100)
//    @Scheduled(cron = "${task.purge.cron.ExpiredFiles}")
@Scheduled(fixedRate = 10000)// каждые 60 секунд
    public void purgeExpiredFiles() {
        try {
            Date now = Date.from(Instant.now());
            fileModelDao.deleteAllExpiredSince(now);// устаревшие файлы, которые привязаны к статье должны остаться
            log.info("Execute purgeExpiredFiles.");
        } catch (Exception ex) {
            log.error(ex);
        }
    }


    @LogExecutionTime(writeErrorIfTimeLess = 100)
    //    @Scheduled(cron = "${task.reCalc.cron.ratings}")
    @Scheduled(fixedRate = 10000)// каждые 60 секунд
    public void recCalculateRatings() {
        try {
        statisticsService.reCalcRatingArticle();
        statisticsService.reCalcRatingAuthor();
        statisticsService.reCalcRatingCategory();
        log.info("recCalculateRatings");
        } catch (Exception ex) {
            log.error(ex);
        }
    }

}
