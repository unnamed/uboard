package team.unnamed.uboard.animated;

import team.unnamed.uboard.Line;
import team.unnamed.uboard.ScoreboardObjective;

import java.util.ArrayList;
import java.util.List;

public interface AnimatedScoreboardObjective extends ScoreboardObjective {
    AnimatedScoreboardObjective setAnimatedLines(List<AnimatedLine> lines);

    AnimatedScoreboardObjective setTitle(AnimatedLine line);

    @Override
    AnimatedScoreboardObjective setTitle(String newTitle);

    @Override
    AnimatedScoreboardObjective setLines(List<Line> lines);

    @Override
    default AnimatedScoreboardObjective setStringLines(List<String> lines) {
        List<Line> entries = new ArrayList<>();

        for (String line : lines) {
            entries.add(Line.of(line));
        }

        return setLines(entries);
    }
}
