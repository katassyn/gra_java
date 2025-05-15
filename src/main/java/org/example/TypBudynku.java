package org.example;

public enum TypBudynku {
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