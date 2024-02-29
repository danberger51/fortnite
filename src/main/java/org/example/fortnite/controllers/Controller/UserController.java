package org.example.fortnite.controllers.Controller;



import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.fortnite.controllers.Configurations.LoginRequest;
import org.example.fortnite.controllers.Services.UserService;
import org.example.fortnite.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import static org.example.fortnite.controllers.Configurations.SecurityConstants.EXPIRATION_TIME;
import static org.example.fortnite.controllers.Configurations.SecurityConstants.SECRET;



@Validated
@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "users/byId/{id}")
    @Operation(summary = "find a user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "409", description = "User was not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})})
    public User getUserById(@Valid @PathVariable Integer id) {
        try {
            return userService.findUserById(id);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User not Found");
        }
    }

    @GetMapping(path = "users/byUsername/{username}")
    @Operation(summary = "find a user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "409", description = "User was not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})})
    public User getUserByUsername(@Valid @PathVariable String username) {
        try {
            return userService.findUserByUsername(username);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User not Found");
        }
    }

    @GetMapping(path = "users")
    @Operation(summary = "get all Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Users were found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "409", description = "did not find user",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})})
    public Iterable<User> getAllUsers() {
        try {
            return userService.findAllUsers();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Users not found");
        }
    }

    @PostMapping(path = "users/sign-up", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was Sign-up successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "409", description = "User could not be Sign-up",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})})
    public Void signUpUser(@Valid @RequestBody User user) {
        try {
            return userService.signUp(user);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user did already sign-up");
        }
    }

    // Hier wird ein POST-Endpunkt für die Authentifizierung (Login) erstellt
// Der Endpunkt-Pfad lautet "/login"
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        // Benutzer authentifizieren und ein JWT-Token generieren
        Authentication authentication = authenticationManager.authenticate(
                // Hier wird ein Authentication-Token erstellt, das Benutzername und Passwort enthält
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        // Setzen Sie die Authentifizierungsinformationen im Sicherheitskontext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Hier wird ein JWT-Token erstellt, das den Benutzernamen als Subjekt enthält
        // und eine Ablaufzeit hat
        String token = JWT.create()
                .withSubject(((UserDetails) authentication.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        // Die Antwort enthält das JWT-Token im Body
        Map<String, String> response = Collections.singletonMap("token", token);
        // Die Antwort wird mit dem HTTP-Status "OK" zurückgegeben
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "users/update", consumes = "application/json")
    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "409", description = "User could not be updated",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})})
    public void update(@Valid @RequestBody User user) {
        try {
            userService.updateUser(user);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already in use");
        }
    }

    @DeleteMapping("users/{id}")
    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was Deletet Sucessfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "409", description = "User could not be Deletet",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})})
    public void deleteUserById(@Valid @PathVariable Integer id) {
        try {
            userService.deleteById(id);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User not Found");
        }
    }
}

