import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;
/**
 * Created by olier1 on 07.06.2017.
 */
@FacesValidator
public class FileValidator implements Validator {

   @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
Part file= (Part) o;
String name=file.getSubmittedFileName();
  if(name.length()== 0){
      FacesMessage message=new FacesMessage("Nie można określić nazwy pliku");
  }else if(name.length() >25){
      FacesMessage message=new FacesMessage("Nazwa pliku jest zbyt długa");
          }
   if(! name.endsWith("pdf") ){
      FacesMessage message=new FacesMessage("Proszę wybrać plik pdf,doc lub docx");
     }
    if(file.getSize() > 308000){
      FacesMessage message=new FacesMessage("Proszę dodać plik mniejszy niż 300kB");
  }
    }
}
