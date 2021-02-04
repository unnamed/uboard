package team.unnamed.uboard;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BufferedScoreboardObjective implements ScoreboardObjective {

    private final String name;

    private String title;

    private List<Line> lines;

    private boolean updated;

    private final Scoreboard scoreboard;

    private Objective objective;
    private Objective buffer;

    public BufferedScoreboardObjective(String name, Scoreboard scoreboard) {
        this.name = name;
        this.title = name;

        lines = new ArrayList<>();

        this.scoreboard = scoreboard;

        objective = scoreboard.registerNewObjective("main", "dummy");
        buffer = scoreboard.registerNewObjective("buffer", "dummy");
    }

    @Override
    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public ScoreboardObjective setTitle(String newTitle) {
        Objects.requireNonNull(newTitle);

        if (title.equals(newTitle)) {
            return this;
        }

        title = newTitle.length() < 48 ? newTitle : newTitle.substring(0, 48);
        return this;
    }

    public List<Line> getLines() {
        return lines;
    }

    public ScoreboardObjective setLines(List<Line> lines) {
        Objects.requireNonNull(lines);

        for (Line line : this.lines) {
            Team team = scoreboard.getTeam(line.getName());

            if (team != null) {
                team.removeEntry(line.getName());
            }

            scoreboard.resetScores(line.getName());
        }


        this.lines = lines;

        int index = lines.size() - 1;
        for (Line line : lines) {
            Team team = scoreboard.getTeam(line.getName());

            if (team == null) {
                team = scoreboard.registerNewTeam(line.getName());
            }

            // Team entry adding
            if (!team.hasEntry(line.getName())) {
                team.addEntry(line.getName());
            }

            team.setDisplayName(line.getName());

            // Team Suffix and prefix adding
            team.setPrefix(line.getPrefix());
            team.setSuffix(line.getSuffix());

            // Team set score
            buffer.getScore(line.getName()).setScore(index-- + 1);
        }

        updated = true;
        return this;
    }

    public void updateScoreboard() {
        if (!updated) {
            return;
        }

        Objective oldMain = objective;

        objective = buffer;
        buffer = oldMain;

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        updateTitle();

        updated = false;
    }

    public void updateTitle() {
        objective.setDisplayName(title);
    }
}
