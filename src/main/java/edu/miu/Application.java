package edu.miu;

import edu.miu.Utils.InMemoryDatabase;
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
    }
}
