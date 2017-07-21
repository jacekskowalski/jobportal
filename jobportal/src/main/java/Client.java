import javax.annotation.PostConstruct;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by olier1 on 03.05.2017.
 */
@ManagedBean
@ViewScoped
public class Client implements Serializable {
    @ManagedProperty(value = "#{userID}")
    private UserID userID;
    private String newoffer;
    private String company, street, zipcode, city,email, nrref,companyId;
    private String joinDate, password, sendmessage;
    private String str;
    List<Client> cli = new ArrayList<Client>();
    List<Client> allRecords = new ArrayList<Client>();
    private List<Messenger> me = new ArrayList<Messenger>();
    List<Client> templist;
    java.sql.Date temp;
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    ConfigProperties cp=new ConfigProperties();
    List<String> cplist=cp.getProperties();
    private List<SendCV> filterresult=new ArrayList<>();
    private List<SendCV> sendCVList=new ArrayList<>();

    public Client() {
        readAllRecord();
    }

    public Client(String company, String street, String zipcode, String city, String email, String password, String companyId) {
        this.company = company;
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
        this.email = email;
        this.password = password;
        this.companyId = companyId;
    }

    public Client(String company, String street, String zipcode, String city, String email, String password, String companyId, String joinDate) {
        this.company = company;
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
        this.email = email;
        this.password = password;
        this.companyId = companyId;
        this.joinDate = joinDate;
    }


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setValue() {
        setNewoffer("1");
    }

    public String getNrref() {
        return nrref;
    }

    public void setNrref(String nrref) {
        this.nrref = nrref;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<Client> getCli() {
        return cli;
    }

    public void setCli(List<Client> cli) {
        this.cli = cli;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public List<Client> getAllRecords() {
        return allRecords;
    }

    public void setAllRecords(List<Client> allRecords) {
        this.allRecords = allRecords;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getSendmessage() {
        return sendmessage;
    }

    public void setSendmessage(String sendmessage) {
        this.sendmessage = sendmessage;
    }

    public UserID getUserID() {
        return userID;
    }

    public void setUserID(UserID userID) {
        this.userID = userID;
    }

    public List<SendCV> getFilterresult() {
        return filterresult;
    }

    public void setFilterresult(List<SendCV> filterresult) {
        this.filterresult = filterresult;
    }

    public List<Client> getTemplist() {
        return templist;
    }

    public void setTemplist(List<Client> templist) {
        this.templist = templist;
    }

    public List<Messenger> getMe() {
        return me;
    }

    public void setMe(List<Messenger> me) {
        this.me = me;
    }

    public List<SendCV> getSendCVList() {
        return sendCVList;
    }

    public void setSendCVList(List<SendCV> sendCVList) {
        this.sendCVList = sendCVList;
    }

    public String getNewoffer() {
        return newoffer;
    }

    public void setNewoffer(String newoffer) {
        this.newoffer = newoffer;
    }

    public void saveData() {

        str = "UPDATE Client SET Nazwa = ?, Ulica = ?, Kod = ?, Miasto= ? ,Email = ?, Password = ? WHERE CompanyId = ?";
             int nr = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        try(Connection conn= DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {

            PreparedStatement stat = conn.prepareStatement(str);
            stat.setString(1, getCompany());
            stat.setString(2, getStreet());
            stat.setString(3, getZipcode());
            stat.setString(4, getCity());
            stat.setString(5, getEmail());
            stat.setString(6, getPassword());
            stat.setString(7, String.valueOf(userID.getID()));
            nr = stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            System.out.println("Zaktualizowano " + nr);
          }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }

    @PostConstruct
    public void init() {
        displayData();
       readApplication();
        showPosts();
    }


    public void displayData() {

        str = "SELECT * FROM Client WHERE CompanyId = ?";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {

                PreparedStatement stat = conn.prepareStatement(str);
                stat.setString(1, String.valueOf(userID.getID()));
                ResultSet rs = stat.executeQuery();

                while (rs.next()) {

                    setCompany(rs.getString(1));
                    setStreet(rs.getString(2));
                    setZipcode(rs.getString(3));
                    setCity(rs.getString(4));
                    setEmail(rs.getString(5));
                    setPassword(rs.getString(6));
                    setCompanyId(rs.getString(7));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void readAllRecord() {
        String req1 = "SELECT * FROM Client";

        try{
            Class.forName("com.mysql.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {

            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(req1);

            while (rs.next()) {
                allRecords.add(new Client(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getDate(8).toString()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clientDelete(String arg1, Client c) {
        String req = "DELETE FROM Client WHERE CompanyId = ?";
        String deleteOffers="DELETE FROM Offers WHERE ClientId= ?";
        String deleteMessages="DELETE FROM Messenger WHERE CompanyId= ?";
    try{
    Class.forName("com.mysql.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {

            try(PreparedStatement stat2=conn.prepareStatement(deleteMessages)){
                stat2.setString(1,arg1);
                stat2.execute();
            }
            try(PreparedStatement stat1=conn.prepareStatement(deleteOffers)){
                stat1.setString(1,arg1);
                stat1.execute();
            }

            try(PreparedStatement stat = conn.prepareStatement(req)) {
                stat.setString(1, arg1);
                stat.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            allRecords.remove(c);
        }
          }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


 public void showPosts(){
     String getnrref="SELECT CONCAT(Imie,' ',Nazwisko)As Personal,CONCAT(SendDate,' ',Nr_Ref) " +
             "AS SendDate,Message FROM Users JOIN Messenger ON Users.Id= Messenger.UserID AND " +
             "Messenger.Nr_Ref IN (select Nr_Ref from messenger WHERE CompanyID = ?)";
     //   String compID=getClientID(getNrref(),getReceiver());
     try {
         Class.forName("com.mysql.jdbc.Driver");
         try (Connection conn = DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {
             PreparedStatement stat = conn.prepareStatement(getnrref);
             stat.setString(1,String.valueOf(userID.getID()));
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

    public void readApplication(){

       // String str="SELECT (SELECT Imie+' '+Nazwisko FROM Users u Join Application a ON WHERE u.Id =a.Id) AS UserData,  FROM Application   WHERE Firma = ?";
        String s1="SELECT CONCAT(u.Imie,' ',u.Nazwisko) AS Id,a.SendDate,a.DocName,a.Nr_Ref,a.Firma FROM Application a Join Users u ON u.Id =a.Id AND a.Firma =?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try(Connection conn =DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {
                PreparedStatement stat = conn.prepareStatement(s1);
                stat.setString(1,String.valueOf(userID.getID()));
                ResultSet rs = stat.executeQuery();

                while (rs.next()) {

                    sendCVList.add(new SendCV(rs.getString(1),rs.getDate(2).toString(),rs.getString(3),rs.getString(4),rs.getString(5)));
                }

            } catch (SQLException e) {  e.printStackTrace();     }
        } catch (ClassNotFoundException e){   e.printStackTrace();    }
    }
}