package edu.miu.model;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rimon Mostafiz
 */
// Name
// File Name: name.basics.tsv
public class Person {
    private String nconst;
    private String primaryName;
    private LocalDate birthYear;
    private LocalDate deathYear;
    private List<Profession> primaryProfession;
    private List<String> knownForTitle;
}
