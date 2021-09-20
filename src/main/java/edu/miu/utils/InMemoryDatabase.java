package edu.miu.utils;

import edu.miu.model.Credit;
import edu.miu.model.Movie;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Rimon Mostafiz
 */
public class InMemoryDatabase {
    private static final List<String[]> creditRows;
    private static final List<String[]> movieRows;
    private final static String CREDIT_FILE_NAME = "data/tmdb_5000_credits.csv";
    private final static String MOVIE_FILE_NAME = "data/tmdb_5000_movies.csv";

    static {
        creditRows = CSVFileReader.read(CREDIT_FILE_NAME);
        movieRows = CSVFileReader.read(MOVIE_FILE_NAME);
    }

    @Getter
    private List<Movie> movies;
    @Getter
    private List<Credit> credits;

    private InMemoryDatabase() {
        initialize();
    }

    public static InMemoryDatabase getInstance() {
        return Database.INSTANCE;
    }

    private void initialize() {
        initMovies();
        initCredits();
    }

    private void initMovies() {
        movies = movieRows.stream()
                .map(Movie::of)
                .collect(Collectors.toList());
    }

    private void initCredits() {
        credits = creditRows.stream()
                .map(Credit::of)
                .collect(Collectors.toList());
    }

    private static class Database {
        private static final InMemoryDatabase INSTANCE = new InMemoryDatabase();
    }

}
