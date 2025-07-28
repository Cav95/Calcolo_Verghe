package CalcoloTubolare.controller;


import java.util.Optional;

import CalcoloTubolare.model.CalcolatorTubolar;
import CalcoloTubolare.model.CollectorPeace;
import CalcoloTubolare.model.TextOutputFactory;
import CalcoloTubolare.model.TubolarMultiListImpl;
import CalcoloTubolare.model.api.TubolarMultiList;
import CalcoloTubolare.view.View;

/**
 * ControllerModel class that extends SceneControllerImpl to manage the model
 * and view of the application.
 */
public class ControllerModel {

    private View view;
    private Optional<CollectorPeace> collector = Optional.empty();
    
    public Optional<CollectorPeace> getCollector() {
        return collector;
    }

    private TubolarMultiList tubolarList = new TubolarMultiListImpl();

    /**
     * Gets the view of the application.
     * 
     * @return the view of the application.
     */
    public View getView() {
        return view;
    }

    /**
     * Constructor for ControllerModel.
     * 
     * @param mainView the main view of the application.
     */
    public ControllerModel(final View mainView) {
        //super(mainView);
        this.view = mainView;
    }

    /**
     * Gets the collector peace.
     * 
     * @return the collector peace.
     */
    public TubolarMultiList getTubolarList() {
        return tubolarList;
    }

    /**
     * Gets the collector peace.
     * 
     * @return the collector peace.
     */
    public void newTubolarList(final String nameTubolar, final int lengthTubolar, int quantity) {
        try {
            tubolarList.openNewQueue(nameTubolar);
        } catch (IllegalArgumentException e) {
            System.out.println("");
        }

        tubolarList.addTubolar(lengthTubolar, nameTubolar, quantity);
    }

    /**
     * Removes a tubolar from the list.
     * 
     * @param nameTubolar   the name of the tubolar.
     * @param lengthTubolar the length of the tubolar.
     * @param quantity      the quantity of the tubolar.
     */
    public void removeTubolarList(final String nameTubolar, final int lengthTubolar, int quantity) {
        tubolarList.removeTubolar(nameTubolar, lengthTubolar);
    }

    public String tubalarAdded() {
        return TextOutputFactory.tubolarInsertedOutput(tubolarList, collector);
    }

    /**
     * Gets the partial calculation of the tubolar.
     * 
     * @return the collector peace.
     */
    public String partialCalcolateTubolar(final Boolean optimal) {
        return TextOutputFactory.cuttedTubolarReduced(CalcolatorTubolar.calcoloTotal(tubolarList, optimal),
                collector);
    }

    /**
     * Gets the total calculation of the tubolar.
     * 
     * @return the collector peace.
     */
    public String totalCalcolateTubolar(final Boolean optimal) {
        return TextOutputFactory
                .cuttedTubolarExtended(CalcolatorTubolar.calcoloTotal(tubolarList, optimal), collector);
    }

    /**
     * Restarts the tubolar list to an empty state.
     * 
     */
    public void restart() {
        this.tubolarList = new TubolarMultiListImpl();
    }

    /**
     * Adds a tubolar from an Excel file.
     * 
     * @param pathString   the path to the Excel file.
     * @param quantySilo   the quantity of the silo.
     */
    public void addTubolarFromExcel(final String pathString, final Integer quantySilo) {
        this.collector = Optional.of(new CollectorPeace(pathString, quantySilo));
        this.tubolarList = collector.get().getTubolarList();

    }

}
