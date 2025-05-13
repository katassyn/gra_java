package org.example;

import java.util.*;

public class CityBuilder {
    public static void main(String[] args) {
        System.out.println("Witaj w City Builder - grze budowania miasta w terminalu!");
        new Gra().start();
    }
}

class Gra {
    private Scanner scanner;
    private Miasto miasto;
    private int dzien;
    private Random random;
    private int poziomTrudnosci;
    private boolean koniecGry;

    public Gra() {
        scanner = new Scanner(System.in);
        random = new Random();
        dzien = 1;
        koniecGry = false;
    }

    public void start() {
        powitanie();
        wybierzPoziomTrudnosci();
        inicjalizujMiasto();

        while (!koniecGry) {
            wyswietlStatystyki();
            pokazMenu();
            int wybor = pobierzWybor(1, 6);

            switch (wybor) {
                case 1:
                    budujBudynek();
                    break;
                case 2:
                    ulepszBudynek();
                    break;
                case 3:
                    zbierzZasoby();
                    break;
                case 4:
                    nastepnyDzien();
                    break;
                case 5:
                    pokazPomoc();
                    break;
                case 6:
                    koniecGry = true;
                    System.out.println("Dziękujemy za grę! Twoje miasto przetrwało " + dzien + " dni.");
                    break;
            }
        }
    }

    private void powitanie() {
        System.out.println("========================================");
        System.out.println("  BUDOWNICZY MIASTA - GRA STRATEGICZNA  ");
        System.out.println("========================================");
        System.out.println("Twoim zadaniem jest zbudowanie i rozwijanie miasta.");
        System.out.println("Zarządzaj zasobami, buduj budynki i reaguj na losowe wydarzenia.");
        System.out.println("Powodzenia w rozwijaniu swojego miasta!");
        System.out.println();
    }

    private void wybierzPoziomTrudnosci() {
        System.out.println("Wybierz poziom trudności:");
        System.out.println("1. Łatwy - więcej zasobów startowych, mniej wymagające cele");
        System.out.println("2. Średni - zbalansowana rozgrywka");
        System.out.println("3. Trudny - mniej zasobów, częstsze katastrofy, wyższe koszty");

        poziomTrudnosci = pobierzWybor(1, 3);
        System.out.println();
    }

    private void inicjalizujMiasto() {
        System.out.println("Jak chcesz nazwać swoje miasto?");
        String nazwa = scanner.nextLine();

        // Wartości startowe zależne od poziomu trudności
        int startowaPieniadze, startowaMaterialy, startowaPasek;
        double mnoznikZdarzen;

        switch (poziomTrudnosci) {
            case 1: // Łatwy
                startowaPieniadze = 1000;
                startowaMaterialy = 500;
                startowaPasek = 50;
                mnoznikZdarzen = 0.5;
                break;
            case 2: // Średni
                startowaPieniadze = 500;
                startowaMaterialy = 250;
                startowaPasek = 25;
                mnoznikZdarzen = 1.0;
                break;
            case 3: // Trudny
                startowaPieniadze = 300;
                startowaMaterialy = 150;
                startowaPasek = 15;
                mnoznikZdarzen = 1.5;
                break;
            default:
                startowaPieniadze = 500;
                startowaMaterialy = 250;
                startowaPasek = 25;
                mnoznikZdarzen = 1.0;
        }

        miasto = new Miasto(nazwa, startowaPieniadze, startowaMaterialy, startowaPasek, mnoznikZdarzen);
        System.out.println("Miasto " + nazwa + " zostało założone!");
        System.out.println();
    }

    private void wyswietlStatystyki() {
        System.out.println("========================================");
        System.out.println("Dzień: " + dzien + " | Miasto: " + miasto.getNazwa());
        System.out.println("========================================");
        System.out.println("Populacja: " + miasto.getPopulacja() + " osób");
        System.out.println("Zadowolenie: " + miasto.getZadowolenie() + "%");
        System.out.println("Pieniądze: " + miasto.getPieniadze() + " złotych");
        System.out.println("Materiały budowlane: " + miasto.getMaterialy() + " jednostek");
        System.out.println("Żywność: " + miasto.getZywnosc() + " jednostek");
        System.out.println("========================================");
        System.out.println("Liczba budynków: " + miasto.getLiczbaBudynkow());
        System.out.println("========================================");
        System.out.println();
    }

    private void pokazMenu() {
        System.out.println("Co chcesz zrobić?");
        System.out.println("1. Buduj nowy budynek");
        System.out.println("2. Ulepsz istniejący budynek");
        System.out.println("3. Zbierz zasoby");
        System.out.println("4. Następny dzień");
        System.out.println("5. Pomoc");
        System.out.println("6. Zakończ grę");
    }

    private int pobierzWybor(int min, int max) {
        int wybor = -1;
        while (wybor < min || wybor > max) {
            System.out.print("Wybierz opcję (" + min + "-" + max + "): ");
            try {
                wybor = Integer.parseInt(scanner.nextLine());
                if (wybor < min || wybor > max) {
                    System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Proszę wprowadzić liczbę.");
            }
        }
        return wybor;
    }

    private void budujBudynek() {
        System.out.println("Jakie budynki możesz wybudować:");
        System.out.println("1. Dom mieszkalny (100 złotych, 50 materiałów) - zwiększa populację o 10 osób");
        System.out.println("2. Farma (150 złotych, 30 materiałów) - produkuje 15 jedzenia dziennie");
        System.out.println("3. Kopalnia (200 złotych, 100 materiałów) - produkuje 20 materiałów dziennie");
        System.out.println("4. Ratusz (300 złotych, 200 materiałów) - zwiększa zadowolenie o 5%");
        System.out.println("5. Targ (250 złotych, 120 materiałów) - generuje 50 złotych dziennie");
        System.out.println("6. Park (150 złotych, 80 materiałów) - zwiększa zadowolenie o 10%");
        System.out.println("7. Powrót do menu głównego");

        int wybor = pobierzWybor(1, 7);
        if (wybor == 7) return;

        TypBudynku typ;
        switch (wybor) {
            case 1: typ = TypBudynku.DOM; break;
            case 2: typ = TypBudynku.FARMA; break;
            case 3: typ = TypBudynku.KOPALNIA; break;
            case 4: typ = TypBudynku.RATUSZ; break;
            case 5: typ = TypBudynku.TARG; break;
            case 6: typ = TypBudynku.PARK; break;
            default: return;
        }

        if (miasto.mozeBudowac(typ)) {
            miasto.budujBudynek(typ);
            System.out.println("Budynek " + typ.getNazwa() + " został wybudowany!");
        } else {
            System.out.println("Nie masz wystarczających zasobów, aby wybudować ten budynek.");
        }
        System.out.println();
    }

    private void ulepszBudynek() {
        List<Budynek> budynki = miasto.getBudynki();
        if (budynki.isEmpty()) {
            System.out.println("Nie masz jeszcze żadnych budynków do ulepszenia.");
            System.out.println();
            return;
        }

        System.out.println("Które budynki chcesz ulepszyć:");
        for (int i = 0; i < budynki.size(); i++) {
            Budynek b = budynki.get(i);
            System.out.println((i+1) + ". " + b.getTyp().getNazwa() + " (Poziom " + b.getPoziom() + ") - Koszt ulepszenia: " +
                    b.getKosztUlepszeniaPieniadze() + " złotych, " + b.getKosztUlepszeniaMaterialy() + " materiałów");
        }
        System.out.println((budynki.size()+1) + ". Powrót do menu głównego");

        int wybor = pobierzWybor(1, budynki.size()+1);
        if (wybor == budynki.size()+1) return;

        Budynek wybranyBudynek = budynki.get(wybor-1);
        if (miasto.mozeUlepszyc(wybranyBudynek)) {
            miasto.ulepszBudynek(wybranyBudynek);
            System.out.println("Budynek " + wybranyBudynek.getTyp().getNazwa() + " został ulepszony do poziomu " + wybranyBudynek.getPoziom() + "!");
        } else {
            System.out.println("Nie masz wystarczających zasobów, aby ulepszyć ten budynek.");
        }
        System.out.println();
    }

    private void zbierzZasoby() {
        int zebraneZloto = miasto.zbierzPieniadze();
        int zebraneMaterialy = miasto.zbierzMaterialy();
        int zebranaZywnosc = miasto.zbierzZywnosc();

        System.out.println("Zebrano następujące zasoby:");
        System.out.println("- " + zebraneZloto + " złotych");
        System.out.println("- " + zebraneMaterialy + " materiałów");
        System.out.println("- " + zebranaZywnosc + " jednostek żywności");
        System.out.println();
    }

    private void nastepnyDzien() {
        dzien++;
        miasto.aktualizujZasoby();

        // Sprawdzanie warunków przetrwania
        if (miasto.getZywnosc() <= 0) {
            miasto.zmniejszZadowolenie(15);
            System.out.println("UWAGA! Twoje miasto głoduje! Zadowolenie mieszkańców spadło o 15%.");
        }

        if (miasto.getZadowolenie() <= 0) {
            System.out.println("Zadowolenie mieszkańców spadło do 0%!");
            System.out.println("Mieszkańcy zbuntowali się i opuścili miasto. Koniec gry!");
            koniecGry = true;
            return;
        }

        // Losowe wydarzenia
        if (random.nextDouble() < (0.2 * miasto.getMnoznikZdarzen())) {
            generujLosoweDarzenie();
        }

        // Zużycie żywności
        int zuzycie = miasto.getPopulacja() / 10;
        miasto.zmniejszZywnosc(zuzycie);
        System.out.println("Twoi mieszkańcy zużyli " + zuzycie + " jednostek żywności.");

        System.out.println("Nastał nowy dzień!");
        System.out.println();
    }

    private void generujLosoweDarzenie() {
        int typ = random.nextInt(10);

        System.out.println("** LOSOWE WYDARZENIE **");

        switch (typ) {
            case 0:
                System.out.println("Wybuchł pożar w mieście! Stracono zasoby i uszkodzono budynki.");
                miasto.zmniejszPieniadze(random.nextInt(100) + 50);
                miasto.zmniejszMaterialy(random.nextInt(50) + 25);
                miasto.zmniejszZadowolenie(random.nextInt(10) + 5);
                break;
            case 1:
                System.out.println("Nastąpiła powódź! Uszkodzono część budynków i zapasów.");
                miasto.zmniejszMaterialy(random.nextInt(80) + 40);
                miasto.zmniejszZywnosc(random.nextInt(30) + 15);
                miasto.zmniejszZadowolenie(random.nextInt(8) + 4);
                break;
            case 2:
                System.out.println("Odkryto nowe złoża surowców! Zdobyto dodatkowe materiały.");
                miasto.zwiekszMaterialy(random.nextInt(100) + 50);
                miasto.zwiekszZadowolenie(random.nextInt(5) + 1);
                break;
            case 3:
                System.out.println("Festiwal miejski! Zwiększyło się zadowolenie mieszkańców, ale kosztowało to trochę pieniędzy.");
                miasto.zwiekszZadowolenie(random.nextInt(15) + 5);
                miasto.zmniejszPieniadze(random.nextInt(100) + 25);
                break;
            case 4:
                System.out.println("Grupa nowych osadników przybyła do miasta! Populacja się zwiększyła.");
                miasto.zwiekszPopulacje(random.nextInt(15) + 5);
                miasto.zwiekszZadowolenie(random.nextInt(5) + 1);
                break;
            case 5:
                System.out.println("Epidemia w mieście! Część mieszkańców zachorowała.");
                miasto.zmniejszPopulacje(random.nextInt(10) + 2);
                miasto.zmniejszZadowolenie(random.nextInt(10) + 5);
                miasto.zmniejszPieniadze(random.nextInt(50) + 25);
                break;
            case 6:
                System.out.println("Udany zbiór! Twoje farmy wyprodukwały więcej żywności niż zwykle.");
                miasto.zwiekszZywnosc(random.nextInt(50) + 25);
                miasto.zwiekszZadowolenie(random.nextInt(3) + 1);
                break;
            case 7:
                System.out.println("Nieurodzaj! Twoje farmy wyprodukwały mniej żywności niż zwykle.");
                miasto.zmniejszZywnosc(random.nextInt(40) + 20);
                miasto.zmniejszZadowolenie(random.nextInt(5) + 2);
                break;
            case 8:
                System.out.println("Otrzymałeś dotację od sąsiedniego miasta! Twoje fundusze wzrosły.");
                miasto.zwiekszPieniadze(random.nextInt(200) + 100);
                miasto.zwiekszZadowolenie(random.nextInt(5) + 1);
                break;
            case 9:
                System.out.println("Złodzieje okradli skarbiec miasta! Stracono część pieniędzy.");
                miasto.zmniejszPieniadze(random.nextInt(150) + 50);
                miasto.zmniejszZadowolenie(random.nextInt(10) + 2);
                break;
        }
        System.out.println();
    }

    private void pokazPomoc() {
        System.out.println("========== POMOC ==========");
        System.out.println("City Builder to gra, w której zarządzasz własnym miastem.");
        System.out.println("Twoim zadaniem jest:");
        System.out.println("- Budowanie różnych budynków, które zapewniają zasoby i korzyści");
        System.out.println("- Dbanie o zadowolenie mieszkańców");
        System.out.println("- Reagowanie na losowe wydarzenia");
        System.out.println("- Zarządzanie zasobami (pieniądze, materiały, żywność)");
        System.out.println();
        System.out.println("Porady:");
        System.out.println("- Upewnij się, że masz wystarczająco żywności dla swoich mieszkańców");
        System.out.println("- Buduj zrównoważone miasto z różnymi typami budynków");
        System.out.println("- Utrzymuj wysoki poziom zadowolenia mieszkańców");
        System.out.println("- Zbieraj zasoby regularnie");
        System.out.println("=============================");
        System.out.println();
    }
}

enum TypBudynku {
    DOM("Dom mieszkalny", 100, 50, 10, 0, 0, 0, 5),
    FARMA("Farma", 150, 30, 0, 0, 15, 0, 3),
    KOPALNIA("Kopalnia", 200, 100, 0, 20, 0, 0, -2),
    RATUSZ("Ratusz", 300, 200, 0, 0, 0, 5, 0),
    TARG("Targ", 250, 120, 0, 0, 0, 2, 50),
    PARK("Park", 150, 80, 0, 0, 0, 10, 0);

    private final String nazwa;
    private final int kosztPieniadze;
    private final int kosztMaterialy;
    private final int bonusPopulacja;
    private final int bonusMaterialy;
    private final int bonusZywnosc;
    private final int bonusZadowolenie;
    private final int bonusPieniadze;

    TypBudynku(String nazwa, int kosztPieniadze, int kosztMaterialy, int bonusPopulacja,
               int bonusMaterialy, int bonusZywnosc, int bonusZadowolenie, int bonusPieniadze) {
        this.nazwa = nazwa;
        this.kosztPieniadze = kosztPieniadze;
        this.kosztMaterialy = kosztMaterialy;
        this.bonusPopulacja = bonusPopulacja;
        this.bonusMaterialy = bonusMaterialy;
        this.bonusZywnosc = bonusZywnosc;
        this.bonusZadowolenie = bonusZadowolenie;
        this.bonusPieniadze = bonusPieniadze;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getKosztPieniadze() {
        return kosztPieniadze;
    }

    public int getKosztMaterialy() {
        return kosztMaterialy;
    }

    public int getBonusPopulacja() {
        return bonusPopulacja;
    }

    public int getBonusMaterialy() {
        return bonusMaterialy;
    }

    public int getBonusZywnosc() {
        return bonusZywnosc;
    }

    public int getBonusZadowolenie() {
        return bonusZadowolenie;
    }

    public int getBonusPieniadze() {
        return bonusPieniadze;
    }
}

class Budynek {
    private TypBudynku typ;
    private int poziom;

    public Budynek(TypBudynku typ) {
        this.typ = typ;
        this.poziom = 1;
    }

    public TypBudynku getTyp() {
        return typ;
    }

    public int getPoziom() {
        return poziom;
    }

    public void podniesPozioM() {
        poziom++;
    }

    public int getKosztUlepszeniaPieniadze() {
        return (int)(typ.getKosztPieniadze() * 0.7 * poziom);
    }

    public int getKosztUlepszeniaMaterialy() {
        return (int)(typ.getKosztMaterialy() * 0.7 * poziom);
    }

    public int getBonusPopulacja() {
        return (int)(typ.getBonusPopulacja() * getMnoznikPoziomu());
    }

    public int getBonusMaterialy() {
        return (int)(typ.getBonusMaterialy() * getMnoznikPoziomu());
    }

    public int getBonusZywnosc() {
        return (int)(typ.getBonusZywnosc() * getMnoznikPoziomu());
    }

    public int getBonusZadowolenie() {
        return (int)(typ.getBonusZadowolenie() * getMnoznikPoziomu());
    }

    public int getBonusPieniadze() {
        return (int)(typ.getBonusPieniadze() * getMnoznikPoziomu());
    }

    private double getMnoznikPoziomu() {
        return 1.0 + (poziom - 1) * 0.5;
    }
}

class Miasto {
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
