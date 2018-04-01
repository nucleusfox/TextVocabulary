package com.nucleusfox.idle.textvocabulary;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.LinkedList;

/**
 * The online dictionary http://www.omegawiki.org/ wrapper class. Communicate with http-resource and parse response.
 * Obtains definitions for the word and wrap them into WordDefinition class.
 * @author nucleusfox
 *
 */
public class OmegaWikiDictionary extends OnlineDictionary {
    private static String URL = "http://www.omegawiki.org/api.php?action=ow_express&search=";
    private static final String FORMAT_JSON = "&format=json";

    OmegaWikiDictionary(String lang) {
        super(lang);
    }

    /* (non-Javadoc)
     * @see com.nucleusfox.idle.textvocabulary.Dictionary#findDefinition(com.nucleusfox.idle.textvocabulary.Word)
     */
    @Override
    public WordDefinition getDefinition(Word word) throws IllegalArgumentException {
        if (word == null) throw new IllegalArgumentException("Word reference should not be null");
        try {
            return new WordDefinition(word, getDefinitionStrings(word.getWord()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDefinitionAsString(String word) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(URL + word + FORMAT_JSON).openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "NucleusFox");

        String contentEncoding = connection.getContentType();
        contentEncoding = contentEncoding.substring(contentEncoding.lastIndexOf("=") + 1);
        Charset cs = Charset.forName(contentEncoding);

        InputStreamReader in = new InputStreamReader(connection.getInputStream(), cs);

        try (JsonParser parser = Json.createParser(in)) {
            while (parser.hasNext()) {
                Event e = parser.next();
                if (e == Event.KEY_NAME && parser.getString().startsWith("ow_define_")) {
                    if (getLanguage(parser).equals(language))
                        return getDefinitionAsString(parser);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.nucleusfox.idle.textvocabulary.Dictionary#getDefinitions(java.util.LinkedList)
     */
    @Override
    public LinkedList<WordDefinition> getDefinitions(LinkedList<Word> words) {
        LinkedList<WordDefinition> wdList = new LinkedList<>();
        for (Word w : words) wdList.add(getDefinition(w));
        return wdList;
    }

    public WordDefinition getDefinition(String word) {
        LinkedList<String> defs;
        try {
            defs = getDefinitionStrings(word);
            if (defs == null) return null;
            return new WordDefinition(new Word(language, word), defs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    private LinkedList<String> getDefinitionStrings(String word) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(URL + word + FORMAT_JSON).openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "NucleusFox");

        String contentEncoding = connection.getContentType();
        contentEncoding = contentEncoding.substring(contentEncoding.lastIndexOf("=") + 1);
        Charset cs = Charset.forName(contentEncoding);

        InputStreamReader in = new InputStreamReader(connection.getInputStream(), cs);

        try (JsonParser parser = Json.createParser(in)) {
            LinkedList<String> definitions = new LinkedList<>();
            while (parser.hasNext()) {
                Event e = parser.next();
                if (e == Event.KEY_NAME && parser.getString().startsWith("ow_define_")) {
                    if (getLanguage(parser).equals(language))
                        definitions.add(getDefinitionAsString(parser));
                }
            }
            return definitions;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LinkedList<String> getDefinitionStrings(String word, int limit) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(URL + word + FORMAT_JSON).openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "NucleusFox");

        String contentEncoding = connection.getContentType();
        contentEncoding = contentEncoding.substring(contentEncoding.lastIndexOf("=") + 1);
        Charset cs = Charset.forName(contentEncoding);

        InputStreamReader in = new InputStreamReader(connection.getInputStream(), cs);
        int defCounter = 0;
        try (JsonParser parser = Json.createParser(in)) {
            LinkedList<String> definitions = new LinkedList<>();
            while (parser.hasNext()) {
                Event e = parser.next();
                if (e == Event.KEY_NAME && parser.getString().startsWith("ow_define_")) {
                    if (getLanguage(parser).equals(language)) {
                        defCounter++;
                        definitions.add(getDefinitionAsString(parser));
                        if (defCounter == limit) return definitions;
                    }
                }
            }
            return definitions;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getLanguage(JsonParser parser) {
        while (parser.hasNext()) {
            if (parser.next() == Event.KEY_NAME && parser.getString().equals("lang")) {
                parser.next();
                return parser.getString();
            }
        }
        return null;
    }

    // TODO Add special case for 'I'

    private static String getDefinitionAsString(JsonParser parser) {
        while (parser.hasNext()) {
            if (parser.next() == Event.KEY_NAME && parser.getString().equals("text")) {
                parser.next();
                return parser.getString();
            }
        }
        return null;
    }




    public static void main(String[] args) {
        OmegaWikiDictionary od = new OmegaWikiDictionary("English");//svenska");//English");

        WordDefinition def = od.getDefinition("and");
        for (Definition d : def.getDefinitions()) {
            System.out.println(d);
        }

        System.out.println();

        def = od.getDefinition("girl");
        for (Definition d : def.getDefinitions()) {
            System.out.println(d);
        }

    }

}