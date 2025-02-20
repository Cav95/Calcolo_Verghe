package CalcoloTubolare.model;

public enum NameTubolar {
    TBQ15003("Tubolare 150x150 sp3"),
    TBQ15004("Tubolare 150x150 sp4"),
    TBQ14003("Tubolare 140x140 sp3"),
    TBQ12003("Tubolare 120x120 sp3"),
    TBQ12004("Tubolare 120x120 sp4"),
    TBQ11003("Tubolare 110x110 sp3"),
    TBQ08004("Tubolare 80x80 sp4"),
    TBQ07004("Tubolare 70x70 sp4"),
    TBQ07003("Tubolare 70x70 sp3"),
    TBQ06003("Tubolare 60x60 sp3"),
    TBQ05002("Tubolare 50x50 sp2"),
    TBQ05004("Tubolare 50x50 sp4"),
    ;

    private final String actualName;

    public String getactualName() {
        return actualName;
    }

    public static String[] stringEnum() {
        String[] optionsToChoose = new String[NameTubolar.values().length];

        for (NameTubolar elem : NameTubolar.values()) {
            optionsToChoose[elem.ordinal()] = elem.getactualName();
        }
        return optionsToChoose;
    }

    NameTubolar(String actualName) {
        this.actualName = actualName;
    }

}
