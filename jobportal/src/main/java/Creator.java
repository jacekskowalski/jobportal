import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;

/**
 * Created by olier1 on 09.06.2017.
 */
@ManagedBean(name="creator")
@ViewScoped
public class Creator implements Serializable {
    @ManagedProperty(value = "#{userID}")
    private UserID userID;
    private String jobPos, city, nrref,company, message,salary, datZakon;
    java.sql.Date temp;
    ConfigProperties cp=new ConfigProperties();
    List<String> cplist=cp.getProperties();

    public Creator() {    }

    public Creator(String jobPos, String city, String nrref, String company, String message, String salary, String datZakon) {
        this.jobPos = jobPos;
        this.city = city;
        this.nrref = nrref;
        this.company = company;
        this.message = message;
        this.salary = salary;
        this.datZakon = datZakon;
    }

    public String getJobPos() {
        return jobPos;
    }

    public void setJobPos(String jobPos) {
        this.jobPos = jobPos;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNrref() {
        return nrref;
    }

    public void setNrref(String nrref) {
        this.nrref = nrref;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDatZakon() {
        return datZakon;
    }

    public void setDatZakon(String datZakon) {
        this.datZakon = datZakon;
    }

    public UserID getUserID() {
        return userID;
    }

    public void setUserID(UserID userID) {
        this.userID = userID;
    }

    public void addOffer() {

        java.util.Date da = new java.util.Date();
        java.sql.Date d = new java.sql.Date(da.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try {
            java.util.Date parsed = sdf.parse(datZakon);
            System.out.println(parsed.toString());
            temp = new java.sql.Date(parsed.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
        try(Connection conn=DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {

            String str = "INSERT INTO Offers (Stanowisko,Miasto,Nr_Ref,Firma,Wynagrodzenie,Opis,DataWyst,DataZakon,ClientId) VALUES (?,?,?,?,?,?,?,?,?)";

            PreparedStatement stat = conn.prepareStatement(str);
            stat.setString(1, jobPos);
            stat.setString(2, city);
            stat.setString(3, nrref);
            stat.setString(4, company);
            stat.setString(5, salary);
            stat.setString(6, message);
            stat.setDate(7, d);
            stat.setDate(8, temp);
            stat.setString(9,String.valueOf(userID.getID()));
            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{       reset();  }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();   }

    }

    public void reset(){
        jobPos=null;
        company=null;
        city=null;
        nrref=null;
        message=null;
        datZakon=null;
        salary=null;
    }
}