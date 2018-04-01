package com.nucleusfox.idle.textvocabulary;

/**
 * The word wrapper class with count.
 *
 * @author nucleusfox
 *
 */
public class WordCount {
    private Word word;
    private int count;
    /**
     * @param word - the word object
     * @param count - the word count
     */
    public WordCount(Word word, int count) {
        this.word = word;
        this.count = count;
    }
    /**
     * @return the word object
     */
    public Word getWord() {
        return word;
    }
    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }
}