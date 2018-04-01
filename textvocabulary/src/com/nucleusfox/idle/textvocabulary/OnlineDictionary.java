package com.nucleusfox.idle.textvocabulary;

/**
 * The common online dictionary operations for communication with online-resources.
 *
 * @author nucleusfox
 *
 */
public abstract class OnlineDictionary implements Dictionary {
    protected String url;
    protected String language;


    public OnlineDictionary() {	}

    OnlineDictionary(String language) {
        this.language = language;
    }

    /* (non-Javadoc)
     * @see com.nucleusfox.idle.textvocabulary.Dictionary#addDefinition(com.nucleusfox.idle.textvocabulary.WordDefinition)
     */
    @Override
    public void addDefinition(WordDefinition wordDefinition) throws Exception {
        throw new Exception("Unsupported");

    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

}