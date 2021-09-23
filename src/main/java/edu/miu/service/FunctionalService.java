package edu.miu.service;

import edu.miu.model.*;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * @author Rimon Mostafiz
 */
public interface FunctionalService {

    Function<Movie, Long> totalBenefit = movie -> movie.getRevenue() - movie.getBudget();

    BiPredicate<Movie, Long> isReleaseYearSame = (movie, year) ->
            movie.getStatus() == Status.Released &&
                    movie.getReleaseDate() != null && movie.getReleaseDate().getYear() == year;

    //Q1. Top k high rated movies in a given year by order
    TriFunction<List<Movie>, Long, Long, List<String>> topKMovies = (movies, year, k) ->
            movies.stream()
                    .filter(movie -> isReleaseYearSame.test(movie, year))
                    .sorted(Comparator.comparingDouble(Movie::getVoteAverage).reversed())
                    .limit(k)
                    .collect(toMap(Movie::getTitle, Movie::getVoteAverage))
                    .entrySet()
                    .stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .map(entry -> String.format("%s(%s)", entry.getKey(), entry.getValue()))
                    .collect(toList());

    //Q2. Top k financially successful movies by a given year by order
    TriFunction<List<Movie>, Long, Long, List<String>> topKSuccessfulMovies = (movies, year, k) ->
            movies.stream()
                    .filter(movie -> isReleaseYearSame.test(movie, year))
                    .sorted(Comparator.comparingLong(totalBenefit::apply).reversed())
                    .limit(k)
                    .collect(toMap(Movie::getTitle, totalBenefit))
                    .entrySet()
                    .stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .map(entry -> String.format("%s -> %s", entry.getKey(), entry.getValue()))
                    .collect(toList());

    //Q3: K top rated movies of each genre in a given year
    BiFunction<Movie, List<Genre>, List<ImmutablePair<Genre, Movie>>> movieGenrePairGenerator = (movie, genres) ->
            genres.stream()
                    .map(genre -> ImmutablePair.of(genre, movie))
                    .collect(Collectors.toList());

    BiFunction<ImmutablePair<String, List<Movie>>, Long, List<Movie>> sortListOfMovieByAverageRating = (pair, k) ->
            Stream.of(pair)
                    .flatMap(p -> p.getRight().stream())
                    .sorted(Comparator.comparingDouble(Movie::getVoteAverage).reversed())
                    .limit(k)
                    .collect(toList());

    Function<List<Movie>, List<String>> convertListOfMoviesToPairOfNameAndRating = movies ->
            movies.stream()
                    .map(m -> String.format("%s(%s)", m.getTitle(), m.getVoteAverage()))
                    .collect(toList());

    TriFunction<List<Movie>, Long, Long, Map<String, List<String>>> topKMoviesByGenre = (movies, year, k) ->
            movies.stream()
                    .filter(movie -> isReleaseYearSame.test(movie, year))
                    .collect(toMap(Function.identity(), Movie::getGenres))
                    .entrySet()
                    .stream()
                    .map(entry -> movieGenrePairGenerator.apply(entry.getKey(), entry.getValue()))
                    .flatMap(Collection::stream)
                    .collect(groupingBy(entry -> entry.getLeft().getName(), Collectors.mapping(ImmutablePair::getRight, toList())))
                    .entrySet()
                    .stream()
                    .map(entry -> ImmutablePair.of(entry.getKey(), sortListOfMovieByAverageRating.apply(ImmutablePair.of(entry.getKey(), entry.getValue()), k)))
                    .collect(toMap(ImmutablePair::getLeft, ip -> convertListOfMoviesToPairOfNameAndRating.apply(ip.getRight())));

    //Q4: K most successful production company in a given year
    Function<List<Movie>, Long> totalRevenue = movies -> movies.stream()
            .map(Movie::getRevenue)
            .reduce(0L, Long::sum);

    BiFunction<List<ProductionCompany>, Movie, List<ImmutablePair<ProductionCompany, Movie>>> companyMoviesPair
            = (companies, movie) -> companies.stream()
            .map(pc -> ImmutablePair.of(pc, movie))
            .collect(Collectors.toList());

    TriFunction<List<Movie>, Long, Long, List<ImmutablePair<String, Long>>> mostSuccessfulKProductionCompany = (movies, year, k) ->
            movies.stream()
                    .filter(movie -> isReleaseYearSame.test(movie, year))
                    .collect(toMap(Function.identity(), Movie::getProductionCompanies))
                    .entrySet()
                    .stream()
                    .map(entry -> companyMoviesPair.apply(entry.getValue(), entry.getKey()))
                    .flatMap(Collection::stream)
                    .collect(groupingBy(entry -> entry.getLeft().getName(), Collectors.mapping(ImmutablePair::getRight, toList())))
                    .entrySet()
                    .stream()
                    .map(entry -> ImmutablePair.of(entry.getKey(), totalRevenue.apply(entry.getValue())))
                    .sorted((pair1, pair2) -> pair2.getRight().compareTo(pair1.getRight()))
                    .limit(k)
                    .collect(toList());

    //Q5. Most popular movie of each genre in a given year
    Function<List<Movie>, Optional<Movie>> mostPopularMovie = movies ->
            movies.stream()
                    .max((m1, m2) -> Double.compare(m2.getPopularity(), m1.getPopularity()));

    TriFunction<List<Movie>, Long, Long, List<String>> kMostPopularMovieInAGivenYear = (movies, year, k) ->
            movies.stream()
                    .filter(movie -> isReleaseYearSame.test(movie, year))
                    .collect(toMap(Function.identity(), Movie::getGenres))
                    .entrySet()
                    .stream()
                    .map(entry -> movieGenrePairGenerator.apply(entry.getKey(), entry.getValue()))
                    .flatMap(Collection::stream)
                    .collect(groupingBy(entry -> entry.getLeft().getName(), Collectors.mapping(ImmutablePair::getRight, toList())))
                    .entrySet()
                    .stream()
                    .map(entry -> ImmutablePair.of(entry.getKey(), mostPopularMovie.apply(entry.getValue())))
                    .map(pair -> String.format("%s -> %s [%s]", pair.getLeft(), pair.getRight().isPresent() ? pair.getRight().get().getOriginalTitle() : "", pair.getRight().isPresent() ? pair.getRight().get().getPopularity() : "0"))
                    .collect(Collectors.toList());

    //Q6: Movie count by spoken language in a given year
    BiFunction<Movie, List<SpokenLanguage>, List<ImmutablePair<SpokenLanguage, Movie>>> movieSpokenLanguagePairGenerator = (movie, spokenLanguages) ->
            spokenLanguages.stream()
                    .map(spokenLanguage -> ImmutablePair.of(spokenLanguage, movie))
                    .collect(Collectors.toList());

    BiFunction<List<Movie>, Long, List<ImmutablePair<String, Integer>>> movieCountByLanguageInGivenYear = (movies, year) ->
            movies.stream()
                    .filter(movie -> isReleaseYearSame.test(movie, year))
                    .collect(toMap(Function.identity(), Movie::getSpokenLanguages))
                    .entrySet()
                    .stream()
                    .map(entry -> movieSpokenLanguagePairGenerator.apply(entry.getKey(), entry.getValue()))
                    .flatMap(Collection::stream)
                    .collect(groupingBy(entry -> entry.getLeft().getName(), Collectors.mapping(ImmutablePair::getRight, toList())))
                    .entrySet()
                    .stream()
                    .collect(toMap(Map.Entry::getKey, entry -> entry.getValue().size()))
                    .entrySet()
                    .stream()
                    .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                    .map(entry -> ImmutablePair.of(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());

    //Q7: top k genre by a given year
    //Q8: top k actor appeared in leading role in a given year
    //Q9: top k movie count by country for a given year
    //Q10: top k movie count by director for a given year
    //Q11: in a given year how many movies were released by given content rating
}
