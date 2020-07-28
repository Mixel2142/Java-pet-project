package portal.education.AuthService.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import portal.education.AuthService.domain.AuthData;
import portal.education.AuthService.dto.AccountDto;
import portal.education.AuthService.dto.PairTokenDto;
import portal.education.AuthService.dto.TokenDto;
import portal.education.AuthService.repo.AuthRepo;
import portal.education.AuthService.service.AuthService;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@Tag(name = "Auth Reactive Service", description = "It keeps login,password, a set of tokens, generates new pair of tokens in any way. It is not used for registration of account.")
@RestController
@RequestMapping("/auth")
public class RegistrationController {

    @Autowired
    private @NonNull AuthService authService;

    @Autowired
    private AuthRepo authRepo;

    @Operation(summary = "Update account in system(used by another services)", description = "For registration account of users, you should use API from monolit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = AuthData.class,
                                    description = "В примере указана всего одна статья, но их будем массив!"
                            ))),
            @ApiResponse(responseCode = "500", description = "Any other run time error")
    })
    @PatchMapping("/data")
    public Mono<PairTokenDto> doUpdateAuthData(@RequestBody @Valid AuthData authData) {
        return authService.updateAuthData(authData);
    }

    @Operation(summary = "Registrate account in system(used by another services)", description = "For registration account of users, you should use API from monolit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = PairTokenDto.class,
                                    description = "В примере указана всего одна статья, но их будем массив!"
                            ))),
            @ApiResponse(responseCode = "500", description = "Any other run time error")
    })
    @PostMapping("/data")
    public Mono<PairTokenDto> doCreateAuthData(@RequestBody @Valid AuthData authData) {
        return authService.createAuthData(authData.asNew());
    }

    @Operation(summary = "Get all information about account in system(used by another services)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = AuthData.class,
                                    description = "В примере указана всего одна статья, но их будем массив!"
                            ))),
            @ApiResponse(responseCode = "500", description = "Any other run time error")
    })
    @GetMapping("/data")
    public Mono<AuthData> getAuthData(@RequestParam UUID id) {
        return authRepo.findById(id);
    }

    @Operation(summary = "Delete all information about account in system(used by another services)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "500", description = "Any other run time error")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/data")
    public void removeRegistryAccount(@RequestParam UUID id) {
        authRepo.deleteById(id);
    }

    @Operation(summary = "Update pair of token by refresh token (for front)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = PairTokenDto.class,
                                    description = "В примере указана всего одна статья, но их будем массив!"
                            ))),
            @ApiResponse(responseCode = "500", description = "Any other run time error")
    })
    @PostMapping("/update/accessToken")
    public Mono<PairTokenDto> updateAccessToken(@RequestBody @Valid TokenDto refreshToken) {
        return authService.generatePairFromRefreshToken(refreshToken.getRefreshToken());
    }

    @Operation(summary = "Update pair of token by login and password (for front)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = PairTokenDto.class,
                                    description = "В примере указана всего одна статья, но их будем массив!"
                            ))),
            @ApiResponse(responseCode = "500", description = "Any other run time error")
    })
    @PostMapping("/update/refreshToken")
    public Mono<PairTokenDto> updateRefreshToken(@RequestBody @Valid AccountDto accountDto) {
        return authService.generatePairFromLoginAndPassword(accountDto);
    }
}
