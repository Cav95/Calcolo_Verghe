package CalcoloTubolare.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.javatuples.Pair;

import com.google.common.base.Optional;

/**
 * TextOutputFactory class for generating text output for tubular cutting
 * calculations.
 */
public class TextOutputFactory {

    private static final String LISTA_DI_TAGLIO = "Lista di taglio:";
    private static final String VERGA_UTILIZZATA = "Verga utilizzata: ";
    private static final String NUMERO = " Numero:";
    private static final String LUNGHEZZA_SINGOLO = "L";
    private static final String LUNGHEZZA_VERGA = "Lunghezza Verga:";
    private static final String NUMERO_TUBOLARI_TOTALI = "Numero Tubolari:";
    private static final String SEPARATOR = " -> ";

    /**
     * Prints the cutted tubulars in a formatted string.
     * 
     * @param mapCut    the map containing cutting results
     * @param collector the collector peace containing tubular and sample data
     * @return a formatted string representing the cutted tubulars
     */
    public static String printCuttedTubolar(HashMap<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> mapCut,
            Optional<CollectorPeace> collector) {
        String out = "";
        if (!mapCut.keySet().isEmpty()) {

            for (var elemEntry : mapCut.entrySet()) {
                out = out + elemEntry.getKey() + getNameTubolar(elemEntry.getKey(), collector) + SEPARATOR
                        + NUMERO_TUBOLARI_TOTALI + elemEntry.getValue().size()
                        + "\n";
                for (var elem : elemEntry.getValue()) {
                    out = out + LUNGHEZZA_VERGA + elem.getValue0() + "\n";
                    out = out + VERGA_UTILIZZATA + elem.getValue1().stream().mapToInt(t -> t).sum() + "\n";
                    out = out + LISTA_DI_TAGLIO + "\n";
                    out = out
                            + elem.getValue1().stream()
                                    .map(t -> LUNGHEZZA_SINGOLO + t + NUMERO
                                            + elem.getValue1().stream().mapToInt(e -> e).filter(g -> g == t).sum() / t)
                                    .distinct().toList()
                            + "\n\n";
                }
                out = out + "\n";
            }
        } else {
            throw new IllegalArgumentException();
        }
        out = out + noTubolarElement(collector);
        System.out.print(out);
        return out;
    }

    /**
     * Gets the name of the tubular based on its code.
     * 
     * @param codeTubolar the code of the tubular
     * @param collector   the collector peace containing tubular data
     * @return a string representing the name of the tubular
     */
    public static String printCuttedTubolarSmoll(
            HashMap<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> mapCut,
            Optional<CollectorPeace> collector) {
        String out = "";
        if (!mapCut.keySet().isEmpty()) {

            for (var elem : mapCut.entrySet()) {
                out = out + elem.getKey() + getNameTubolar(elem.getKey(), collector) + "\n";

                out = out + LUNGHEZZA_VERGA + elem.getValue().getFirst().getValue0() + "\n";

                out = out + NUMERO_TUBOLARI_TOTALI + elem.getValue().size() + "\n" + "\n";
            }
        } else {
            throw new IllegalArgumentException();
        }
        out = out + noTubolarElement(collector);
        System.out.print(out);
        return out;
    }

    private static String getNameTubolar(String codeTubolar, Optional<CollectorPeace> collector) {
        if (collector.isPresent()) {
            String ret = collector.get().getTableTubolarList().stream().filter(t -> t.getValue0() == codeTubolar)
                    .map(t -> " (" + t.getValue1()).toList().getFirst() + ")";
            return ret;
        } else {
            return "";
        }
    }

    private static String noTubolarElement(Optional<CollectorPeace> collector) {
        String out = "";
        if (collector.isPresent()) {

            List<String> ret = collector.get().getTableSeampleList().stream()
                    .map(t -> t.code() + " " + t.description() + " Quantità=" + t.quantity() + " \n").toList();
            for (var elem : ret) {
                out = out + elem;

            }
            return out;
        } else {
            return out;
        }
    }

}
