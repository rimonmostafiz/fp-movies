package edu.miu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class Credit {
    private Long movieId;
    private String title;
    private List<Cast> castList;
    private List<Crew> crewList;

    public static Credit of(String[] row) {
        Credit credit = new Credit();

        try {
            ObjectMapper mapper = new ObjectMapper();
            credit.setMovieId(Long.valueOf(row[0]));
            credit.setTitle(row[1]);
            credit.setCastList(Arrays.asList(mapper.readValue(row[2], Cast[].class)));
            credit.setCrewList(Arrays.asList(mapper.readValue(row[3], Crew[].class)));
        } catch (Exception ex) {
            System.out.println("Error while creating credit object {}");
            ex.printStackTrace(System.out);
        }

        return credit;
    }
}
