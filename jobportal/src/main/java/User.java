import org.primefaces.event.CellEditEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by olier1 on 03.05.2017.
 */

@ManagedBean(name = "user")
@ViewScoped
public class User implements Serializable {
    @ManagedProperty(value = "#{userID}")
    private UserID userID;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private String nrref;
    private String birth;
    private ArrayList<User> li=new ArrayList<User>();
    private List<User> allRecords=new ArrayList<User>();
    private List<User> templist;
    private boolean renderOrNot=false;
 private List<Messenger> me=new ArrayList<Messenger>();
    private List<History> hi=new ArrayList<History>();
    java.sql.Date temp;
  private List<String> listfiles=new ArrayList<String>();
    ConfigProperties cp=new ConfigProperties();
    List<String> cplist=cp.getProperties();
int id;

    public User() {    }

    public User(String name, String lastname, String email, String password,String birth) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.birth = birth;
    }

    public User(String name, String lastname, String email, String password, String birth, java.sql.Date temp,int id) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.birth = birth;
        this.temp = temp;
        this.id=id;
    }

    public UserID getUserID() {
        return userID;
    }

    public void setUserID(UserID userID) {
        this.userID = userID;
    }


  public List<String> getListfiles() {
        return listfiles;
    }

    public void setListfiles(List<String> listfiles) {
        this.listfiles = listfiles;
    }

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
        this.lastname = lastname;
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

    public ArrayList<User> getLi() {
        return li;
    }

    public void setLi(ArrayList<User> li) {
        this.li = li;
    }

    public List<User> getTemplist() {
        return templist;
    }

    public void setTemplist(List<User> templist) {
        this.templist = templist;
    }

    public List<User> getAllRecords() {
        return allRecords;
    }

    public String getNrref() {
        return nrref;
    }

    public void setNrref(String nrref) {
        this.nrref = nrref;
    }

    public void setAllRecords(List<User> allRecords) {
        this.allRecords = allRecords;
    }

    public List<History> getHi() {
        return hi;
    }

    public void setHi(List<History> hi) {
        this.hi = hi;
    }

    public boolean isRenderOrNot() {
        return renderOrNot;
    }

    public void setRenderOrNot(boolean renderOrNot) {
        this.renderOrNot = renderOrNot;
    }


    public void changeVisibility(){
        renderOrNot=true;
    }

    public List<Messenger> getMe() {
        return me;
    }

    public void setMe(List<Messenger> me) {
        this.me = me;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void saveData(){
        String str="UPDATE Users SET Imie = ?,Nazwisko = ?, Email =?, Password = ?,Data_ur = ? WHERE Id = ?";
       int wyk=0;

       try {
            SimpleDateFormat  sdf=new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsed = sdf.parse(getBirth());
            temp=new java.sql.Date(parsed.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try{
            Class.forName("com.mysql.jdbc.Driver");
        try(Connection conn = DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {

            PreparedStatement stat = conn.prepareStatement(str);
            stat.setString(1, getName());
            stat.setString(2, getLastname());
            stat.setString(3, getEmail());
            stat.setString(4, getPassword());
            stat.setDate(5,temp);
            stat.setInt(6,(int)userID.getID());
            wyk= stat.executeUpdate();

            } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            System.out.println("Zmieniono wpisów "+wyk);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
         }
        }


@PostConstruct
public void init(){
            displayData();
            getDocs();
     showPosts();
           displayApplications();
}

    public void showPosts(){
        String getnrref="SELECT CONCAT(Imie,' ',Nazwisko)As Personal,CONCAT(SendDate,' ',Nr_Ref)" +
                " AS SendDate,Message FROM Users JOIN Messenger ON Users.Id= Messenger.UserID AND Messenger.Nr_Ref " +
                "IN (select Nr_Ref from messenger WHERE UserID = ?)";
        //   String compID=getClientID(getNrref(),getReceiver());
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {
                PreparedStatement stat = conn.prepareStatement(getnrref);
                stat.setInt(1,(int)userID.getID());
                ResultSet rs = stat.executeQuery();

                while (rs.next()) {
                    me.add(new Messenger(rs.getString("Personal"), rs.getString("SendDate"),rs.getString("Message")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void displayData(){

        String str="SELECT * FROM Users WHERE Id = ?";

        try{
            Class.forName("com.mysql.jdbc.Driver");
              try(Connection conn= DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {

               PreparedStatement stat = conn.prepareStatement(str);
            stat.setInt(1,(int)userID.getID());

                ResultSet rs = stat.executeQuery();

                while (rs.next()) {

                  name=rs.getString(1);
                  lastname=rs.getString(2);
                   email=rs.getString(3);
                   password=rs.getString(4);
                   birth=rs.getDate(5).toString();

                }

            } catch (SQLException e) {
                  e.printStackTrace();
              }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public String logout(){

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";

    }


    public void getDocs(){

        String str="SELECT * FROM Resume WHERE Id = ?";

        try(Connection conn=DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement stat = conn.prepareStatement(str);
            stat.setInt(1,(int)userID.getID());
            ResultSet rs = stat.executeQuery();

            while (rs.next()) {

                listfiles.add(rs.getString("FileName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void deleteFile(String na){

        String req="DELETE FROM Resume WHERE FileName = ? AND Id = ?";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {
                PreparedStatement stat = conn.prepareStatement(req);
                stat.setString(1, na);
                stat.setInt(2,(int)userID.getID());
                stat.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                int ind = listfiles.indexOf(na);
                System.out.println("Usunięto" + na);
                listfiles.remove(ind);
            }
        }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
    }


    public void displayApplications(){

        String str="SELECT * FROM History WHERE ClientID = ?";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {
                PreparedStatement stat = conn.prepareStatement(str);
                stat.setInt(1, (int)userID.getID());
                ResultSet rs = stat.executeQuery();

                while (rs.next()) {

                    hi.add(new History(rs.getInt("ClientID"), rs.getDate("DataAplikacji").toString(), rs.getString("Stanowisko")));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
