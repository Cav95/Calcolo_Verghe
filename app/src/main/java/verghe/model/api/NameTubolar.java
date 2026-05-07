package verghe.model.api;

import java.util.Arrays;

/**
 * Enum representing the names of tubulars.         
 */
public enum NameTubolar {
    TBQ15003("Tubolare 150x150 sp3"),
    TBQ15004("Tubolare 150x150 sp4"),
    TBQ15005("Tubolare 150x150 sp5"),
    TBQ15006("Tubolare 150x150 sp6"),
    TBQ15008("Tubolare 150x150 sp8"),
    TBQ14003("Tubolare 140x140 sp3"),
    TBQ14002("Tubolare 140x140 sp2"),
    TBQ14004("Tubolare 140x140 sp4"),
    TBQ12003("Tubolare 120x120 sp3"),
    TBQ12004("Tubolare 120x120 sp4"),
    TBQ11003("Tubolare 110x110 sp3"),
    TBQ10004("Tubolare 100x100 sp4"),
    TBQ10003("Tubolare 100x100 sp3"),
    TBQ09003("Tubolare 90x90 sp3"),
    TBQ08004("Tubolare 80x80 sp4"),
    TBQ07004("Tubolare 70x70 sp4"),
    TBQ07003("Tubolare 70x70 sp3"),
    TBQ06003("Tubolare 60x60 sp3"),
    TBQ05002("Tubolare 50x50 sp2"),
    TBQ05004("Tubolare 50x50 sp4");

    private final String actualName;

    /**
     * Gets the name of the tubular.
     * 
     * @return the name of the tubular.
     */
    public String getActualName() {
        return actualName;
    }

    /**
     * Gets the name of the tubular based on the key.
     * * @param key the key of the tubular.
     * 
     * @return the name of the tubular.
     */
    public static String[] stringEnum() {
        String[] optionsToChoose = new String[NameTubolar.values().length];

        for (NameTubolar elem : NameTubolar.values()) {
            optionsToChoose[elem.ordinal()] = elem.getActualName();
        }
        Arrays.sort(optionsToChoose);
        return optionsToChoose;
    }

    /**
     * Constructor for NameTubolar.
     * 
     * @param actualName the actual name of the tubular.
     */
    NameTubolar(String actualName) {
        this.actualName = actualName;
    }

}
