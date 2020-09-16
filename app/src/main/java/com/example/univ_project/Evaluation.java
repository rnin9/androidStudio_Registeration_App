package com.example.univ_project;

public class Evaluation {


    public Evaluation(String evalTitle, String evalProfessor) {
        this.evalTitle = evalTitle;
        this.evalProfessor = evalProfessor;
    }

    public String getEvalTitle() {
        return evalTitle;
    }

    public void setEvalTitle(String evalTitle) {
        this.evalTitle = evalTitle;
    }

    public Evaluation(String evalTitle, String evalProfessor, String evalMajor) {
        this.evalTitle = evalTitle;
        this.evalProfessor = evalProfessor;
        this.evalMajor = evalMajor;
    }

    String evalTitle;
    String evalProfessor;
    String evalMajor;

    public String getEvalMajor() {
        return evalMajor;
    }

    public void setEvalMajor(String evalMajor) {
        this.evalMajor = evalMajor;
    }


    public String getEvalProfessor() {
        return evalProfessor;
    }

    public void setEvalProfessor(String evalProfessor) {
        this.evalProfessor = evalProfessor;
    }
}
