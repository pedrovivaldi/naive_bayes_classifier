package com.cotta.naivebayesclassifier;

/**
 *
 * @author Pedro Vivaldi <pedro.cotta.vivaldi@gmail.com>
 */
public class Sample {

    private int id;
    private boolean sentiment;
    private String source;
    private String text;

    public Sample(int id, boolean sentiment, String source, String text) {
        this.id = id;
        this.sentiment = sentiment;
        this.source = source;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPositive() {
        return sentiment;
    }

    public void setSentiment(boolean sentiment) {
        this.sentiment = sentiment;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Sentiment{" + "id=" + id + ", sentiment=" + sentiment + ", source=" + source + ", text=" + text + '}';
    }
}
