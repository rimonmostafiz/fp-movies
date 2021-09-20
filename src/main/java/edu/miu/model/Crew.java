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
public class Crew {
    @JsonProperty("credit_id")
    private String creditId;
    private Department department;
    private Gender gender;
    private Long id;
    private String job;
    private String name;
}
