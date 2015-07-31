package main.parse;

public class AutomataLink {

	// fields
	private String methodName;
	private int source;
	private int target;

	public AutomataLink(int source, int target){
		this.source = source;
		this.target = target;
	}

	public String getName(){
		return methodName;
	}

	public int getSource(){
		return source;
	}

	public int getTarget(){
		return target;
	}
}
