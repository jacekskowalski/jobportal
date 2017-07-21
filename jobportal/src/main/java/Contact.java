import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by olier1 on 06.05.2017.
 */
@ManagedBean(name="contact")
@ViewScoped
public class Contact implements Serializable{
    private String name, message, email, getday;
    private String str;
   private List<Contact> concon=new ArrayList<Contact>();
   private List<Contact> templist;
    ConfigProperties cp=new ConfigProperties();
    List<String> cplist=cp.getProperties();

    public Contact() { getData();  }

    public Contact(String name, String email, String message, String getday) {
        this.name = name;
        this.email = email;
        this.message = message;
        this.getday = getday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGetday() {
        return getday;
    }

    public void setGetday(String getday) {
        this.getday = getday;
    }

    public List<Contact> getTemplist() {
        return templist;
    }

    public void setTemplist(List<Contact> templist) {
        this.templist = templist;
    }

    public List<Contact> getConcon() {
        return concon;
    }

    public void setConcon(List<Contact> concon) {
        this.concon = concon;
    }

    public void sendToDB() {
        String selectdata="SELECT Name_LastName, Email FROM Contact_Data WHERE Name_LastName = ? AND Email = ?";
        str = "INSERT INTO ContactMessages (Name_LastName, Email, Message, GetDate) VALUES(?,?,?,?)";
  boolean checkit=false;
        java.util.Date da = new java.util.Date();
        java.sql.Date d = new java.sql.Date(da.getTime());

        try{
            Class.forName("com.mysql.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {

            try(PreparedStatement stat0 = conn.prepareStatement(selectdata)) {
                stat0.setString(1, name);
                stat0.setString(2, email);
                ResultSet rs = stat0.executeQuery();

                while (rs.next()) {

                    if (name.equals(rs.getString("Name_LastName")) && email.equals(rs.getString("Email")))
                        checkit = true;
                   System.out.println("Znaleziono "+checkit);
                }
            }
            if(checkit ==false ){

                    String newquery="INSERT INTO Contact_Data (Name_LastName,Email) VALUES (?,?)";

                    try(PreparedStatement stat1 = conn.prepareStatement(newquery)){
                        stat1.setString(1,name);
                        stat1.setString(2,email);
                        stat1.execute();
                }
            }
            PreparedStatement stat = conn.prepareStatement(str);
            stat.setString(1, name);
            stat.setString(2, email);
            stat.setString(3, message);
            stat.setDate(4, d);
            stat.execute();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getData() {
        String str = "SELECT * FROM ContactMessages";
    try{
    Class.forName("com.mysql.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {

            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(str);

            while (rs.next()) {
                concon.add(new Contact(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getDate(4).toString()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void delete(String str1,String str2,Contact c){

        String req="DELETE FROM ContactMessages WHERE Name_LastName= ? AND Email = ?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {

                PreparedStatement stat = conn.prepareStatement(req);
                stat.setString(1, str1);
                stat.setString(2, str2);
                stat.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                concon.remove(c);

            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }

    }
}
