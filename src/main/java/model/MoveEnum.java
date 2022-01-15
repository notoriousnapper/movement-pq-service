package model;

public enum MoveEnum {

    PRIORITY("PRIORITY"),
    ATOM_SIZE_DESCENDING("ATOM_SIZE_DESCENDING");

    private final String text;

    /**
     * @param text
     */
    MoveEnum(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }

}
