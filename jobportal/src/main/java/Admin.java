import com.mysql.jdbc.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by olier1 on 03.05.2017.
 */
@ManagedBean
@ViewScoped
public class Admin implements Serializable {

    @ManagedProperty(value="#{client}")
    private Client client;
   @ManagedProperty(value="#{contact}")
    private Contact contact;
    @ManagedProperty(value="#{history}")
    private History history;
    @ManagedProperty(value="#{offers}")
    private Offers offers;
    private String email;
     private String password;
   private String tableName;
    private List<Offers> allRecords=new ArrayList<Offers>();
 private List<User> us=new ArrayList<User>();
    ConfigProperties cp=new ConfigProperties();
    List<String> cplist=cp.getProperties();
    private List<Messenger> lis=new ArrayList<>();

    public Admin() {    }


    @PostConstruct
    public void init(){
        allRecords();
        readAllRecords();
        readMessages();

    }

    public String logout(){

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public Offers getOffers() {
        return offers;
    }

    public void setOffers(Offers offers) {
        this.offers = offers;
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Offers> getAllRecords() {
        return allRecords;
    }

    public void setAllRecords(List<Offers> allRecords) {
        this.allRecords = allRecords;
    }

    public List<User> getUs() {
        return us;
    }

    public void setUs(List<User> us) {
        this.us = us;
    }

    public List<Messenger> getLis() {
        return lis;
    }

    public void setLis(List<Messenger> lis) {
        this.lis = lis;
    }

    public void allRecords(){

        String req0="SELECT * FROM Users";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        try( Connection conn=DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))){

            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(req0);
            while(rs.next()){
                us.add(new User(rs.getString(1),rs.getString(2),
                        rs.getString(3),rs.getString(4),rs.getDate(5).toString()
                        ,rs.getDate(6),rs.getInt(7)));
            }

            }catch(SQLException se){
            se.printStackTrace();        }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();     }
    }
    public void deleteUser(int arg1,User u){
        String req="DELETE FROM Users WHERE Id = ?";
       String req1="DELETE FROM History WHERE ClientID= ?";
       String req2="DELETE FROM Messenger WHERE UserID= ?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
                try(Connection conn= DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))){
        try(PreparedStatement stat = conn.prepareStatement(req)) {
             stat.setInt(1, arg1);
              stat.execute();
            }
        try( PreparedStatement stat1 = conn.prepareStatement(req1)) {
              stat1.setInt(1,arg1);
              stat1.execute();
            }
            try(PreparedStatement stat2 = conn.prepareStatement(req2)){
                stat2.setInt(1,arg1);
                stat2.execute();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            us.remove(u);
         }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void readAllRecords(){
     String  req4="SELECT * FROM Offers";
      String  listsize="";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        try( Connection conn= DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(req4);

            while (rs.next()) {
                allRecords.add(new Offers(rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getDate(7).toString(),rs.getDate(8).toString(),rs.getString(9)));
            }

             } catch (SQLException e) {
            e.printStackTrace();    }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void deleteOffer(String arg1,String arg2,Offers o){
        String req="DELETE FROM Offers WHERE Nr_Ref = ? AND ClientId =?";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {

                PreparedStatement stat = conn.prepareStatement(req);
                stat.setString(1, arg1);
                stat.setString(2,arg2);
                stat.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                allRecords.remove(o);
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public String checkForm() {

        String returnurl = "";
        Properties props = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream in = classLoader.getResourceAsStream("\\admin.properties"))
        {
            props.load(in);
            String admin=props.getProperty("admin");
            String login=props.getProperty("login");

            if(email.equals(login) && password.equals(admin)){
                returnurl="/Pages/admin.xhtml?faces-redirect=true";
            }

        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnurl;
    }
  public void readMessages(){
        String readall="SELECT * FROM Messenger";
      try {
          Class.forName("com.mysql.jdbc.Driver");
          try (Connection conn = DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {
              PreparedStatement stat = conn.prepareStatement(readall);
              ResultSet rs = stat.executeQuery();

              while (rs.next()) {
                  lis.add(new Messenger(rs.getInt(1),rs.getString(2),rs.getDate(3).toString(),
                          rs.getInt(4), rs.getString(5),rs.getString(6)));
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }
      }catch (ClassNotFoundException e) {
          e.printStackTrace();
      }

  }

    public void deleteMessage(int arg1,String arg2,String arg3, Messenger m){

        String req5="DELETE FROM Messenger WHERE UserId = ? AND CompanyId = ?  AND Nr_Ref=?";

        try(Connection conn=DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))){
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement stat=conn.prepareStatement(req5);
            stat.setInt(1,arg1);
            stat.setString(2,arg2);
            stat.setString(3,arg3);
            stat.execute();

        }catch(SQLException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }finally{
            lis.remove(m);
        }
    }
    }





