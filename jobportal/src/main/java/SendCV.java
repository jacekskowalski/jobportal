/**
 * Created by olier1 on 09.06.2017.
 */

import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.*;
import java.util.*;


@ManagedBean
@ViewScoped
public class SendCV implements Serializable{
    @ManagedProperty(value = "#{userID}")
    private UserID userID;
    private String Id;
    private String nrref,  docname, appdate,compname;
    ConfigProperties cp=new ConfigProperties();
    List<String> cplist=cp.getProperties();

    public SendCV() {    }

    public SendCV( String id,String appdate, String docname,String nrref,String compname) {
       this.Id=id;
       this.appdate=appdate;
       this.docname=docname;
       this.nrref=nrref;
       this.compname=compname;
    }

    @PostConstruct
    public void init(){}

    public UserID getUserID() {
        return userID;
    }

    public void setUserID(UserID userID) {
        this.userID = userID;
    }

    public String getNrref() {
        return nrref;
    }

    public void setNrref(String nrref) {
        this.nrref = nrref;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getAppdate() {
        return appdate;
    }

    public void setAppdate(String appdate) {
        this.appdate = appdate;
    }

    public String getCompname() {
        return compname;
    }

    public void setCompname(String compname) {
        this.compname = compname;
    }

    public void sendApplication(){
        String  str = "INSERT INTO Application (Id,SendDate,DocName,Nr_Ref,Firma) VALUES (?,?,?,?,?)";
        String str1="INSERT INTO History (ClientID,DataAplikacji,Stanowisko) VALUES(?,?,?)";
        String compID=getClientID(nrref,compname);
          java.util.Date da=new java.util.Date();
        java.sql.Date d= new java.sql.Date(da.getTime());
        String getpasitionname=getPosition();

        try {
            Class.forName("com.mysql.jdbc.Driver");
                try( Connection conn = DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {

            try(PreparedStatement stat = conn.prepareStatement(str)) {
                stat.setInt(1, (int)userID.getID());
                stat.setDate(2, d);
                stat.setString(3, docname);
                stat.setString(4, nrref);
                stat.setString(5,compID);
                stat.execute();
            }
            try(PreparedStatement stat1=conn.prepareStatement(str1)){
              stat1.setInt(1,(int)userID.getID());
              stat1.setDate(2,d);
                stat1.setString(3,getpasitionname);
              stat1.execute();
            }

        } catch ( SQLException e) {  e.printStackTrace();   }
                finally {    reset();    }
        } catch (ClassNotFoundException e) {
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


public String getPosition(){
    String ans=" ";
 //   String fir="SELECT Stanowisko FROM Offers WHERE Nr_Ref IN (SELECT Nr_Ref FROM Application WHERE Id IN (SELECT CONCAT(Imie,' ',Nazwisko) AS Id FROM Users WHERE Id = ?))";
String fir="SELECT Stanowisko FROM Offers WHERE Nr_Ref = ?";

    try {
        Class.forName("com.mysql.jdbc.Driver");
    try(Connection conn=DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {

        PreparedStatement stat = conn.prepareStatement(fir);
        stat.setString(1,nrref);
        ResultSet rs = stat.executeQuery();

        while (rs.next()) {
            ans=rs.getString("Stanowisko");
        }

    } catch (SQLException e) {  e.printStackTrace();  }
    } catch (ClassNotFoundException e) {  e.printStackTrace();   }

    return ans;
}
   public void reset(){
    nrref=null;
    compname=null;
   }
}
