package com.example.univ_project;

public class EstimationMy {


    String estTitle;
    String estProfessor;
    String estMyRating;   //강의별점
    String estMyYear; // 강의평 평가년도
    String estMyTerm; // 강의평 평가학기
    String estMyContent;  // 강의평

    public String getEstTitle() {
        return estTitle;
    }

    public void setEstTitle(String estTitle) {
        this.estTitle = estTitle;
    }

    public String getEstProfessor() {
        return estProfessor;
    }

    public void setEstProfessor(String estProfessor) {
        this.estProfessor = estProfessor;
    }

    public String getEstMyRating() {
        return estMyRating;
    }

    public void setEstMyRating(String estMyRating) {
        this.estMyRating = estMyRating;
    }

    public String getEstMyYear() {
        return estMyYear;
    }

    public void setEstMyYear(String estMyYear) {
        this.estMyYear = estMyYear;
    }

    public String getEstMyTerm() {
        return estMyTerm;
    }

    public void setEstMyTerm(String estMyTerm) {
        this.estMyTerm = estMyTerm;
    }

    public String getEstMyContent() {
        return estMyContent;
    }

    public void setEstMyContent(String estMyContent) {
        this.estMyContent = estMyContent;
    }

    public EstimationMy(String estTitle, String estProfessor, String estMyRating, String estMyYear, String estMyTerm, String estMyContent) {
        this.estTitle = estTitle;
        this.estProfessor = estProfessor;
        this.estMyRating = estMyRating;
        this.estMyYear = estMyYear;
        this.estMyTerm = estMyTerm;
        this.estMyContent = estMyContent;
    }
}
