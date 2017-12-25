package br.unesp.amoraes.dbvis.ui;

import br.unesp.amoraes.dbvis.beans.DatabaseConnectionEntity;
import br.unesp.amoraes.dbvis.internals.SelectedData;

/**
 * Default interface to all project types palettes
 * @author Alessandro Moraes (sanfatec at gmail.com)
 * @since 2013-01-08
 */
public interface IPalette {
    public void disableAllComponents();
    public void enableAllComponents(SelectedData selectedData,String sqlRelationships, Integer idColumn, DatabaseConnectionEntity databaseConnection);
}
