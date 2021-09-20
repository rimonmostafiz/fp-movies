package edu.miu;

import edu.miu.utils.InMemoryDatabase;
import edu.miu.service.FunctionalService;
import lombok.extern.slf4j.Slf4j;

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

        Map<String, Double> topKMovies = FunctionalService.topKMovies.apply(database.getMovies(), 2015L, 10L);
        topKMovies.forEach((key, value) -> log.debug("{} --> {}", key, value));

        Map<String, Long> topKSuccessfulMovies = FunctionalService.topKSuccessfulMovies.apply(database.getMovies(), 2015L, 10L);
        topKSuccessfulMovies.forEach((key, value) -> log.debug("{} --> {}", key, value));
    }
}
