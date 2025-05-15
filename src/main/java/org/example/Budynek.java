package org.example;

public class Budynek {
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