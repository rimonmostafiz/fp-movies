package edu.miu.utils;

import edu.miu.model.Credit;
import edu.miu.model.Movie;
import edu.miu.model.ProductionCompany;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
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
    @Getter
    private List<ProductionCompany> allProductionCompany;

    private InMemoryDatabase() {
        initialize();
    }

    public static InMemoryDatabase getInstance() {
        return Database.INSTANCE_HOLDER;
    }

    private void initialize() {
        initMovies();
        initCredits();

        //TODO: fix me
        //initProductionCompany();
    }

    private void initProductionCompany() {
        allProductionCompany = movies.parallelStream()
                .flatMap(m -> m.getProductionCompanies().stream())
                .collect(Collectors.toSet())
                .stream()
                .sorted(Comparator.comparing((ProductionCompany::getId)))
                .collect(Collectors.toList());

        BiPredicate<Movie, ProductionCompany> isProducedBy = (movie, pCompany) ->
                movie.getProductionCompanies()
                        .stream()
                        .filter(pc -> pc.getId().equals(pCompany.getId())).count() >= 1;

        Function<ProductionCompany, List<Movie>> movieByPc = company ->
                movies.parallelStream()
                        .filter(movie -> isProducedBy.test(movie, company))
                        .collect(Collectors.toList());

        allProductionCompany.forEach(pc -> pc.getMovieProduced().addAll(movieByPc.apply(pc)));
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
        private static final InMemoryDatabase INSTANCE_HOLDER = new InMemoryDatabase();
    }

}
