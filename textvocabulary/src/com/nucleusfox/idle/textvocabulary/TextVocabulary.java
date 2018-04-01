package com.nucleusfox.idle.textvocabulary;

import java.util.LinkedList;

/**
 * The main TextVocabulary class. Works with string input, counts words and provides definitions.
 *
 * @author nucleusfox
 *
 */
public class TextVocabulary {
    private OnlineDictionary od;
    private WordsStatistics ws;


    public TextVocabulary() {
        od = (OmegaWikiDictionary) DictionaryFactory.getDictionary("OmegaWiki");
        ws = new WordsStatistics();
    }



    public WordCount[] getWordsCounts(String text) {
        return ws.countWordsFromString(text).sortResult().getCountArray();
    }

    public WordCount[] getWordsCountsTop(String text, int top) {
        return ws.countWordsFromString(text).sortResult().getCountArrayTop(top);
    }


    public WordDefinition getDefinition(Word word) {
        return od.getDefinition(word);
    }

    public LinkedList<Definition> getDefinitionList(Word word) {
        return od.getDefinition(word).values();
    }

    public static void main(String[] args) {
        TextVocabulary tv = new TextVocabulary();
        WordCount[] wc = tv.getWordsCounts("Hello hey hope hello miss\nhope corn hope village girl pan");
        for (WordCount c : wc) {
            System.out.println("Word = " + c.getWord().value() + ", count = " + c.getCount() + ", definitions : ");
            for (Definition wd : tv.getDefinitionList(c.getWord())) {
                System.out.println(wd);
            }
            System.out.println();
        }
    }

}