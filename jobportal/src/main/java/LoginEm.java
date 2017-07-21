import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by olier1 on 06.06.2017.
 */
@ManagedBean
@SessionScoped
public class LoginEm implements Serializable{
    private String email, password;
    @ManagedProperty(value ="#{userID}" )
    private UserID userid;
    ConfigProperties cp=new ConfigProperties();
    List<String> cplist=cp.getProperties();

    public LoginEm() {    }

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

    public String checkForm() {
        Properties props = new Properties();

        String returnurl="";
        ArrayList<User> al=new ArrayList<User>();
        String str="SELECT * FROM Client WHERE Email = ? AND Password = ?";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        try( Connection conn=DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {
                PreparedStatement stat = conn.prepareStatement(str);
                stat.setString(1,email );
                stat.setString(2,password);
                ResultSet rs = stat.executeQuery();

                while (rs.next()) {

                    if (email.equals(rs.getString("Email")) && password.equals(rs.getString("Password")))
                    { returnurl = "client";
                         userid.setID(rs.getString("CompanyId"));
                    }
                    else returnurl = "index";
                }
            } catch (SQLException e) {   e.printStackTrace();   }
        } catch (ClassNotFoundException e) {   e.printStackTrace();   }

        return returnurl;
    }

    public UserID getUserid() {
        return userid;
    }

    public void setUserid(UserID userid) {
        this.userid = userid;
    }
}
