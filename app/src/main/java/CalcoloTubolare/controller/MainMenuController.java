package CalcoloTubolare.controller;

import CalcoloTubolare.controller.scene.SceneControllerImpl;
import CalcoloTubolare.model.*;
import CalcoloTubolare.model.api.MultiQueue;
import CalcoloTubolare.view.View;

public class MainMenuController extends SceneControllerImpl {
    
    public MainMenuController(View mainView) {
        super(mainView);
    }

    MultiQueue<Integer, String> tubolarList = new MultiQueueImpl<>();



    public MultiQueue<Integer, String> getTubolarList() {
        return tubolarList;
    }

    public void newTubolarList(String nameTubolar, int lengthTubolar, int quantity) {
        try {
            tubolarList.openNewQueue(nameTubolar);
        } catch (IllegalArgumentException e) {
            System.out.println("");
        }

        tubolarList.addTubolar(lengthTubolar, nameTubolar, quantity);
    }

    public void removeTubolarList(String nameTubolar, int lengthTubolar, int quantity) {
        tubolarList.removeTubolar(nameTubolar, lengthTubolar);
    }

    public String partialCalco() {
        return CalcolatorTubolar

                .printCuttedTubolarSmoll(CalcolatorTubolar.calcoloTotal(tubolarList.getMultiQueue()));
    }

    public String totalCalco() {
        return CalcolatorTubolar
                .printCuttedTubolar(CalcolatorTubolar.calcoloTotal(tubolarList.getMultiQueue()));
    }

    public void restart() {
        this.tubolarList = new MultiQueueImpl<>();
    }

}
