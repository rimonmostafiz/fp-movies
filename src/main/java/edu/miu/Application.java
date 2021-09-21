package edu.miu;

import edu.miu.model.Genre;
import edu.miu.model.Movie;
import edu.miu.utils.InMemoryDatabase;
import edu.miu.service.FunctionalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;
import java.util.Map;

/**
 * @author Rimon Mostafiz
 */
@Slf4j
public class Application {

    private static final InMemoryDatabase database = InMemoryDatabase.getInstance();

    public static void main(String[] args) {
        System.out.println("Total Movies: " + database.getMovies().size());
        System.out.println("Total Credits: " + database.getCredits().size());
        System.out.println("Total Production Company: " + database.getAllProductionCompany().size());
        //database.getAllProductionCompany().forEach(pc -> log.debug("{} {}", pc.getName(), pc.getMovieProduced().size()));

//        Map<String, Double> topKMovies = FunctionalService.topKMovies.apply(database.getMovies(), 2015L, 10L);
//        topKMovies.forEach((key, value) -> log.debug("{} --> {}", key, value));
//
//        Map<String, Long> topKSuccessfulMovies = FunctionalService.topKSuccessfulMovies.apply(database.getMovies(), 2015L, 10L);
//        topKSuccessfulMovies.forEach((key, value) -> log.debug("{} --> {}", key, value));

//        Map<String, List<String>> topKMovieByGenre = FunctionalService.topKMoviesByGenre.apply(database.getMovies(), 2015L, 3L);
//        topKMovieByGenre.forEach((key, value) -> log.debug("{} --> {}", key, value));
    }
}
