package br.unesp.amoraes.dbvis.internals;

/**
 * Provides easy use of Default Models for JTable, JComboBox, JList, etc.
 * @author Alessandro Moraes (sanfatec at gmail.com)
 * @since 2013-01-06
 */
public class ValueTextItem<T> {
    private T value;
    private String label;

    public ValueTextItem(T value, String label) {
        this.value = value;
        this.label = label;
    }

    public T getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public String toString() {
        return label;
    }
}
