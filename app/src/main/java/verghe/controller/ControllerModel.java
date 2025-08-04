package verghe.controller;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import verghe.model.CalcolatorTubolar;
import verghe.model.CollectorPeace;
import verghe.model.TextOutputFactory;
import verghe.model.TubolarMultiListImpl;
import verghe.model.api.ExcludedTubolar;
import verghe.model.api.GroupMerceologiciTubolar;
import verghe.model.api.Peace;
import verghe.model.api.TubolarMultiList;
import verghe.view.View;

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

    public String tubolarAdded() {
        try {
            return TextOutputFactory.tubolarInsertedOutput(tubolarList, collector);
        } catch (Exception e) {
            
        }
        return "";
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
     * @param pathString the path to the Excel file.
     * @param quantySilo the quantity of the silo.
     */
    public void addTubolarFromExcel(final String pathString, final Integer quantySilo) {
        this.collector = Optional.of(new CollectorPeace(pathString, quantySilo));
        this.tubolarList = collector.get().getTubolarList();

    }

    public Stream<Peace> getPeaceStream(Integer numSilo) {
        return this.getCollector().get().getTableSeampleList().stream()
                .filter(h -> Arrays.asList(ExcludedTubolar.values()).stream()
                        .noneMatch(t -> h.code().contains(t.name())))
                .filter(h -> h.lenght() != 0)
                .filter(h -> h.code().contains(GroupMerceologiciTubolar.TBQ.name()))
                .map(h -> new Peace(h.code(), h.description(),
                        sumQuantity(h.code(), h.lenght(), numSilo, collector),
                        h.material(), h.lenght()))
                .distinct();
    }

    private final int sumQuantity(String code, Integer lenght, Integer numSilo,
            Optional<CollectorPeace> collector) {
        return collector.isEmpty() ? 0
                : collector.get().getTableSeampleList().stream()
                        .filter(t -> t.code().equals(code) && t.lenght().equals(lenght))
                        .mapToInt(Peace::quantity).sum();
    }
}
