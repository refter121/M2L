package m2l.m2l;

public class personne {
    private int IDPERSONNE;
    private String NOMPERSONNE;
    private String PRENOMPERSONNE;
    private String EMAIL;

    public personne(int idpersonne, String nompersone, String prenompersonne, String email) {
        IDPERSONNE = idpersonne;
        NOMPERSONNE = nompersone;
        PRENOMPERSONNE = prenompersonne;
        EMAIL = email;
    }

    public String getNOMPERSONNE() {
        return NOMPERSONNE;
    }

    public void setNOMPERSONE(String NOMPERSONE) {
        this.NOMPERSONNE = NOMPERSONE;
    }

    public int getIDPERSONNE() {
        return IDPERSONNE;
    }

    public void setIDPERSONNE(int IDPERSONNE) {
        this.IDPERSONNE = IDPERSONNE;
    }

    public String getPRENOMPERSONNE() {
        return PRENOMPERSONNE;
    }

    public void setPRENOMPERSONNE(String PRENOMPERSONNE) {
        this.PRENOMPERSONNE = PRENOMPERSONNE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }
}
