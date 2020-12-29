package team.unnamed.uboard.builder;

import team.unnamed.uboard.Line;
import team.unnamed.uboard.ScoreboardObjective;

import java.util.List;

public interface ScoreboardBuilder {

    AnimatedScoreboardBuilder animated();

    ScoreboardBuilder setTitle(String title);

    ScoreboardBuilder setStringLines(List<String> lines);

    ScoreboardBuilder setLines(List<Line> lines);

    ScoreboardBuilder addLine(String line);

    ScoreboardBuilder addLine(Line line);

    ScoreboardBuilder addSpace();

    ScoreboardObjective build();
}
