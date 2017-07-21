/**
 * Created by olier1 on 09.06.2017.
 */
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@ManagedBean
@ViewScoped
public class FileDownload implements Serializable{

    private StreamedContent file;
    String ty,name;

    public FileDownload() {

    }

    public String getTy() {
        return ty;
    }

    public void setTy(String ty) {
        this.ty = ty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StreamedContent getFile() {
        return file;
    }



   public void downloadChoosenFile(String s){
       String str1=System.getProperty("user.home");
       Path pa1=Paths.get(str1);//lub to
   //InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(str1+"\\DB\\"+s);
       InputStream stream= null;
       try {
           stream = new FileInputStream(new File(str1+"\\DB\\"+s));
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }
       file = new DefaultStreamedContent(stream, "application/pdf", s);

   }


}
