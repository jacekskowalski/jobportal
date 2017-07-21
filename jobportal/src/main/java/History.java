import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by olier1 on 28.05.2017.
 */
@ManagedBean
@ViewScoped
public class History implements Serializable{
    @ManagedProperty(value = "#{userID}")
    private UserID userID;
    private int clientid;
    private String  appdate, position;
    private List<History> hist=new ArrayList<History>() ;
    private List<History> userhist=new ArrayList<>();
    private List<History> templist;
    ConfigProperties cp=new ConfigProperties();
    List<String> cplist=cp.getProperties();

    public History() {  readAllRecords();  }

    public History(int clientid, String appdate, String position) {
        this.clientid = clientid;
        this.appdate = appdate;
        this.position = position;
    }


    @PostConstruct
    public void init(){
        readUserRecords();
    }

    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public String getAppdate() {
        return appdate;
    }

    public void setAppdate(String appdate) {
        this.appdate = appdate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<History> getHist() {
        return hist;
    }

    public void setHist(List<History> hist) {
        this.hist = hist;
    }

    public List<History> getTemplist() {
        return templist;
    }

    public UserID getUserID() {
        return userID;
    }

    public void setUserID(UserID userID) {
        this.userID = userID;
    }

    public List<History> getUserhist() {
        return userhist;
    }

    public void setUserhist(List<History> userhist) {
        this.userhist = userhist;
    }

    public void setTemplist(List<History> templist) {
        this.templist = templist;
    }

    public void readAllRecords(){
        String str="SELECT * FROM History";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {

                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery(str);

                while (rs.next()) {
                    hist.add(new History(rs.getInt(1), rs.getDate(2).toString(),
                            rs.getString(3)));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void deleteHistory(int arg1,History h) {
        String req = "DELETE FROM History WHERE ClientId = ?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {

                PreparedStatement stat = conn.prepareStatement(req);
                stat.setInt(1, arg1);
                stat.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {   hist.remove(h);   }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void readUserRecords(){
        String str="SELECT * FROM History WHERE ClientID =?" ;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {

                PreparedStatement stat = conn.prepareStatement(str);
                stat.setString(1, String.valueOf(userID.getID()));
                ResultSet rs = stat.executeQuery();

                while (rs.next()) {
                    userhist.add(new History(rs.getInt(1), rs.getDate(2).toString(), rs.getString(3)));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
