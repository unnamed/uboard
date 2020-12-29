package team.unnamed.uboard.animated;

import team.unnamed.uboard.Line;
import team.unnamed.uboard.ScoreboardObjective;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AnimatedScoreboardObjectiveImpl implements AnimatedScoreboardObjective {
    private final ScoreboardObjective objective;
    private List<AnimatedLine> lines;
    private AnimatedLine title;

    public AnimatedScoreboardObjectiveImpl(ScoreboardObjective objective) {
        this.objective = objective;

        lines = new ArrayList<>();
        title = AnimatedLine.of(1, objective.getTitle());
    }

    @Override
    public String getName() {
        return objective.getName();
    }

    @Override
    public String getTitle() {
        return objective.getTitle();
    }

    @Override
    public AnimatedScoreboardObjective setTitle(String newTitle) {
        return setTitle(AnimatedLine.of(1, newTitle));
    }

    @Override
    public List<Line> getLines() {
        return objective.getLines();
    }

    @Override
    public AnimatedScoreboardObjective setLines(List<Line> lines) {
        List<AnimatedLine> animatedLines = new ArrayList<>();

        for (Line line : lines) {
            animatedLines.add(AnimatedLine.of(1, Collections.singletonList(line)));
        }

        return this.setAnimatedLines(animatedLines);
    }

    @Override
    public void updateScoreboard() {
        List<Line> currentLines = new ArrayList<>();

        for (AnimatedLine line : lines) {
            if (line.tick()) {
                currentLines.add(line.next());
            } else {
                currentLines.add(line.current());
            }
        }

        objective.setLines(currentLines);
        objective.updateScoreboard();
    }

    @Override
    public void updateTitle() {
        if (title.tick()) {
            Line next = title.next();

            objective.setTitle(next.getPrefix() + next.getName() + next.getSuffix());
        }

        objective.updateTitle();
    }

    @Override
    public AnimatedScoreboardObjective setAnimatedLines(List<AnimatedLine> lines) {
        this.lines = Objects.requireNonNull(lines);

        return this;
    }

    @Override
    public AnimatedScoreboardObjective setTitle(AnimatedLine line) {
        this.title = Objects.requireNonNull(line);

        return this;
    }
}
