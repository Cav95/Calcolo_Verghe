package CalcoloTubolare.model;

import java.util.HashMap;
import java.util.LinkedList;

import org.javatuples.Pair;

public class TextOutputFactory {

    private static final String LISTA_DI_TAGLIO = "Lista di taglio:";
    private static final String VERGA_UTILIZZATA = "Verga utilizzata: ";
    private static final String NUMERO = " Numero:";
    private static final String LUNGHEZZA_SINGOLO = "L";
    private static final String LUNGHEZZA_VERGA = "Lunghezza Verga:";
    private static final String NUMERO_TUBOLARI_TOTALI = "Numero Tubolari:";
    private static final String SEPARATOR = " -> ";

    public static String printCuttedTubolar(HashMap<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> mapCut) {
        String out = "";
        if (!mapCut.keySet().isEmpty()) {

            for (var elemEntry : mapCut.entrySet()) {
                out = out + elemEntry.getKey() + SEPARATOR + NUMERO_TUBOLARI_TOTALI + elemEntry.getValue().size()
                        + "\n";
                for (var elem : elemEntry.getValue()) {
                    out = out + LUNGHEZZA_VERGA + elem.getValue0() + "\n";
                    out = out + VERGA_UTILIZZATA + elem.getValue1().stream().mapToInt(t -> t).sum() +"\n";
                    out = out + LISTA_DI_TAGLIO + "\n";
                    out = out
                            + elem.getValue1().stream()
                                    .map(t -> LUNGHEZZA_SINGOLO + t + NUMERO
                                            + elem.getValue1().stream().mapToInt(e -> e).filter(g -> g == t).sum() / t)
                                    .distinct().toList()
                            + "\n";
                }
                out = out + "\n";
            }
        } else {
            throw new IllegalArgumentException();
        }
        System.out.print(out);
        return out;
    }

    public static String printCuttedTubolarSmoll(
            HashMap<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> mapCut) {
        String out = "";
        if (!mapCut.keySet().isEmpty()) {

            for (var elem : mapCut.entrySet()) {
                out = out + elem.getKey() + "\n";
                out = out + LUNGHEZZA_VERGA + elem.getValue().getFirst().getValue0() + "\n";

                out = out + NUMERO_TUBOLARI_TOTALI + elem.getValue().size() + "\n" + "\n";
            }
        } else {
            throw new IllegalArgumentException();
        }
        System.out.print(out);
        return out;
    }

}
