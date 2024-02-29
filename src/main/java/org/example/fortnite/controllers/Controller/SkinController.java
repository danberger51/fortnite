package org.example.fortnite.controllers.Controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.fortnite.controllers.Services.SkinService;
import org.example.fortnite.models.Skin;
import org.example.fortnite.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/skins")
public class SkinController {

    @Autowired
    private final SkinService skinService;

    @GetMapping("{id}")
    @Operation(summary = "find a skin by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skin was found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Skin was not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad-Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})})
    public Skin getSkinById(@Valid @PathVariable Integer id) {
        try {
            return skinService.findSkinById(id);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Skin not Found");
        }
    }

    @GetMapping
    @Operation(summary = "find all Skins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skins were found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Skins were not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad-Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})})
    public Iterable<Skin> getAllSkins() {
        try {
            return skinService.findAllSkins();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Skins not Found");
        }
    }

    @PostMapping(path = "/insert",consumes = "application/json")
    @Operation(summary = "Insert a Skin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "409", description = "Insert failed",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})})
    public void insertSkin(@Valid @RequestBody Skin skin) {
        try {
            skinService.insertSkin(skin);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Skin not Found");
        }
    }
    @PutMapping(consumes = "application/json")
    @Operation(summary = "update a skin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skin was updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Skin not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})})
    public void updateSkin(@Valid @RequestBody Skin skin) {
        try {
            skinService.updateSkin(skin);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Skin not Found");
        }
    }

    @DeleteMapping(path = "{id}")
    @Operation(summary = "Delete a skin by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skin was Deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Skin was not Found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad-Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})})
    public void deleteSkinById(@Valid @PathVariable Integer id) {
        try {
            skinService.deleteById(id);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Skin not Found");
        }
    }



    public SkinController(SkinService skinService) {
        this.skinService = skinService;
    }
}
