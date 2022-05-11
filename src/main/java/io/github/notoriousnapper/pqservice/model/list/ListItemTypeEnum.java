package io.github.notoriousnapper.pqservice.model.list;

public enum ListItemTypeEnum {

    SONG("SONG"),
    PHYSICAL_ACTION("PHYSICAL_ACTIONS"), // prehab, dance moves, movements, physical_therapy
    FOOD("FOOD"),
    CS_ALG_DATA_STRUCTURE_PROBLEM("CS_ALG_DATA_STRUCTURE_PROBLEM");

    private final String text;

    /**
     * @param text
     */
    ListItemTypeEnum(final String text) {
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
