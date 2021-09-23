package edu.miu;

import edu.miu.service.FunctionalService;
import edu.miu.utils.FileReader;
import edu.miu.utils.InMemoryDatabase;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.Scanner;

/**
 * @author Rimon Mostafiz
 */
@Slf4j
public class Application {

    private static final String OPTION_FILE = "options";
    private static final InMemoryDatabase database = InMemoryDatabase.getInstance();

    public static void main(String[] args) {
        Optional<String> optional = FileReader.readFileContent(OPTION_FILE);
        String options = optional.orElseThrow(RuntimeException::new);
        String userSelection = "go";
        Scanner scanner = new Scanner(System.in);
        while (!userSelection.equals("q")) {
            System.out.println("=============");
            System.out.println(options);
            System.out.println("=============");
            System.out.print("Enter Query Number: ");
            userSelection = scanner.nextLine();
            switch (userSelection) {
                case "1":
                    System.out.print("Enter K: ");
                    long k = scanner.nextLong();
                    System.out.print("Enter Year: ");
                    long year = scanner.nextLong();
                    System.out.println("------------------");
                    FunctionalService.topKMovies
                            .apply(database.getMovies(), year, k)
                            .forEach(System.out::println);
                    System.out.println("------------------");
                    System.out.print("Do you want to test more?: ");
                    int cont = Integer.parseInt(String.valueOf(scanner.nextInt()).trim());
                    if (cont == 0) {
                        System.exit(0);
                    }
                    break;
                case "2":
                    System.out.print("Enter K: ");
                    k = scanner.nextLong();
                    System.out.print("Enter Year: ");
                    year = scanner.nextLong();
                    System.out.println("------------------");
                    FunctionalService.topKSuccessfulMovies
                            .apply(database.getMovies(), year, k)
                            .forEach(System.out::println);
                    System.out.println("------------------");
                    System.out.print("Do you want to test more?: ");
                    cont = Integer.parseInt(String.valueOf(scanner.nextInt()).trim());
                    if (cont == 0) {
                        System.exit(0);
                    }
                    break;
                case "3":
                    System.out.print("Enter K: ");
                    k = scanner.nextLong();
                    System.out.print("Enter Year: ");
                    year = scanner.nextLong();
                    System.out.println("------------------");
                    FunctionalService.topKMoviesByGenre
                            .apply(database.getMovies(), year, k)
                            .forEach((key, value) -> System.out.printf("%s %s\n", key, value));
                    System.out.println("------------------");
                    System.out.print("Do you want to test more?: ");
                    cont = Integer.parseInt(String.valueOf(scanner.nextInt()).trim());
                    if (cont == 0) {
                        System.exit(0);
                    }
                    break;
                case "4":
                    System.out.print("Enter K: ");
                    k = scanner.nextLong();
                    System.out.print("Enter Year: ");
                    year = scanner.nextLong();
                    System.out.println("------------------");
                    FunctionalService.mostSuccessfulKProductionCompany
                            .apply(database.getMovies(), year, k)
                            .forEach(pair -> System.out.printf("%s %s\n", pair.getLeft(), pair.getRight()));
                    System.out.println("------------------");
                    System.out.print("Do you want to test more?: ");
                    cont = Integer.parseInt(String.valueOf(scanner.nextInt()).trim());
                    if (cont == 0) {
                        System.exit(0);
                    }
                    break;
                case "5":
                    System.out.print("Enter Year: ");
                    year = scanner.nextLong();
                    System.out.println("------------------");
                    FunctionalService.kMostPopularMovieInAGivenYear
                            .apply(database.getMovies(), year)
                            .forEach(System.out::println);
                    System.out.println("------------------");
                    System.out.print("Do you want to test more?: ");
                    cont = Integer.parseInt(String.valueOf(scanner.nextInt()).trim());
                    if (cont == 0) {
                        System.exit(0);
                    }
                    break;
                case "6":
                    System.out.print("Enter Year: ");
                    year = scanner.nextInt();
                    System.out.println("------------------");
                    FunctionalService.movieCountByLanguageInGivenYear
                            .apply(database.getMovies(), year)
                            .forEach(pair -> System.out.printf("%s %d\n", pair.getLeft(), pair.getRight()));
                    System.out.println("------------------");
                    System.out.print("Do you want to test more?: ");
                    cont = Integer.parseInt(String.valueOf(scanner.nextInt()).trim());
                    if (cont == 0) {
                        System.exit(0);
                    }
                    break;
                default:
                    System.out.println("not implemented");
            }
        }
    }
}
