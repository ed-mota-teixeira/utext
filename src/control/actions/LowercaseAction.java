package control.actions;

public class LowercaseAction implements Action {

	@Override
	public String process(String source) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(source.toLowerCase());
		
		return sb.toString();
	}

}
