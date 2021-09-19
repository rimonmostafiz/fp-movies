package edu.miu.model;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rimon Mostafiz
 */
public class Movie {
    private Long budget;
    private List<Genre> genres;
    private String homepage;
    private Long id;
    private List<Keyword> keywords;
    private String originalLanguage;
    private String originalTitle;
    private String overView;
    private Double popularity;
    private List<ProductionCompany> productionCompanies;
    private List<ProductionCountry> productionCountries;
    private LocalDate releaseDate;
    private Long revenue;
    private Integer runtime;
    private List<SpokenLanguage> spokenLanguages;
    private Status status;
    private String tagline;
    private String title;
    private Double voteAverage;
    private Integer voteCount;

}
