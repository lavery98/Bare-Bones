import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class BareBones {

	protected Map<String, Variable> variables = new HashMap<String, Variable>();
	protected Map<Integer, Integer> whileBlocks = new HashMap<Integer, Integer>();
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
			Deque<Integer> loopStack = new ArrayDeque<Integer>();

			// Loop until we are at the end of the file/program
			while(currentPosition != fileLines.size()) {
				System.out.println(fileLines.get(currentPosition));
				
				String[] parts = fileLines.get(currentPosition).split(" ");
				
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
					if(variables.containsKey(parts[1])) {
						Variable checkVariable = variables.get(parts[1]);
						
						if(checkVariable.getVariableValue() != 0) {
							loopStack.push(currentPosition);
						} else {
							currentPosition = whileBlocks.get(currentPosition);
						}
					} else {
						throw new Exception("Variable not defined. " + fileLines.get(currentPosition));
					}
				} else if(parts[0].equals("end")) {
					currentPosition = loopStack.pop() - 1;
				} else {
					// TODO: Make more user friendly
					throw new Exception("Unknown command used. " + fileLines.get(currentPosition));
				}
				
				System.out.println("**************************************");
				System.out.println("Current Variables");
				System.out.println("**************************************");
				
				// Loop through and output variables
				for(Map.Entry<String, Variable> entry : variables.entrySet()) {
					System.out.print(entry.getKey());
					Variable oldVariable = entry.getValue();
					System.out.print(" - ");
					System.out.print(oldVariable.getVariableValue());
					System.out.println();
				}
				
				currentPosition++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadCodeFile(String filename) {
		BufferedReader reader;
		
		try {
			// Try and open the file given
			reader = new BufferedReader(new FileReader(filename));
			
			// These variables are used to find the while blocks
			int lineCount = 0;
			Deque<Integer> loopStack = new ArrayDeque<Integer>();
			
			// Add each line of the file to the array list
			String line;
			while((line = reader.readLine()) != null) {
				if(!line.endsWith(";")) {
					throw new Exception("There is a missing semi-colon. " + line);
				}
				
				String newLine = line.substring(0, line.length() - 1).trim();
				
				fileLines.add(newLine);
				
				String[] parts = newLine.split(" ");
				
				if(parts[0].equals("while")) {
					loopStack.push(lineCount);
				} else if(parts[0].equals("end")) {
					int whileStart = loopStack.pop();
					
					whileBlocks.put(whileStart, lineCount);
				}
				
				lineCount++;
			}
			
			reader.close();
			
			if(loopStack.size() > 0) {
				throw new Exception("A while loop does not end.");
			}
		} catch(Exception e) {
			System.err.println("There was an error reading the code file");
			e.printStackTrace();
		}
	}
}
