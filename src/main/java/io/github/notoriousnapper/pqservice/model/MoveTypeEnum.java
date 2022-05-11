package io.github.notoriousnapper.pqservice.model;

public enum MoveTypeEnum {

    RITUAL("Ritual"),
    FLOW("Flow"),
    WORKOUT("Workout"),
    AFFIRMATION("Affirmation"),
    TECHNIQUEREPEAT("TechniqueRepeat"),
    STRETCH("Stretch"),
    RELEASE("Release"),
    TIK("Tik"),
    TEXT("Text"),
    CONDITION("Condition"),
    SNAPSHOT("Snapshot"),
    MULTISELECT("MultiSelect");

    private final String text;

    /**
     * @param text
     */
    MoveTypeEnum(final String text) {
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
