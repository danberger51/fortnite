package org.example.fortnite.controllers.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.fortnite.controllers.Services.UserService;
import org.example.fortnite.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;



@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;


    @GetMapping(path = "{id}")
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

    @GetMapping(path = "{username}")
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






    @GetMapping
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







    @PostMapping(path = "/sign-up", consumes = "application/json")
    @Operation(summary = "Sign-up a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was Sign-up successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "409", description = "User could not be Sign-up",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})})
    public void signUpUser(@Valid @RequestBody User user) {
        try {
            userService.signUp(user);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user did already sign-up");
        }
    }





    @PutMapping(path = "/update", consumes = "application/json")
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

    @DeleteMapping("{id}")
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



    public UserController(UserService userService) {
        this.userService = userService;
    }
}

