package controller.listeners;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class PriceBonusHandler implements EventHandler<ActionEvent> {
    private Controller controller;

    public PriceBonusHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void handle(ActionEvent event) {
        controller.priceBonus();
    }
}
