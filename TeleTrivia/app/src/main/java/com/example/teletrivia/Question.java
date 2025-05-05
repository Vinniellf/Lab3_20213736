package com.example.teletrivia;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    private String question;
    private String correct_answer;
    private String dificultad;
    private List<String> incorrect_answers;

    // Constructor
    public Question(String question, String dificultad, String correct_answer, List<String> incorrect_answers) {
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answers = incorrect_answers;
        this.dificultad = dificultad;
    }

    // Métodos Parcelable
    protected Question(Parcel in) {
        question = in.readString();
        correct_answer = in.readString();
        incorrect_answers = in.createStringArrayList();
        dificultad = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(correct_answer);
        dest.writeStringList(incorrect_answers);
        dest.writeString(dificultad);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters y setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public List<String> getIncorrect_answers() {
        return incorrect_answers;
    }

    public void setIncorrect_answers(List<String> incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
}



