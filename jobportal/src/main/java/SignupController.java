import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * Created by olier1 on 06.05.2017.
 */
@ManagedBean
@ViewScoped
public class SignupController  {
    private String name, lastname,email,password, birth;
    private String str;
    java.sql.Date temp;
    ConfigProperties cp=new ConfigProperties();
    List<String> cplist=cp.getProperties();

    public SignupController() {  }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname=lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

     public String createRecord(){
        String redir="/Pages/confirmation.xhtml?faces-redirect=true";
       java.util.Date da=new java.util.Date();
        java.sql.Date d= new java.sql.Date(da.getTime());
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
         str = "INSERT INTO Users (Imie, Nazwisko, Email, Password, Data_ur,Data_zal_kon) VALUES (?,?,?,?,?,?)";
         String checkintegrity="SELECT Imie,Nazwisko,Email FROM Users WHERE Imie = ? AND Nazwisko= ? AND Email= ?";
         boolean checkit=false;
         Date parsed= null;
         try {
             parsed = sdf.parse(birth);
             temp=new java.sql.Date(parsed.getTime());
         } catch (ParseException e) {
             e.printStackTrace();
         }
         try{
             Class.forName("com.mysql.jdbc.Driver");
        try( Connection conn=DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {

            try(PreparedStatement stat0 = conn.prepareStatement(checkintegrity)) {
                stat0.setString(1, getName());
                stat0.setString(2, getLastname());
                stat0.setString(3, getEmail());
                ResultSet rs = stat0.executeQuery();

                while (rs.next()) {

                    if (name.equals(rs.getString("Imie")) && lastname.equals(rs.getString("Nazwisko")) &&
                            email.equals(rs.getString("Email"))) checkit = true;
                    redir="/Pages/signuppr.xhtml?faces-redirect=true";
                        break;
                }
            }
            if(checkit ==false ) {
                PreparedStatement stat = conn.prepareStatement(str);
                stat.setString(1, name);
                stat.setString(2, lastname);
                stat.setString(3, email);
                stat.setString(4, password);
                stat.setDate(5, temp);
                stat.setDate(6, d);
                stat.execute();
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        } finally {     reset();}
         }catch (ClassNotFoundException e) {
             e.printStackTrace();
         }
         return redir;
    }

    public void reset(){
        name=null;
        email=null;
        lastname=null;
        password=null;
        birth=null;
    }
}
