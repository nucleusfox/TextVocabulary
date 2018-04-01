package com.nucleusfox.idle.textvocabulary;

import java.util.LinkedList;

/**
 * The word definition wrapper class. Contains a word reference and a list of its definitions.
 *
 * @author nucleusfox
 *
 */
public class WordDefinition {
    private Word word;
    private LinkedList<Definition> definitions;

    WordDefinition(Word w, LinkedList<String> defs) {
        word = w;
        definitions = new LinkedList<>();
        for (String d : defs) {
            definitions.add(new Definition(d));
        }
    }

    /**
     * @param w - the word object
     * @param def -the definition string
     */
    public WordDefinition(Word w, String def) {
        definitions = new LinkedList<>();
        definitions.add(new Definition(def));
    }

    /**
     * @return the word
     */
    public Word getWord() {
        return word;
    }
    /**
     * @param word - the word to set
     */
    public void setWord(Word word) {
        this.word = word;
    }
    /**
     * @return the definitions list
     */
    public LinkedList<Definition> getDefinitions() {
        return definitions;
    }

    /**
     * @return the definitions
     */
    public LinkedList<Definition> values() {
        return getDefinitions();
    }

    /**
     * @param definitions the definitions to set
     */
    public void setDefinitions(LinkedList<Definition> definitions) {
        this.definitions = definitions;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Definition d : definitions)
            sb.append(d.value()).append('\n');
        return sb.toString();
    }

}