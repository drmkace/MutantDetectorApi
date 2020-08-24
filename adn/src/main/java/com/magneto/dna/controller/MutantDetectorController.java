package com.magneto.dna.controller;

import com.magneto.dna.dto.DnaSampleRequestDto;
import com.magneto.dna.dto.PingResponseDto;
import com.magneto.dna.exception.InvalidDnaException;
import com.magneto.dna.service.MutantDetectorService;
import com.magneto.dna.config.DbSchemaBuilder;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value =  "Pings for Api and DB Statuses", notes = "Verifies if the APi and Database are Alive ")
    public PingResponseDto ping() {
        var pingDatabase = dbschemaBuilder.ping();
        PingResponseDto responseDto = new PingResponseDto("OK", pingDatabase ? "OK" : "N/A");
        return responseDto;
    }

    @PostMapping("/mutant")
    @ApiOperation(value =  "Detects if the Dna is Mutant or Not", notes = "Returns 200 - OK if Its Mutant 403 - Forbidden if not")
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
