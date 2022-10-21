package control.actions;

public class RemoveSpacesAction implements Action {

	@Override
	public String process(String source) {
		if(source == null) {
			System.out.println("Empty source");
			return "";
		}
		return source.replaceAll("\\s", "");
	}

}
