package p12.exercise;

public enum NameTubolar {
    TBQ15023("Tubolare 150x150 sp3 "),
    TBQ14023("Tubolare 140x140 sp3 "),
    TBQ12023("Tubolare 120x120 sp3 "),
    TBQ11023("Tubolare 110x110 sp3 ");

    private final String actualName;

    public String getactualName() {
        return actualName;
    }

    public String[] stringEnum() {
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
