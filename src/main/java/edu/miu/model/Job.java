package edu.miu.model;

/**
 * @author Rimon Mostafiz
 */
public enum Job {
    Editor("Editor"),
    ProductionDesign("Production Design"),
    SoundDesigner("Sound Designer"),
    SupervisingSoundEditor("Supervising Sound Editor"),
    Casting("Casting"),
    OriginalMusicComposer("Original Music Composer"),
    Director("Director"),
    Writer("Writer"),
    Producer("Producer"),
    Screenplay("Screenplay"),
    ArtDirection("Art Direction"),
    VisualEffectsProducer("Visual Effects Producer"),
    SupervisingArtDirector("Supervising Art Director"),
    MusicEditor("Music Editor"),
    SoundEffectsEditor("Sound Effects Editor"),
    Foley("Foley"),
    CostumeDesign("Costume Design"),
    SetDecoration("Set Decoration"),
    SetDesigner("Set Designer"),
    ExecutiveProducer("Executive Producer"),
    DirectorOfPhotography("Director of Photography");

    private String title;

    Job(String title) {
        this.title = title;
    }

}
