package com.cotta.naivebayesclassifier;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BayesMain {

    private static final String FILE_NAME = "SentimentAnalysisDataset.csv";

    public static void main(String[] args) {
        TwitterAnalysis analysis = new TwitterAnalysis(FILE_NAME);
        try {
            analysis.readFile();
        } catch (IOException ex) {
            Logger.getLogger(BayesMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        analysis.trainBayes();
        analysis.testBayes();
    }
}
