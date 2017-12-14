package com.cotta.naivebayesclassifier;

import de.daslaboratorium.machinelearning.classifier.Classifier;
import de.daslaboratorium.machinelearning.classifier.bayes.BayesClassifier;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Pedro Vivaldi <pedro.cotta.vivaldi@gmail.com>
 */
public class TwitterAnalysis {

    private final Classifier<String, String> bayes = new BayesClassifier<>();
    private List<Sample> samples = new ArrayList<>();
    private final String fileName;
    private int errorCount;
    private static final int TRAIN_QTD = 100000;
    private static final double TEST_QTD = 1000.0;

    public TwitterAnalysis(String fileName) {
        this.fileName = fileName;
        bayes.setMemoryCapacity(100000);
        errorCount = 0;
    }

    public void readFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        reader.readLine();
        String line;

        while ((line = reader.readLine()) != null) {
            if (!line.isEmpty()) {
                String[] tokens = line.split(",", 4);
                if (tokens.length == 4) {
                    boolean value = false;
                    if (tokens[1].equals("1")) {
                        value = true;
                    }

                    if (tokens[3].length() > 2) {
                        Sample sample = new Sample(Integer.valueOf(tokens[0]), value, tokens[2], tokens[3]);
                        samples.add(sample);
                    }
                }
            }
        }
    }

    public void trainBayes() {
        for (int i = 0; i < TRAIN_QTD; i++) {
            Sample sample = samples.get((int) Math.round(Math.random() * (samples.size() - 1)));
            String value = "negative";
            if (sample.isPositive()) {
                value = "positive";
            }

            List<String> words = new ArrayList<>(Arrays.asList(sample.getText().split("\\s")));

            // Removing unwanted characters, usernames and links
            for (int j = 0; j < words.size(); j++) {
                if (words.get(j).contains("@") || words.get(j).startsWith("http")) {
                    words.remove(j);
                    i--;
                }
            }

            bayes.learn(value, words);
        }
    }

    public void testBayes() {
        for (int i = 0; i < TEST_QTD; i++) {
            int index = (int) Math.round(Math.random() * (samples.size() - 1));
            String category = bayes.classify(Arrays.asList(samples.get(index).getText().split("\\s"))).getCategory();
            boolean value = false;
            if (category.equals("positive")) {
                value = true;
            }

            if (value != samples.get(i).isPositive()) {
                errorCount++;
            }
        }

        System.out.println("");

        System.out.println("Número de amostras: " + samples.size());
        System.out.println("Número de amostras para treinamento: " + TRAIN_QTD);
        System.out.println("Número de testes: " + TEST_QTD);

        System.out.println("");

        System.out.println("A porcentagem de acerto foi de: " + (1 - errorCount / TEST_QTD));
        System.out.println("Logo, a porcentagem de erro foi de: " + errorCount / TEST_QTD);

        System.out.println("");
    }
}
