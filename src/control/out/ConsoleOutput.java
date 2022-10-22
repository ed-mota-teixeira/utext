package control.out;

public class ConsoleOutput implements Output {

	@Override
	public boolean send(String data) {

		if (data == null) {
			return false;
		}

		System.out.println(data);
		return false;
	}

}
