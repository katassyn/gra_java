package org.example;

import java.io.IOException;
import java.util.*;

public class Gra {
    private Scanner scanner;
    private Miasto miasto;
    private int dzien;
    private Random random;
    private int poziomTrudnosci;
    private int rasa;
    private boolean koniecGry;

    //                                             FUNKCYJNE/MENU

    public Gra() {
        scanner = new Scanner(System.in);
        random = new Random();
        dzien = 1;
        koniecGry = false;
    }

    public void menuGlowne() {
        powitanie();
        boolean wyjscie = false;

        while (!wyjscie) {
            pokazMenuGlowne();
            int wybor = pobierzWybor(1, 4);
            System.out.println();

            switch (wybor) {
                case 1:
                    wyjscie = true;
                    nowaGra();
                    break;
                case 2:
                    wyjscie = true;
                    wczytajGra();
                    break;
                case 3:
                    pokazPomoc();
                    break;
                case 4:
                    wyjscie = true;
                    System.out.println("Do zobaczenia następnym razem!");
                    break;
            }
        }
    }

    private void nowaGra() {
        wybierzPoziomTrudnosci();
        wybierzRase();
        inicjalizujMiasto();
        start();
    }

    private void wczytajGra() {
        System.out.println("Wybierz slot zapisu:");
        int wybor = pobierzWybor(1, 3);
        System.out.println();

        String nazwaPliku = "zapis" + wybor + ".txt";

        try {
            StanGry stan = StanGry.wczytajZPliku(nazwaPliku);
            this.miasto = stan.odtworzMiasto();
            this.dzien = stan.getDzien();
            this.rasa = stan.getRasa();
            System.out.println("Wczytano grę z zapisu " + wybor + "!");
            System.out.println();
            start();
        } catch (IOException e) {
            System.out.println("Nie udało się wczytać zapisu: " + e.getMessage());
            System.out.println();
        }
    }

    private void start() {
        while (!koniecGry) {
            wyswietlStatystyki();
            pokazMenu();
            int wybor = pobierzWybor(1, 6);
            System.out.println();

            switch (wybor) {
                case 1:
                    budujBudynek();
                    break;
                case 2:
                    ulepszBudynek();
                    break;
                case 3:
                    istniejaceBudynki();
                    break;
                case 4:
                    zbierzZasoby();
                    nastepnyDzien();
                    break;
                case 5:
                    pokazPomocBudynki();
                    break;
                case 6:
                    wyjscie();
                    break;
            }
        }
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


    private void powitanie() {
        System.out.println("========================================");
        System.out.println("  BUDOWNICZY OSADY - GRA STRATEGICZNA  ");
        System.out.println("========================================");
        System.out.println("Twoim zadaniem jest zbudowanie i rozwijanie twojej osady.");
        System.out.println("Zarządzaj zasobami, buduj budynki i reaguj na losowe wydarzenia jedną z 4 unikalnych ras.");
        System.out.println("Powodzenia w rozwijaniu swojego małego królestwa!");
        System.out.println();
    }

    private void pokazMenuGlowne() {
        System.out.println("========== MENU ==========");
        System.out.println("1. Nowa gra");
        System.out.println("2. Wczytaj zapis");
        System.out.println("3. Pomoc");
        System.out.println("4. Wyjście");
    }

    private void pokazPomoc() {
        System.out.println("========== POMOC ==========");
        System.out.println("Fantasy City Builder to gra, w której zarządzasz własnym fantastycznym miastem.");
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

    private void pokazPomocBudynki() {
        System.out.println("========== POMOC - BUDYNKI ==========");
        System.out.println("Lista budynków wraz z ich zastosowaniem:");

        switch (rasa) {
            case 1:
                System.out.println("Ratusz - podstawowy budynek każdego miasta, jego poziom ogranicza liczbę i poziom budynków które możesz wybudować");
                System.out.println("Liczba bydynków 2 x poziom ratusza, maksymalny poziom budynku = poziom ratusza ");
                System.out.println("");
                System.out.println("Dom - budynek zwiększający liczbę miesdzkańców osady");
                System.out.println("Dodatkowo generuje - złota i + zadowolenia dziennie");
                System.out.println("");
                System.out.println("Pole uprawne - pozwala na uprawę jedzenia");
                System.out.println("Dodatkowo generuje - złota i + zadowolenia dziennie");
                System.out.println("");
                System.out.println("Kopalnia kamienia - pozwala na wydobycie kamienia");
                System.out.println("Dodatkowo generuje - złota  i  zadowolenia dziennie");
                System.out.println("");
                System.out.println("Kopalnia złota - pozwala na wydobycie złota");
                System.out.println("Dodatkowo generuje + zadowolenia dziennie");
                System.out.println("");
                System.out.println("Światynia - zwieksza zadowolenie mieszkańców");
                System.out.println("Dodatkowo generuje + złota dziennie");
                System.out.println("");
        }
    }

    private void wyjscie() {
        System.out.println("Czy chcesz zapisać stan gry?");
        System.out.println("1. Tak");
        System.out.println("2. Nie");;

        int wybor = pobierzWybor(1, 2);
        System.out.println();

        if(wybor == 1) {
            System.out.println("Wybierz slot zapisu:");
            System.out.println("||UWAGA|| -- Spowoduje to usunięcie poprzedniego zapisu w tym slocie! -- ||UWAGA||");
            int nrZapisu = pobierzWybor(1, 3);
            String nazwaZapisu = "zapis" + nrZapisu + ".txt";
            System.out.println();

            try {
                StanGry stan = new StanGry(miasto,dzien,rasa);
                stan.zapiszDoPliku(nazwaZapisu);
                koniecGry = true;
                System.out.println("Dziękujemy za grę! Stan gry został zapisany.");
                System.out.println();
            } catch (IOException e) {
                System.out.println("Błąd podczas zapisu gry: " + e.getMessage());
            }

        }else {
            koniecGry = true;
            System.out.println("Dziękujemy za grę! Twoja osada przetrwała " + dzien + " dni.");
            System.out.println();
        }
    }

    //                                             GRA

    private void wybierzPoziomTrudnosci() {
        System.out.println("Wybierz poziom trudności:");
        System.out.println("1. Łatwy - więcej zasobów startowych, mniej wymagające cele");
        System.out.println("2. Średni - zbalansowana rozgrywka");
        System.out.println("3. Trudny - mniej zasobów, częstsze katastrofy, wyższe koszty");

        poziomTrudnosci = pobierzWybor(1, 3);
        System.out.println();
    }

    private void wybierzRase() {
        System.out.println("Wybierz rasę:");
        System.out.println("1. Ludzie - # ");
        System.out.println("2. Elfy - #");
        System.out.println("3. Orki - #");
        System.out.println("4. Nieumarli - #");

        rasa = pobierzWybor(1, 4);
        System.out.println();
    }

    private String nazwaRasy(int rasa) {
        switch (rasa) {
            case 1: return "Ludzie";
            case 2: return "Elfy";
            case 3: return "Orki";
            case 4: return "Nieumarli";
            default: return "Nieznana";
        }
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
        System.out.println("Dzień: " + dzien + " | Miasto: " + miasto.getNazwa() + " | Rasa: " + nazwaRasy(rasa));
        System.out.println("========================================");
        System.out.println("Populacja: " + miasto.getPopulacja() + " osób");
        System.out.println("Zadowolenie: " + miasto.getZadowolenie() + "%");
        System.out.println("Pieniądze: " + miasto.getPieniadze() + " złota");
        System.out.println("Materiały budowlane: " + miasto.getMaterialy() + " kamienia");
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
        System.out.println("3. Istniejące budynki");
        System.out.println("4. Następny dzień");
        System.out.println("5. Pomoc - lista budynków i ich zastosowania");
        System.out.println("6. Wyjście");
    }

    public int getPoziomRatusza() {
        Budynek ratusz = miasto.getBudynki().get(0);
        return ratusz.getPoziom();
    }


    private void budujBudynek() {
        if(miasto.getLiczbaBudynkow() -1 >= getPoziomRatusza()*2) {
            System.out.println("Nie możesz wybudować wiecej budynków, zwiększ poziom ratusza!");
            System.out.println();
        }else{
            System.out.println("Jakie budynki możesz wybudować:");
            System.out.println("1. Dom (100 złota, 50 kamienia) - zwiększa populację o 10 osób");
            System.out.println("2. Pole uprawne (150 złota, 30 kamienia) - produkuje 15 jedzenia dziennie");
            System.out.println("3. Kopalnia kamienia(200 złota, 100 kamienia) - produkuje 20 kamienia dziennie");
            System.out.println("4. Kopalnia złota (250 złota, 120 kamienia) - generuje 50 złota dziennie");
            System.out.println("5. Światynia (150 złota, 80 kamienia) - zwiększa zadowolenie o 10%");
            System.out.println("6. Powrót do menu głównego");

            int wybor = pobierzWybor(1, 6);
            if (wybor == 6) return;

            TypBudynku typ;
            switch (wybor) {
                case 1: typ = TypBudynku.DOM; break;
                case 2: typ = TypBudynku.POLE; break;
                case 3: typ = TypBudynku.KOPALNIAK; break;
                case 4: typ = TypBudynku.KOPALNIAZ; break;
                case 5: typ = TypBudynku.SWIATYNIA; break;
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
    }

    private void istniejaceBudynki() {
        List<Budynek> budynki = miasto.getBudynki();
        System.out.println("Istniejące budynki:");
        for (int i = 0; i < budynki.size(); i++) {
            Budynek b = budynki.get(i);
            System.out.println((i+1) + ". " + b.getTyp().getNazwa() + " (Poziom " + b.getPoziom() + ")");
        }

        System.out.println();
    }

    private void ulepszBudynek() {
        List<Budynek> budynki = miasto.getBudynki();
        System.out.println("Które budynki chcesz ulepszyć:");
        for (int i = 0; i < budynki.size(); i++) {
            Budynek b = budynki.get(i);
            System.out.println((i+1) + ". " + b.getTyp().getNazwa() + " (Poziom " + b.getPoziom() + ") - Koszt ulepszenia: " +
                    b.getKosztUlepszeniaPieniadze() + " złota, " + b.getKosztUlepszeniaMaterialy() + " kamienia");
        }
        System.out.println((budynki.size()+1) + ". Powrót do menu głównego");

        int wybor = pobierzWybor(1, budynki.size()+1);
        if (wybor == budynki.size()+1) return;
        System.out.println("");

        Budynek wybranyBudynek = budynki.get(wybor-1);
        if (wybranyBudynek.getPoziom() >= getPoziomRatusza() && wybranyBudynek.getTyp() != TypBudynku.RATUSZ) {
            System.out.println("Budowla może mieć maksymalnie poziom ratusza, zwiększ poziom ratusza!");
        }else if (miasto.mozeUlepszyc(wybranyBudynek)) {
            miasto.ulepszBudynek(wybranyBudynek);
            System.out.println("Budynek " + wybranyBudynek.getTyp().getNazwa() + " został ulepszony do poziomu " + wybranyBudynek.getPoziom() + "!");
        }else {
            System.out.println("Nie masz wystarczających zasobów, aby ulepszyć ten budynek.");
        }
        System.out.println();
    }

    private void zbierzZasoby() {
        int zebraneZloto = miasto.zbierzPieniadze();
        int zebraneMaterialy = miasto.zbierzMaterialy();
        int zebranaZywnosc = miasto.zbierzZywnosc();

        System.out.println("Zebrano następujące zasoby:");
        System.out.println("- " + zebraneZloto + " złota");
        System.out.println("- " + zebraneMaterialy + " kamienia");
        System.out.println("- " + zebranaZywnosc + " jedzenia");
        System.out.println();
    }

    private void nastepnyDzien() {
        dzien++;

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
        System.out.println("Twoi mieszkańcy zużyli " + zuzycie + " jedzenia.");

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
                System.out.println("Udany zbiór! Twoje pola uprawne wyprodukwały więcej żywności niż zwykle.");
                miasto.zwiekszZywnosc(random.nextInt(50) + 25);
                miasto.zwiekszZadowolenie(random.nextInt(3) + 1);
                break;
            case 7:
                System.out.println("Nieurodzaj! Twoje pola uprawne wyprodukwały mniej żywności niż zwykle.");
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
}