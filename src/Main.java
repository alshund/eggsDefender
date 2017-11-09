import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.BattleLogic;
import view.BattleField;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BattleLogic battleLogic = new BattleLogic();
        Controller controller = new Controller(battleLogic);
        BattleField battleField = new BattleField(primaryStage, controller);
    }
}
