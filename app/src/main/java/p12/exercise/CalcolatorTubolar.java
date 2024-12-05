package p12.exercise;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.javatuples.Pair;

public class CalcolatorTubolar {
   

    public static HashMap<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> calcoloTotal(HashMap<String, Set<Tubolar<Integer>>> multiQueue) {
        HashMap<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> mapCut = new HashMap<>();

        for (Entry<String, Set<Tubolar<Integer>>> elemEntry : multiQueue.entrySet()) {
            mapCut.put(elemEntry.getKey(), calcoloVerga(multiQueue ,elemEntry.getKey(), 6000));
        }
        return mapCut;
    }

    
    public static LinkedList<Pair<Integer, LinkedList<Integer>>> calcoloVerga(HashMap<String, Set<Tubolar<Integer>>> multiQueue ,String queue, int lenght) {

        LinkedList<Pair<Integer, LinkedList<Integer>>> listShort = tubConfronto(multiQueue.get(queue), lenght);
        LinkedList<Pair<Integer, LinkedList<Integer>>> listLong = tubConfronto(multiQueue.get(queue), lenght * 2);
        return listShort.size() <= (listLong.size() * 2) ? listShort : listLong;
    }

    public static String printCuttedTubolar(HashMap<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> mapCut) {
        String out = "";
        if (!mapCut.keySet().isEmpty()) {

            for (Entry<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> elemEntry : mapCut.entrySet()) {
                out = out + elemEntry.getKey() + "\n";
                for (Pair<Integer, LinkedList<Integer>> elem : elemEntry.getValue()) {
                    out = out + "Lunghezza tubolare:" + elem.getValue0() + "\n";
                    out = out + elem.getValue1().toString() + "\n";
                }
                out = out + "Numero Tubolari:" + elemEntry.getValue().size() + "\n";
            }
        } else {
            throw new IllegalArgumentException();
        }
        System.out.print(out);
        return out;
    }
    public static String printCuttedTubolarSmoll(HashMap<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> mapCut) {
        String out = "";
        if (!mapCut.keySet().isEmpty()) {

            for (Entry<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> elemEntry : mapCut.entrySet()) {
                out = out + elemEntry.getKey() + "\n";
                    out = out + "Lunghezza tubolare:" + elemEntry.getValue().getFirst().getValue0() + "\n";
                
                out = out + "Numero Tubolari:" + elemEntry.getValue().size() + "\n";
            }
        } else {
            throw new IllegalArgumentException();
        }
        System.out.print(out);
        return out;
    }

    private static LinkedList<Pair<Integer, LinkedList<Integer>>> tubConfronto(Set<Tubolar<Integer>> tempList, int lenght) {
        LinkedList<Pair<Integer, LinkedList<Integer>>> listList = new LinkedList<>();

        Set<Tubolar<Integer>> temp = new TreeSet<>((o1, o2) -> (int) o2.getLenght() - (int) o1.getLenght());

        for (Tubolar<Integer> elemTubolar : tempList) {
            temp.add(new Tubolar<Integer>(elemTubolar.getLenght(), elemTubolar.getQuantity()));
        }

        while (temp.stream().mapToInt( t -> t.getQuantity()).sum() != 0) {

            int total1 = lenght;

            LinkedList<Integer> list = new LinkedList<>();
            Pair<Integer, LinkedList<Integer>> pairList = new Pair<Integer, LinkedList<Integer>>(Integer.valueOf(lenght), list);
            for (Tubolar<Integer> elem : temp) {

                while (elem.getQuantity() > 0 && (total1 - (int) elem.getLenght()) >= 0) {
                    list.add(elem.getLenght());
                    elem.setQuantity(elem.getQuantity() - 1);
                    total1 = total1 - (int) elem.getLenght();
                }
            }         
            listList.add(pairList);
        }
        return listList;
    }
}
