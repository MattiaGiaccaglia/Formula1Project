package it.unicam.cs.formula1.Track;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import it.unicam.cs.formula1.Position.Position;

public class DefaultTrack implements Track {
    private int[][] track;
    private List<Position> start;
    private List<Position> end;

    @Override
    public void loadTrackFromFile(String filePath) throws TrackException, IOException {
        Path of = Path.of(filePath);
        if (!Files.exists(of))
            throw new NoSuchFileException("File not found: " + filePath);
        String content = new String(Files.readAllBytes(of));
        JSONObject jsonObject = new JSONObject(content);
        JSONArray jsonArray = jsonObject.getJSONArray("track");
        track = new int[jsonArray.length()][];
        this.end = new ArrayList<>();
        this.start = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            String row = jsonArray.getString(i);
            track[i] = new int[row.length()];
            for (int j = 0; j < row.length(); j++) {
                track[i][j] = Character.getNumericValue(row.charAt(j));
                if (track[i][j] == 2)
                    this.start.add(new Position(i, j));
                else if (track[i][j] == 3)
                    this.end.add(new Position(i, j));
            }
        }
        if (this.start.isEmpty() || this.end.isEmpty())
            throw new TrackException("Start or finish positions are missing in the track.");
    }

    @Override
    public void printTrack() {
        for (int[] row : track) {
            for (int cell : row)
                System.out.print(cell + " ");
            System.out.println();
        }
    }

    public int[][] getTrack() {
        return track;
    }

    public void setTrack(int[][] track) {
        this.track = track;
    }

    public List<Position> getStart() {
        return start;
    }

    public void setStart(List<Position> start) {
        this.start = start;
    }

    public List<Position> getEnd() {
        return end;
    }

    public void setEnd(List<Position> end) {
        this.end = end;
    }
}


