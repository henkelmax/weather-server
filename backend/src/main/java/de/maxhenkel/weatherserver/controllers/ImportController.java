package de.maxhenkel.weatherserver.controllers;

import de.maxhenkel.weatherserver.annotations.ValidateApiKey;
import de.maxhenkel.weatherserver.csvimport.ImportService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/import")
public class ImportController {

    @Autowired
    private ImportService importService;

    @PostMapping(consumes = "text/csv")
    @ValidateApiKey
    public ResponseEntity<String> importData(HttpServletRequest request) {
        try {
            int entryCount = importService.importCsv(request.getInputStream());
            return ResponseEntity.ok().body("Imported %s entries".formatted(entryCount));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File could not be read");
        }
    }

}

