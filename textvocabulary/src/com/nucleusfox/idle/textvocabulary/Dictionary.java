package com.nucleusfox.idle.textvocabulary;

import java.util.LinkedList;

/**
 * The interface for dictionary classes: online, database, in-memory.
 *
 * @author nucleusfox
 *
 */
public interface Dictionary {
    WordDefinition getDefinition(Word word);
    LinkedList<WordDefinition> getDefinitions(LinkedList<Word> word);
    void addDefinition(WordDefinition wordDefinition) throws Exception;
}