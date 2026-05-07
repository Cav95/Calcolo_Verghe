package verghe.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Comparator;
import verghe.controller.ControllerModel;
import verghe.model.api.ExcludedTubolar;
import verghe.model.api.NameTubolar;
import verghe.model.api.Tubolar;
import verghe.model.api.TubolarMultiList;

/**
 * TextOutputFactory class for generating text output for tubular cutting
 * calculations.
 */
public class TextOutputFactory {

    private static final String EMPTY_LINE = "-";
    private static final String M = "m";
    private static final int MM_TO_M = 1000;
    private static final String A_CAPO = "\n";
    private static final String TUBOLARE_UTILIZZATO = "Tubolare Utilizzato:";
    private static final String QUANTITA = " Quantità=";
    private static final String CODICE_DELLA_STRUTURA = "Codice della struttura: ";
    private static final String CASO_PESSIMO_TUBOLARI_SOLO_6MT = "Con solo verghe da 6 mt\n\n";
    private static final String CASO_OTTIMO_TUBOLARI_12M_6MT = "Con verghe da 12 m e 6 mt\n\n";
    private static final String LISTA_DI_TAGLIO = "Lista di taglio:";
    private static final String VERGA_UTILIZZATA = "Somma Millimetri Utilizzati: ";
    private static final String NUMERO = "Q.TA=";
    private static final String SEP = "-";
    private static final String LUNGHEZZA_SINGOLO = "L=";
    private static final String LUNGHEZZA_VERGA = "Lunghezza Verga:";
    private static final String NUMERO_TUBOLARI_TOTALI = "Quantità Verghe:";
    private static final String SEPARATOR = " -> ";

    private TextOutputFactory() {
        // Private constructor to prevent instantiation
    }

    /**
     * Prints the cutted tubulars in a formatted string.
     * 
     * @param mapCut    the map containing cutting results
     * @param collector the collector peace containing tubular and sample data
     * @return a formatted string representing the cutted tubulars
     */
    public static String cuttedTubolarExtended(CalcolatorTubolar calcolator,
            Optional<CollectorPeace> collector) {
        StringBuilder out = new StringBuilder();
        if (!calcolator.getMapCut().keySet().isEmpty()) {

            for (var elemEntry : calcolator.getMapCut().entrySet()) {
                out.append(elemEntry.getKey() + getNameTubolar(elemEntry.getKey(), collector) + SEPARATOR
                        + NUMERO_TUBOLARI_TOTALI + elemEntry.getValue().size()
                        + A_CAPO);
                for (var elem : elemEntry.getValue()) {
                    var lengths = elem.getValue1();
                    var countByLength = lengths.stream()
                        .collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));
                    out.append(LUNGHEZZA_VERGA + elem.getValue0() + A_CAPO
                        + VERGA_UTILIZZATA + lengths.stream().mapToInt(t -> t).sum() + A_CAPO
                            + LISTA_DI_TAGLIO + A_CAPO
                        + lengths.stream()
                            .map(t -> LUNGHEZZA_SINGOLO + t + SEP + NUMERO + countByLength.get(t))
                                    .distinct().toList()
                            + A_CAPO +
                            A_CAPO);
                }
                out.append(A_CAPO);
            }
        } else {
            throw new IllegalArgumentException();
        }
        out.append(noTubolarElement(collector));
        return out.toString();
    }

    /**
     * Gets the list of the tubular cutted in ottimization mode.
     * 
     * @param mapCut    the map containing cutting results
     * @param collector the collector peace containing tubular and sample data
     * @return a formatted string representing the cutted tubulars in reduced form
     * 
     */
    public static String cuttedTubolarReduced(
            CalcolatorTubolar calcolator,
            Optional<CollectorPeace> collector) {
        StringBuilder out = new StringBuilder();
        if (!calcolator.getMapCut().keySet().isEmpty()) {

            for (var elem : calcolator.getMapCut().entrySet()) {
                out.append(elem.getKey() + getNameTubolar(elem.getKey(), collector) + A_CAPO
                        + LUNGHEZZA_VERGA + elem.getValue().getFirst().getValue0() + SEPARATOR
                        + NUMERO_TUBOLARI_TOTALI + elem.getValue().size() + SEPARATOR + TUBOLARE_UTILIZZATO
                        + String.format("%.2f", elem.getValue().stream()
                                .mapToDouble(t -> t.getValue1().stream().mapToDouble(h -> h).sum() / MM_TO_M)
                                .sum())
                        + M + A_CAPO
                        + A_CAPO);

            }
        } else {
            throw new IllegalArgumentException();
        }
        out.append(noTubolarElement(collector));
        return out.toString();
    }

    /**
     * Provides rules for using the Excel file in the application.
     * 
     * @return a string containing the rules for using the Excel file.
     */
    public static String rulesOfUseExcel() {
        return "Il file Excel deve provenire dalla tabella SolidWorks \n"
                + "Il file Excel deve avere le colonne: \n"
                + "Codice, Lunghezza, Quantità, Diametro, Spessore\n"
                + "In questo ordine\n"
                + "Il file deve essere salvato con estensione .xlsx\n";
    }

    /**
     * Generates a string output for the inserted tubulars in the multi-list.
     * 
     * @param multi     the TubolarMultiList containing tubular data
     * @param collector the optional CollectorPeace containing additional data
     * @return a formatted string representing the inserted tubulars
     */
    public static String tubolarInsertedOutput(TubolarMultiList multi, Optional<CollectorPeace> collector) {
        StringBuilder out = new StringBuilder();
        if (!multi.availableQueues().isEmpty()) {
            for (Entry<String, Set<Tubolar>> elemEntry : multi.getMultiQueue().entrySet()) {
                var tubularList = elemEntry.getValue().stream()
                        .map(t -> LUNGHEZZA_SINGOLO + t.getLenght() + " " + NUMERO + t.getQuantity())
                        .map(t -> "[ " + t + " ] ")
                        .map(String::toUpperCase)
                        .distinct()
                        .collect(Collectors.joining());
                out.append(elemEntry.getKey() + getNameTubolar(elemEntry.getKey(), collector) + A_CAPO
                        + tubularList
                        + A_CAPO
                        + A_CAPO);
            }
        } else {
            throw new IllegalArgumentException();
        }
        out.append(noTubolarElement(collector));
        return out.toString();
    }

    /**
     * Generates a reduced result string for the tubular cutting calculation.
     * 
     * @param siloCode   the code of the silo
     * @param optimal    whether the calculation is optimal
     * @param controller the ControllerModel containing calculation data
     * @return a formatted string representing the reduced result
     */
    public static String reducedResultString(String siloCode, Boolean optimal, ControllerModel controller,
            Integer numSilo) {
        return userName() + A_CAPO
                + siloPropretiesOutput(siloCode, numSilo) + A_CAPO
                + ottimalOutputString(optimal)
                + controller.partialCalcolateTubolar(optimal)
                + EMPTY_LINE;
    }

    /**
     * Generates an extended result string for the tubular cutting calculation.
     * 
     * @param siloCode   the code of the silo
     * @param optimal    whether the calculation is optimal
     * @param controller the ControllerModel containing calculation data
     * @return a formatted string representing the extended result
     */
    public static String extendedResultString(String siloCode, Boolean optimal, ControllerModel controller,
            Integer numSilo) {
        return userName() + A_CAPO
                + siloPropretiesOutput(siloCode, numSilo) + A_CAPO
                + ottimalOutputString(optimal)
                + controller.totalCalcolateTubolar(optimal)
                + EMPTY_LINE;
    }

    /**
     * Generate output for provider Confert.
     * 
     * @param controller
     * @return a formatted string representing the tubular data.
     */
    public static String confertOutPut(ControllerModel controller, String siloCode, int numSilo){
        var out = new StringBuilder()
            .append(userName())
            .append(A_CAPO)
            .append(siloPropretiesOutput(siloCode, numSilo))
            .append(A_CAPO)
            .append(A_CAPO);

            if(controller.getTubolarList().availableQueues().isEmpty()){
                throw new IllegalArgumentException();
            }
        controller.getTubolarList().getMultiQueue().entrySet().stream()
            .sorted(Entry.comparingByKey())
            .forEach(entry -> entry.getValue().stream()
                .sorted(Comparator.comparingInt(Tubolar::getLenght))
                .forEach(tubolar -> out.append(confertLine(entry.getKey(), tubolar.getQuantity(),
                    tubolar.getLenght(), controller.getCollector()))));

        return out.append(EMPTY_LINE).toString();
        }

        private static String confertLine(String codeTubolar, int quantity, int lenght,
            Optional<CollectorPeace> collector) {
        var tubolarName = getNameTubolar(codeTubolar, collector);
        if (tubolarName.isEmpty()) {
            return "";
        }
        return (tubolarName
            + " (" + codeTubolar + ")"
            + SEPARATOR + QUANTITA + quantity
            + SEPARATOR + LUNGHEZZA_SINGOLO + lenght + A_CAPO).toUpperCase();
    }
    private static String ottimalOutputString(Boolean optimal) {
        return optimal ? CASO_OTTIMO_TUBOLARI_12M_6MT : CASO_PESSIMO_TUBOLARI_SOLO_6MT;

    }

    private static String structureCode(String codeSilo) {
        return codeSilo.isBlank() ? "" : CODICE_DELLA_STRUTURA + codeSilo + " ";

    }

    private static String userName() {
        return "Autore: " + System.getProperty("user.name", "").toUpperCase() + " - " + LocalDate.now();
    }

    private static String getNameTubolar(String codeTubolar, Optional<CollectorPeace> collector) {
        if (collector.isPresent()) {
            var sampleDescription = collector.get().getTableSeampleList().stream()
                    .filter(t -> t.code().equals(codeTubolar))
                    .map(t -> t.description())
                    .findFirst();
            if (sampleDescription.isPresent()) {
                return " (" + sampleDescription.get() + ")";
            }
        }
        return Arrays.stream(NameTubolar.values())
                .filter(t -> t.name().equals(codeTubolar))
                .map(NameTubolar::getActualName)
                .findFirst()
                .map(name -> " (" + name + ")")
                .orElse("");
    }

    private static String noTubolarElement(Optional<CollectorPeace> collector) {
        return collector.isEmpty() ? ""
                : collector.get().getTableSeampleList().stream()
                        .filter(h -> Arrays.stream(ExcludedTubolar.values())
                                .noneMatch(t -> h.code().contains(t.name())))
                        .filter(h -> h.lenght() == 0)
                        .map(h -> h.code() + " (" + h.description() + ") " + QUANTITA
                                + h.quantity() + A_CAPO + A_CAPO)
                        .collect(Collectors.joining());

    }

    private static String numSilOuput(Integer numSilo) {
        return "Numero Silo: " + numSilo + A_CAPO;
    }

    private static String siloPropretiesOutput(String siloCode, Integer numSilo) {
        return structureCode(siloCode) + numSilOuput(numSilo);
    }

}
