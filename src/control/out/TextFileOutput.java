package control.out;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileOutput implements Output {

	@Override
	public boolean send(String data) {
		if (data == null) {
			return false;
		}

		String ft = "" + System.currentTimeMillis();
		File myFile = new File(ft + ".txt");
		try {
			if (myFile.createNewFile()) {
				FileWriter myWriter = new FileWriter(ft + ".txt");
				try {
					myWriter.write(data);
				} catch (IOException ee) {
					System.out.println(ee);
				} finally {
					myWriter.close();
				}
			} else {
				System.out.println("Unable to create file.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
			return false;
		}

		return true;
	}

}
