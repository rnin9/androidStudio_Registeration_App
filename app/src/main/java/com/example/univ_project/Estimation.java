package com.example.univ_project;

public class Estimation {

    public String getEstimationYear() {
        return estimationYear;
    }

    public void setEstimationYear(String estimationYear) {
        this.estimationYear = estimationYear;
    }

    public String getEstimationTerm() {
        return estimationTerm;
    }

    public void setEstimationTerm(String estimationTerm) {
        this.estimationTerm = estimationTerm;
    }

    public String getEstimationContent() {
        return estimationContent;
    }

    public void setEstimationContent(String estimationContent) {
        this.estimationContent = estimationContent;
    }

    public Estimation(Double estimationRating, String estimationYear, String estimationTerm, String estimationContent) {
        this.estimationRating = estimationRating;
        this.estimationYear = estimationYear;
        this.estimationTerm = estimationTerm;
        this.estimationContent = estimationContent;
    }


    public void setEstimationRating(Double estimationRating) {
        this.estimationRating = estimationRating;
    }

    public Double getEstimationRating() {
        return estimationRating;
    }

    Double estimationRating;   //강의별점
    String estimationYear; // 강의평 평가년도
    String estimationTerm; // 강의평 평가학기
   String estimationContent;  // 강의평
}
