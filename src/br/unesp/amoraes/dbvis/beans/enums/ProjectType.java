package br.unesp.amoraes.dbvis.beans.enums;

/**
 * Types of representation available on our application
 * @author Alessandro Moraes (sanfatec at gmail.com)
 * @since 2013-01-04
 */
public enum ProjectType {
    GRAPH {
        @Override
        public boolean getRelationshipStep() {
            return true;
        }
    };
    
    public abstract boolean getRelationshipStep();
}
