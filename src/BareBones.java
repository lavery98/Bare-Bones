import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BareBones {

	protected Map<String, Variable> variables = new HashMap<String, Variable>();
	protected ArrayList<String> fileLines = new ArrayList<String>();
	
	public static void main(String[] args) {
		BareBones program = new BareBones();
		program.runProgram();
	}
	
	public void runProgram() {
		System.out.println("Enter the bare bones file name: ");
		
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			String filename = inputReader.readLine();
			loadCodeFile(filename);
			
			int currentPosition = 0;
			
			while(currentPosition != fileLines.size()) {
				String[] parts = fileLines.get(currentPosition).split(" ");
				
				currentPosition++;
			}
			
			/*BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			String fileLine;
			while((fileLine = reader.readLine()) != null) {
				String[] parts = fileLine.split(" ");
				
				if(parts[0].equals("clear")) {
					if(variables.containsKey(parts[1])) {
						variables.remove(parts[1]);
					}
				} else if(parts[0].equals("incr")) {
					if(variables.containsKey(parts[1])) {
						Variable oldVariable = variables.get(parts[1]);
						oldVariable.increaseVariableValue();
					} else {
						Variable newVariable = new Variable(parts[1]);
						// Assume they want to increase the 0 value
						newVariable.increaseVariableValue();
						variables.put(parts[1], newVariable);
					}
				} else if(parts[0].equals("decr")) {
					// The variable needs to exist otherwise we would have to create negative values
					if(variables.containsKey(parts[1])) {
						Variable var = variables.get(parts[1]);
						
						// We can't have variables with a value less than 0
						if(var.getVariableValue() > 0) {
							var.decreaseVariableValue();
						}
					}
				} else if(parts[0].equals("while")) {
					
				} else {
					// TODO: Make more user friendly
					throw new Exception("Unknown command used. " + fileLine);
				}
				
				System.out.println(fileLine);
				
				System.out.println("**************************************");
				System.out.println("Current Variables");
				System.out.println("**************************************");
				
				for(Map.Entry<String, Variable> entry : variables.entrySet()) {
					System.out.print(entry.getKey());
					Variable oldVariable = entry.getValue();
					System.out.print(" - ");
					System.out.print(oldVariable.getVariableValue());
					System.out.println();
				}
			}
			
			reader.close();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadCodeFile(String filename) {
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader(filename));
			
			String line;
			while((line = reader.readLine()) != null) {
				fileLines.add(line);
			}
			reader.close();
		} catch(Exception e) {
			System.err.println("There was an error reading the code file");
		}
	}
}
