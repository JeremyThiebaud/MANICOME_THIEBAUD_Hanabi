import fr.umlv.hanabi.controller.GameController;
import fr.umlv.hanabi.view.View;
import fr.umlv.hanabi.view.ZenView;

public class Main {
    public static void main(String[] args) {
        View view = new ZenView();
        GameController g = new GameController(view);
        g.menu();
    }
}