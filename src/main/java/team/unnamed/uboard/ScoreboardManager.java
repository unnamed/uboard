package team.unnamed.uboard;

import team.unnamed.uboard.builder.ScoreboardBuilder;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Optional;

public interface ScoreboardManager {
    Optional<ScoreboardObjective> getScoreboard(String name);

    default void registerScoreboard(ScoreboardObjective objective, Scoreboard usedScoreboard) {
        registerScoreboard(objective, usedScoreboard, false);
    }

    void registerScoreboard(ScoreboardObjective objective, Scoreboard usedScoreboard, boolean updating);

    void removeScoreboard(ScoreboardObjective objective);

    ScoreboardBuilder newScoreboard(String name);

    ScoreboardBuilder newScoreboard(String name, Scoreboard scoreboard);

    Optional<Scoreboard> getBackingScoreboard(ScoreboardObjective objective);

    ScoreboardManager stopUpdating(ScoreboardObjective scoreboard);

    ScoreboardManager startUpdating(ScoreboardObjective scoreboard);

    ScoreboardManager setToPlayer(Player player, ScoreboardObjective scoreboard);

    ScoreboardManager removeScoreboard(Player player);

    /**
     * Stops the updating tasks for this manager.
     */
    void stop();
}
