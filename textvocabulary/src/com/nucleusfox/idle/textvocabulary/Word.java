package com.nucleusfox.idle.textvocabulary;

/**
 * The word class.
 *
 * @author nucleusfox
 *
 */
public class Word {
    private int id;
    private final String lang;
    private final String word;

    public Word(String l, String w) {
        lang = l;
        word = w;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return lang;
    }

    public String getWord() {
        return word;
    }

    /**
     * @return word
     */
    public String value() {
        return word;
    }
}