package edu.miu.model;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rimon Mostafiz
 */
// File Name: title.basics.tsv
public class Title {
    private String tconst;
    private TitleType titleType;
    private String primaryTitle;
    private String originalTitle;
    private boolean isAdult;
    private LocalDate startYear;
    private LocalDate endYear;
    private Long runtimeMinutes;
    private List<Genre> genres;
    private List<AlsoKnownAs> alsoKnownAsList;
}
