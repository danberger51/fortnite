package org.example.fortnite.controllers.Controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.fortnite.controllers.Services.WeaponService;
import org.example.fortnite.models.User;
import org.example.fortnite.models.Weapon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/skins")
public class WeaponController {

    @Autowired
    private final WeaponService weaponService;

    @GetMapping("{id}")
    @Operation(summary = "find a weapon by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weapon was found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Weapom was not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad-Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})})
    public Weapon getWeaponById(@Valid @PathVariable Integer id) {
        try {
            return weaponService.findWeaponById(id);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Weapon not Found");
        }
    }




    @GetMapping
    @Operation(summary = "find all Weapons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weapons were found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Weapons were not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad-Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})})
    public Iterable<Weapon> getAllWeapons() {
        try {
            return weaponService.findAllWeapons();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Weapons not Found");
        }
    }

    @PostMapping(consumes = "application/json")
    @Operation(summary = "Insert a Weapon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "409", description = "Insert failed",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})})
    public void insertWeapon(@Valid @RequestBody Weapon weapon) {
        try {
            weaponService.insertWeapon(weapon);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Weapon not Found");
        }
    }
    @PutMapping(consumes = "application/json")
    @Operation(summary = "update a weapon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weapon was updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Weapon not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})})
    public void updateMovie(@Valid @RequestBody Weapon weapon) {
        try {
            weaponService.updateWeapon(weapon);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Weapon not Found");
        }
    }

    @DeleteMapping(path = "{id}")
    @Operation(summary = "Delete a weapon by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weapon was Deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Weapon was not Found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad-Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})})
    public void deleteWeaponById(@Valid @PathVariable Integer id) {
        try {
            weaponService.deleteById(id);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Weapon not Found");
        }
    }



    public WeaponController(WeaponService weaponService) {
        this.weaponService = weaponService;
    }
}
