package fr.umlv.hanabi.view;

import java.util.Objects;

public class Option<E> {
    private final E value;
    private final boolean enabled;
    private final ChoiceProcessor choiceProcessor;

    public Option(E value, boolean enabled, ChoiceProcessor choiceProcessor) {
        this.value = Objects.requireNonNull(value);
        this.enabled = enabled;
        this.choiceProcessor = Objects.requireNonNull(choiceProcessor);
    }

    public E getValue() {
        return value;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public ChoiceProcessor getChoiceProcessor() {
        return choiceProcessor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option<?> option = (Option<?>) o;
        return enabled == option.enabled &&
                value.equals(option.value) &&
                choiceProcessor.equals(option.choiceProcessor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, enabled, choiceProcessor);
    }
}
