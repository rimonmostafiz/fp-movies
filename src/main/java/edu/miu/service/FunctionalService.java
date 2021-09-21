package edu.miu.service;

import edu.miu.model.Genre;
import edu.miu.model.Movie;
import edu.miu.utils.Tuple;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Rimon Mostafiz
 */
public interface FunctionalService {

    Function<Movie, Long> benefit = movie -> movie.getRevenue() - movie.getBudget();

    //Q1. Top K Movies by a given year
    TriFunction<List<Movie>, Long, Long, Map<String, Double>> topKMovies = (movies, year, k) ->
            movies.stream()
                    .filter(movie -> movie.getReleaseDate() != null && movie.getReleaseDate().getYear() == year)
                    .sorted(Comparator.comparingDouble(Movie::getVoteAverage).reversed())
                    .limit(k)
                    .collect(Collectors.toMap(Movie::getTitle, Movie::getVoteAverage));

    //Q2. Top K successful Movies by a given year
    TriFunction<List<Movie>, Long, Long, Map<String, Long>> topKSuccessfulMovies = (movies, year, k) ->
            movies.stream()
                    .filter(movie -> movie.getReleaseDate() != null && movie.getReleaseDate().getYear() == year)
                    .sorted(Comparator.comparingLong(benefit::apply).reversed())
                    .limit(k)
                    .collect(Collectors.toMap(Movie::getTitle, benefit));

    //Q3: top k movies from each genre in a given year

    FourFunction<List<Movie>,Long,Integer,String,List<Movie>> topKMovieForGenre=(movies,year,k,genre)->
            movies.stream()
                    .filter(m ->  m.getReleaseDate().getYear() == year)
                    .filter(m->m.checkGenre(genre))
                    .sorted((m1,m2)-> (int) -(m1.getVoteAverage()-m2.getVoteAverage()))
                    .limit(3)
                    .collect(Collectors.toList());
    Function<List<Movie>,List<String>> getAllGenres=movies->movies.stream()
            .flatMap(m->m.getGenres().stream())
            .map(g->g.getName())
            .distinct()
            .collect(Collectors.toList());
    TriFunction<List<Movie>,Long,Integer,List<Tuple<String,List<Movie>>>> topKMovieForEachGenre=(movies,year,k)->
              getAllGenres.apply(movies).stream().map(s->new Tuple<String,List<Movie>>(s,topKMovieForGenre.apply(movies,year,k,s)))
                      .collect(Collectors.toList());

    //Q4: top k genre by a given year


    //Q5: top k actor appeared in leading role in a given year
    //Q6: top k movie count by country for a given year
    //Q7: movie count by language for a given year
    //Q8: top k movie count by director for a given year

}
