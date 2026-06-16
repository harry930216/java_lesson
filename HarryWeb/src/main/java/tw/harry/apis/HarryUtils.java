package tw.harry.apis;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class HarryUtils {
	public static String loadView() throws Exception {
		String source = "C:\\Users\\User\\eclipse-workspace\\HarryPeng\\HarryWeb\\src\\main\\webapp\\view\\view1.html";
		File html = new File(source);
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(html));
		byte[] data = bin.readAllBytes();
		bin.close();
	}
}
