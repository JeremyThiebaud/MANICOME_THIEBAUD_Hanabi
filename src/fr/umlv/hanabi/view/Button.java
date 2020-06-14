package fr.umlv.hanabi.view;

import java.util.Objects;

public class Button {
    private final Option option;
    private final double x;
    private final double y;
    private final double width;
    private final double height;

    public Button(Option option, double x, double y, double width, double height) {
        this.option = option;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean buttonClicked(double click_x, double click_y) {
        return option.isEnabled() && click_x >= x && click_x <= x + width && click_y >= y && click_y <= y + height;
    }

    @Override
    public String toString() {
        return "Button{" +
                "option=" + option.toString() +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Button button = (Button) o;
        return  x == button.getX() &&
                y == button.getY() &&
                width == button.getWidth() &&
                height == button.getHeight() &&
                option.equals(button.getOption());

    }

    @Override
    public int hashCode() {
        return Objects.hash(option, x, y, width, height);
    }

    public Option getOption() {
        return option;
    }

    public String getValue() {
        return option.getName();
    }

    public double getX() {
        return x;
    }

    public int getXAsInt() {
        return Math.round((float) x);
    }

    public double getY() {
        return y;
    }

    public int getYAsInt() {
        return Math.round((float) y);
    }

    public double getWidth() {
        return width;
    }

    public int getWidthAsInt() {
        return Math.round((float) width);
    }

    public double getHeight() {
        return height;
    }

    public int getHeightAsInt() {
        return Math.round((float) height);
    }

    public boolean isEnabled() {
        return option.isEnabled();
    }
}
