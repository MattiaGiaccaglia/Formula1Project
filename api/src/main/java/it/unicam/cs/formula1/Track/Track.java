package it.unicam.cs.formula1.Track;

import java.io.IOException;

public interface Track {
    void loadTrackFromFile(String filePath) throws IOException, TrackException;
    void printTrack();
}
