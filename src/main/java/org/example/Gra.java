package org.example;

import java.io.IOException;
import java.util.*;

public class Gra {
    private Scanner scanner;
    private Miasto miasto;
    private int dzien;
    private Random random;
    private int poziomTrudnosci;
    private Rasa rasa;
    private boolean koniecGry;
    private boolean specjalny;

    //                                             FUNKCYJNE/MENU

    public Gra() {
        scanner = new Scanner(System.in);
        random = new Random();
        dzien = 1;
        koniecGry = false;
        specjalny = false;
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
                    pokazPomocGlowne();
                    break;
                case 4:
                    wyjscie = true;
                    System.out.println("Do zobaczenia następnym razem!");
                    break;
            }
        }
    }

    private void nowaGra() {
        wybierzRase();
        wybierzPoziomTrudnosci();
        inicjalizujMiasto();
        start();
    }

    private void wczytajGra() {
        StanGry[] stany = new StanGry[3];
        Rasa[] rasy = new Rasa[3];
        Miasto[] miasta = new Miasto[3];
        int[] dni = new int[3];

        System.out.println("====== DOSTĘPNE ZAPISY GRY ======");

        for (int i = 0; i < 3; i++) {
            String nazwaPliku = "zapis" + (i + 1) + ".txt";
            try {
                stany[i] = StanGry.wczytajZPliku(nazwaPliku);
                miasta[i] = stany[i].odtworzMiasto();
                dni[i] = stany[i].getDzien();
                rasy[i] = Rasa.fromBitmask(stany[i].getRasa());

                System.out.println((i+1) + ". Slot [" + (i + 1) + "] Nazwa: " + miasta[i].getNazwa()
                        + " / Rasa: " + rasy[i].getNazwa() + " / Dzień: " + dni[i]);
            } catch (IOException e) {
                System.out.println((i+1) + ". Slot [" + (i + 1) + "] Brak zapisu gry.");
            }
        }
        System.out.println("4. Powrót do menu głównego");
        System.out.println();
        int wybor = pobierzWybor(1, 4);
        System.out.println();
        if(wybor ==4) {menuGlowne();}
        String nazwaPliku = "zapis" + wybor + ".txt";

        try {
            StanGry stan = StanGry.wczytajZPliku(nazwaPliku);
            this.miasto = stan.odtworzMiasto();
            this.dzien = stan.getDzien();
            this.rasa = Rasa.fromBitmask(stan.getRasa());
            System.out.println("Wczytano grę z zapisu " + wybor + "!");
            System.out.println();
            start();
        } catch (IOException e) {
            System.out.println("Nie udało się wczytać zapisu: " + e.getMessage());
            System.out.println();
            menuGlowne();
        }
    }

    private void start() {
        while (!koniecGry) {
            wyswietlStatystyki();
            int wybor = pokazMenu();
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
                default:
                    if(rasa.getBitmask()==16) {
                        switch (wybor) {
                            case 4:
                                wypuscHorde();
                                break;
                            case 5:
                                zbierzZasoby();
                                nastepnyDzien();
                                break;
                            case 6:
                                pokazPomoc();
                                break;
                            case 7:
                                wyjscie();
                                break;
                        }
                    }else if(rasa.getBitmask()==32) {
                        switch (wybor) {
                            case 4:
                                lowyKrwi();
                                break;
                            case 5:
                                oczyscKrew();
                                break;
                            case 6:
                                zbierzZasoby();
                                nastepnyDzien();
                                break;
                            case 7:
                                pokazPomoc();
                                break;
                            case 8:
                                wyjscie();
                                break;
                        }
                    } else {
                        switch (wybor) {
                            case 4:
                                zbierzZasoby();
                                nastepnyDzien();
                                break;
                            case 5:
                                pokazPomoc();
                                break;
                            case 6:
                                wyjscie();
                                break;
                        }
                    }
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
        System.out.println("Zarządzaj zasobami, buduj budynki i reaguj na losowe wydarzenia jedną z 6 unikalnych ras.");
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

    private void pokazPomocGlowne() {
        boolean wyjscie = false;

        while (!wyjscie) {
            System.out.println("========== POMOC ==========");
            System.out.println("1. POMOC - GRA");
            System.out.println("2. POMOC - RASY");
            System.out.println("3. Powrót do menu głównego");
            int wybor = pobierzWybor(1, 3);
            System.out.println();

            switch (wybor) {
                case 1:
                    pokazPomocGra();
                    break;
                case 2:
                    pokazPomocRasy();
                    break;
                case 3:
                    wyjscie = true;
                    break;
            }
        }
    }

    private void pokazPomoc() {
        boolean wyjscie = false;

        while (!wyjscie) {
            System.out.println("========== POMOC ==========");
            System.out.println("1. POMOC - GRA");
            System.out.println("2. POMOC - BUDYNKI");
            System.out.println("3. Powrót do menu głównego");
            int wybor = pobierzWybor(1, 3);
            System.out.println();

            switch (wybor) {
                case 1:
                    pokazPomocGra();
                    break;
                case 2:
                    pokazPomocBudynki();
                    break;
                case 3:
                    wyjscie = true;
                    break;
            }
        }
    }

    private void pokazPomocGra() {
        System.out.println("========== POMOC - GRA ==========");
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
        System.out.println("Pieniądze, materiały oraz żywność to zasoby generowane każdego dnia, zadowolenie oraz liczba mieszkańców zmienia się tylko po wybudowaniu/ulepszeniu budynku.");
        System.out.println("Lista budynków dostępnych dla rasy: " + rasa.getNazwa());
        System.out.println();

        for (TypBudynku typ : TypBudynku.values()) {
            if (typ.jestRatuszem()) {continue;}

            if ((typ.getBitmaskRasy() & rasa.getBitmask()) != 0) {
                String info = typ.getNazwa() + " (" + typ.getTyp() + ")";

                List<String> statystyki = new ArrayList<>();
                if (typ.getKosztPieniadze() != 0) statystyki.add(typ.getKosztPieniadze() + " " + rasa.getPieniadze());
                if (typ.getKosztMaterialy() != 0) statystyki.add(typ.getKosztMaterialy() + " " + rasa.getMaterialy());
                if (typ.getKosztMaterialySpec() != 0) statystyki.add(typ.getKosztMaterialySpec() + " " + rasa.getMaterialySpec());

                if (!statystyki.isEmpty()) {
                    info += " - Koszt wybudowania: " + String.join(" / ", statystyki);
                }

                statystyki = new ArrayList<>();
                if (typ.getBonusPieniadze() != 0) statystyki.add((typ.getBonusPieniadze() >= 0 ? "+" : "") +typ.getBonusPieniadze() + " " + rasa.getPieniadze());
                if (typ.getBonusMaterialy() != 0) statystyki.add((typ.getBonusMaterialy() >= 0 ? "+" : "") +typ.getBonusMaterialy() + " " + rasa.getMaterialy());
                if (typ.getBonusMaterialySpec() != 0) statystyki.add((typ.getBonusMaterialySpec() >= 0 ? "+" : "") +typ.getBonusMaterialySpec() + " " + rasa.getMaterialySpec());
                if (typ.getBonusZywnosc() != 0) statystyki.add((typ.getBonusZywnosc() >= 0 ? "+" : "") +typ.getBonusZywnosc() + " " + rasa.getZywnosc());
                if (typ.getBonusZadowolenie() != 0) statystyki.add("+"+typ.getBonusZadowolenie() + "% Zadowolenia");
                if (typ.getBonusPopulacja() != 0) statystyki.add("+"+typ.getBonusPopulacja() + " Populacji");

                if (!statystyki.isEmpty()) {
                    info += "  -  Statystyki: " + String.join(" / ", statystyki);
                }

                if(typ.getNazwa().equalsIgnoreCase("Horda")) {
                    info += " - pozwala wysłać nieumarłych na plądrowanie, Zapewnia: (0-2)*populacja %zadowolenia";
                }
                if(typ.getNazwa().equalsIgnoreCase("Oczyszczalnia Krwi")) {
                    info += " - pozwala oczyścić zanieczyszczoną krew, Koszt: 10 Zanieczyszczonej Krwi / Szansa: 70% na 5 Fiolek Czystej Krwi";
                }
                if(typ.getNazwa().equalsIgnoreCase("Koszary Wojowników Krwi")) {
                    info += " - pozwala wysłać wojowników na łowy, Zapewnia: (0-1)*populacja Fiolek Czystej Krwi / (0-4)*populacja Zanieczyszczonej Krwi / (0-9) Nowych Wampirów";
                }


                System.out.println(info);
                System.out.println();
            }
        }
    }

    private void pokazPomocRasy() {
        System.out.println("========== POMOC - RASY ==========");
        System.out.println("W Fantasy City Builder możesz zagrać jedną z 6 unikalnych ras");
        System.out.println("Rasy róznią się stylem gry, zasobami oraz budynkami");
        System.out.println("Dostępne rasy: ");
        System.out.println();
        System.out.println("- Ludzie");
        System.out.println("Wszechstronna rasa o zbalansowanych surowcach i możliwościach rozwoju");
        System.out.println();
        System.out.println("- Elfy");
        System.out.println("Magiczne istoty żyjące w harmonii z naturą, wykorzystujące magię i jagody");
        System.out.println();
        System.out.println("- Krasnoludy");
        System.out.println("Mistrzowie kopalń i rzemiosła, gromadzący diamenty i żelazo");
        System.out.println();
        System.out.println("- Orki");
        System.out.println("Dzicy wojownicy polegający na sile, nieznający pojecią pieniądza");
        System.out.println();
        System.out.println("- Nieumarli");
        System.out.println("Mroczne wybryki natury, nie potrzebują pieniędzy ani jedzenia,");
        System.out.println("czerpią przyjemność z plądrowania hordą pobliskich osad");
        System.out.println();
        System.out.println("- Wampiry");
        System.out.println("Arystokratyczne potwory ukrywające się w cieniu, ich głównym surowcem jest krew,");
        System.out.println("zwiekszają populacje i uzupełniają zasoby atakując pobliskie miasta,");
        System.out.println("mogą oczyszczać brudną krew zamieniając ją w drogocenne fiolki czystej krwi");
        System.out.println();
    }


    private void wyjscie() {
        System.out.println("Czy chcesz zapisać stan gry?");
        System.out.println("1. Tak");
        System.out.println("2. Nie");;

        int wybor = pobierzWybor(1, 2);
        System.out.println();

        if(wybor == 1) {
            System.out.println("====== ISTNIEJĄCE ZAPISY ======");

            for (int i = 0; i < 3; i++) {
                String nazwaPliku = "zapis" + (i + 1) + ".txt";
                try {
                    StanGry stan = StanGry.wczytajZPliku(nazwaPliku);
                    Miasto m = stan.odtworzMiasto();
                    Rasa r = Rasa.fromBitmask(stan.getRasa());
                    int dzien = stan.getDzien();

                    System.out.println((i+1) + ". Slot [" + (i + 1) + "] Nazwa: " + m.getNazwa()
                            + " / Rasa: " + r.getNazwa() + " / Dzień: " + dzien);
                } catch (IOException e) {
                    System.out.println((i+1) + ". Slot [" + (i + 1) + "] Brak zapisu gry.");
                }
            }

            System.out.println("4. Powrót do menu gry");
            System.out.println();
            System.out.println("Wybierz slot zapisu:");
            System.out.println("||UWAGA|| -- Spowoduje to usunięcie poprzedniego zapisu w tym slocie! -- ||UWAGA||");
            int nrZapisu = pobierzWybor(1, 4);

            if(nrZapisu == 4) {start();}

            String nazwaZapisu = "zapis" + nrZapisu + ".txt";
            System.out.println();

            try {
                StanGry stan = new StanGry(miasto,dzien,rasa.getBitmask());
                stan.zapiszDoPliku(nazwaZapisu);
                koniecGry = true;
                System.out.println("Dziękujemy za grę! Stan gry został zapisany.");
                System.out.println();
            } catch (IOException e) {
                System.out.println("Błąd podczas zapisu gry: " + e.getMessage());
                System.out.println("Wracam do menu");
                start();
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
        System.out.println("1. Łatwy - więcej zasobów startowych, mniej wymagające cele, mała liczba zasobów każdego dnia");
        System.out.println("2. Średni - zbalansowana rozgrywka");
        System.out.println("3. Trudny - mniej zasobów, częstsze katastrofy, wyższe koszty");

        poziomTrudnosci = pobierzWybor(1, 3);
        System.out.println();
    }

    private void wybierzRase() {
        System.out.println("Wybierz rasę:");

        Rasa[] wszystkieRasy = Rasa.values();
        for (int i = 0; i < wszystkieRasy.length; i++) {
            System.out.printf("%d. %s%n", i + 1, wszystkieRasy[i].getNazwa());
        }

        int wybor = pobierzWybor(1, wszystkieRasy.length);
        rasa = wszystkieRasy[wybor - 1];

        System.out.println("Wybrano rasę: " + rasa.getNazwa());
        System.out.println();
    }

    private void inicjalizujMiasto() {
        System.out.println("Jak chcesz nazwać swoje miasto?");
        String nazwa = scanner.nextLine();

        // Wartości startowe zależne od poziomu trudności
        int startowaPieniadze, startowaMaterialy,startoweMaterialySpec, startowaPasek,startowaPopulacja, startowaZywnosc;
        double mnoznikZdarzen;

        switch (poziomTrudnosci) {
            case 1: // Łatwy
                startowaPieniadze = (int)(rasa.getPieniadzeStart()*1.5);
                startowaMaterialy = (int)(rasa.getMaterialyStart()*1.5);
                startoweMaterialySpec = (int)(rasa.getMaterialySpecStart()*1.5);
                startowaPopulacja = (int)(rasa.getPopulacjaStart()*1.5);
                startowaZywnosc = (int)(rasa.getZywnoscStart()*1.5);
                startowaPasek = 75;
                mnoznikZdarzen = 0.5;
                break;
            case 2: // Średni
                startowaPieniadze = rasa.getPieniadzeStart();
                startowaMaterialy = rasa.getMaterialyStart();
                startoweMaterialySpec = rasa.getMaterialySpecStart();
                startowaPopulacja = rasa.getPopulacjaStart();
                startowaZywnosc = rasa.getZywnoscStart();
                startowaPasek = 50;
                mnoznikZdarzen = 1.0;
                break;
            case 3: // Trudny
                startowaPieniadze = (int)(rasa.getPieniadzeStart()*0.75);
                startowaMaterialy = (int)(rasa.getMaterialyStart()*0.75);
                startoweMaterialySpec = (int)(rasa.getMaterialySpecStart()*0.75);
                startowaPopulacja = (int)(rasa.getPopulacjaStart()*0.75);
                startowaZywnosc = (int)(rasa.getZywnoscStart()*0.75);
                startowaPasek = 25;
                mnoznikZdarzen = 1.5;
                break;
            default:
                startowaPieniadze = rasa.getPieniadzeStart();
                startowaMaterialy = rasa.getMaterialyStart();
                startoweMaterialySpec = rasa.getMaterialySpecStart();
                startowaPopulacja = rasa.getPopulacjaStart();
                startowaZywnosc = rasa.getZywnoscStart();
                startowaPasek = 50;
                mnoznikZdarzen = 1.0;
        }

        miasto = new Miasto(nazwa, rasa, startowaPieniadze, startowaMaterialy, startoweMaterialySpec, startowaPasek,startowaPopulacja,startowaZywnosc, mnoznikZdarzen);
        System.out.println("Miasto " + nazwa + " zostało założone!");
        System.out.println();
    }

    private void wyswietlStatystyki() {
        System.out.println("========================================");
        System.out.println((rasa.getBitmask() != 32 ? "Dzień: " : "Noc: ") + dzien + " | Miasto: " + miasto.getNazwa() + " | Rasa: " + rasa.getNazwa());
        System.out.println("========================================");
        System.out.println("Populacja: " + miasto.getPopulacja());
        System.out.println("Zadowolenie: " + miasto.getZadowolenie() + "%");
        if(!rasa.getPieniadze().isEmpty())System.out.println("Pieniądze: " + miasto.getPieniadze() + " " +rasa.getPieniadze());
        if(!rasa.getMaterialy().isEmpty())System.out.println("Materiały: " + miasto.getMaterialy() + " " + rasa.getMaterialy());
        if(!rasa.getMaterialySpec().isEmpty())System.out.println("Materiały dodatkowe: " + miasto.getMaterialySpec() + " " + rasa.getMaterialySpec());
        if(!rasa.getZywnosc().isEmpty())System.out.println("Żywność: " + miasto.getZywnosc() + " " + rasa.getZywnosc());
        System.out.println("========================================");
        System.out.println("Liczba budynków: " + miasto.getLiczbaBudynkow());
        System.out.println("========================================");
        System.out.println();
    }

    private int pokazMenu() {
        int n;
        System.out.println("Co chcesz zrobić?");
        System.out.println("1. Buduj nowy budynek");
        System.out.println("2. Ulepsz istniejący budynek");
        System.out.println("3. Istniejące budynki");
        if(rasa.getBitmask()==16){
            n=7;
            System.out.println("4. Wypuść hordę");
            System.out.println("5. Następny dzień");
            System.out.println("6. Pomoc");
            System.out.println("7. Wyjście");
        }
        else if(rasa.getBitmask()==32){
            n = 8;
            System.out.println("4. Rozpocznij Łowy");
            System.out.println("5. Oczyść Krew");
            System.out.println("6. Następny dzień");
            System.out.println("7. Pomoc");
            System.out.println("8. Wyjście");
        } else{
            n=6;
            System.out.println("4. Następny dzień");
            System.out.println("5. Pomoc");
            System.out.println("6. Wyjście");
        }

        int wybor = pobierzWybor(1, n);

        return wybor;
    }

    public int getPoziomRatusza() {
        Budynek ratusz = miasto.getBudynki().get(0);
        return ratusz.getPoziom();
    }


    private void budujBudynek() {
        if(miasto.getLiczbaBudynkow() - 1 >= getPoziomRatusza() * 2) {
            System.out.println("Nie możesz wybudować więcej budynków, zwiększ poziom ratusza!");
            System.out.println();
            return;
        }

        int bitRasy = rasa.getBitmask();

        List<TypBudynku> dostepne = new ArrayList<>();
        int index = 1;

        System.out.println("Jakie budynki możesz wybudować:");

        for(TypBudynku b : TypBudynku.values()) {
            if (b.jestRatuszem()) continue;
            if ((b.getBitmaskRasy() & bitRasy) != 0) {
                dostepne.add(b);

                String info = index + ". " + b.getNazwa() + " (" + b.getTyp() + ")";

                List<String> statystyki = new ArrayList<>();
                if (b.getKosztPieniadze() != 0) statystyki.add(b.getKosztPieniadze() + " " + rasa.getPieniadze());
                if (b.getKosztMaterialy() != 0) statystyki.add(b.getKosztMaterialy() + " " + rasa.getMaterialy());
                if (b.getKosztMaterialySpec() != 0) statystyki.add(b.getKosztMaterialySpec() + " " + rasa.getMaterialySpec());

                if (!statystyki.isEmpty()) {
                    info += " - Koszt wybudowania: " + String.join(" / ", statystyki);
                }

                statystyki = new ArrayList<>();
                if (b.getBonusPieniadze() != 0) statystyki.add((b.getBonusPieniadze() >= 0 ? "+" : "") +b.getBonusPieniadze() + " " + rasa.getPieniadze());
                if (b.getBonusMaterialy() != 0) statystyki.add((b.getBonusMaterialy() >= 0 ? "+" : "") +b.getBonusMaterialy() + " " + rasa.getMaterialy());
                if (b.getBonusMaterialySpec() != 0) statystyki.add((b.getBonusMaterialySpec() >= 0 ? "+" : "") +b.getBonusMaterialySpec() + " " + rasa.getMaterialySpec());
                if (b.getBonusZywnosc() != 0) statystyki.add((b.getBonusZywnosc() >= 0 ? "+" : "") +b.getBonusZywnosc() + " " + rasa.getZywnosc());
                if (b.getBonusZadowolenie() != 0) statystyki.add("+"+b.getBonusZadowolenie() + "% Zadowolenia");
                if (b.getBonusPopulacja() != 0) statystyki.add("+"+b.getBonusPopulacja() + " Populacji");

                if (!statystyki.isEmpty()) {
                    info += "  -  Statystyki: " + String.join(" / ", statystyki);
                }

                System.out.println(info);
                index++;
            }
        }
        System.out.println(index + ". Powrót do menu głównego");

        int wybor = pobierzWybor(1, dostepne.size() + 1);
        if (wybor == dostepne.size() + 1) return;

        TypBudynku typ = dostepne.get(wybor - 1);

        if (miasto.mozeBudowac(typ)) {
            miasto.budujBudynek(typ);
            System.out.println("Budynek " + typ.getNazwa() + " został wybudowany!");
        } else {
            System.out.println("Nie masz wystarczających zasobów, aby wybudować ten budynek.");
        }
        System.out.println();
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
            if(b.getTyp().jestSpecjalny()) continue;
            if(b.getTyp().jestRatuszem()){
                System.out.println((i+1) + ". " + b.getTyp().getNazwa() + " (POZIOM " + b.getPoziom() + ") - Koszt ulepszenia: " +
                        b.getKosztUlepszeniaPieniadze() + " " + rasa.getPieniadze() + " / " + b.getKosztUlepszeniaMaterialy()+ " " + rasa.getMaterialy()
                        + " / " + b.getKosztUlepszeniaMaterialySpec()+ " " + rasa.getMaterialySpec()+ " ---> "+ b.getTyp().getNazwa() +" (POZIOM "+
                        (b.getPoziom()+1)+") - Maksymalna liczba budynków: " + (getPoziomRatusza()+1)*2 + " / Maksymalny poziom budynków: " + (getPoziomRatusza()+1));
            }else{
                String info = (i+1) + ". " + b.getTyp().getNazwa() + " (" +b.getTyp().getTyp() +", Poziom " + b.getPoziom() + ")";

                List<String> statystyki = new ArrayList<>();
                if (b.getKosztUlepszeniaPieniadze() != 0) statystyki.add(b.getKosztUlepszeniaPieniadze() + " " + rasa.getPieniadze());
                if (b.getKosztUlepszeniaMaterialy() != 0) statystyki.add(b.getKosztUlepszeniaMaterialy() + " " + rasa.getMaterialy());
                if (b.getKosztUlepszeniaMaterialySpec() != 0) statystyki.add(b.getKosztUlepszeniaMaterialySpec() + " " + rasa.getMaterialySpec());

                if (!statystyki.isEmpty()) {
                    info += " - Koszt ulepszenia: " + String.join(" / ", statystyki) + " ---> " + b.getTyp().getNazwa() + " (POZIOM "+(b.getPoziom()+1)+")" ;
                }

                statystyki = new ArrayList<>();
                if (b.getTyp().getBonusPieniadze() != 0) statystyki.add((b.getBonusPieniadze() >= 0 ? "+" : "") +(int)(b.getBonusPieniadze()* (1.0 + b.getPoziom() * 0.5))+ " " + rasa.getPieniadze());
                if (b.getTyp().getBonusMaterialy() != 0) statystyki.add((b.getBonusMaterialy() >= 0 ? "+" : "") +(int)(b.getBonusMaterialy()* (1.0 + b.getPoziom() * 0.5))+ " " + rasa.getMaterialy());
                if (b.getTyp().getBonusMaterialySpec() != 0) statystyki.add((b.getBonusMaterialySpec() >= 0 ? "+" : "") +(int)(b.getBonusMaterialySpec()* (1.0 + b.getPoziom() * 0.5))+ " " + rasa.getMaterialySpec());
                if (b.getTyp().getBonusZywnosc() != 0) statystyki.add((b.getBonusZywnosc() >= 0 ? "+" : "") +(int)(b.getBonusZywnosc()* (1.0 + b.getPoziom() * 0.5))+ " " + rasa.getZywnosc());
                if (b.getTyp().getBonusZadowolenie() != 0) statystyki.add("+"+(int)((b.getBonusZadowolenie()* (1.0 + b.getPoziom() * 0.5)) - b.getBonusZadowolenie()) + "% Zadowolenia");
                if (b.getTyp().getBonusPopulacja() != 0) statystyki.add("+"+(int)((b.getBonusPopulacja()* (1.0 + b.getPoziom() * 0.5)) - b.getBonusPopulacja()) + " Populacji");

                if (!statystyki.isEmpty()) {
                    info += " - Nowe statystyki: " + String.join(" / ", statystyki);
                }

                System.out.println(info);
            }
        }
        System.out.println((budynki.size()+1) + ". Powrót do menu głównego");

        int wybor = pobierzWybor(1, budynki.size()+1);
        if (wybor == budynki.size()+1) return;
        System.out.println("");

        Budynek wybranyBudynek = budynki.get(wybor-1);
        if (wybranyBudynek.getPoziom() >= getPoziomRatusza() && !wybranyBudynek.getTyp().jestRatuszem()) {
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
        int zebranePieniadze = miasto.zbierzPieniadze();
        int zebraneMaterialy = miasto.zbierzMaterialy();
        int zebraneMaterialySpec = miasto.zbierzMaterialySpec();;
        int zebranaZywnosc = miasto.zbierzZywnosc();

        System.out.println("Zebrano następujące zasoby:");
        if(zebranePieniadze !=0) System.out.println("- " + (zebranePieniadze >= 0 ? "+" : "")+ zebranePieniadze + " " + rasa.getPieniadze());
        if(zebraneMaterialy != 0) System.out.println("- " + (zebraneMaterialy >= 0 ? "+" : "")+ zebraneMaterialy + " " + rasa.getMaterialy());
        if(zebraneMaterialySpec !=0) System.out.println("- " + (zebraneMaterialySpec >= 0 ? "+" : "")+ zebraneMaterialySpec + " " + rasa.getMaterialySpec());
        if(zebranaZywnosc !=0) System.out.println("- " + (zebranaZywnosc >= 0 ? "+" : "")+ zebranaZywnosc + " " + rasa.getZywnosc());
        System.out.println();
    }

    private void nastepnyDzien() {
        dzien++;
        specjalny = false;

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
        if (random.nextDouble() < (0.1 * miasto.getMnoznikZdarzen())) {
            generujLosoweDarzenie();
        }

        // Zużycie żywności
        if(rasa.getBitmask() != 16) {
            int zuzycie = miasto.getPopulacja() / 3;
            miasto.zmniejszZywnosc((int)(zuzycie*rasa.getMnoznikZywnosci()*poziomTrudnosci));
            System.out.println("Twoi mieszkańcy zużyli " + (int)(zuzycie*rasa.getMnoznikZywnosci()*poziomTrudnosci) + " " + rasa.getZywnosc());
        }

        // Spadające zadowolenie
        miasto.zmniejszZadowolenie(poziomTrudnosci);

        if(poziomTrudnosci==1){
            miasto.aktualizujZasoby(rasa);
        }



        System.out.println("Nastał nowy dzień!");
        System.out.println();
    }

    private void wypuscHorde(){
        if(miasto.maSpecjalny()==1) {
            if (specjalny) {
                System.out.println("Horda dziś już plądrowała!");
                System.out.println();
            } else {
                System.out.println("Wypuszczono hordę nieumarłych!");
                System.out.println();
                int mnoznik = random.nextInt(3) * miasto.getPopulacja();
                System.out.println("Horda splądrowałą pobliskie tereny, zadowolenie zwiekszyło się o " + mnoznik + " %.");
                System.out.println();
                miasto.zwiekszZadowolenie(mnoznik);
                specjalny = true;
            }
        }else{
            System.out.println("Aby wysłać hordę na plądrowanie musisz ją pierwsze zbudować!");
            System.out.println();
        }
    }

    private void lowyKrwi(){
        if(miasto.maSpecjalny()==1 || miasto.maSpecjalny()==3) {
            if (specjalny) {
                System.out.println("Łowy już dziś się obdyły!");
                System.out.println();
            } else {
                System.out.println("Wojownicy wyruszyli na łowy!");
                System.out.println();
                int pieniadze = random.nextInt(2) * miasto.getPopulacja();
                int jedzenie = random.nextInt(5) * miasto.getPopulacja();
                int populacja = random.nextInt(10);
                System.out.println("Łowcy wrócili z pobliskiego miasta, zdobyli:");
                System.out.println();
                if(pieniadze!=0) System.out.println("- " + (pieniadze > 0 ? "+" : "")+ pieniadze + " " + rasa.getPieniadze());
                if(jedzenie!=0)  System.out.println("- " + (jedzenie > 0 ? "+" : "")+ jedzenie + " " + rasa.getZywnosc());
                if(populacja!=0) System.out.println("- " + (populacja > 0 ? "+" : "")+ populacja + " nowych wampirów");
                miasto.zwiekszPieniadze(pieniadze);
                miasto.zwiekszZywnosc(jedzenie);
                miasto.zwiekszPopulacje(populacja);
                specjalny = true;
            }
        }else{
            System.out.println("Aby wysłać wojowników na łowy musisz pierwsze zbudować koszary!");
            System.out.println();
        }
    }

    private void oczyscKrew(){
        if(miasto.maSpecjalny()==2 || miasto.maSpecjalny()==3) {
            if(miasto.getZywnosc()>=10){
                miasto.zmniejszZywnosc(10);
                int szansa = random.nextInt(10);
                if(szansa>=3){
                    System.out.println("Oczyszczono krew!");
                    System.out.println("+5 " + rasa.getPieniadze());
                    System.out.println();
                    miasto.zmniejszPieniadze(5);
                }else{
                    System.out.println("Oczyszczanie się nie powiodło!");
                    System.out.println();
                }
            }else{
                System.out.println("Niewystarczająca ilość zanieczyszczonej krwi!");
                System.out.println();
            }
        }else{
            System.out.println("Aby móc oczyszczać krew musisz zbudować Oczyszczalnie Krwi!");
            System.out.println();
        }
    }

    private void generujLosoweDarzenie() {
        int typ = random.nextInt(10);

        System.out.println("** LOSOWE WYDARZENIE **");


        switch (typ) {
            case 0:
                if(rasa.getBitmask()==1) System.out.println("Wybuchł pożar w mieście! Stracono zasoby i uszkodzono budynki.");
                else if(rasa.getBitmask()==2) System.out.println("Wybuchł pożar w lesie! Stracono zasoby i uszkodzono budynki.");
                else if(rasa.getBitmask()==4) System.out.println("Wybuchł pożar w kopalni! Stracono zasoby i uszkodzono budynki.");
                else if(rasa.getBitmask()==8) System.out.println("Wybuchł pożar w wiosce! Stracono zasoby i uszkodzono budynki.");
                else if(rasa.getBitmask()==16) System.out.println("Wybuchł pożar na cmentarzu! Stracono zasoby i uszkodzono budynki.");
                else if(rasa.getBitmask()==32) System.out.println("Wybuchł pożar w jaskini! Stracono zasoby i uszkodzono budynki.");

                if(rasa.getBitmask()!=8 && rasa.getBitmask()!=16)miasto.zmniejszPieniadze(random.nextInt(100) + 50);
                miasto.zmniejszMaterialy(random.nextInt(50) + 25);
                miasto.zmniejszMaterialySpec(random.nextInt(40) + 20);
                miasto.zmniejszZadowolenie(random.nextInt(10) + 5);
                break;
            case 1:
                if(rasa.getBitmask()==1) System.out.println("Nastąpiła powódź! Uszkodzono część budynków i zapasów.");
                else if(rasa.getBitmask()==2) System.out.println("Pobliska rzeka wylała! Uszkodzono część budynków i zapasów.");
                else if(rasa.getBitmask()==4) System.out.println("Po wielkiej ulewie część wody dostałą sie do kopalni! Uszkodzono część budynków i zapasów.");
                else if(rasa.getBitmask()==8) System.out.println("Wielka nawałnica uszkodziła wioskę! Uszkodzono część budynków i zapasów.");
                else if(rasa.getBitmask()==16) System.out.println("Pobliska rzeka wylała! Uszkodzono część budynków i zapasów.");
                else if(rasa.getBitmask()==32) System.out.println("Część jaskini się zawaliła! Uszkodzono część budynków i zapasów.");
                miasto.zmniejszMaterialy(random.nextInt(80) + 40);
                miasto.zmniejszMaterialySpec(random.nextInt(70) + 35);
                if(rasa.getBitmask()!=16)miasto.zmniejszZywnosc(random.nextInt(30) + 15);
                miasto.zmniejszZadowolenie(random.nextInt(8) + 4);
                break;
            case 2:
                System.out.println("Odkryto nowe złoża surowców! Zdobyto dodatkowe materiały.");
                miasto.zwiekszMaterialy(random.nextInt(100) + 50);
                miasto.zwiekszMaterialySpec(random.nextInt(120) + 55);
                miasto.zwiekszZadowolenie(random.nextInt(5) + 1);
                break;
            case 3:
                if(rasa.getBitmask()==1)System.out.println("Festiwal miejski! Zwiększyło się zadowolenie mieszkańców, ale kosztowało to trochę pieniędzy.");
                else if(rasa.getBitmask()==2)System.out.println("Festiwal natury! Zwiększyło się zadowolenie mieszkańców, ale kosztowało to trochę pieniędzy.");
                else if(rasa.getBitmask()==4)System.out.println("Odbył się huczny ślub! Zwiększyło się zadowolenie mieszkańców, ale kosztowało to trochę pieniędzy.");
                else if(rasa.getBitmask()==8)System.out.println("Odbył się turniej siły! Zwiększyło się zadowolenie mieszkańców");
                else if(rasa.getBitmask()==16)System.out.println("Trupy miały okazję zaatakować przejezdnych wędrowców! Zwiększyło się zadowolenie mieszkańców");
                else if(rasa.getBitmask()==32)System.out.println("Obył się jubileusz założenia klanu! Zwiększyło się zadowolenie mieszkańców, ale kosztowało to trochę pieniędzy.");
                miasto.zwiekszZadowolenie(random.nextInt(15) + 5);
                if(rasa.getBitmask()!=16 && rasa.getBitmask()!=8)miasto.zmniejszPieniadze(random.nextInt(100) + 25);
                break;
            case 4:
                if(rasa.getBitmask()==1)System.out.println("Do miasta przybyła fala ambitnych osadników z sąsiedniego królestwa.");
                else if(rasa.getBitmask()==2)System.out.println("Nowi osadnicy zamieszkali wśród drzew, szukając harmonii z naturą.");
                else if(rasa.getBitmask()==4)System.out.println("Nowi górnicy zeszli do podziemi – populacja wzrosła.");
                else if(rasa.getBitmask()==8)System.out.println("Silni wojownicy dołączyli do twojego plemienia – populacja rośnie!");
                else if(rasa.getBitmask()==16)System.out.println("Z pobliskiego cmentarzyska powstały nowe nieumarłe istoty.");
                else if(rasa.getBitmask()==32)System.out.println("Z ciemnych zakamarków jaskiń wyszły nowe pokolenia mieszkańców.");
                if(rasa.getBitmask()!= 16 && rasa.getBitmask()!= 32)miasto.zwiekszPopulacje(random.nextInt(10) + 5);
                if(rasa.getBitmask()== 16 || rasa.getBitmask()== 32)miasto.zwiekszPopulacje(random.nextInt(5) + 2);
                miasto.zwiekszZadowolenie(random.nextInt(5) + 1);
                break;
            case 5:
                if(rasa.getBitmask()==1)System.out.println("Zaraza rozprzestrzeniła się po mieście, wielu mieszkańców zachorowało.");
                if(rasa.getBitmask()==2)System.out.println("Zaraza rozprzestrzeniła się po mieście, wielu mieszkańców zachorowało.");
                if(rasa.getBitmask()==4)System.out.println("W kopalni wybuchła tajemnicza choroba, osłabiając robotników.");
                if(rasa.getBitmask()==8)System.out.println("Orki zapadły na gorączkę bitewną. Tylko najsilniejsi przetrwali.");
                if(rasa.getBitmask()==16)System.out.println("Część twojej nieumarłej armii rozsypała się w pył w wyniku klątwy.");
                if(rasa.getBitmask()==32)System.out.println("Brudna krew spowodowała liczne choroby.");
                if(rasa.getBitmask()!= 32 && rasa.getBitmask()!= 16)miasto.zmniejszPopulacje(random.nextInt(10) + 5);
                if(rasa.getBitmask()== 16 || rasa.getBitmask()== 32)miasto.zwiekszPopulacje(random.nextInt(5) + 2);
                miasto.zmniejszZadowolenie(random.nextInt(10) + 5);
                if(rasa.getBitmask()!= 8 && rasa.getBitmask()!= 16)miasto.zmniejszPieniadze(random.nextInt(50) + 25);
                break;
            case 6:
                if(rasa.getBitmask()==1)System.out.println("Obfite żniwa przyniosły mnóstwo pożywienia w miejskich spichlerzach.");
                else if(rasa.getBitmask()==2)System.out.println("Natura była łaskawa – plony elfów obrodziły obficie.");
                else if(rasa.getBitmask()==4)System.out.println("Tygiel górniczy zapełniony zapasami – zdobyto wiele jadła.");
                else if(rasa.getBitmask()==8)System.out.println("Orki złupiły sąsiednie osady, zdobywając mnóstwo jedzenia.");
                else if(rasa.getBitmask()==16)System.out.println("Ciała przypadkowych wędrowców stały się pożywką – nieumarli świętują.");
                else if(rasa.getBitmask()==32)System.out.println("W głębi nocy udało się upolować przypadkowe istoty.");
                if(rasa.getBitmask()!=16)miasto.zwiekszZywnosc(random.nextInt(50) + 25);
                miasto.zwiekszZadowolenie(random.nextInt(3) + 1);
                break;
            case 7:
                if(rasa.getBitmask()==1)System.out.println("Susza spustoszyła plony – brakuje jedzenia.");
                else if(rasa.getBitmask()==2)System.out.println("Susza spustoszyła plony – brakuje jedzenia.");
                else if(rasa.getBitmask()==4)System.out.println("Zalanie chłodników zniszczyło zapasy jedzenia górników.");
                else if(rasa.getBitmask()==8)System.out.println("Orki nie zdołały złupić niczego – brzuchy puste, nastroje złe.");
                else if(rasa.getBitmask()==16)System.out.println("Nieumarli nie znaleźli nowych ofiar i złóż – zaczyna się rozkład.");
                else if(rasa.getBitmask()==32)System.out.println("Zbiorniki z krwią się rozbiły – czas głodu.");
                if(rasa.getBitmask()!=16)miasto.zmniejszZywnosc(random.nextInt(20) + 20);
                if(rasa.getBitmask()==16)miasto.zmniejszMaterialy(random.nextInt(20) + 20);
                miasto.zmniejszZadowolenie(random.nextInt(5) + 2);
                break;
            case 8:
                if(rasa.getBitmask()==1)System.out.println("Sąsiednie miasto przysłało darowiznę. Skarbiec się wzbogacił.");
                else if(rasa.getBitmask()==2)System.out.println("Sąsiednie miasto przysłało darowiznę. Skarbiec się wzbogacił.");
                else if(rasa.getBitmask()==4)System.out.println("Kopalnia otrzymała dotację od królestwa za wyjątkowe wyniki.");
                else if(rasa.getBitmask()==8)System.out.println("Orki zrabowały wrogą karawanę. Wzrosły zapasy specjalne.");
                else if(rasa.getBitmask()==16)System.out.println("Nieumarli przechwycili konwój i wyssali energię z jego dusz.");
                else if(rasa.getBitmask()==32)System.out.println("Wampiry otrzymali ofiary od przestraszonych wędrowców.");
                if(rasa.getBitmask()!=8 && rasa.getBitmask()!= 16)miasto.zwiekszPieniadze(random.nextInt(200) + 100);
                if(rasa.getBitmask()==8 || rasa.getBitmask()== 16) miasto.zwiekszMaterialy(random.nextInt(80) + 50);
                miasto.zwiekszZadowolenie(random.nextInt(5) + 1);
                break;
            case 9:
                if(rasa.getBitmask()==1)System.out.println("Złodzieje splądrowali miasto i zabrali złoto!");
                else if(rasa.getBitmask()==2)System.out.println("Złodzieje ukradli magiczne artefakty i złoto elfów!");
                else if(rasa.getBitmask()==4)System.out.println("Skarbiec górników został splądrowany – duże straty.");
                else if(rasa.getBitmask()==8)System.out.println("Wrogie plemię orków zniszczyło część zapasów.");
                else if(rasa.getBitmask()==16)System.out.println("Zbezczeszczone relikwie zostały sprofanowane. Strata niematerialna.");
                else if(rasa.getBitmask()==32)System.out.println("W jaskiniach doszło do sabotażu – część zapasów zniknęła.");
                if(rasa.getBitmask()!=8 && rasa.getBitmask()!= 16) miasto.zmniejszPieniadze(random.nextInt(150) + 50);
                if(rasa.getBitmask()==8 || rasa.getBitmask()== 16) miasto.zmniejszMaterialySpec(random.nextInt(50) + 50);
                miasto.zmniejszZadowolenie(random.nextInt(10) + 2);
                break;
        }
        System.out.println();
    }
}