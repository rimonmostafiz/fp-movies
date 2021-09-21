package edu.miu.service;

import edu.miu.model.Genre;
import edu.miu.model.Movie;
import edu.miu.utils.Tuple;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
            movie.getReleaseDate() != null && movie.getReleaseDate().getYear() == year;

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

    //Q1. Top k movies(average rating) in a given year in order
    TriFunction<List<Movie>, Long, Long, List<String>> topKMovies = (movies, year, k) ->
            movies.stream()
                    .filter(movie -> isReleaseYearSame.test(movie, k))
                    .sorted(Comparator.comparingDouble(Movie::getVoteAverage).reversed())
                    .limit(k)
                    .collect(toMap(Movie::getTitle, Movie::getVoteAverage))
                    .entrySet()
                    .stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .map(entry -> String.format("%s(%s)", entry.getKey(), entry.getValue()))
                    .collect(toList());

    //Q2. Top k financially successful movies by a given year in order
    TriFunction<List<Movie>, Long, Long, List<String>> topKSuccessfulMovies = (movies, year, k) ->
            movies.stream()
                    .filter(movie -> isReleaseYearSame.test(movie, k))
                    .sorted(Comparator.comparingLong(totalBenefit::apply).reversed())
                    .limit(k)
                    .collect(toMap(Movie::getTitle, totalBenefit))
                    .entrySet()
                    .stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .map(entry -> String.format("%s -> %s", entry.getKey(), entry.getValue()))
                    .collect(toList());

    //Q3: Top k movies by average rating from each genre in a given year
    Function<List<Movie>, List<String>> getAllGenres = movies ->
            movies.stream()
                    .flatMap(m -> m.getGenres().stream())
                    .map(Genre::getName)
                    .distinct()
                    .collect(Collectors.toList());

    FourFunction<List<Movie>, Long, Integer, String, List<Movie>> topKMovieForGenre = (movies, year, k, genre) ->
            movies.stream()
                    .filter(movie -> isReleaseYearSame.test(movie, year))
                    .filter(movie -> movie.checkGenre(genre))
                    .sorted((m1, m2) -> Double.compare(m2.getVoteAverage(), m1.getVoteAverage()))
                    .limit(k)
                    .collect(Collectors.toList());

    TriFunction<List<Movie>, Long, Integer, List<Tuple<String, List<Movie>>>> topKMovieForEachGenre = (movies, year, k) ->
            getAllGenres.apply(movies)
                    .stream()
                    .map(s -> new Tuple<>(s, topKMovieForGenre.apply(movies, year, k, s)))
                    .collect(Collectors.toList());

    // Q3. Version 2 of Q3
    TriFunction<List<Movie>, Long, Long, Map<String, List<String>>> topKMoviesByGenre = (movies, year, k) ->
            movies.stream()
                    .filter(movie -> movie.getReleaseDate() != null && movie.getReleaseDate().getYear() == year)
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


    //Q4: top k genre by a given year


    //Q5: top k actor appeared in leading role in a given year
    //Q6: top k movie count by country for a given year
    //Q7: movie count by language for a given year
    //Q8: top k movie count by director for a given year
    //Q9: most successful production company for a given year
}
