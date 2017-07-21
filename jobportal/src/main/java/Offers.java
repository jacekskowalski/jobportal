import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@ManagedBean(name="offers")
@SessionScoped
public class Offers implements Serializable{
    @ManagedProperty(value = "#{userID}")
    private UserID userID;
    private FileUpload fup;
    private String dataWyst, jobPos,city, message, datZakon,company,nrref,salary,clientid;
    private String count;
    private String req1;
    private List<Offers> offersList=new ArrayList<Offers>();
  private List<Offers> allRecords=new ArrayList<Offers>();
  private List<Offers> templist;
  private List<FileUpload> fu=new ArrayList<>();
    private String listsize;
    java.sql.Date temp;
    ConfigProperties cp=new ConfigProperties();
    List<String> cplist=cp.getProperties();

    public Offers(){}


    public Offers(String jobPos,String city,String nrref,String company,
                  String salary, String message,String dataWyst, String datZakon,String clientid) {
        this.jobPos = jobPos;
        this.city = city;
        this.nrref=nrref;
        this.company=company;
        this.salary = salary;
        this.message = message;
        this.dataWyst = dataWyst;
       this.datZakon = datZakon;
        this.clientid=clientid;
    }

    public void displayOffers(){
offersList.clear();
        listsize="";
        ResultSet rs=null;
PreparedStatement stat=null;
try {
    Class.forName("com.mysql.jdbc.Driver");
    try (Connection conn = DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {

        req1 = "SELECT * FROM Offers WHERE Stanowisko = ? OR Miasto = ?";
        stat = conn.prepareStatement(req1);
        stat.setString(1, jobPos);
        stat.setString(2, city);

        rs = stat.executeQuery();
        while (rs.next()) {
            offersList.add(new Offers(rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6),
                    rs.getDate(7).toString(), rs.getDate(8).toString(),rs.getString(9)));
        }

                      } catch (SQLException e) {
                       e.printStackTrace();
                 } finally {
                   listsize = String.valueOf(offersList.size());
                  }
          }catch (ClassNotFoundException e) {
         e.printStackTrace();
        }
    }


    public List<Offers> getOffersList() {
        return offersList;
    }

    public void setOffersList(List<Offers> offersList) {
        this.offersList = offersList;
    }

    public String getDataWyst() {
        return dataWyst;
    }

    public void setDataWyst(String dataWyst) {
        this.dataWyst = dataWyst;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getJobPos() {
        return jobPos;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatZakon() {
        return datZakon;
    }

    public void setDatZakon(String datZakon) {
        this.datZakon = datZakon;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

   public List<Offers> getAllRecords() {
        return allRecords;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNrref() {
        return nrref;
    }

    public void setNrref(String nrref) {
        this.nrref = nrref;
    }

    public List<FileUpload> getFu() {
        return fu;
    }

    public void setFu(List<FileUpload> fu) {
        this.fu = fu;
    }

    public void setAllRecords(List<Offers> allRecord) {
        this.allRecords = allRecord;
    }

    public UserID getUserID() {
        return userID;
    }

    public void setUserID(UserID userID) {
        this.userID = userID;
    }

    public String getListsize() {
        return listsize;
    }

    public FileUpload getFup() {
        return fup;
    }

    public void setFup(FileUpload fup) {
        this.fup = fup;
    }

    public void setListsize(String listsize) {
        this.listsize = listsize;
    }

    public List<Offers> getTemplist() {
        return templist;
    }

    public void setTemplist(List<Offers> templist) {
        this.templist = templist;
    }

    public String sortOffers(){
offersList.clear();

        if((jobPos.equals("") && city.equals(""))  || (jobPos==null && city==null))  {

            try{
                Class.forName("com.mysql.jdbc.Driver");
            try(Connection conn= DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {
                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery("SELECT * FROM Offers WHERE DATEDIFF(DATE(DataZakon),CURDATE()) >=0");

                while (rs.next()) {
                    offersList.add(new Offers(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4),rs.getString(5), rs.getString(6),
                            rs.getDate(7).toString(), rs.getDate(8).toString(),rs.getString(9)));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                req1 = null;
            }
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            displayOffers();
        }

        return "/Pages/offers.xhtml?faces-redirect=true";
    }

    public String detailedSearch(){
        int gettime= Integer.parseInt(count);
offersList.clear();
        if(salary.equals("3000")){
            req1="SELECT * FROM Offers WHERE Stanowisko = ? OR  Miasto = ? OR Wynagrodzenie <= 3000 OR " +
                    "DATE(DataWyst) BETWEEN CURDATE() - INTERVAL ? DAY AND CURDATE()" +
                   " UNION SELECT * FROM Offers WHERE Wynagrodzenie IS NULL";
        }else if(salary.equals("3001")){
            req1="SELECT * FROM Offers WHERE Stanowisko = ? OR  Miasto = ? OR  Wynagrodzenie BETWEEN 3001 AND 5000 OR " +
                    "DATE(DataWyst) BETWEEN CURDATE() - INTERVAL ? DAY AND CURDATE()";
        }else if(salary.equals("7000")){
            req1="SELECT * FROM Offers WHERE Stanowisko = ? OR  Miasto = ? OR  Wynagrodzenie BETWEEN 5001 AND  7000 OR " +
                    "DATE(DataWyst) BETWEEN CURDATE() - INTERVAL ? DAY AND CURDATE()";
        }else {
            req1="SELECT * FROM Offers WHERE Stanowisko = ? OR  Miasto = ? OR Wynagrodzenie > 7000 OR " +
                    "DATE(DataWyst) BETWEEN CURDATE() - INTERVAL ? DAY AND CURDATE()";
        }

        listsize="";
      try {
          Class.forName("com.mysql.jdbc.Driver");
          try (Connection conn = DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {
              PreparedStatement stat = conn.prepareStatement(req1);
              stat.setString(1, jobPos);
              stat.setString(2, city);
              stat.setInt(3, gettime);
              ResultSet rs = stat.executeQuery();

              while (rs.next()) {
                  offersList.add(new Offers(rs.getString(1), rs.getString(2), rs.getString(3),
                          rs.getString(4), rs.getString(5), rs.getString(6),
                          rs.getDate(7).toString(), rs.getDate(8).toString(),rs.getString(9)));
              }

          } catch (SQLException e) {
              e.printStackTrace();
          } finally {
              listsize = String.valueOf(offersList.size());
              req1 = "";

          }
      }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "/Pages/offers.xhtml?faces-redirect=true";
    }

        public void getFiles(){

        }
        public void addFile()
        {
            String filerequest="SELECT FileName FROM Resume WHERE Id =?";

            try {
                Class.forName("com.mysql.jdbc.Driver");
                try (Connection conn = DriverManager.getConnection(cplist.get(0),cplist.get(1),cplist.get(2))) {
                    PreparedStatement stat = conn.prepareStatement(filerequest);
                    stat.setString(1, String.valueOf(userID.getID()));
                    ResultSet rs = stat.executeQuery();

                    while (rs.next()) {
                        fup.getFiup().add(new FileUpload(rs.getString(1), rs.getString(2), rs.getInt(3),
                                rs.getDate(4).toString()));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }


