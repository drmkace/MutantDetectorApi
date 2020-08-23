package com.magneto.adn.controller;

import com.magneto.adn.dto.StatResponseDto;
import com.magneto.adn.service.StatService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
public class StatController {
    private StatService service;

    @Autowired
    public StatController(StatService service) {
        this.service = service;
    }

    @GetMapping("/stats")
    @ApiOperation(value =  "Gets Mutant Detector Stats", notes = "Gets Human and Mutant Count and Ratio")
    public ResponseEntity<StatResponseDto> getStat() {
        var stat = service.get();
        var statResponseDto = new StatResponseDto(stat.getHumanCount(), stat.getMutantCount());
        return ResponseEntity.ok().body(statResponseDto);
    }
}
