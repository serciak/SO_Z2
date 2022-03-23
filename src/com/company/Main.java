package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static ArrayList<Zgloszenie> generator(int ilosc, int rozmiarDysku) {
        ArrayList<Zgloszenie> z = new ArrayList<>();
        Random rand = new Random();

        for(int i = 0; i < ilosc; i++) {
            z.add(new Zgloszenie(rand.nextInt(0, ilosc*10), rand.nextInt(0, rozmiarDysku), rozmiarDysku+1));
        }
        return z;
    }

    public static ArrayList<Zgloszenie> RTgenerator(int ilosc, int rozmiarDysku) {
        ArrayList<Zgloszenie> z = new ArrayList<>();
        Random rand = new Random();
        double szansa = 0.001;

        for(int i = 0; i < ilosc; i++) {
            if(rand.nextDouble() < szansa) {
                z.add(new Zgloszenie(rand.nextInt(0, ilosc * 10), rand.nextInt(0, rozmiarDysku), rand.nextInt(0, rozmiarDysku)));
            } else {
                z.add(new Zgloszenie(rand.nextInt(0, ilosc * 10), rand.nextInt(0, rozmiarDysku), rozmiarDysku + 1));
            }
        }
        return z;
    }

    public static void main(String[] args) {
        Algorytmy algorytmy = new Algorytmy();

        int iloscZgloszen = 10000;
        int rozmiarDysku = 200;

        algorytmy.FCFS(generator(iloscZgloszen, rozmiarDysku));
        algorytmy.SSTF(generator(iloscZgloszen, rozmiarDysku));
        algorytmy.SCAN(generator(iloscZgloszen, rozmiarDysku), rozmiarDysku);
        algorytmy.CSCAN(generator(iloscZgloszen, rozmiarDysku), rozmiarDysku);
        algorytmy.EDF(RTgenerator(iloscZgloszen, rozmiarDysku));
        algorytmy.FDSCAN(RTgenerator(iloscZgloszen, rozmiarDysku), rozmiarDysku);
    }
}
