package portal.education.Monolit.controller.registrations;


import portal.education.Monolit.data.dto.*;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.security.JwtPairTokenService;
import portal.education.Monolit.security.SecurityService;
import portal.education.Monolit.service.notification.MailSenderService;
import portal.education.Monolit.service.notification.controller.PasswordForgetNotification;
import portal.education.Monolit.service.notification.mailInfo.MailInfoFactory;
import portal.education.Monolit.service.person.AccountConfirmationService;
import portal.education.Monolit.service.person.UserService;
import portal.education.Monolit.validator.UserValidator;
import io.swagger.v3.oas.annotations.Hidden;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;

@RestController()
@RequestMapping("/free/registry")
@Tag(name = "Free Registration", description = "There are actions with users accounts.\nAll methods do not need any tokens.")
@Log4j2(topic = "ASYNC__JSON__FILE__APPENDER")
public class FreeRegistryController {

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    PasswordForgetNotification passwordConfirmNotificationS;

    @Autowired
    AccountConfirmationService accountConfirmationService;

    @Autowired
    JwtPairTokenService jwtPairTokenService;

    @Autowired
    private MailInfoFactory mailInfoFactory;

    @Autowired
    private MailSenderService mailSender;

    @Operation(summary = "Registration any person", description = "Need a unique login and mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful operation",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = JwtPairTokenDto.class,
                                    description = "В примере указана всего одна статья, но их будем массив!"
                            ))),
            @ApiResponse(responseCode = "500", description = "Any other run time error")
    })
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtPairTokenDto RegistrationController(@RequestBody @Valid UserDto data,
                                                  @Parameter(hidden = true) BindingResult result) {
        try {
            User user = new User(data);
            userValidator.validate(user, result);

            if (result.hasErrors()) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, result.getAllErrors().toString());
            }

            userService.save(user);
            securityService.autoLogin(data.getNickname(), data.getPassword());

            return jwtPairTokenService.createFromLogin(data.getNickname());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }


    }


    @Operation(summary = "Login/sign in any person who already was registered")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Any other run time error")
    })
    @PostMapping("/signin")
    public ResponseEntity<?> LoginController(@RequestBody @Valid LoginDto data) {
        try {
            securityService.autoLogin(data.getLogin(), data.getPassword());
            userService.purgeAllExpiredTokens(data.getLogin());
            JwtPairTokenDto newPair = jwtPairTokenService.createFromLogin(data.getLogin());

            return ResponseEntity.ok().body(newPair);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Operation(summary = "If password was forgotten", description = "Send mail on user's email with link. Additional action: set email confirm true.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = UserAttemptToAccountConfirmDto.class,
                                    description = "Время жизни ссылки и почта на которую было отправлено"
                            ))),
            @ApiResponse(responseCode = "404", description = "Usertest's email not found"),
            @ApiResponse(responseCode = "418", description = "Any other run time error")
    }
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/password/forget")
    public UserAttemptToAccountConfirmDto passwordForgetController(@RequestBody @Valid PasswordForgetDto dto,
                                                                   @Parameter(hidden = true) HttpServletRequest request) {

        return (UserAttemptToAccountConfirmDto) mailSender.send(mailInfoFactory.buildFromPasswordConfirmDto(dto,request)).getItem(0);
    }

    @Hidden
    @Operation(summary = "Catch redirect from email")
    @GetMapping("/password/forget/check")
    public ResponseEntity<?> passwordForgetController(@RequestParam String code,
                                                      @Parameter(hidden = true) HttpServletResponse response) throws IOException {
        String decodedToken = new String(Base64.getDecoder().decode(code));
        try {
            jwtPairTokenService.validateAccessToken(decodedToken);

            response.addHeader("token", decodedToken);
            response.sendRedirect(jwtPairTokenService.getRedirectOkFromToken(decodedToken));//редеректить на страничку с параметром код, чтобы она смогла вернуть его обратно

            return new ResponseEntity<>(HttpStatus.TEMPORARY_REDIRECT);
        } catch (Exception e) {

            response.addHeader("Error message", e.getMessage());
            response.sendRedirect(jwtPairTokenService.getRedirectErrorFromToken(decodedToken));

            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @Hidden
    @Operation(summary = "Catch redirect from email")
    @GetMapping({"/account/check"})
    public void checkEmailController(@Parameter(hidden = true)HttpServletResponse response, @RequestParam String code) throws IOException {

        String decodedToken = new String(Base64.getDecoder().decode(code));
        try {
            jwtPairTokenService.validateAccessToken(decodedToken);
            userService.emailConfirmedChangeTrue(decodedToken);
            accountConfirmationService.validAccountOrThrowError(decodedToken);
            response.sendRedirect(jwtPairTokenService.getRedirectOkFromToken(decodedToken));
        } catch (Exception e) {
            response.sendRedirect(jwtPairTokenService.getRedirectErrorFromToken(decodedToken));
        }
    }

}
