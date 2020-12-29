package me.fixeddev.uboard.builder;

import me.fixeddev.uboard.BufferedScoreboardObjective;
import me.fixeddev.uboard.Line;
import me.fixeddev.uboard.ScoreboardManager;
import me.fixeddev.uboard.ScoreboardObjective;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class ScoreboardBuilderImpl implements ScoreboardBuilder {

    protected String name;
    protected String title;
    protected List<Line> lines;
    protected Set<String> usedEmptySpaces;

    protected Scoreboard usedScoreboard;

    protected ScoreboardManager manager;

    private static final Random RANDOM = new Random();

    public ScoreboardBuilderImpl(String name, Scoreboard usedScoreboard, ScoreboardManager manager) {
        this.name = name;
        this.manager = manager;

        this.lines = new ArrayList<>();
        usedEmptySpaces = new HashSet<>();

        this.usedScoreboard = usedScoreboard;
    }

    @Override
    public AnimatedScoreboardBuilder animated() {
        return new AnimatedScoreboardBuilderImpl(this);
    }

    @Override
    public ScoreboardBuilder setTitle(String title) {
        Objects.requireNonNull(title);
        this.title = title.length() >= 48 ? title.substring(0, 47) : title;

        return this;
    }

    @Override
    public ScoreboardBuilder setStringLines(List<String> lines) {
        List<Line> lineList = new ArrayList<>();

        Objects.requireNonNull(lines);

        for (String line : lines) {
            lineList.add(Line.of(line));
        }

        return setLines(lineList);
    }

    @Override
    public ScoreboardBuilder setLines(List<Line> lines) {
        this.lines = Objects.requireNonNull(lines);

        return this;
    }

    @Override
    public ScoreboardBuilder addLine(String line) {
        addLine(Line.of(line));

        return this;
    }

    @Override
    public ScoreboardBuilder addLine(Line line) {
        lines.add(line);

        return this;
    }

    @Override
    public ScoreboardBuilder addSpace() {
        int colorIndex = RANDOM.nextInt(17);
        ChatColor color = ChatColor.values()[colorIndex];

        StringBuilder emptySpace = new StringBuilder(ChatColor.COLOR_CHAR + color.toString());

        boolean moreThanSixteen = usedEmptySpaces.size() > 16;

        while (usedEmptySpaces.contains(emptySpace.toString())) {
            if (moreThanSixteen) {
                emptySpace
                        .append(ChatColor.COLOR_CHAR)
                        .append(color.toString());
            }

            colorIndex = RANDOM.nextInt(17);
            color = ChatColor.values()[colorIndex];
        }

        addLine(emptySpace.toString());

        return this;
    }

    @Override
    public ScoreboardObjective build() {
        BufferedScoreboardObjective objective = new BufferedScoreboardObjective(name, usedScoreboard);
        if (title != null) {
            objective.setTitle(title);
        }

        objective.setLines(lines);
        manager.registerScoreboard(objective, usedScoreboard);

        return objective;
    }
}
