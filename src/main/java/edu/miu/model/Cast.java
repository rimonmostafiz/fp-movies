package edu.miu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Rimon Mostafiz
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cast {
    @JsonProperty("cast_id")
    private Long castId;
    private String character;
    @JsonProperty("credit_id")
    private String creditId;
    private Gender gender;
    private Long id;
    private String name;
    private Integer order;
}
