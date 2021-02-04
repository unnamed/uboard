package team.unnamed.uboard;

import team.unnamed.uboard.builder.ScoreboardBuilder;
import team.unnamed.uboard.builder.ScoreboardBuilderImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleScoreboardManager implements ScoreboardManager {

    private final Map<String, ScoreboardObjective> objectivesByName;
    private final Map<ScoreboardObjective, Scoreboard> scoreboardsByObjective;
    private final Set<ScoreboardObjective> updatingObjectives;

    private final BukkitTask updateScoreboardTask;
    private final BukkitTask updateTitlesTask;

    public SimpleScoreboardManager(Plugin plugin) {
        objectivesByName = new ConcurrentHashMap<>();
        scoreboardsByObjective = new ConcurrentHashMap<>();
        updatingObjectives = Collections.newSetFromMap(new ConcurrentHashMap<>());

        updateScoreboardTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (ScoreboardObjective updatingObjective : updatingObjectives) {
                updatingObjective.updateScoreboard();
            }
        }, 1, 1);

        updateTitlesTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (ScoreboardObjective updatingObjective : updatingObjectives) {
                updatingObjective.updateTitle();
            }
        }, 1, 1);
    }

    @Override
    public Optional<ScoreboardObjective> getScoreboard(String name) {
        return Optional.ofNullable(objectivesByName.get(name));
    }

    @Override
    public void registerScoreboard(ScoreboardObjective objective, Scoreboard usedScoreboard, boolean updating) {
        if (objectivesByName.putIfAbsent(objective.getName(), objective) != null) {
            throw new IllegalArgumentException("An objective with the name " + objective.getName() + " is already registered!");
        }

        scoreboardsByObjective.put(objective, usedScoreboard);
        
        if (updating) {
            updatingObjectives.add(objective);
        } else {
            objective.updateScoreboard();
        }
    }

    @Override
    public void removeScoreboard(ScoreboardObjective objective) {
        objectivesByName.remove(objective.getName(), objective);
        scoreboardsByObjective.remove(objective);
        updatingObjectives.remove(objective);
    }

    @Override
    public ScoreboardBuilder newScoreboard(String title) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        return newScoreboard(title, scoreboard);
    }

    @Override
    public ScoreboardBuilder newScoreboard(String title, Scoreboard scoreboard) {
        Objects.requireNonNull(title);
        Objects.requireNonNull(scoreboard);

        return new ScoreboardBuilderImpl(title, scoreboard, this);
    }

    @Override
    public Optional<Scoreboard> getBackingScoreboard(ScoreboardObjective objective) {
        return Optional.ofNullable(scoreboardsByObjective.get(objective));
    }

    @Override
    public ScoreboardManager stopUpdating(ScoreboardObjective objective) {
        updatingObjectives.remove(objective);

        return this;
    }

    @Override
    public ScoreboardManager startUpdating(ScoreboardObjective objective) {
        updatingObjectives.add(objective);

        return this;
    }

    @Override
    public ScoreboardManager setToPlayer(Player player, ScoreboardObjective scoreboard) {
        Optional<Scoreboard> optionalBacking = getBackingScoreboard(scoreboard);
        if (!optionalBacking.isPresent()) {
            throw new IllegalArgumentException("The provided scoreboard(" + scoreboard.getName() + ") isn't registered into this manager!");
        }

        Scoreboard backing = optionalBacking.get();
        player.setScoreboard(backing);

        return this;
    }

    @Override
    public ScoreboardManager removeScoreboard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());

        return this;
    }

    @Override
    public void stop() {
        updateTitlesTask.cancel();
        updateScoreboardTask.cancel();
    }
}
