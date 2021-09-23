package edu.miu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * @author Rimon Mostafiz
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    private Long budget;
    private List<Genre> genres;
    private String homepage;
    private Long id;
    private List<Keyword> keywords;
    private String originalLanguage;
    private String originalTitle;
    private String overView;
    private Double popularity;
    private List<ProductionCompany> productionCompanies;
    private List<ProductionCountry> productionCountries;
    private LocalDate releaseDate;
    private Long revenue;
    private Double runtime;
    private List<SpokenLanguage> spokenLanguages;
    private Status status;
    private String tagline;
    private String title;
    private Double voteAverage;
    private Integer voteCount;

    public static Movie of(String[] row) {
        Movie movie = new Movie();

        try {
            ObjectMapper mapper = new ObjectMapper();
            movie.setBudget(Long.valueOf(row[0].trim()));
            movie.setGenres(Arrays.asList(mapper.readValue(row[1].trim(), Genre[].class)));
            movie.setHomepage(row[2].trim());
            movie.setId(Long.valueOf(row[3].trim()));
            movie.setKeywords(Arrays.asList(mapper.readValue(row[4].trim(), Keyword[].class)));
            movie.setOriginalLanguage(row[5].trim());
            movie.setOriginalTitle(row[6].trim());
            movie.setOverView(row[7].trim());
            movie.setPopularity(Double.valueOf(row[8].trim()));
            movie.setProductionCompanies(Arrays.asList(mapper.readValue(row[9].trim(), ProductionCompany[].class)));
            movie.setProductionCountries(Arrays.asList(mapper.readValue(row[10].trim(), ProductionCountry[].class)));
            movie.setReleaseDate(StringUtils.isNotBlank(row[11]) ? LocalDate.parse(row[11].trim()) : null);
            movie.setRevenue(StringUtils.isNotBlank(row[12]) ? Long.valueOf(row[12].trim()) : null);
            movie.setRuntime(StringUtils.isNotBlank(row[13]) ? Double.valueOf(row[13].trim()) : null);
            movie.setSpokenLanguages(Arrays.asList(mapper.readValue(row[14].trim(), SpokenLanguage[].class)));
            movie.setStatus(Status.getStatus(row[15].trim()));
            movie.setTagline(row[16].trim());
            movie.setTitle(row[17].trim());
            movie.setVoteAverage(Double.valueOf(row[18].trim()));
            movie.setVoteCount(Integer.valueOf(row[19].trim()));
        } catch (Exception ex) {
            System.out.println("Error while creating movie object");
            ex.printStackTrace(System.out);
        }

        return movie;
    }

    public Boolean checkGenre(String genre) {
        for (Genre value : genres) {
            if (value.getName().equals(genre))
                return true;
        }
        return false;
    }

}
