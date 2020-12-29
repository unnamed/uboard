package me.fixeddev.uboard.builder;

import me.fixeddev.uboard.animated.AnimatedLine;
import me.fixeddev.uboard.animated.AnimatedScoreboardObjective;

import java.util.List;

public interface AnimatedScoreboardBuilder extends ScoreboardBuilder {
    AnimatedScoreboardBuilder setTitle(AnimatedLine title);

    AnimatedScoreboardBuilder setAnimatedLines(List<AnimatedLine> lines);

    AnimatedScoreboardBuilder addLine(AnimatedLine line);

    AnimatedScoreboardObjective build();
}
