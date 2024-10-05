package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CountryService {

        private CountryRepository countryRepository;

        private RestTemplate restTemplate = new RestTemplate();

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(toList());
        }

        public List<CountryDTO> getAllCountriesDTO() {
                List<CountryDTO> countriesList = new ArrayList<>();
                List<Country> countries = this.getAllCountries();
                for (Country country : countries) {
                        countriesList.add(this.mapToDTO(country));
                }
                return countriesList;
        }

        public CountryDTO getCountryByCode(String code) {
             Optional<Country> countryByCode = this.getAllCountries().stream().filter(country -> country.getCode().equals(code.toUpperCase())).findFirst();

             return mapToDTO(countryByCode.orElse(null));
        }

        public CountryDTO getCountryByName(String name) {
                Optional<Country> countryByName = this.getAllCountries().stream().filter(country -> country.getName().equalsIgnoreCase(name)).findFirst();

                return mapToDTO(countryByName.orElse(null));
        }


        public List<CountryDTO> getCountryByContinent(String continent) {
                List<Country> countries = this.getAllCountries();

                Map<String, List<Country>> countriesByContinent =  countries.stream().collect(groupingBy(country -> country.getRegion()));

                List<Country> country = countriesByContinent.get(continent);

                return country.stream().map(this::mapToDTO).collect(toList());

        }

        public CountryDTO getCountryMoreBorders(){
                int maxBorder =  0;
                Country cFound = new Country();
                List<Country> countries = this.getAllCountries();
                for (Country country : countries) {
                        if(maxBorder == 0)
                        {
                            maxBorder = country.getBorders().size();
                        }
                        else {
                                if(country.getBorders().size() > maxBorder)
                                {
                                        maxBorder = country.getBorders().size();
                                        cFound = country;
                                }
                        }

                }

                return mapToDTO(cFound);

        }

        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .code((String) countryData.get("cca3"))
                        .borders((List<String>) countryData.get("borders"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .build();
        }


        private CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }
}