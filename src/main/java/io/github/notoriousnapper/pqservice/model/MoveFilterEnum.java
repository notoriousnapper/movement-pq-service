package io.github.notoriousnapper.pqservice.model;

public enum MoveFilterEnum {

    PRIORITY("PRIORITY"),
    ATOM_SIZE_DESCENDING("ATOM_SIZE_DESCENDING"),
    CURRENT_WEEK("CURRENT_WEEK");

    private final String text;

    /**
     * @param text
     */
    MoveFilterEnum(final String text) {
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
