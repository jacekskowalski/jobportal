import org.apache.commons.io.FilenameUtils;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by olier1 on 07.06.2017.
 */

@ManagedBean
@ViewScoped

public class FileUpload implements Serializable{
    String validateName="";

    @ManagedProperty(value = "#{userID}")
    private UserID userID;
    String name;
    private Part file;
    private String id,getDate;
    private List<FileUpload> fiup = new ArrayList<FileUpload>();
    private int fsize;
    int size = 0;
    String str = "";
    private static final Logger logger = Logger.getLogger(FileUpload.class.getName());
    ConfigProperties cp=new ConfigProperties();
    List<String> cplist=cp.getProperties();
    public FileUpload() {    }

    public FileUpload(String id,String name, int size,String getDate) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.getDate=getDate;
    }

    private static String getFileName(Part p){
        for(String s: p.getHeader("content-disposition").split(";")){
            if(s.trim().startsWith("filename")){
                String filename = s.substring(s.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
            }
        }
        return null;

    }

    public void upload() {
     //   InputStream inputs = null;
        FileOutputStream fis = null;

        logger.info("Szczegółowe informacje o pliku:");
        logger.log(Level.INFO, "Identyfikator komponentu file:{0}", file.getName());
        logger.log(Level.INFO, "Typ zawartości:{0}", file.getContentType());
        logger.log(Level.INFO, "Nazwa przesłanego pliku:{0}", getFileName(file));
        logger.log(Level.INFO, "Wielkość pliku:{0}", file.getSize());
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try( InputStream inputs=file.getInputStream()) {
FacesContext fc=FacesContext.getCurrentInstance();
ExternalContext ec=fc.getExternalContext();
Path pa=Paths.get(((ServletContext)ec.getContext()).getRealPath("\\DB")).getRoot();
String str1=System.getProperty("user.home");
Path pa1=Paths.get(str1);
System.out.println("Home path "+str1);
          String s=pa1.toString();

          byte[] newbyte = new byte[308000];

            fis = new FileOutputStream(s+"\\DB\\"+getFileName(file));
            validateName=getFileName(file);
            while ((size = inputs.read(newbyte)) != -1) {
                fis.write(newbyte, 0, size);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Przesłano plik pomyślnie"));
            fis.close();
        //    inputs.close();

            saveData();

        }
        catch (IOException ex) {
            ex.printStackTrace();
            ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nie udało się przesłać pliku"));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserID getUserID() {
        return userID;
    }

    public void setUserID(UserID userID) {
        this.userID = userID;
    }

    public int getFsize() {
        return fsize;
    }

    public void setFsize(int fsize) {
        this.fsize = fsize;
    }

    public FileUpload(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<FileUpload> getFiup() {
        return fiup;
    }

    public void setFiup(List<FileUpload> fiup) {
        this.fiup = fiup;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public void saveData() {

        java.util.Date da = new java.util.Date();
        java.sql.Date d = new java.sql.Date(da.getTime());
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(cplist.get(0), cplist.get(1), cplist.get(2))) {

                str = "INSERT INTO Resume (Id,FileName,FileSize,SendDate,UserID) VALUES (?,?,?,?,?)";
                PreparedStatement stat = conn.prepareStatement(str);
                stat.setInt(1, (int)userID.getID());
                stat.setString(2, validateName);
                stat.setInt(3, (int) file.getSize());
                stat.setDate(4, d);
                stat.setInt(5,(int)userID.getID());
                stat.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static String getValidateName(Part p){
        String s1=" ";
        for(String s: p.getHeader("content-disposition").split(";")){

            if(s.trim().startsWith("filename")){
                String filename = s.substring(s.indexOf('=') + 1).trim().replace("\"", "");
                s1= filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix
            }
        }
        return s1;

    }
}


