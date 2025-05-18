package org.example;

import java.util.*;

public class Miasto {
    private String nazwa;
    private int pieniadze;
    private int materialy;
    private int zywnosc;
    private int populacja;
    private int zadowolenie;
    private double mnoznikZdarzen;
    private List<Budynek> budynki;

    public Miasto(String nazwa, int pieniadze, int materialy, int zywnosc, double mnoznikZdarzen) {
        this.nazwa = nazwa;
        this.pieniadze = pieniadze;
        this.materialy = materialy;
        this.zywnosc = zywnosc;
        this.populacja = 10;
        this.zadowolenie = 50;
        this.mnoznikZdarzen = mnoznikZdarzen;
        this.budynki = new ArrayList<>();
    }

    public Miasto(String nazwa, int pieniadze, int materialy, int zywnosc, int populacja,
                  int zadowolenie, double mnoznikZdarzen, List<Budynek> budynki) {
        this.nazwa = nazwa;
        this.pieniadze = pieniadze;
        this.materialy = materialy;
        this.zywnosc = zywnosc;
        this.populacja = populacja;
        this.zadowolenie = zadowolenie;
        this.mnoznikZdarzen = mnoznikZdarzen;
        this.budynki = budynki;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getPieniadze() {
        return pieniadze;
    }

    public int getMaterialy() {
        return materialy;
    }

    public int getZywnosc() {
        return zywnosc;
    }

    public int getPopulacja() {
        return populacja;
    }

    public int getZadowolenie() {
        return zadowolenie;
    }

    public double getMnoznikZdarzen() {
        return mnoznikZdarzen;
    }

    public int getLiczbaBudynkow() {
        return budynki.size();
    }

    public List<Budynek> getBudynki() {
        return budynki;
    }

    public boolean mozeBudowac(TypBudynku typ) {
        return pieniadze >= typ.getKosztPieniadze() && materialy >= typ.getKosztMaterialy();
    }

    public void budujBudynek(TypBudynku typ) {
        if (mozeBudowac(typ)) {
            pieniadze -= typ.getKosztPieniadze();
            materialy -= typ.getKosztMaterialy();

            Budynek nowyBudynek = new Budynek(typ);
            budynki.add(nowyBudynek);

            populacja += nowyBudynek.getBonusPopulacja();
            zadowolenie = Math.min(100, zadowolenie + nowyBudynek.getBonusZadowolenie());
        }
    }

    public boolean mozeUlepszyc(Budynek budynek) {
        return pieniadze >= budynek.getKosztUlepszeniaPieniadze() &&
                materialy >= budynek.getKosztUlepszeniaMaterialy();
    }

    public void ulepszBudynek(Budynek budynek) {
        if (mozeUlepszyc(budynek)) {
            pieniadze -= budynek.getKosztUlepszeniaPieniadze();
            materialy -= budynek.getKosztUlepszeniaMaterialy();

            // Zapamiętaj stare bonusy
            int staraBonusPopulacja = budynek.getBonusPopulacja();
            int staryBonusZadowolenie = budynek.getBonusZadowolenie();

            budynek.podniesPozioM();

            // Dodaj tylko różnicę bonusów
            populacja += budynek.getBonusPopulacja() - staraBonusPopulacja;
            zadowolenie = Math.min(100, zadowolenie + (budynek.getBonusZadowolenie() - staryBonusZadowolenie));
        }
    }

    public int zbierzPieniadze() {
        int suma = 0;
        for (Budynek b : budynki) {
            suma += b.getBonusPieniadze();
        }
        pieniadze += suma;
        return suma;
    }

    public int zbierzMaterialy() {
        int suma = 0;
        for (Budynek b : budynki) {
            suma += b.getBonusMaterialy();
        }
        materialy += suma;
        return suma;
    }

    public int zbierzZywnosc() {
        int suma = 0;
        for (Budynek b : budynki) {
            suma += b.getBonusZywnosc();
        }
        zywnosc += suma;
        return suma;
    }

    public void aktualizujZasoby() {
        // Automatyczne zbieranie niewielkiej ilości zasobów każdego dnia
        pieniadze += populacja / 20;
        materialy += populacja / 50;
        zywnosc += populacja / 30;
    }

    public void zwiekszPieniadze(int ilosc) {
        pieniadze += ilosc;
    }

    public void zmniejszPieniadze(int ilosc) {
        pieniadze = Math.max(0, pieniadze - ilosc);
    }

    public void zwiekszMaterialy(int ilosc) {
        materialy += ilosc;
    }

    public void zmniejszMaterialy(int ilosc) {
        materialy = Math.max(0, materialy - ilosc);
    }

    public void zwiekszZywnosc(int ilosc) {
        zywnosc += ilosc;
    }

    public void zmniejszZywnosc(int ilosc) {
        zywnosc = Math.max(0, zywnosc - ilosc);
    }

    public void zwiekszPopulacje(int ilosc) {
        populacja += ilosc;
    }

    public void zmniejszPopulacje(int ilosc) {
        populacja = Math.max(0, populacja - ilosc);
    }

    public void zwiekszZadowolenie(int ilosc) {
        zadowolenie = Math.min(100, zadowolenie + ilosc);
    }

    public void zmniejszZadowolenie(int ilosc) {
        zadowolenie = Math.max(0, zadowolenie - ilosc);
    }
}