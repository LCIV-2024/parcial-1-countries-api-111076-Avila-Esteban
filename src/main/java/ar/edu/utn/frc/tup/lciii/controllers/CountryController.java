package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/api/countries")
    public ResponseEntity<List<CountryDTO>> getAllCountries()
    {
        return ResponseEntity.ok(countryService.getAllCountriesDTO());
    }
    @RequestMapping(value = "/api/countries", params = "code")
    public  ResponseEntity<CountryDTO> getCountryByCode(@RequestParam String code)
    {
        return ResponseEntity.ok(countryService.getCountryByCode(code));
    }

    @RequestMapping(value = "/api/countries", params = "name")
    public  ResponseEntity<CountryDTO> getCountryByName(@RequestParam String name)
    {
        return ResponseEntity.ok(countryService.getCountryByName(name));
    }

    @RequestMapping(value = "/api/countries/continent", params = "continent")
    public  ResponseEntity<List<CountryDTO>> getCountryByContinent(@RequestParam String continent)
    {
        return ResponseEntity.ok(countryService.getCountryByContinent(continent));
    }

    @GetMapping("/api/countries/most-borders")
    public ResponseEntity<CountryDTO> getCountryMostBorders()
    {
        return ResponseEntity.ok(countryService.getCountryMoreBorders());
    }
}