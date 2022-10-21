package control.actions;

public class RemoveNumbersAction implements Action {

	@Override
	public String process(String source) {
		if(source == null) {
			return "";
		}
		return source.replaceAll("[0123456789]", "");
	}

}
