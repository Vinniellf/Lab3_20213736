package com.example.teletrivia;

import java.util.ArrayList;
import java.util.List;

public class QuestionResponse {
    private ArrayList<Question> results;

    public ArrayList<Question> getResults() {
        return results;
    }

    public void setResults(ArrayList<Question> results) {
        this.results = results;
    }
}