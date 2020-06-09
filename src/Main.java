import fr.umlv.hanabi.controller.GameController;
import fr.umlv.hanabi.view.ZenView;

public class Main {
    public static void main(String[] args) {
        GameController g = new GameController(new ZenView());
        g.menu();
    }
}