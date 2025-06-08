package org.example;

import java.io.*;
import java.util.*;

public class StanGry {
    private String nazwa;
    private int pieniadze;
    private int materialy;
    private int materialySpec;
    private int zywnosc;
    private int populacja;
    private int zadowolenie;
    private double mnoznikZdarzen;
    private List<Budynek> budynki;
    private int dzien;
    private int rasa;


    public StanGry(Miasto miasto, int dzien,int rasa) {
        this.nazwa = miasto.getNazwa();
        this.pieniadze = miasto.getPieniadze();
        this.materialy = miasto.getMaterialy();
        this.materialySpec = miasto.getMaterialySpec();
        this.zywnosc = miasto.getZywnosc();
        this.populacja = miasto.getPopulacja();
        this.zadowolenie = miasto.getZadowolenie();
        this.mnoznikZdarzen = miasto.getMnoznikZdarzen();
        this.budynki = new ArrayList<>(miasto.getBudynki());
        this.dzien = dzien;
        this.rasa = rasa;
    }

    public StanGry(String nazwa, int pieniadze, int materialy,int materialySpec, int zywnosc, int populacja,
                   int zadowolenie, double mnoznikZdarzen, int dzien, int rasa,List<Budynek> budynki) {
        this.nazwa = nazwa;
        this.pieniadze = pieniadze;
        this.materialy = materialy;
        this.materialySpec = materialySpec;
        this.zywnosc = zywnosc;
        this.populacja = populacja;
        this.zadowolenie = zadowolenie;
        this.mnoznikZdarzen = mnoznikZdarzen;
        this.dzien = dzien;
        this.rasa =rasa;
        this.budynki = budynki;
    }

    public int getDzien() {
        return dzien;
    }

    public int getRasa() {
        return rasa;
    }

    public void zapiszDoPliku(String nazwaZapisu) throws IOException {
        try (PrintWriter plik = new PrintWriter(new FileWriter(nazwaZapisu))) {
            plik.println(nazwa);
            plik.println(pieniadze);
            plik.println(materialy);
            plik.println(materialySpec);
            plik.println(zywnosc);
            plik.println(populacja);
            plik.println(zadowolenie);
            plik.println(mnoznikZdarzen);
            plik.println(dzien);
            plik.println(rasa);
            plik.println(budynki.size());
            for (Budynek b : budynki) {
                plik.println(b.getTyp().name());
                plik.println(b.getPoziom());
            }
        }
    }

    public static StanGry wczytajZPliku(String nazwaZapisu) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(nazwaZapisu))) {
            String nazwa = reader.readLine();
            int pieniadze = Integer.parseInt(reader.readLine());
            int materialy = Integer.parseInt(reader.readLine());
            int materialyspec = Integer.parseInt(reader.readLine());
            int zywnosc = Integer.parseInt(reader.readLine());
            int populacja = Integer.parseInt(reader.readLine());
            int zadowolenie = Integer.parseInt(reader.readLine());
            double mnoznikZdarzen = Double.parseDouble(reader.readLine());
            int dzien = Integer.parseInt(reader.readLine());
            int rasa = Integer.parseInt(reader.readLine());
            int liczbaBudynkow = Integer.parseInt(reader.readLine());
            List<Budynek> budynki = new ArrayList<>();
            for (int i = 0; i < liczbaBudynkow; i++) {
                TypBudynku typ = TypBudynku.valueOf(reader.readLine());
                int poziom = Integer.parseInt(reader.readLine());
                Budynek budynek = new Budynek(typ);
                for (int j = 1; j < poziom; j++) {
                    budynek.podniesPozioM();
                }
                budynki.add(budynek);
            }

            StanGry stan = new StanGry(nazwa, pieniadze, materialy,materialyspec, zywnosc, populacja,
                    zadowolenie, mnoznikZdarzen, dzien, rasa, budynki);
            return stan;
        }
    }

    public Miasto odtworzMiasto() {
        return new Miasto(nazwa, pieniadze, materialy,materialySpec, zywnosc, populacja,
                zadowolenie, mnoznikZdarzen, budynki);
    }
}



