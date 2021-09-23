package edu.miu.service;

import edu.miu.model.*;
import edu.miu.utils.Tuple;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


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
        movie2 = new Movie(24500l, List.of(genre2), "homepage2", 2L, keywords, "frensh", "title2", "overView2", 65.987, productionCompanies, productionCountries, LocalDate.of(2010, 04, 15), 98765L, 543.976, spokenLanguages, Status.Released, "tagline2", "title2", 5.2, 9800);
        movie3 = new Movie(65432l, List.of(genre3), "homepage3", 3L, keywords, "english", "title3", "overView3", 87.8765, productionCompanies, productionCountries, LocalDate.of(2010, 6, 15), 87653L, 987.654, spokenLanguages, Status.Released, "tagline3", "title3", 8.2, 12500);
        movie4 = new Movie(98765l, genres, "homepage4", 4L, keywords, "frensh", "title4", "overView4", 45.99867, productionCompanies, productionCountries, LocalDate.of(2010, 8, 30), 122786L, 3564.976, spokenLanguages, Status.Released, "tagline4", "title4", 6.4, 6780);
        movies = List.of(movie1, movie2, movie3, movie4);

    }
    @Test
    public void totalBenefit() {

        assertTrue(FunctionalService.totalBenefit.apply(movie1)==276496);

    }
    @Test
    public void isReleaseYearSame() {

        assertTrue(FunctionalService.isReleaseYearSame.test(movie1,2010L));
        assertFalse(FunctionalService.isReleaseYearSame.test(movie1,2004L));

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

    @Test
    public void movieGenrePairGenerator () {
        assertEquals(FunctionalService.movieGenrePairGenerator .apply(movie1,genres).size(), 3);

        assertEquals(FunctionalService.movieGenrePairGenerator.apply(movie1,genres).get(0).getValue(), movie1);
        assertEquals(FunctionalService.movieGenrePairGenerator.apply(movie1,genres).get(0).getKey(), genre1);


    }

    @Test
    public void  sortListOfMovieByAverageRating () {
        ImmutablePair pair=new ImmutablePair(genre2,List.of(movie1,movie2,movie4));
        assertEquals(FunctionalService. sortListOfMovieByAverageRating .apply(pair,2L).size(), 2);
        assertEquals(FunctionalService. sortListOfMovieByAverageRating .apply(pair,2L), List.of(movie1,movie4));

    }
    @Test
    public void  convertListOfMoviesToPairOfNameAndRating () {
        assertEquals(FunctionalService. convertListOfMoviesToPairOfNameAndRating .apply(movies).size(), 4);
        assertEquals(FunctionalService. convertListOfMoviesToPairOfNameAndRating .apply(movies), List.of("title1(7.2)", "title2(5.2)", "title3(8.2)", "title4(6.4)"));

    }
    @Test
    public void  topKMoviesByGenre () {

        assertEquals(FunctionalService. topKMoviesByGenre .apply(movies,2010L,2L).size(), 3);
        assertEquals(FunctionalService. topKMoviesByGenre .apply(movies,2010L,2L).get("genre3"), List.of("title3(8.2)", "title1(7.2)"));

    }

    @Test
    public void  totalRevenue () {
        assertTrue(FunctionalService. totalRevenue .apply(movies)==588000L);
    }
    @Test

    public void  companyMoviesPair () {
        assertEquals(FunctionalService. companyMoviesPair .apply(productionCompanies,movie1).size(),4);
        assertEquals(FunctionalService. companyMoviesPair .apply(productionCompanies,movie1).get(0).getKey(),pc1);

    }

    @Test

    public void  mostSuccessfulKProductionCompany() {
        assertEquals(FunctionalService. mostSuccessfulKProductionCompany .apply(movies,2010L,2L).size(),2);
        assertEquals(FunctionalService. mostSuccessfulKProductionCompany .apply(movies,2010L,2L).get(0).getKey(),"company1");

    }

    @Test

    public void  mostPopularMovie() {
        assertTrue(FunctionalService. mostPopularMovie .apply(movies).isPresent());
        assertEquals(FunctionalService. mostPopularMovie .apply(movies).get(),movie3);

    }

    @Test

    public void   kMostPopularMovieInAGivenYear() {
        assertEquals(FunctionalService.  kMostPopularMovieInAGivenYear .apply(movies,2010L).size(),3);
        assertEquals(FunctionalService.  kMostPopularMovieInAGivenYear .apply(movies,2010L),List.of("genre3 -> title3 [87.8765]", "genre1 -> title4 [45.99867]", "genre2 -> title2 [65.987]"));

    }

    @Test

    public void    movieSpokenLanguagePairGenerator() {
        assertEquals(FunctionalService.   movieSpokenLanguagePairGenerator .apply(movie1,spokenLanguages).size(),3);
        assertEquals(FunctionalService.   movieSpokenLanguagePairGenerator .apply(movie1,spokenLanguages).get(0).getKey(),language1);

    }
    @Test

    public void    movieCountByLanguageInGivenYear() {
        assertEquals(FunctionalService.   movieCountByLanguageInGivenYear .apply(movies,2010L).size(),3);
        assertEquals(FunctionalService.   movieCountByLanguageInGivenYear .apply(movies,2010L).get(0).getKey(),"iso2");
        assertTrue(FunctionalService.   movieCountByLanguageInGivenYear .apply(movies,2010L).get(0).getValue()==4);


    }






}
