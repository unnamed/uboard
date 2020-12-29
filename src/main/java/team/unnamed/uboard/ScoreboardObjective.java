package team.unnamed.uboard;

import java.util.ArrayList;
import java.util.List;

public interface ScoreboardObjective {

    String getName();

    /**
     * Gets the current title for this ScoreboardObjective, not necessarily the one that's being displayed.
     *
     * @return A String containing the title of this ScoreboardObjective.
     */
    String getTitle();

    /**
     * Sets the title for this ScoreboardObjective without actually displaying it to the player.
     *
     * @param newTitle The new title for this ScoreboardObjective with a maximum of 48 characters, otherwise it will be truncated.
     * @return The same ScoreboardObjective instance.
     * @see ScoreboardObjective#updateTitle() Use it to display it to the player.
     */
    ScoreboardObjective setTitle(String newTitle);

    List<Line> getLines();

    ScoreboardObjective setLines(List<Line> lines);

    default ScoreboardObjective setStringLines(List<String> lines) {
        List<Line> entries = new ArrayList<>();

        for (String line : lines) {
            entries.add(Line.of(line));
        }

        return setLines(entries);
    }

    void updateScoreboard();

    void updateTitle();

}
