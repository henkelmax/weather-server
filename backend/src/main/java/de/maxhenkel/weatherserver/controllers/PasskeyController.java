package de.maxhenkel.weatherserver.controllers;

import de.maxhenkel.weatherserver.annotations.ValidateApiKey;
import de.maxhenkel.weatherserver.dtos.Passkey;
import de.maxhenkel.weatherserver.services.PasskeyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passkeys")
public class PasskeyController {

    @Autowired
    private PasskeyService passkeyService;

    @GetMapping
    @ValidateApiKey
    public List<Passkey> passkeys() {
        return passkeyService.getAll();
    }

    @PostMapping
    @ValidateApiKey
    public ResponseEntity<Void> addPasskey(@RequestBody @Valid Passkey passkey) {
        if (!passkeyService.addPasskey(passkey)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Station already has a passkey"); //TODO Return problem+json
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ValidateApiKey
    public ResponseEntity<Void> deletePasskey(@PathVariable long id) {
        if (!passkeyService.deletePasskey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Passkey not found"); //TODO Return problem+json
        }
        return ResponseEntity.ok().build();
    }

}
