package utilities;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({
	"file:/home/vimal.s/eclipse-workspace-new/global_api/src/test/java/propertyFiles/config.Properties"
})

public interface ConfigProperty extends Config{

	@Key("URI")
	String getBaseURI();
}
