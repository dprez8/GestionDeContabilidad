import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyFile {
    public static void main(String[] args) throws IOException {
        Properties prop=new Properties();
        FileInputStream ip= new FileInputStream("src/main/resources/config.properties");
        prop.load(ip);
    }
}
