package edu.miu.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Rimon Mostafiz
 */
public enum Department {
    Actors("Actors"),
    Editing("Editing"),
    Camera("Camera"),
    Sound("Sound"),
    Writing("Writing"),
    Production("Production"),
    Art("Art"),
    Directing("Directing"),
    CostumeAndMakeUp("Costume & Make-Up"),
    Crew("Crew"),
    VisualEffects("Visual Effects"),
    MusicSupervisor("Music Supervisor"),
    Hairstylist("Hairstylist"),
    UnitProductionManager("Unit Production Manager"),
    Lighting("Lighting");

    private final String title;

    Department(String title) {
        this.title = title;
    }

    @JsonValue
    public String getTitle() {
        return title;
    }
}
