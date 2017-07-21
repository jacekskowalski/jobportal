import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by olier1 on 28.05.2017.
 */
@ManagedBean
@ViewScoped
public class Messenger implements Serializable{
    @ManagedProperty(value = "#{userID}")
    private UserID userID;
    private String sender,nrref, receiver,textmess, senddate,getrecord;
    int id,userid;
    String str="";
    private List<Messenger> userreq=new ArrayList<>();
    ConfigProperties cp=new ConfigProperties();
    List<String> cplist=cp.getProperties();

    public Messenger() {    }

    public Messenger(String textmess,String senddate,String nrref) {
        this.textmess = textmess;
        this.senddate = senddate;
        this.nrref=nrref;
    }

    public Messenger( String textmess, String senddate,String sender, String receiver, String nrref) {
        this.textmess = textmess;
        this.senddate = senddate;
        this.sender = sender;
        this.receiver = receiver;
        this.nrref=nrref;
    }

    public Messenger( int id,String textmess, String senddate,int userid, String receiver, String nrref) {
       this.id=id;
        this.textmess = textmess;
        this.senddate = senddate;
        this.userid = userid;
        this.receiver = receiver;
        this.nrref=nrref;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTextmess() {
        return textmess;
    }

    public void setTextmess(String textmess) {
        this.textmess = textmess;
    }

    public String getSenddate() {
        return senddate;
    }

    public void setSenddate(String senddate) {
        this.senddate = senddate;
    }

    public List<Messenger> getUserreq() {
        return userreq;
    }

    public void setUserreq(List<Messenger> userreq) {
        this.userreq = userreq;
    }

    public String getNrref() {
        return nrref;
    }

    public void setNrref(String nrref) {
        this.nrref = nrref;
    }

    public UserID getUserID() {
        return userID;
    }

    public void setUserID(UserID userID) {
        this.userID = userID;
    }

    public String getGetrecord() {
        return getrecord;
    }

    public void setGetrecord(String getrecord) {
        this.getrecord = getrecord;
    }


        public void createMessage(){
            String newrequest =" INSERT INTO Messenger (Message,SendDate,UserID,CompanyID,Nr_Ref) VALUES(?,?,?,?,?)";
            String newrequest1="SELECT ClientID FROM Offers WHERE Nr_Ref = ? AND ";
            String insertTODB="INSERT INTO MailDB (UserID,CompanyID,Nr_Ref) VALUES (?,?,?)";
            String checkmailDB="SELECT * FROM MailDB WHERE UserID = ? AND CompanyID = ? AND Nr_Ref = ?";
            boolean checkit=false;
            String compID=getClientID(nrref,receiver);
            java.util.Date da=new java.util.Date();
            java.sql.Date d= new java.sql.Date(da.getTime());
            try {
                Class.forName("com.mysql.jdbc.Driver");

                try (Connection conn = DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {

                    try(PreparedStatement prep=conn.prepareStatement(checkmailDB)){
                        prep.setInt(1,(int)userID.getID());
                        prep.setString(2,compID);
                        prep.setString(3,getNrref());

                        ResultSet rs0= prep.executeQuery();
                        while(rs0.next()){
                            if((int)userID.getID()==rs0.getInt("UserID") &&
                                    compID.equals(rs0.getString("CompanyID")) &&
                                    getNrref().equals(rs0.getString("Nr_Ref")))checkit=true;
                            break;
                        }
                    }
                if(checkit==false){
                        try(PreparedStatement ps=conn.prepareStatement(insertTODB)){
                            ps.setInt(1,(int)userID.getID());
                            ps.setString(2,compID);
                            ps.setString(3,getNrref());
                            ps.executeUpdate();
                        }
                }

                  try(PreparedStatement stat = conn.prepareStatement(newrequest)) {
                      stat.setString(1, textmess);
                      stat.setDate(2,d);
                      stat.setInt(3,(int)userID.getID());
                      stat.setString(4, compID);
                      stat.setString(5, getNrref());
                      stat.execute();

                  }
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally{
                    reset();
                }
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
   public void replyForMessage(){
        String newrequest =" INSERT INTO Messenger (Message,SendDate,UserID,CompanyID,Nr_Ref) VALUES(?,?,?,?,?)";
        String newrequest1="SELECT Company FROM Client WHERE CompanyId = ?";
       String checkmailDB="SELECT * FROM MailDB WHERE UserID = ? AND CompanyID = ? AND Nr_Ref = ?";
       String insertTODB="INSERT INTO MailDB (UserID,CompanyID,Nr_Ref) VALUES (?,?,?)";
        int getname=getUserID(getReceiver(),getNrref());
       boolean checkit=false;
        java.util.Date da=new java.util.Date();
        java.sql.Date d= new java.sql.Date(da.getTime());
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {

                try(PreparedStatement stat0=conn.prepareStatement(checkmailDB)){
                    stat0.setInt(1,getname);
                    stat0.setString(2,String.valueOf(userID.getID()));
                    stat0.setString(3,getNrref());

                    ResultSet rs0= stat0.executeQuery();
                    while(rs0.next()){
                        if(getname==rs0.getInt("UserID") &&
                                String.valueOf(userID.getID()).equals(rs0.getString("CompanyID")) &&
                                getNrref().equals(rs0.getString("Nr_Ref")))checkit=true;
                        break;
                    }
                }
                if(checkit==false){
                    try(PreparedStatement ps=conn.prepareStatement(insertTODB)){
                        ps.setInt(1,getname);
                        ps.setString(2,String.valueOf(userID.getID()));
                        ps.setString(3,getNrref());
                        ps.executeUpdate();
                    }
                }
                try(PreparedStatement stat = conn.prepareStatement(newrequest)) {
                    stat.setString(1, textmess);
                    stat.setDate(2,d);
                    stat.setInt(3, getname);
                    stat.setString(4,String.valueOf(userID.getID()) );
                    stat.setString(5, getNrref());
                    stat.execute();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                reset();
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getClientID(String s1,String s2){
        String compID="";
        String compid="SELECT ClientId FROM Offers WHERE Nr_Ref =? AND Firma = ? ";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try(Connection conn=DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {

                PreparedStatement stat = conn.prepareStatement(compid);
                stat.setString(1,s1);
                stat.setString(2,s2);
                ResultSet rs = stat.executeQuery();

                while (rs.next()) {
                    compID=rs.getString("ClientId");
                }

            } catch (SQLException e) { e.printStackTrace();  }
        } catch (ClassNotFoundException e) {  e.printStackTrace();
        }
        return compID;
    }

    public int getUserID(String s1,String s2){
        int uid=0;
        String compid="SELECT u.Id FROM Users u JOIN Messenger m ON u.Id=m.UserId AND CONCAT(u.Imie,' ',u.Nazwisko)= ? AND m.Nr_Ref = ?"
        +"AND m.CompanyId = ?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try(Connection conn=DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {

                PreparedStatement stat = conn.prepareStatement(compid);
                stat.setString(1,s1);
                stat.setString(2,s2);
                stat.setString(3,String.valueOf(userID.getID()));
                ResultSet rs = stat.executeQuery();

                while (rs.next()) {
                    uid=rs.getInt("u.Id");
                }

            } catch (SQLException e) { e.printStackTrace();  }
        } catch (ClassNotFoundException e) {  e.printStackTrace();
        }
        return uid;
    }

    public void reset(){
        nrref=null;
        textmess=null;
        receiver=null;
    }
}