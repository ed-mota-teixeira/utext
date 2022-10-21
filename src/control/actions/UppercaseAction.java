package control.actions;

public class UppercaseAction implements Action {

	@Override
	public String process(String source) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(source.toUpperCase());
		
		return sb.toString();
	}

}
