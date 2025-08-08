package verghe.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import org.javatuples.Pair;

import verghe.model.api.Tubolar;
import verghe.model.api.TubolarMultiList;

/**
 * CalcolatorTubolar class for calculating the optimal cutting of tubulars.
 */
public class CalcolatorTubolar {

    private static final int STARDARD_TUB = 6000;
    private static final int EXTENDED_TUB = 12000;
    private static final int LAMA = 2;

    HashMap<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> mapCut;

    /**
     * Factory method to create an instance of CalcolatorTubolar.
     * 
     * @param tubolarlist the TubolarMultiList containing the tubulars
     * @param optimal     whether to calculate optimally or not
     * @return an instance of CalcolatorTubolar
     */
    public HashMap<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> getMapCut() {
        return mapCut;
    }

    public CalcolatorTubolar(TubolarMultiList tubolarlist, Boolean optimal) {
        this.mapCut = tubolarResultMap(tubolarlist, optimal);
    }

    /**
     * Calculates the total cutting of tubulars based on the provided
     * TubolarMultiList.
     * 
     * @param tubolarlist the TubolarMultiList containing the tubulars
     * @param optimal     whether to calculate optimally or not
     * @return a HashMap containing the cutting results
     */
    private HashMap<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> tubolarResultMap(
            TubolarMultiList tubolarlist, Boolean optimal) {
        HashMap<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> mapCut = new HashMap<>();
        var multiQueue = tubolarlist.getMultiQueue();

        if (optimal) {
            for (var elemEntry : multiQueue.entrySet()) {
                mapCut.put(elemEntry.getKey(), calcoloVergaOttimale(multiQueue, elemEntry.getKey()));
            }

        } else {
            for (var elemEntry : multiQueue.entrySet()) {
                mapCut.put(elemEntry.getKey(), listOfTubolarByLenght(multiQueue.get(elemEntry.getKey()), STARDARD_TUB));
            }
        }

        return mapCut;
    }

    /**
     * Calculates the optimal cutting of tubulars based on the provided multiQueue
     * and queue.
     * 
     * @param mapTubolar  the multiQueue containing the tubulars
     * @param codeTubolar the queue to be processed
     * @return a LinkedList containing the cutting results
     */
    private LinkedList<Pair<Integer, LinkedList<Integer>>> calcoloVergaOttimale(
            HashMap<String, Set<Tubolar>> mapTubolar, String codeTubolar) {

        var listShort = listOfTubolarByLenght(mapTubolar.get(codeTubolar), STARDARD_TUB);
        var listLong = listOfTubolarByLenght(mapTubolar.get(codeTubolar), EXTENDED_TUB);
        return listShort.size() <= (listLong.size() * (EXTENDED_TUB / STARDARD_TUB)) ? listShort : listLong;
    }

    /**
     * Creates a list of tubulars based on their lengths from the provided set.
     * 
     * @param tempList the set of tubulars
     * @param lenght   the length to be used for cutting
     * @return a LinkedList containing pairs of length and a list of tubulars
     */
    private LinkedList<Pair<Integer, LinkedList<Integer>>> listOfTubolarByLenght(Set<Tubolar> tempList,
            int lenght) {
        LinkedList<Pair<Integer, LinkedList<Integer>>> listList = new LinkedList<>();

        Set<Tubolar> temp = new TreeSet<>((o1, o2) -> o2.getLenght() - o1.getLenght());

        for (Tubolar elemTubolar : tempList) {
            temp.add(new Tubolar(elemTubolar.getLenght(), elemTubolar.getQuantity()));
        }

        while (temp.stream().mapToInt(Tubolar::getQuantity).sum() != 0) {

            int total = lenght;

            LinkedList<Integer> list = new LinkedList<>();
            Pair<Integer, LinkedList<Integer>> pairList = new Pair<>(
                    Integer.valueOf(lenght), list);
            for (var elem : temp) {

                while (elem.getQuantity() > 0 && (total - elem.getLenght()) >= 0) {
                    list.add(elem.getLenght());
                    elem.setQuantity(elem.getQuantity() - 1);
                    total = total - elem.getLenght() - LAMA;
                }
            }
            listList.add(pairList);
        }
        return listList;
    }
}
