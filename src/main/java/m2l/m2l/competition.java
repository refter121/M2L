package m2l.m2l;

public class competition {
    private int IDCOMPETITION;
    private String INTITULECOMP;
    private String DATECOMP;
    private boolean ENEQUIPE;


    public competition() {}

    public competition(int idcompetiton,String intituleComp, String dateComp, boolean enEquipe) {
        this.IDCOMPETITION = idcompetiton;
        this.INTITULECOMP = intituleComp;
        this.DATECOMP = dateComp;
        this.ENEQUIPE = enEquipe;
    }

    public int getIDCOMPETITION() {
        return IDCOMPETITION;
    }
    public void setIDCOMPETITION(int IDCOMPETITION) {
        this.IDCOMPETITION = IDCOMPETITION;
    }
    public String getINTITULECOMP() {
        return INTITULECOMP;
    }
    public void setINTITULECOMP(String INTITULECOMP) {
        this.INTITULECOMP = INTITULECOMP;
    }
    public String getDATECOMP() {
        return DATECOMP;
    }
    public void setDATECOMP(String DATECOMP) {
        this.DATECOMP = DATECOMP;
    }
    public boolean isENEQUIPE() {
        return ENEQUIPE;
    }
    public void setENEQUIPE(boolean ENEQUIPE) {
        this.ENEQUIPE = ENEQUIPE;
    }
}
