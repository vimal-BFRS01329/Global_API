package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {

	public static void main(String[] args) throws IOException {
		
		System.out.println(System.getProperty("user.dir"));
		Properties config = new Properties();
		Properties param = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/java/propertyFiles/config.Properties");
		config.load(fis);
		
		fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/java/propertyFiles/param.Properties");
		param.load(fis);
	}
}
