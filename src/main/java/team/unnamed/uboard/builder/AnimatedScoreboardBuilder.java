package team.unnamed.uboard.builder;

import team.unnamed.uboard.animated.AnimatedLine;
import team.unnamed.uboard.animated.AnimatedScoreboardObjective;

import java.util.List;

public interface AnimatedScoreboardBuilder extends ScoreboardBuilder {
    AnimatedScoreboardBuilder setTitle(AnimatedLine title);

    AnimatedScoreboardBuilder setAnimatedLines(List<AnimatedLine> lines);

    AnimatedScoreboardBuilder addLine(AnimatedLine line);

    AnimatedScoreboardObjective build();
}
