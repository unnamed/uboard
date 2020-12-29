package team.unnamed.uboard.builder;

import team.unnamed.uboard.BufferedScoreboardObjective;
import team.unnamed.uboard.Line;
import team.unnamed.uboard.ScoreboardManager;
import team.unnamed.uboard.animated.AnimatedLine;
import team.unnamed.uboard.animated.AnimatedScoreboardObjective;
import team.unnamed.uboard.animated.AnimatedScoreboardObjectiveImpl;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class AnimatedScoreboardBuilderImpl implements AnimatedScoreboardBuilder {

    protected String name;

    protected AnimatedLine title;
    protected List<AnimatedLine> lines;
    protected Set<String> usedEmptySpaces;

    protected Scoreboard usedScoreboard;

    protected ScoreboardManager scoreboardManager;

    private static final Random RANDOM = new Random();

    AnimatedScoreboardBuilderImpl(ScoreboardBuilderImpl builder) {
        name = builder.name;

        usedScoreboard = builder.usedScoreboard;

         if(builder.title != null){
             title = AnimatedLine.of(1, builder.title);
         } else {
             title = AnimatedLine.of(1, name);
         }

        lines = new ArrayList<>();

        for (Line line : builder.lines) {
            lines.add(AnimatedLine.of(1, Collections.singletonList(line)));
        }

        scoreboardManager = builder.manager;

        usedEmptySpaces = new HashSet<>();
    }

    @Override
    public AnimatedScoreboardBuilder setTitle(AnimatedLine title) {
        this.title = Objects.requireNonNull(title);

        return this;
    }

    @Override
    public AnimatedScoreboardBuilder setAnimatedLines(List<AnimatedLine> lines) {
        this.lines = Objects.requireNonNull(lines);

        return this;
    }

    @Override
    public AnimatedScoreboardBuilder addLine(AnimatedLine line) {
        lines.add(Objects.requireNonNull(line));

        return this;
    }

    @Override
    public AnimatedScoreboardBuilder animated() {
        return this;
    }

    @Override
    public ScoreboardBuilder setTitle(String title) {
        return setTitle(AnimatedLine.of(1, title));
    }

    @Override
    public ScoreboardBuilder setStringLines(List<String> lines) {
        List<AnimatedLine> lineList = new ArrayList<>();

        for (String line : lines) {
            lineList.add(AnimatedLine.of(1, line));
        }

        return setAnimatedLines(lineList);
    }

    @Override
    public ScoreboardBuilder setLines(List<Line> lines) {
        List<AnimatedLine> lineList = new ArrayList<>();

        for (Line line : lines) {
            lineList.add(AnimatedLine.of(1, Collections.singletonList(line)));
        }

        return setAnimatedLines(lineList);
    }

    @Override
    public ScoreboardBuilder addLine(String line) {
        return addLine(AnimatedLine.of(1, line));
    }

    @Override
    public ScoreboardBuilder addLine(Line line) {

        return addLine(AnimatedLine.of(1, Collections.singletonList(line)));
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
    public AnimatedScoreboardObjective build() {
        BufferedScoreboardObjective objective = new BufferedScoreboardObjective(name, usedScoreboard);
        AnimatedScoreboardObjective animatedScoreboard = new AnimatedScoreboardObjectiveImpl(objective)
                .setAnimatedLines(lines)
                .setTitle(title);

        scoreboardManager.registerScoreboard(animatedScoreboard, usedScoreboard, true);

        return animatedScoreboard;
    }
}
