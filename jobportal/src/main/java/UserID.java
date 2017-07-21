import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 * Created by olier1 on 07.06.2017.
 */
@ManagedBean
@SessionScoped
public class UserID<T> implements Serializable{
private T ID;

    public UserID(){}

    public T getID() {
        return ID;
    }

    public void setID(T ID) {
        this.ID = ID;
    }

    public boolean checkType(){
        return getID().getClass().getName() == "java.lang.Integer" ? true : false;
    }
}
