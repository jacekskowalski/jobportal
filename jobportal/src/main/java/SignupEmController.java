import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * Created by olier1 on 24.05.2017.
 */
@ManagedBean
@ViewScoped
public class SignupEmController {
    private String email, password, company, street, zipcode, city,companyId;
    private String str;
    ConfigProperties cp=new ConfigProperties();
    List<String> cplist=cp.getProperties();

    public SignupEmController() {    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String createRecord(){
        boolean checkit=false;
        String redir="/Pages/confirmation.xhtml?faces-redirect=true";
        str="INSERT INTO Client (Nazwa,Ulica, Kod, Miasto,Email,Password,CompanyId,Data_zal_kon) VALUES (?,?,?,?,?,?,?,?)";
        String checkintegrity="SELECT CompanyId FROM Client WHERE CompanyId= ?";
        java.util.Date da=new java.util.Date();
        java.sql.Date d= new java.sql.Date(da.getTime());
      try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {

                try(PreparedStatement stat0 = conn.prepareStatement(checkintegrity)) {
                    stat0.setString(1, getCompanyId());
                    ResultSet rs = stat0.executeQuery();

                    while (rs.next()) {

                        if (companyId.equals(rs.getString("CompanyId")) ) checkit = true;
                            redir="/Pages/signupem.xhtml?faces-redirect=true";
                            break;
                    }
                }
                if(checkit ==false ) {
                    PreparedStatement stat = conn.prepareStatement(str);
                    stat.setString(1, company);
                    stat.setString(2, street);
                    stat.setString(3, zipcode);
                    stat.setString(4, city);
                    stat.setString(5, email);
                    stat.setString(6, password);
                    stat.setString(7, companyId);
                    stat.setDate(8, d);
                    stat.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                reset();
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
          return redir;
    }

        public void reset(){
        email=null;
        password=null;
        company=null;
        companyId=null;
        street=null;
        zipcode=null;
        city=null;
        }
}
