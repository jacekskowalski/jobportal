

import javax.faces.bean.*;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.ws.RequestWrapper;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by olier1 on 06.05.2017.
 */
@ManagedBean
@RequestScoped
public class Login implements Serializable {
    private String email;
    private String password;
@ManagedProperty(value ="#{userID}" )
private UserID userid;
    ConfigProperties cp=new ConfigProperties();
    List<String> cplist=cp.getProperties();

    public Login() {     }


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

    public UserID getUserid() {
        return userid;
    }

    public void setUserid(UserID userid) {
        this.userid = userid;
    }

    public String checkForm() {

        String returnurl = "";
        ArrayList<User> al = new ArrayList<User>();

        String str = "SELECT * FROM Users WHERE Email = ? AND Password = ?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {

                PreparedStatement stat = conn.prepareStatement(str);
                stat.setString(1, email);
                stat.setString(2, password);
                ResultSet rs = stat.executeQuery();

                while (rs.next()) {

                    if (email.equals(rs.getString("Email")) && password.equals(rs.getString("Password")))

                    {
                        returnurl = "user";
                        userid.setID(rs.getInt("Id"));
                    } else returnurl = "index";
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
    }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

 System.out.println("Wartość sessionid"+userid.getID());
        return returnurl;
         }

}

