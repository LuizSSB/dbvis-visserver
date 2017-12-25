package br.unesp.amoraes.dbvis.beans;

/**
 * Display Entity
 * Represents a configured video out or display to show generated graphs
 * @author Alessandro Moraes (sanfatec at gmail.com)
 * @since 2013-05-04
 */
public class DisplayEntity {
    private Integer id;
    private String name;
    private Integer width;
    private Integer height;
    private Boolean active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DisplayEntity other = (DisplayEntity) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    
}
