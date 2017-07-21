import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by olier1 on 13.06.2017.
 */
public class ConfigProperties {


    public ArrayList<String> getProperties(){

        ArrayList<String> temp=new ArrayList<>();
        Properties props = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try (InputStream in = classLoader.getResourceAsStream("\\admin.properties"))
        {
            props.load(in);
            temp.add(props.getProperty("url"));
            temp.add(props.getProperty("user"));
            temp.add(props.getProperty("password"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }
}
