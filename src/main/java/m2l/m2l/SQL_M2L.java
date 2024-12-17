package m2l.m2l;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQL_M2L {
    private java.sql.Statement st = null;
    private java.sql.ResultSet rs = null;
    /*------------------------------------------------------------------------------------------------------------------
    * Builder connection a la base
    ------------------------------------------------------------------------------------------------------------------*/
    public SQL_M2L() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/m2l";
        String login = "root";
        String passwd = "";
        java.sql.Connection cn = DriverManager.getConnection(url, login, passwd);
        st = cn.createStatement();
    }
    /*------------------------------------------------------------------------------------------------------------------
    * Function concerning les candidat
    ------------------------------------------------------------------------------------------------------------------*/
    public void createcandidat(int ID) throws SQLException {
        String Request ="insert into candidat(IDCANDIDAT) values("+ID+")";
        st.executeUpdate(Request);
    }

    public int getID() throws SQLException { // donne un ID disponible
        ArrayList<Integer> idIndispo = new ArrayList<Integer>();
        int newID = 0;
        String request = "SELECT COUNT(*) AS NB FROM candidat";
        rs = st.executeQuery(request);
        int nbr = 0 ;
        while (rs.next()) {
            nbr = rs.getInt("NB"); //recuperation du nombre de candidat
        }
        /*System.out.println(nbr);*/
        int y = 0;
        for(int i = 1 ; i < nbr+1 ; i++) {  // boucle qui cherche les ID disponible
            request = "SELECT IDCANDIDAT FROM candidat";
            rs = st.executeQuery(request);
            while (rs.next()) {
                idIndispo.add(rs.getInt("IDCANDIDAT"));

                if(idIndispo.contains(i)){
                    y = i;
                }
                else{newID = i; break;
                }
            }
            if(newID != 0){
                break;
            }
        }
        if(newID == 0){
            newID = y + 1;
        }
        return newID;
    }

    /*------------------------------------------------------------------------------------------------------------------
    * Function concerning les competitions
    ------------------------------------------------------------------------------------------------------------------*/
    public Integer compID() throws SQLException {
        ArrayList<Integer> idIndispo = new ArrayList<Integer>();
        int newID = 0;
        String request = "SELECT COUNT(*) AS NB FROM competition";
        rs = st.executeQuery(request);
        int nbr = 0 ;
        while (rs.next()) {
            nbr = rs.getInt("NB"); //recuperation du nombre de candidat
        }
        /*System.out.println(nbr);*/
        int y = 0;
        for(int i = 1 ; i < nbr+1 ; i++) {  // boucle qui cherche les ID disponible
            request = "SELECT IDCOMPETITION FROM competition";
            rs = st.executeQuery(request);
            while (rs.next()) {
                idIndispo.add(rs.getInt("IDCOMPETITION"));

                if(idIndispo.contains(i)){
                    y = i;
                }
                else{newID = i; break;
                }
            }
            if(newID != 0){
                break;
            }
        }
        if(newID == 0){
            newID = y + 1;
        }
        return newID;
    }

    public void addCompetitionE(int ID,String intitule, String date,ArrayList<equipe> participant) throws SQLException {
        String request = "INSERT INTO competition(IDCOMPETITION,INTITULECOMP,DATECOMP,ENEQUIPE) VALUES("+ID+",'"+intitule+"','"+date+"',true);";
        st.executeUpdate(request);
        for(int i = 0 ; i < participant.size() ; i++) {
            request = "INSERT INTO participe VALUES ("+participant.get(i).getIDEQUIPE()+","+ID+");";
            st.executeUpdate(request);
        }
    }
    public void addCompetitionP(int ID,String intitule, String date, ArrayList<personne> participant) throws SQLException {
        String request = "INSERT INTO competition(IDCOMPETITION,INTITULECOMP,DATECOMP,ENEQUIPE) VALUES("+ID+",'"+intitule+"','"+date+"',false);";
        st.executeUpdate(request);
        for(int i = 0 ; i < participant.size() ; i++) {
            request = "INSERT INTO participe VALUES ("+participant.get(i).getIDPERSONNE()+","+ID+");";
            st.executeUpdate(request);
        }
    }

    public ArrayList<competition> listComp() throws SQLException {
        String request = "SELECT * FROM competition";
        ArrayList<competition> cl = new ArrayList<>();
        rs = st.executeQuery(request);

        while (rs.next()) {
            int id = rs.getInt("IDCOMPETITION");
            String intitule = rs.getString("INTITULECOMP");
            String date = rs.getString("DATECOMP");
            int team = rs.getInt("ENEQUIPE");
            if(team == 1){
                cl.add(new competition(id,intitule,date,true));
            }
            else {
                cl.add(new competition(id,intitule,date,false));
            }

        }

        return cl;
    }

    //  recuperation des listes de participants

    public ArrayList<personne> listCandidatP(int ID) throws SQLException {
        String request = "SELECT IDCANDIDAT FROM participe WHERE IDCOMPETITION = '" + ID + "'";
        ArrayList<personne> listParticipant = new ArrayList<>();
        rs = st.executeQuery(request);
        ArrayList<Integer> idp = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("IDCANDIDAT");
            idp.add(id);
        }
        for(int i = 1 ; i < idp.size() ; i++) {
            listParticipant.addAll(createObjetPersonne(idp.get(i)));
        }
        return listParticipant;
    }
    public ArrayList<equipe> listCandidatE(int ID) throws SQLException {
        String request = "SELECT IDCANDIDAT FROM participe WHERE IDCOMPETITION = '" + ID + "'";
        ArrayList<equipe> listParticipant = new ArrayList<>();
        rs = st.executeQuery(request);
        ArrayList<Integer> idp = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("IDCANDIDAT");
            idp.add(id);
        }
        for(int i = 1 ; i < idp.size() ; i++) {
            listParticipant.addAll(createObjetEquipe(idp.get(i)));
        }
        return listParticipant;
    }

    public void majCompE(int id,String nom, String date,boolean equipe, ArrayList<Integer>participant ) throws SQLException {
        String request = "UPDATE competition SET INTITULECOMP ='" + nom + "', DATECOMP ='" + date + "', ENEQUIPE =" + equipe + " WHERE IDCOMPETITION =" + id;
        st.executeUpdate(request);
        // changement des participants
        request = "DELETE FROM participe WHERE IDCOMPETITION =" + id;
        st.executeUpdate(request);
        for(int i = 1 ; i < participant.size() ; i++) {
            request ="INSERT INTO participe VALUES("+id+","+participant.get(i)+") ";
            st.executeUpdate(request);
        }

    }


    /*------------------------------------------------------------------------------------------------------------------
    * Function concerning les Equipes
    ------------------------------------------------------------------------------------------------------------------*/
    public void addEquipe(String nom, ArrayList<Integer> membres) throws SQLException {
        int id = this.getID();
        this.createcandidat(id);
        String request ="INSERT INTO equipe(IDCANDIDAT,IDEQUIPE,NOM) values("+id+","+id+",'"+nom+"')";
        st.executeUpdate(request);
        for(int i = 0 ; i < membres.size() ; i++){
            System.out.println("size :"+membres.size());
            System.out.println(membres.get(i));
            System.out.println("id"+id);
            request = "INSERT INTO appartient VALUES("+id+","+membres.get(i)+")";
            st.executeUpdate(request);
        }
    }

    public ArrayList<equipe> createObjetEquipe(int id) throws SQLException {
        String request = "SELECT * FROM equipe WHERE IDCANDIDAT = '" + id + "'";
        ArrayList<equipe> el = new ArrayList<>();
        ResultSet rs = st.executeQuery(request);
        while (rs.next()) {
            id =rs.getInt("IDEQUIPE");
            String nom = rs.getString("NOM");
            el.add(new equipe(id,nom));

        }

        return el;
    }

    public ArrayList<equipe> listEquipe() throws SQLException {
        String request = "SELECT * FROM equipe";
        ArrayList<equipe> el = new ArrayList<>();
        rs = st.executeQuery(request);

        while (rs.next()) {
            int id = rs.getInt("IDEQUIPE");
            String nom = rs.getString("NOM");
            el.add(new equipe(id,nom));
        }
        for(int i = 0 ; i < el.size() ; i++){
            el.get(i).setList(getMembres(el.get(i).getIDEQUIPE()));

        }

        return el;
    }

    public ArrayList<personne> getMembres(int id) throws SQLException{
        ArrayList<personne> al = new ArrayList();
        String request = "SELECT * FROM appartient WHERE IDEQUIPE ="+id+";";
        rs = st.executeQuery(request);
        ArrayList<Integer> idj = new ArrayList<Integer>();
        while(rs.next()){
            int idpersonne= rs.getInt("IDPERSONNE");
            System.out.println(idpersonne);
            idj.add(idpersonne);
        }
        for(int i = 0 ; i < idj.size() ; i++) {
            al.addAll(createObjetPersonne(idj.get(i)));
        }
        return al;
    }

    public void majEquipe(int id , String Nom, ArrayList<Integer>membres) throws SQLException {
        String request ="UPDATE equipe SET NOM ='"+Nom+"' WHERE IDEQUIPE ="+id+";";
        st.executeUpdate(request);
        /* mise a jour des membre de l'équipe */
        request ="DELETE FROM appartient WHERE IDEQUIPE ="+id+";";
        st.executeUpdate(request);
        for(int i = 0 ; i < membres.size() ; i++){
            System.out.println("size :"+membres.size());
            System.out.println(membres.get(i));
            System.out.println("id:"+id);
            request = "INSERT INTO appartient VALUES("+id+","+membres.get(i)+")";
            st.executeUpdate(request);
        }


    }

    /*------------------------------------------------------------------------------------------------------------------
    * Function concerning les personnes
    ------------------------------------------------------------------------------------------------------------------*/
    public void addPersonne(String Nom, String Prenom,String mail) throws SQLException{
        int id = this.getID();
        this.createcandidat(id);
        String request = "INSERT INTO personne (IDCANDIDAT,IDPERSONNE,NOMPERSONE,PRENOMPERSONNE,EMAIL) VALUES("+id+","+id+",'"+Nom+"','"+Prenom+"','"+mail+"')";
        st.executeUpdate(request);
    }

    public boolean personneid(int id) throws SQLException {
        boolean isAPersonne = false;
        String request = "SELECT * FROM personne WHERE IDPERSONNE = "+id+";";
        rs = st.executeQuery(request);
        if(rs.next()){
            if(rs.getInt("IDPERSONNE") == id){
                isAPersonne = true;
                System.out.println("trouvé");
            }
        }
        return isAPersonne;
    }

    public ArrayList<personne> createObjetPersonne(int id) throws SQLException{
        String request = "SELECT IDPERSONNE,NOMPERSONE,PRENOMPERSONNE,EMAIL FROM personne where IDPERSONNE = "+id+";";
        ArrayList<personne> al = new ArrayList<>();
        rs = st.executeQuery(request);
        String nom = null;
        String prenom = null;
        String mail = null;
        while(rs.next()){
            id = rs.getInt("IDPERSONNE");
            nom = rs.getString("NOMPERSONE");
            prenom = rs.getString("PRENOMPERSONNE");
            mail = rs.getString("EMAIL");
            al.add(new personne(id,nom,prenom,mail));
        }
        return al;
    }

    public ArrayList<personne> listPersonne() throws SQLException{
        String request = "SELECT IDPERSONNE,NOMPERSONE,PRENOMPERSONNE,EMAIL FROM personne";
        ArrayList<personne> al = new ArrayList<>();
        rs = st.executeQuery(request);
        int id = 0;
        String nom = null;
        String prenom = null;
        String mail = null;
        while(rs.next()){
            id = rs.getInt("IDPERSONNE");
            nom = rs.getString("NOMPERSONE");
            prenom = rs.getString("PRENOMPERSONNE");
            mail = rs.getString("EMAIL");
            al.add(new personne(id,nom,prenom,mail));
        }
        return al;
    }

    public void majPersonne(int id, String majNom, String majPrenom, String majMail) throws SQLException{
        String request = "UPDATE personne SET NOMPERSONE ='"+majNom+"', PRENOMPERSONNE ='"+majPrenom+"', EMAIL= '"+majMail+"' WHERE IDPERSONNE = "+id+";";
        st.executeUpdate(request);
    }
    /*------------------------------------------------------------------------------------------------------------------
   * supression
   ------------------------------------------------------------------------------------------------------------------*/
    public void DeletePersonne(int id) throws SQLException{
        String request1= "DELETE FROM participe WHERE participe.IDCANDIDAT= "+id+";";
        String request2 = "DELETE FROM appartient WHERE appartient.IDPERSONNE = "+id+";";
        String request3 = "DELETE FROM personne WHERE IDPERSONNE = "+id+";";
        st.executeUpdate(request1);
        st.executeUpdate(request2);
        st.executeUpdate(request3);
    }

    public void DeleteEquipe(int id) throws SQLException{
        String request1= "DELETE FROM participe WHERE participe.IDCANDIDAT= "+id+";";
        String request2 = "DELETE FROM appartient WHERE appartient.IDEQUIPE = "+id+";";
        String request3 = "DELETE FROM Equipe WHERE IDEQUIPE = "+id+";";
        st.executeUpdate(request1);
        st.executeUpdate(request2);
        st.executeUpdate(request3);
    }

    public void DeleteCompetition(int id) throws SQLException{
        String request1= "DELETE FROM participe WHERE participe.IDCOMPETITION = "+id+";";
        String request3 = "DELETE FROM competition WHERE IDCOMPETITION = "+id+";";
        st.executeUpdate(request1);
        st.executeUpdate(request3);
    }

     /*------------------------------------------------------------------------------------------------------------------
    * Test
    ------------------------------------------------------------------------------------------------------------------*/
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
         SQL_M2L m2l = new SQL_M2L();
         m2l.DeletePersonne(15);
     }

}
