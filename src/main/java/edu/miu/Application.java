package edu.miu;

import edu.miu.service.FunctionalService;
import edu.miu.utils.FileReader;
import edu.miu.utils.InMemoryDatabase;

import java.util.Optional;
import java.util.Scanner;

/**
 * @author Rimon Mostafiz
 */
public class Application {

    private static final String OPTION_FILE = "options";
    private static final InMemoryDatabase database = InMemoryDatabase.getInstance();

    public static void main(String[] args) {
        FileReader fileReader = new FileReader();
        Optional<String> optional = fileReader.readFileContent(OPTION_FILE);
        String options = optional.orElseThrow(RuntimeException::new);
        String userSelection = "go";
        Scanner scanner = new Scanner(System.in);
        int flag = 0;
        while (!userSelection.equals("q")) {
            int notImplemented = 0;
            System.out.println("=============");
            System.out.println(options);
            System.out.println("=============");
            System.out.print("Enter Query Number: ");
            if (flag > 0) scanner.nextLine();
            else flag++;
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
                    System.out.print("Do you want to test more?(0/1): ");
                    int cont = scanner.nextInt();
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
                    System.out.print("Do you want to test more?(0/1): ");
                    cont = scanner.nextInt();
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
                    System.out.print("Do you want to test more?(0/1): ");
                    cont = scanner.nextInt();
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
                    System.out.print("Do you want to test more?(0/1): ");
                    cont = scanner.nextInt();
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
                    System.out.print("Do you want to test more?(0/1): ");
                    cont = scanner.nextInt();
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
                    System.out.print("Do you want to test more?(0/1): ");
                    cont = scanner.nextInt();
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
