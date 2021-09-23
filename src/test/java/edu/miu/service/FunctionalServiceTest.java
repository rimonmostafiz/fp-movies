package edu.miu.service;

import edu.miu.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class FunctionalServiceTest {

    List<Genre> genres;
    List<Keyword> keywords;
    List<ProductionCompany> productionCompanies;
    List<ProductionCountry> productionCountries;
    List<SpokenLanguage> spokenLanguages;
    List<Movie> movies;

    Movie movie1, movie2, movie3, movie4;
    ProductionCompany pc1, pc2, pc3, pc4;
    ProductionCountry country1, country2, country3;
    SpokenLanguage language1, language2, language3;
    Genre genre1, genre2, genre3;
    Keyword keyword1, keyword2, keyword3;

    @BeforeEach
    public void setUp() {
        //create Production country
        country1 = new ProductionCountry("iso1", "country1");
        country2 = new ProductionCountry("iso2", "country2");
        country3 = new ProductionCountry("iso3", "country3");
        productionCountries = List.of(country1, country2, country3);

        //create production compagnie
        pc1 = new ProductionCompany(1L, "company1", new ArrayList<>());
        pc2 = new ProductionCompany(2L, "company2", new ArrayList<>());
        pc3 = new ProductionCompany(3L, "company3", new ArrayList<>());
        pc4 = new ProductionCompany(4L, "company4", new ArrayList<>());
        productionCompanies = List.of(pc1, pc2, pc3, pc4);

        //create spoken language
        language1 = new SpokenLanguage("iso1", "language1");
        language2 = new SpokenLanguage("iso2", "language2");
        language3 = new SpokenLanguage("iso3", "language3");
        spokenLanguages = List.of(language1, language2, language3);

        //create genre
        genre1 = new Genre(1L, "genre1");
        genre2 = new Genre(2L, "genre2");
        genre3 = new Genre(3L, "genre3");
        genres = List.of(genre1, genre2, genre3);

        //create Keywords
        keyword1 = new Keyword(1L, "keyword1");
        keyword2 = new Keyword(2L, "keyword2");
        keyword3 = new Keyword(3L, "keyword3");
        keywords = List.of(keyword1, keyword2, keyword3);


        movie1 = new Movie(2300l, genres, "homepage1", 1L, keywords, "english", "title1", "overView1", 12.987, productionCompanies, productionCountries, LocalDate.of(2010, 12, 3), 278796L, 162.98, spokenLanguages, Status.Released, "tagline1", "title1", 7.2, 11800);
        movie2 = new Movie(24500l, genres, "homepage2", 2L, keywords, "frensh", "title2", "overView2", 65.987, productionCompanies, productionCountries, LocalDate.of(2010, 04, 15), 98765L, 543.976, spokenLanguages, Status.Released, "tagline2", "title2", 5.2, 9800);
        movie3 = new Movie(65432l, genres, "homepage3", 3L, keywords, "english", "title3", "overView3", 87.8765, productionCompanies, productionCountries, LocalDate.of(2010, 6, 15), 87653L, 987.654, spokenLanguages, Status.Released, "tagline3", "title3", 8.2, 12500);
        movie4 = new Movie(98765l, genres, "homepage4", 4L, keywords, "frensh", "title4", "overView4", 45.99867, productionCompanies, productionCountries, LocalDate.of(2010, 8, 30), 122786L, 3564.976, spokenLanguages, Status.Released, "tagline4", "title4", 6.4, 6780);
        movies = List.of(movie1, movie2, movie3, movie4);

    }

    @Test
    public void topKMovies() {
        assertEquals(FunctionalService.topKMovies.apply(movies, 2010L, 2L).size(), 2);
        assertEquals(FunctionalService.topKMovies.apply(movies, 2010L, 2L), List.of("title3(8.2)", "title1(7.2)"));
        assertTrue(FunctionalService.topKMovies.apply(movies, 2015L, 2L).isEmpty());

    }

    @Test
    public void topKSuccessfulMovies() {
        assertEquals(FunctionalService.topKSuccessfulMovies.apply(movies, 2010L, 3L).size(), 3);
        assertEquals(FunctionalService.topKSuccessfulMovies.apply(movies, 2010L, 3L), List.of("title1 -> 276496", "title2 -> 74265", "title4 -> 24021"));
        assertTrue(FunctionalService.topKSuccessfulMovies.apply(movies, 2015L, 3L).isEmpty());

    }


}
