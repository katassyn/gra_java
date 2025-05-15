package org.example;

import java.util.*;

public class Gra {
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