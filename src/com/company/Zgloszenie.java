package com.company;

public class Zgloszenie {

    private int momentZgloszenia;
    private int pozycja;
    private int deadline;

    public Zgloszenie(int momentZgloszenia, int pozycja, int deadline) {
        this.momentZgloszenia = momentZgloszenia;
        this.pozycja = pozycja;
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Moment pojawienia: " + momentZgloszenia + " Numer cylindra: " + pozycja + " Deadline: " + deadline;
    }

    public int getMomentZgloszenia() {
        return momentZgloszenia;
    }

    public int getPozycja() {
        return pozycja;
    }

    public int getDeadline() {
        return deadline;
    }
}
