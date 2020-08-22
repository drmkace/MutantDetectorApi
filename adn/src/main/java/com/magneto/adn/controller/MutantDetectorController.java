package com.magneto.adn.controller;

import com.magneto.adn.dto.DnaSampleRequestDto;
import com.magneto.adn.dto.PingResponseDto;
import com.magneto.adn.exception.InvalidDnaException;
import com.magneto.adn.service.MutantDetectorService;
import com.magneto.adn.util.DbSchemaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MutantDetectorController {

    private MutantDetectorService mutantDetectorService;
    private DbSchemaBuilder dbschemaBuilder;

    @Autowired
    public MutantDetectorController(
            MutantDetectorService mutantDetectorService,
            DbSchemaBuilder dbschemaBuilder) {
        this.mutantDetectorService = mutantDetectorService;
        this.dbschemaBuilder = dbschemaBuilder;
    }

    @GetMapping("/ping")
    @ResponseStatus(HttpStatus.OK)
    public PingResponseDto ping() {
        var pingDatabase = dbschemaBuilder.ping();
        PingResponseDto responseDto = new PingResponseDto("OK", pingDatabase ? "OK" : "N/A");
        return responseDto;
    }

    @PostMapping("/mutant")
    public ResponseEntity<String> detectMutant(@RequestBody DnaSampleRequestDto dnaSampleRequestDto) {
        if(dnaSampleRequestDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else
        {
            try {
                boolean isMutant = this.mutantDetectorService.isMutant(dnaSampleRequestDto.getDna());
                if(isMutant)
                {
                    return ResponseEntity.status(HttpStatus.OK).build();
                }
                else
                {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            } catch(InvalidDnaException ex)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
    }
}
