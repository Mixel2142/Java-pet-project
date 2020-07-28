package portal.education.FileStorage.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import portal.education.FileStorage.domain.File;
import portal.education.FileStorage.dto.UrlForDownloadDto;
import portal.education.FileStorage.repo.FileStorageRepo;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageService {
    @Autowired
    FileStorageRepo repo;

    static private String downloadUri = "api/storage/file/download?fileId=";

    static private Long TIME_LIFE_FILE_WITHOUT_ARTICLE;

    public Mono<byte[]> loadFileAsByteArray(UUID fileId) {
        return repo.findById(fileId)
                .map(File::getData);
    }

    public Mono<UrlForDownloadDto> saveFileForArticle(FilePart file) {
        LocalDateTime now = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        LocalDateTime expiryDate = LocalDateTime.ofInstant(Instant.now().plusMillis(TIME_LIFE_FILE_WITHOUT_ARTICLE), ZoneId.systemDefault());

        return DataBufferUtils.join(file.content())
                .flatMap(
                        dataBuffer ->
                                repo.save(
                                        File.builder()
                                                .fileId(UUID.randomUUID())
                                                .size(dataBuffer.capacity())
                                                .data(dataBuffer.asByteBuffer().array())
                                                .expiryDate(expiryDate)
                                                .createdOn(now)
                                                .build()
                                                .asNew()
                                ).map(
                                        savedFile -> new UrlForDownloadDto(savedFile.getFileId().toString())
                                )
                );
    }


    public Mono<UrlForDownloadDto> saveFile(FilePart file) {
        return DataBufferUtils.join(file.content())
                .flatMap(
                        dataBuffer ->
                                repo.save(
                                        File.builder()
                                                .fileId(UUID.randomUUID())
                                                .size(dataBuffer.capacity())
                                                .data(dataBuffer.asByteBuffer().array())
                                                .build()
                                                .asNew()
                                ).map(
                                        savedFile -> new UrlForDownloadDto(savedFile.getFileId().toString())
                                )
                );
    }

    public Mono<Void> deleteById(UUID fileId) {
        return repo.deleteById(fileId);
    }

    @Value("${time.life.file.without.article}")
    public void setTIME_LIFE_FILE_WITHOUT_ARTICLE(Long timeLifeFileWithoutArticle) {
        this.TIME_LIFE_FILE_WITHOUT_ARTICLE = timeLifeFileWithoutArticle * 1000;
    }

}
