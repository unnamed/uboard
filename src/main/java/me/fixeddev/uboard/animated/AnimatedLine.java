package me.fixeddev.uboard.animated;

import me.fixeddev.uboard.Line;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

public class AnimatedLine {
    private final Deque<Line> frames;

    private final int updateTicks;
    private int currentTick;

    private AnimatedLine(LinkedList<Line> frames, int updateTicks) {
        this.frames = frames;
        this.updateTicks = updateTicks;
        currentTick = 0;
    }

    private AnimatedLine(Deque<Line> frames, int updateTicks) {
        this(new LinkedList<>(frames), updateTicks);
    }


    public static AnimatedLine of(int updateTicks, String... frames) {
        LinkedList<Line> framesList = new LinkedList<>();

        for (String frame : frames) {
            framesList.add(Line.of(frame));
        }

        return new AnimatedLine(framesList, updateTicks);
    }

    public static AnimatedLine of(int updateTicks, Collection<Line> frames) {
        return new AnimatedLine(new LinkedList<>(frames), updateTicks);
    }

    public static AnimatedLine ofStrings(int updateTicks, Collection<String> frames) {
        LinkedList<Line> framesList = new LinkedList<>();

        for (String frame : frames) {
            framesList.add(Line.of(frame));
        }

        return new AnimatedLine(framesList, updateTicks);
    }

    /**
     * Tick(iterate) this animated line 1 time
     *
     * @return If the line should be updated into the next frame.
     */
    public boolean tick() {
        if (++currentTick == updateTicks) {
            currentTick = 0;

            return true;
        }

        return false;
    }

    /**
     * Gets the next update for this AnimatedLine
     *
     * @return The next update for this line.
     */
    public Line next() {
        Line next = frames.poll();
        frames.offer(next);

        return next;
    }

    /**
     * Gets the last update performed to this AnimatedLine
     *
     * @return The last update of this line.
     */
    public Line last() {
        return frames.peekLast();
    }

    /**
     * Gets the current state of this AnimatedLine
     *
     * @return The current state of this line.
     */
    public Line current() {
        return frames.peek();
    }
}
