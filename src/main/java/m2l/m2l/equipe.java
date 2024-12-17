package m2l.m2l;

import java.util.ArrayList;

public class equipe {
    private int IDEQUIPE;
    private String NOM;
    private ArrayList<personne> list;

    public equipe() {}
    public equipe(int IDEQUIPE, String NOM) {
        this.IDEQUIPE = IDEQUIPE;
        this.NOM = NOM;
    }
    public equipe(int id, String equipe_name, ArrayList<personne> list) {
        this.IDEQUIPE = id;
        this.NOM = equipe_name;
        this.list = list;
    }


    public int getIDEQUIPE() {
        return IDEQUIPE;
    }

    public void setIDEQUIPE(int id) {
        this.IDEQUIPE = id;
    }

    public String getNOM() {
        return NOM;
    }

    public void setNOM(String equipe_name) {
        this.NOM = equipe_name;
    }

    public ArrayList<personne> getList() {
        return list;
    }

    public void setList(ArrayList<personne> list) {
        this.list = list;
    }
}
