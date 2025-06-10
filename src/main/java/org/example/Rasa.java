package org.example;

public enum Rasa {
    LUDZIE("Ludzie",1, "Złota", "Kamienia", "Drewna", "Żywności",300,400,350,50,10,1),
    ELFY("Elfy",2, "Złota", "Drewna", "Magii", "Jagód",150,120,70,30,7,0.75),
    KRASNOLUDY("Krasnoludy",4, "Diamentów", "Żelaza", "Złota", "Żywności",150,300,250,70,15,1.5),
    ORKI("Orki",8, "", "Kamienia", "Drewna", "Żywności",100,300,250,100,20,2),
    NIEUMARLI("Nieumarli",16, "", "Kości", "Dusz", "",100,70,50,100,3,1),
    WAMPIRY("Wampiry",32, "Fiolki Czystej Krwi", "Stali", "Mrocznych Kryształów", "Zanieczyszczonej Krwi",120,300,180,30,5,0.5);

    private final String nazwa;
    private final int bitmask;
    private final String pieniadze;
    private final String materialy;
    private final String materialySpec;
    private final String zywnosc;
    private final int pieniadzeStart;
    private final int materialyStart;
    private final int materialySpecStart;
    private final int zywnoscStart;
    private final int populacjaStart;
    private final double mnoznikZywnosci;

    Rasa(String nazwa,int bitmask, String pieniadze, String materialy,String materialySpec, String zywnosc,int pieniadzeStart,int materialyStart,int materialySpecStart,int zywnoscStart,int populacjaStart,double mnoznikZywnosci) {
        this.nazwa = nazwa;
        this.bitmask = bitmask;
        this.pieniadze = pieniadze;
        this.materialy = materialy;
        this.materialySpec = materialySpec;
        this.zywnosc = zywnosc;
        this.pieniadzeStart = pieniadzeStart;
        this.materialyStart = materialyStart;
        this.materialySpecStart = materialySpecStart;
        this.zywnoscStart = zywnoscStart;
        this.populacjaStart = populacjaStart;
        this.mnoznikZywnosci = mnoznikZywnosci;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getBitmask() {
        return bitmask;
    }

    public String getPieniadze() {
        return pieniadze;
    }

    public String getMaterialy() {
        return materialy;
    }

    public String getMaterialySpec() {
        return materialySpec;
    }

    public String getZywnosc() {
        return zywnosc;
    }

    public static Rasa fromBitmask(int bitmask) {
        for (Rasa rasa : values()) {
            if (rasa.getBitmask() == bitmask) {
                return rasa;
            }
        }
        return null;
    }

    public int getPieniadzeStart(){ return pieniadzeStart;}

    public int getMaterialyStart(){ return materialyStart;}

    public int getMaterialySpecStart() {return materialySpecStart;}

    public int getZywnoscStart() {return zywnoscStart;}

    public int getPopulacjaStart() { return populacjaStart;}

    public double getMnoznikZywnosci() {
        return mnoznikZywnosci;
    }
}
