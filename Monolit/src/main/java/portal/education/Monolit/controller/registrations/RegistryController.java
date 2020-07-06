package portal.education.Monolit.controller.registrations;


import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.dto.EmailConfirmDto;
import portal.education.Monolit.data.dto.JwtPairTokenDto;
import portal.education.Monolit.data.dto.PasswordDto;
import portal.education.Monolit.data.dto.UserAttemptToAccountConfirmDto;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.repos.person.UserRepository;
//import portal.education.Monolit.security.JwtPairTokenService;
import portal.education.Monolit.service.notification.MailSenderService;
import portal.education.Monolit.service.notification.mailInfo.MailInfoFactory;
import portal.education.Monolit.service.person.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Transactional
@RestController()
@RequestMapping("/registry")
@Tag(name = "Registration", description = "There are actions with users accounts.\nAll methods need any tokens: refresh or access.")
@Log4j2(topic = "ASYNC__JSON__FILE__APPENDER")
public class RegistryController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private JwtPairTokenService jwtPairTokenService;

    @Autowired
    private MailInfoFactory mailInfoFactory;

    @Autowired
    private MailSenderService mailSender;

    @Autowired
    private UserRepository<User> userDao;

    @Operation(summary = "Change person's password", description = "Need a valid password and new password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "417", description = "Password is not valid"),
            @ApiResponse(responseCode = "500", description = "Any other exceptions. See error message")
    }
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/password/reset")
    public void updatePasswordController(@Parameter(hidden = true) @AuthenticationPrincipal User user,
                                         @RequestBody @Valid PasswordDto data) {
        try {

            if (!userService.checkIfValidOldPassword(user, data.getOldPassword()))
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Old password is not valid!");

            userService.changeUserPassword(user, data.getNewPassword());
        } catch (Exception e) {
            log.error(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }


    }


    @Operation(summary = "Send a new pair of tokens", description = "Need a valid refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful operation(send new pair)",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = JwtPairTokenDto.class
                            ))),
            @ApiResponse(responseCode = "500", description = "Any other exceptions. See error message")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping({"/token/refresh"})
    public JwtPairTokenDto refreshTokenController(@Parameter(hidden = true) @RequestAttribute @NotNull String token) {
        try {
//            jwtPairTokenService.validateRefreshToken(token);
            return null;//jwtPairTokenService.createFromTokin(token);
        } catch (Exception e) {
            log.error(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @Operation(summary = "Set status: online", description = "Other people will see online status of the person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping({"/connectStatus"})
    @ResponseStatus(HttpStatus.OK)
    public void connectController(@Parameter(hidden = true) @AuthenticationPrincipal User user,@RequestAttribute Boolean status) {
        userService.changeActiveStatus(user.getNickname(), status);
    }

    @Operation(summary = "Logout from system", description = "Delete refresh token and set offline status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "500", description = "Any other exceptions. See error message.")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping({"/logout"})
    public void logoutController(@Parameter(hidden = true) @AuthenticationPrincipal User user,
                                 @Parameter(hidden = true) @RequestAttribute @NotNull String token) {
        try {
//            jwtPairTokenService.validateRefreshToken(token);
//            jwtPairTokenService.deleteRefreshToken(token, user);
            userService.changeActiveStatus(user.getNickname(), false);
        } catch (Exception e) {
            log.error(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @Operation(summary = "Send mail to user's email to confirm", description = "The link from email redirect to back server and change status confirm on 'true'. The next step is redirect to redirectOk's URL. The time to life of link = ttl refresh token's")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation(send new pair)",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = UserAttemptToAccountConfirmDto.class,
                                    description = "Время жизни ссылки и почта на которую было отправлено."
                            ))),
            @ApiResponse(responseCode = "417", description = "Any other exceptions. See error message.")
    })
    @PostMapping({"/send/email/confirm"})
    @ResponseStatus(HttpStatus.OK)
    public UserAttemptToAccountConfirmDto accountConfirmController(@Parameter(hidden = true) @AuthenticationPrincipal User user,
                                                                   @RequestBody @Valid EmailConfirmDto dto,
                                                                   @Parameter(hidden = true) HttpServletRequest request) {

        return (UserAttemptToAccountConfirmDto) mailSender.send(mailInfoFactory.buildFromEmailConfirmDto(dto, user, request)).getItem(0);

    }

}
