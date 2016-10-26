
public class Variable {
	protected String variableName;
	protected int variableValue = 0;
	
	public Variable(String name) {
		variableName = name;
	}
	
	public int getVariableValue() {
		return variableValue;
	}
	
	public void increaseVariableValue() {
		variableValue++;
	}
	
	public void decreaseVariableValue() {
		variableValue--;
	}
}
