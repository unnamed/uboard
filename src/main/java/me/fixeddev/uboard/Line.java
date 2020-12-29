package me.fixeddev.uboard;

import java.util.Objects;

public class Line {
    private final String prefix;
    private final String name;
    private final String suffix;

    public Line(String prefix, String name, String suffix) {
        this.prefix = prefix;
        this.name = name;
        this.suffix = suffix;
    }

    public static Line of(String string) {
        Objects.requireNonNull(string, "The frame content can't be null!");

        if (string.length() < 16) {
            return new Line("", string, "");
        } else if (string.length() < 32) {
            return new Line(string.substring(0, 15), string.substring(15), "");
        } else if (string.length() < 48) {
            return new Line(string.substring(0, 15), string.substring(15, 31), string.substring(31));
        }

        return of(string.substring(47));
    }

    public String getPrefix() {
        return prefix;
    }

    public String getName() {
        return name;
    }

    public String getSuffix() {
        return suffix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line)) return false;
        Line line = (Line) o;
        return Objects.equals(prefix, line.prefix) &&
                Objects.equals(name, line.name) &&
                Objects.equals(suffix, line.suffix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefix, name, suffix);
    }
}
