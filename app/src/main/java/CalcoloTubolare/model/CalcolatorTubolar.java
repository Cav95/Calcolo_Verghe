package CalcoloTubolare.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.javatuples.Pair;

import CalcoloTubolare.model.api.Tubolar;
import CalcoloTubolare.model.api.TubolarMultiList;

public class CalcolatorTubolar {

    private static final int STARDARD_TUB = 6000;
    private static final int EXTENDED_TUB = 12000;
    private static final int LAMA = 2;

    public static HashMap<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> calcoloTotal(
            TubolarMultiList tubolarlist , Boolean optimal) {
        HashMap<String ,LinkedList<Pair<Integer, LinkedList<Integer>>> > mapCut = new HashMap<>();
        var multiQueue= tubolarlist.getMultiQueue();

        if (optimal){
            for (Entry<String, Set<Tubolar>> elemEntry : multiQueue.entrySet()) {
            mapCut.put(elemEntry.getKey(), calcoloVergaOttimale(multiQueue, elemEntry.getKey()));
        }

        } else{
            for (Entry<String, Set<Tubolar>> elemEntry : multiQueue.entrySet()) {
            mapCut.put(elemEntry.getKey(), listOfTubolarByLenght(multiQueue.get(elemEntry.getKey()), STARDARD_TUB));
        }
    }

        return mapCut;
    }

    public static LinkedList<Pair<Integer, LinkedList<Integer>>> calcoloVergaOttimale(
            HashMap<String, Set<Tubolar>> multiQueue, String queue) {

        var listShort = listOfTubolarByLenght(multiQueue.get(queue), STARDARD_TUB);
        var listLong = listOfTubolarByLenght(multiQueue.get(queue), EXTENDED_TUB);
        return listShort.size() <= (listLong.size() * 2) ? listShort : listLong;
    }


    private static LinkedList<Pair<Integer, LinkedList<Integer>>> listOfTubolarByLenght(Set<Tubolar> tempList,
            int lenght) {
        LinkedList<Pair<Integer, LinkedList<Integer>>> listList = new LinkedList<>();

        Set<Tubolar> temp = new TreeSet<>((o1, o2) -> o2.getLenght() - o1.getLenght());

        for (Tubolar elemTubolar : tempList) {
            temp.add(new Tubolar(elemTubolar.getLenght(), elemTubolar.getQuantity()));
        }

        while (temp.stream().mapToInt(Tubolar::getQuantity).sum() != 0) {

            int total = lenght;

            LinkedList<Integer> list = new LinkedList<>();
            Pair<Integer, LinkedList<Integer>> pairList = new Pair<Integer, LinkedList<Integer>>(
                    Integer.valueOf(lenght), list);
            for (Tubolar elem : temp) {

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
