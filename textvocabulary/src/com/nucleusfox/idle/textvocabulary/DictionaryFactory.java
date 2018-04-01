package com.nucleusfox.idle.textvocabulary;

/**
 * The dictionary factory. Provides only online dictionary Omega Wiki.
 * In future is to be extended with JDBC and in-memory dictionaries.
 *
 * @author nucleusfox
 *
 */
public class DictionaryFactory {

    public static Dictionary getDictionary(String type) {
        switch (type) {
            case "OmegaWiki" :	return new OmegaWikiDictionary("English");
            //case "JDBC" : 	return new JDBCDictionary("English");
            //case "InMemory" :   return new InMemoryDictionary("English");
        }
        return null;
    }

}