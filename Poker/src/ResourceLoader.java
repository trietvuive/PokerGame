import java.io.InputStream;

public final class ResourceLoader {
	public static InputStream load(String path)
	{
		InputStream input = ResourceLoader.class.getClassLoader().getResourceAsStream(path);
		if(input == null)
		{
			input = ResourceLoader.class.getClassLoader().getResourceAsStream("/"+path);
		}
		return input;
	}
}
