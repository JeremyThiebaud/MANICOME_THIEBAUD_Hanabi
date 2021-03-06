package fr.umlv.hanabi.view;

import fr.umlv.hanabi.model.Board;
import fr.umlv.hanabi.model.CardColor;
import fr.umlv.hanabi.model.Player;
import fr.umlv.hanabi.model.Reserve;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ZenView extends View implements ViewInterface {
    private ApplicationContext context;
    private float height;
    private float width;

    public ZenView() {
        Application.run(Color.BLACK, context -> {
            this.context = context;
            height = context.getScreenInfo().getHeight();
            width = context.getScreenInfo().getWidth();
        });
    }

    @Override
    public void displayChoice(Reserve r) {
        HashMap<String, Boolean> options = new HashMap<>();
        options.put("Engage a card", true);
        options.put("Give a tip", r.getBlueTokens() > 0);
        options.put("Discard", r.getBlueTokens() < 8);

        List <Button> buttons = createButtons(options);
        buttons.forEach(System.out::println);
        drawButtons(buttons);
        Button clicked = listenButton(buttons);
        System.out.println(clicked.getValue());
    }

    @Override
    public void displayBoard(Board b) {

    }

    @Override
    public void displayHand(Player p) {

    }

    @Override
    public void displayReserve(Reserve r) {

    }

    @Override
    public void displayTips(CardColor cardColor, Player player) {

    }

    @Override
    public void displayTips(int value, Player player) {

    }

    public void displayTips(List<Integer> indexes, StringBuilder builder) {

    }

    @Override
    public void displayValueOrColor() {

    }

    @Override
    public void displayText(String text) {

    }

    @Override
    public void displayPlayers(List<Player> players, int playerIndex) {

    }

    @Override
    public void displayOptions(List values) {

    }

    @Override
    public void displayIndexError(int min, int max, List<Integer> exceptions) {

    }

    public List<Button> createButtons(HashMap<String, Boolean> options) {
        AtomicInteger i = new AtomicInteger();
        double x = this.width / 4;
        double height = this.height / (3 * options.size()); // one third of screen for buttons
        double yOffset = 2 * this.height / (3 * (options.size() + 1)); // remaining blank space
        double width = this.width / 2;

        List<Button> buttons = new ArrayList<>();

        options.forEach((k, v) -> {
            double y = i.get() * (yOffset + height) + yOffset;
            buttons.add(new Button(k, x, y, width, height, v));
            i.getAndIncrement();
        });

        return buttons;
    }

    public void drawButtons(List<Button> buttons) {
        context.renderFrame(graphics2D -> {
            buttons.forEach( b -> {
                graphics2D.setColor(Color.ORANGE);

                if (!b.isEnabled())
                    graphics2D.setColor(Color.LIGHT_GRAY);

                graphics2D.draw3DRect(b.getXAsInt(), b.getYAsInt(), b.getWidthAsInt(), b.getHeightAsInt(), b.isEnabled());
                graphics2D.drawString(b.getValue().toString(), this.width / 2, (float) (b.getY() + b.getHeight() / 2));
            });
        });
    }

    public Button listenButton(List<Button> buttons) {
        boolean noneClicked = false;
        Button button = null;

        while(!noneClicked) {
            Event event = context.pollEvent();
            if (event == null || event.getAction() != Event.Action.POINTER_DOWN)
                continue;

            Point2D point = event.getLocation();

            for (Button b : buttons) {
                if (b.buttonClicked(point.getX(), point.getY())) {
                    noneClicked = true;
                    button = b;
                }
            }
        }

        return button;
    }
}
