package CalcoloTubolare.controller;

import CalcoloTubolare.controller.scene.SceneControllerImpl;
import CalcoloTubolare.model.CalcolatorTubolar;
import CalcoloTubolare.model.CollectorPeace;
import CalcoloTubolare.model.TextOutputFactory;
import CalcoloTubolare.model.TubolarMultiListImpl;
import CalcoloTubolare.model.api.TubolarMultiList;
import CalcoloTubolare.view.View;

public class ControllerModel extends SceneControllerImpl {

    View view;

    public View getView() {
        return view;
    }

    public ControllerModel(View mainView) {
        super(mainView);
        this.view = mainView;
    }

    TubolarMultiList tubolarList = new TubolarMultiListImpl();

    public TubolarMultiList getTubolarList() {
        return tubolarList;
    }

    public void newTubolarList(String nameTubolar, int lengthTubolar, int quantity) {
        try {
            tubolarList.openNewQueue(nameTubolar);
        } catch (IllegalArgumentException e) {
            System.out.println("");
        }

        tubolarList.addTubolar(lengthTubolar,nameTubolar,  quantity);
    }

    public void removeTubolarList(String nameTubolar, int lengthTubolar, int quantity) {
        tubolarList.removeTubolar(nameTubolar, lengthTubolar);
    }

    public String partialCalcolateTubolar(Boolean optimal) {
        return TextOutputFactory.printCuttedTubolarSmoll(CalcolatorTubolar.calcoloTotal(tubolarList,optimal));
    }

    public String totalCalcolateTubolar(Boolean optimal) {
        return TextOutputFactory
                .printCuttedTubolar(CalcolatorTubolar.calcoloTotal(tubolarList,optimal));
    }

    public void restart() {
        this.tubolarList = new TubolarMultiListImpl();
    }

    public void addTubolarFromExcel(final String pathString , final Integer quantySilo) {

        this.tubolarList = new CollectorPeace(pathString , quantySilo).getTubolarList();

    }

}
