package edu.miu;

import edu.miu.utils.InMemoryDatabase;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Rimon Mostafiz
 */
@Slf4j
public class Application {

    private static final InMemoryDatabase database = InMemoryDatabase.getInstance();

    public static void main(String[] args) {
        System.out.println("Total Movies: " + database.getMovies().size());
        System.out.println("Total Credits: " + database.getCredits().size());
        //System.out.println("Total Production Company: " + database.getAllProductionCompany().size());
        //database.getAllProductionCompany().forEach(pc -> log.debug("{} {}", pc.getName(), pc.getMovieProduced().size()));

//        List<String> topKMovies = FunctionalService.topKMovies.apply(database.getMovies(), 2015L, 10L);
//        topKMovies.forEach(value -> log.debug("{}", value));

//        List<String> topKSuccessfulMovies = FunctionalService.topKSuccessfulMovies.apply(database.getMovies(), 2015L, 10L);
//        topKSuccessfulMovies.forEach(value -> log.debug("{}", value));

//        Map<String, List<String>> topKMovieByGenre = FunctionalService.topKMoviesByGenre.apply(database.getMovies(), 2015L, 3L);
//        topKMovieByGenre.forEach((key, value) -> log.debug("{} --> {}", key, value));

//        List<ImmutablePair<String, Long>> topKMovieByGenre = FunctionalService.mostSuccessfulKProductionCompany.apply(database.getMovies(), 2016L, 5L);
//        topKMovieByGenre.forEach((pair) -> log.debug("{} --> {}", pair.getLeft(), pair.getRight()));
    }
}
