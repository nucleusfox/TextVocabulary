package com.nucleusfox.idle.textvocabulary;

/**
 * The word definition class.
 * @author nucleusfox
 *
 */
public class Definition {
    private int id;
    private int wordId;
    private String definition;

    Definition(String def) {
        definition = def;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the wordId
     */
    public int getWordId() {
        return wordId;
    }

    /**
     * @param wordId the wordId to set
     */
    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    /**
     * @return the definition
     */
    public String getDefinition() {
        return definition;
    }

    /**
     * @param definition the definition to set
     */
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String value() {
        return definition;
    }

    public String toString() {
        return definition;
    }
}