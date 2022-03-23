package com.company;

import java.util.ArrayList;
import java.util.Comparator;

public class Algorytmy {

    public void FCFS(ArrayList<Zgloszenie> z) {
        System.out.println("FCFS:");

        int aktualnaPozycja = 0;
        int czas = 0;
        ArrayList<Zgloszenie> zakonczone = new ArrayList<>();
        ArrayList<Integer> dystansy = new ArrayList<>();
        int suma = 0;

        Comparator<Zgloszenie> comp = (o1, o2) -> {
            if (o1.getMomentZgloszenia() == o2.getMomentZgloszenia()) return 0;
            if (o1.getMomentZgloszenia() > o2.getMomentZgloszenia()) return 1;
            else return -1;
        };

        z.sort(comp);
        Zgloszenie aktualne;

        while(!z.isEmpty()) {
            if(z.get(0).getMomentZgloszenia() <= czas) {
                aktualne = z.get(0);
                czas += Math.abs(aktualne.getPozycja() - aktualnaPozycja);
                suma += Math.abs(aktualne.getPozycja() - aktualnaPozycja);
                dystansy.add(Math.abs(aktualne.getPozycja() - aktualnaPozycja));
                zakonczone.add(aktualne);
                aktualnaPozycja = aktualne.getPozycja();
                z.remove(aktualne);
            }
            else {
                czas++;
            }
        }

        System.out.println("Pokonany dystans: " + suma);
        double srednia = dystansy.stream().mapToInt(a -> a).average().getAsDouble();
        System.out.println("Srednie wychylenie glowicy: " + srednia + "\n");
    }

    public void SSTF(ArrayList<Zgloszenie> z) {
        System.out.println("SSTF:");
        Zgloszenie najblizsze;

        int aktualnaPozycja = 0;
        int czas = 0;
        ArrayList<Zgloszenie> zakonczone = new ArrayList<>();
        ArrayList<Integer> dystansy = new ArrayList<>();
        ArrayList<Zgloszenie> toDO = new ArrayList<>();
        int suma = 0;
        int dystans;

        while (!z.isEmpty() || !toDO.isEmpty()) {
            for (int i = 0; i < z.size(); i++) {
                Zgloszenie zgloszenie = z.get(i);
                if (zgloszenie.getMomentZgloszenia() <= czas) {
                    toDO.add(zgloszenie);
                    z.remove(zgloszenie);
                }
            }

            if(toDO.isEmpty()) {
                czas++;
            } else {
                najblizsze = toDO.get(0);

                for(Zgloszenie zgloszenie : toDO) {
                    if(Math.abs(aktualnaPozycja - najblizsze.getPozycja()) > Math.abs(aktualnaPozycja - zgloszenie.getPozycja())) {
                        najblizsze = zgloszenie;
                    }
                }

                if(Math.abs(najblizsze.getMomentZgloszenia() - czas) > 1000) {
                    System.out.println("Zaglodzone: " + najblizsze + Math.abs(najblizsze.getMomentZgloszenia() - czas));
                }
                dystans = Math.abs(aktualnaPozycja-najblizsze.getPozycja());
                suma += dystans;
                czas+= dystans;
                dystansy.add(dystans);
                zakonczone.add(najblizsze);
                toDO.remove(najblizsze);
                aktualnaPozycja = najblizsze.getPozycja();
            }
        }
        System.out.println("Pokonany dystans: " + suma);
        double srednia = dystansy.stream().mapToInt(a -> a).average().getAsDouble();
        System.out.println("Srednie wychylenie glowicy: " + srednia + "\n");
    }

    public void SCAN(ArrayList<Zgloszenie> z, int rozmiar) {
        System.out.println("SCAN:");
        int suma = 0;
        int zmiany = 0;
        int kierunek = 1;
        int pozostale = z.size();
        int pozycja = 0;
        ArrayList<Zgloszenie> kolejka = new ArrayList<>();
        ArrayList<Zgloszenie> temp = new ArrayList<>();
        Zgloszenie zgl;

        Comparator<Zgloszenie> comp1 = (o1, o2) -> {
            if (o1.getMomentZgloszenia() == o2.getMomentZgloszenia()) return 0;
            if (o1.getMomentZgloszenia() > o2.getMomentZgloszenia()) return 1;
            else return -1;
        };
        Comparator<Zgloszenie> comp2 = (o1, o2) -> {
            if (o1.getPozycja() == o2.getPozycja()) return 0;
            if (o1.getPozycja() > o2.getPozycja()) return 1;
            else return -1;
        };
        Comparator<Zgloszenie> comp3 = (o1, o2) -> {
            if (o1.getPozycja() == o2.getPozycja()) return 0;
            if (o1.getPozycja() > o2.getPozycja()) return -1;
            else return 1;
        };

        z.sort(comp1);

        while (pozostale > 0) {
            for(int i = 0; i < z.size(); i++) {
                zgl = z.get(i);

                if(zgl.getMomentZgloszenia() <= suma) {
                    if((kierunek > 0 && zgl.getPozycja() >= pozycja) || (kierunek < 0 && zgl.getPozycja() <= pozycja)) {
                        kolejka.add(zgl);
                    }
                    if((kierunek > 0 && zgl.getPozycja() < pozycja) || (kierunek < 0 && zgl.getPozycja() > pozycja)) {
                        temp.add(zgl);
                    }
                    z.remove(zgl);
                }
            }
            if(!kolejka.isEmpty()) {
                if(kierunek > 0) {
                    kolejka.sort(comp2);
                } else {
                    kolejka.sort(comp3);
                }

                while(!kolejka.isEmpty() && kolejka.get(0).getPozycja() == pozycja) {
                    kolejka.remove(kolejka.get(0));
                    pozostale--;
                }
            }

            suma++;
            pozycja += kierunek;

            if(pozycja == rozmiar || pozycja == 0) {
                kierunek = -kierunek;
                zmiany++;
                kolejka.addAll(temp);
                temp.clear();
            }
        }
        System.out.println("Pokonany dystans: " + suma);
        System.out.println("Zmiany kierunku: " + zmiany + "\n");
    }

    public void CSCAN(ArrayList<Zgloszenie> z, int rozmiar) {
        System.out.println("C-SCAN");

        int suma = 0;
        int pozycja = 0;
        int zawroty = 0;
        ArrayList<Zgloszenie> kolejka = new ArrayList<>();
        ArrayList<Zgloszenie> temp = new ArrayList<>();
        int pozostale = z.size();
        Zgloszenie zgl;

        Comparator<Zgloszenie> comp1 = (o1, o2) -> {
            if (o1.getMomentZgloszenia() == o2.getMomentZgloszenia()) return 0;
            if (o1.getMomentZgloszenia() > o2.getMomentZgloszenia()) return 1;
            else return -1;
        };
        Comparator<Zgloszenie> comp2 = (o1, o2) -> {
            if (o1.getPozycja() == o2.getPozycja()) return 0;
            if (o1.getPozycja() > o2.getPozycja()) return 1;
            else return -1;
        };

        z.sort(comp1);

        while(pozostale > 0) {
            for(int i = 0; i < z.size(); i++) {
                zgl = z.get(i);

                if(zgl.getMomentZgloszenia() <= suma) {
                    if(zgl.getPozycja() >= pozycja) {
                        kolejka.add(zgl);
                    } else {
                        temp.add(zgl);
                    }
                    z.remove(zgl);
                }
            }

            if(!kolejka.isEmpty()) {
                kolejka.sort(comp2);

                while(!kolejka.isEmpty() && kolejka.get(0).getPozycja() == pozycja) {
                    kolejka.remove(kolejka.get(0));
                    pozostale--;
                }
            }

            suma++;
            pozycja++;

            if(pozycja > rozmiar) {
                pozycja = 0;
                zawroty++;
                kolejka.addAll(temp);
                temp.clear();
            }
        }

        System.out.println("Pokonany dystans: " + suma);
        System.out.println("Zawrocenia: " + zawroty + "\n");
    }

    public void EDF(ArrayList<Zgloszenie> z) {
        System.out.println("EDF:");

        int pozycja = 0;
        int czas = 0;
        int suma = 0;
        int temp = 0;
        ArrayList<Integer> dystansy = new ArrayList<>();
        ArrayList<Zgloszenie> toDO = new ArrayList<>();
        Zgloszenie zgl;

        Comparator<Zgloszenie> comp1 = (o1, o2) -> {
            if (o1.getMomentZgloszenia() == o2.getMomentZgloszenia()) return 0;
            if (o1.getMomentZgloszenia() > o2.getMomentZgloszenia()) return 1;
            else return -1;
        };
        Comparator<Zgloszenie> comp2 = (o1, o2) -> {
            if (o1.getDeadline() == o2.getDeadline()) {
                if(o1.getMomentZgloszenia() == o2.getMomentZgloszenia()) {
                    return 0;
                } else {
                    if(o1.getMomentZgloszenia() > o2.getMomentZgloszenia()) return 1;
                    else return -1;
                }
            }
            if (o1.getDeadline() > o2.getDeadline()) return 1;
            else return -1;
        };

        z.sort(comp1);

        while(!z.isEmpty() || !toDO.isEmpty()) {
            for(int i = 0; i < z.size(); i++) {
                zgl = z.get(i);

                if(zgl.getMomentZgloszenia() <= czas) {
                    toDO.add(zgl);
                    z.remove(zgl);
                }
            }

            if(toDO.isEmpty()) {
                czas++;

            } else {
                toDO.sort(comp2);
                zgl = toDO.get(0);
                czas += Math.abs(zgl.getPozycja() - pozycja);
                suma += Math.abs(zgl.getPozycja() - pozycja);
                dystansy.add(zgl.getPozycja() - pozycja);
                pozycja = zgl.getPozycja();
                toDO.remove(zgl);
                temp++;
            }
        }
        System.out.println("Pokonany dystans: " + suma);
        double srednia = (double) suma/temp;
        System.out.println("Srednie wychylenie glowicy: " + srednia + "\n");
    }

    public void FDSCAN(ArrayList<Zgloszenie> z, int rozmiar) {
        System.out.println("FD-SCAN");

        int suma = 0;
        int pozycja = 0;
        int kierunek = 1;
        ArrayList<Zgloszenie> kolejka = new ArrayList<>();
        ArrayList<Zgloszenie> temp = new ArrayList<>();
        int zawroty = 0;
        int pozostale = z.size();
        Zgloszenie zgl;

        Comparator<Zgloszenie> comp1 = (o1, o2) -> {
            if (o1.getMomentZgloszenia() == o2.getMomentZgloszenia()) return 0;
            if (o1.getMomentZgloszenia() > o2.getMomentZgloszenia()) return 1;
            else return -1;
        };
        Comparator<Zgloszenie> comp2 = (o1, o2) -> {
            if (o1.getDeadline() == o2.getDeadline()) {
                if(o1.getPozycja() == o2.getPozycja()) {
                    return 0;
                } else {
                    if(o1.getPozycja() > o2.getPozycja()) return 1;
                    else return -1;
                }
            }
            if (o1.getDeadline() > o2.getDeadline()) return 1;
            else return -1;
        };
        Comparator<Zgloszenie> comp3 = (o1, o2) -> {
            if (o1.getDeadline() == o2.getDeadline()) {
                if(o1.getPozycja() == o2.getPozycja()) {
                    return 0;
                } else {
                    if(o1.getPozycja() > o2.getPozycja()) return -1;
                    else return 1;
                }
            }
            if (o1.getDeadline() > o2.getDeadline()) return -1;
            else return 1;
        };

        z.sort(comp1);

        while(pozostale > 0) {
            for(int i = 0; i < z.size(); i++) {
                zgl = z.get(i);

                if(zgl.getMomentZgloszenia() <= suma) {
                    if(zgl.getDeadline() != rozmiar+1 && Math.abs(zgl.getPozycja()-pozycja) > zgl.getDeadline()) {
                        System.out.println("Niemozliwy do obsluzenia\t" + "Aktualna pozycja glowicy: " + pozycja + "\tDocelowa pozycja: " + zgl.getPozycja() + "\tDeadline: " + zgl.getDeadline());

                    } else if(zgl.getDeadline() != rozmiar+1) {
                        if(pozycja > zgl.getPozycja()) {
                            zawroty++;
                            kierunek = -1;
                        } else {
                            zawroty++;
                            kierunek = 1;
                        }
                    }

                    if((kierunek > 0 && zgl.getPozycja() >= pozycja) || (kierunek < 0 && zgl.getPozycja() <= pozycja)) {
                        kolejka.add(zgl);
                    }
                    if((kierunek < 0 && zgl.getPozycja() > pozycja) || (kierunek > 0 && zgl.getPozycja() < pozycja)) {
                        temp.add(zgl);
                    }

                    z.remove(zgl);
                }
            }

            if(!kolejka.isEmpty()) {
                if(kierunek > 0) {
                    kolejka.sort(comp2);
                } else {
                    kolejka.sort(comp3);
                }
                while (!kolejka.isEmpty() && kolejka.get(0).getPozycja() == pozycja) {
                    kolejka.remove(kolejka.get(0));
                    pozostale--;
                }
            }

            suma++;
            pozycja += kierunek;

            if(pozycja == rozmiar || pozycja == 0) {
                kierunek = -kierunek;
                zawroty++;
                kolejka.addAll(temp);
                temp = new ArrayList<>();
            }
        }

        System.out.println("Pokonany dystans: " + suma);
        System.out.println("Zmiany kierunku: " + zawroty + "\n");

    }
}
