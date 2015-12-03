package fr.alma.mw1516.services;

/**
 * @author Arnaud Thimel : thimel@codelutin.com
 */
public class Country {

    protected Long id;
    protected String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
