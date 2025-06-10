package org.example;

public enum TypBudynku {

    //Ludzie
    RATUSZ_LUDZIE("Ratusz", "RATUSZ",300, 300,300, 0, 0, 0,0, 0, 0,1),

    DOM("Dom", "Populacja",100, 50,40, 10, 0, 0,0, 5, -1,1),
    KOPALNIA_KAMIENIA("Kopalnia Kamienia", "Materiały", 200,0, 100, 0, 20, 0,0, 1, -2,1),
    KOPALNIA_ZLOTA("Kopalnia Złota", "Pieniądze",0, 120,100, 0, 0, 0,0, 2, 50,1),

    //Elfy
    RATUSZ_ELFY("Centrum lasu", "RATUSZ",250, 250,250, 0, 0, 0,0, 0, 0,2),

    DOM_ELFY("Domek na drzewie", "Populacja", 90, 40, 30, 5, 0, 0, 0, 6, -1, 2),
    MAGICZNY_OGROD("Magiczny Ogród", "Żywność", 20, 120, 50, 0, 0, 0, 8, 4, -1, 2),
    TARTAK_ELFY("Skład Drewna", "Materiały", 120, 0, 100, 0, 25, 0, 0, 2, -2, 2),
    MAGICZNA_SWIATYNIA("Koszary Magii", "Materiały", 250, 140, 0, 0, 0, 20, 0, 3, -3, 2),
    TARG("Leśny Targ", "Pieniądze", 0, 190, 20, 0, 0, 0, 0, 3, 40, 2),

    //Krasnoludy
    RATUSZ_KRASNOLUDY("Serce Kopalni", "RATUSZ",350, 350,350, 0, 0, 0,0, 0, 0,4),

    DOM_KRASNOLUDY("Podziemny Dom", "Populacja", 110, 60, 50, 12, 0, 0, 0, 4, -1, 4),
    KOPALNIA_DIAMENTOW("Kopalnia Diamentów", "Pieniądze", 0, 250, 200, 0, 0, 0, 0, 5, 30, 4),
    KOPALNIA_ZELAZA("Kopalnia Żelaza", "Materiały", 100, 0, 180, 0, 35, 0, 0, 4, -3, 4),
    KOPALNIA_ZLOTA_K("Kopalnia Złota", "Materiały", 150, 200, 0, 0, 0, 40, 0, 4, -3, 4),


    //Orki
    RATUSZ_ORKI("Chata Wodza", "RATUSZ",0, 270,270, 0, 0, 0,0, 0, 0,8),

    CHATKA_ORKA("Chatka wojownika", "Populacja", 0, 170, 130, 11, 0, 0, 0, 3, 0, 8),
    KAMIENIOŁOM("Kamieniołom", "Materiały", 0, 0, 200, 0, 25, 0, 0, 2, 0, 8),

    //Nieumarli
    RATUSZ_NIEUMARLI("Cmentarzysko", "RATUSZ",0, 50,50, 0, 0, 0,0, 0, 0,16),

    NAGROBEK("Grób","Populacja",0, 40, 20, 1, 0, 0, 0, 0, 0, 16),
    SWIATYNIA_DUSZ("Miejsce Kultu", "Materiały", 0, 50, 0, 0, 0, 5, 0, 2, 0, 16),
    KOPALNIA_KOSCI("Kopalnia Kości","Materiały", 0, 0, 40, 0, 10, 0, 0, 2, 0, 16),
    HORDA("Horda","SPECJALNE",0,120,100, 0, 0, 0,0, 0, 0, 16),

    //Wampiry
    RATUSZ_WAMPIRY("Ukryta Jaskinia", "RATUSZ",100, 420,380, 0, 0, 0,0, 0, 0,32),
    HUTA_STALI("Huta Stali", "Materiały", 20, 0, 130, 0, 30, 0, 0, 2, -1, 32),
    ALCHEMIK("Alchemik", "Materiały", 25, 140, 0, 0, 0, 25, 0, 3, -1, 32),
    NOCNE_POLA_LOWIECKIE("Nocne Pola Łowieckie", "Żywność, Status", 30, 80, 90, 0, 0, 0, 6, 10, 0, 32),
    MROCZNE_TARGOWISKO("Mroczne Targowisko", "Pieniądze, Materiały", 30, 140, 100, 0, 0, 10, 0, 3, 5, 32),
    NOCNE_LOWY("Koszary Wojowników Krwi","SPECJALNE",120,500,400, 0, 0, 0,0, 7, 0, 32),
    OCZYSZCZALNIA_KRWI("Oczyszczalnia Krwi","SPECJALNE",0,200,180, 0, 0, 0,0, 5, 0, 32),

    //Uniwersalne
    TARTAK("Tartak", "Materiały", 0,150, 0, 0, 0, 15,0, 1, -2,1|8),
    PASTWISKO("Pastwisko", "Żywność", 25, 120, 50, 0, 0, 0, 8, 4, -1, 1|2|4),
    POLE("Pole Uprawne", "Żywność",150, 30, 0,0, 0, 0,5, 3, -1,1|2|4),
    POLA_LOWIECKIE("Pola Łowieckie", "Żywność", 0, 70, 100, 0, 0, 0, 6, 2, 0, 1|2|8),
    TAWERNA("Tawerna", "Status",150, 80,70, 0, 0, 0,0, 10, -2,1 | 4),
    SWIATYNIA("Świątynia Natury", "Status", 200, 100, 80, 0, 0, 0, 0, 12, -3, 1 | 2),
    SZAŁ_WOJENNY("Świątynia Szału", "Status", 0, 130, 200, 0, 0, 0, 0, 9, 0, 1|8),
    ZBROJOWNIA("Zbrojownia", "Status", 220, 150, 0, 0, 0, 0, 0, 8, -2, 1 | 4);



    private final String nazwa;
    private final String typ;
    private final int kosztPieniadze;
    private final int kosztMaterialy;
    private final int kosztMaterialySpec;
    private final int bonusPopulacja;
    private final int bonusMaterialy;
    private final int bonusMaterialySpec;
    private final int bonusZywnosc;
    private final int bonusZadowolenie;
    private final int bonusPieniadze;
    private final int bitmaskRasy;


    TypBudynku(String nazwa, String typ, int kosztPieniadze, int kosztMaterialy,int kosztMaterialySpec, int bonusPopulacja,
               int bonusMaterialy,int bonusMaterialySpec, int bonusZywnosc, int bonusZadowolenie, int bonusPieniadze,int bitmaskRasy) {
        this.nazwa = nazwa;
        this.typ = typ;
        this.kosztPieniadze = kosztPieniadze;
        this.kosztMaterialy = kosztMaterialy;
        this.kosztMaterialySpec = kosztMaterialySpec;
        this.bonusPopulacja = bonusPopulacja;
        this.bonusMaterialy = bonusMaterialy;
        this.bonusMaterialySpec = bonusMaterialySpec;
        this.bonusZywnosc = bonusZywnosc;
        this.bonusZadowolenie = bonusZadowolenie;
        this.bonusPieniadze = bonusPieniadze;
        this.bitmaskRasy = bitmaskRasy;
    }

    public String getNazwa() { return nazwa; }

    public String getTyp() { return typ; }

    public int getKosztPieniadze() {
        return kosztPieniadze;
    }

    public int getKosztMaterialy() {
        return kosztMaterialy;
    }

    public int getKosztMaterialySpec() { return kosztMaterialySpec;}

    public int getBonusPopulacja() {
        return bonusPopulacja;
    }

    public int getBonusMaterialy() {
        return bonusMaterialy;
    }

    public int getBonusMaterialySpec() {
        return bonusMaterialySpec;
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

    public int getBitmaskRasy() { return bitmaskRasy; }

    public boolean jestRatuszem() {
        return this == RATUSZ_LUDZIE || this == RATUSZ_ELFY || this == RATUSZ_KRASNOLUDY || this == RATUSZ_ORKI || this == RATUSZ_NIEUMARLI || this == RATUSZ_WAMPIRY;
    }

    public boolean jestSpecjalny() {
        return this == HORDA || this == NOCNE_LOWY || this == OCZYSZCZALNIA_KRWI;
    }
}