import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class StateMachine {

    int index = 0;
    State startState;
    State activeState;
    HashMap<String, State> states = new HashMap<>();

    public StateMachine(String file) {
        try {
            load(file);
        } catch (Exception e) {
            // TODO: handle exception
        }

        states.put("ERROR", new State(true, true));
    }

    public void run(String inStr) {
        index = 0;
        activeState = startState;

        char[] input = inStr.toCharArray();

        while (index < inStr.length()) {

            System.out.println(input[index] + " -> " +  activeState.transition(input[index]));
            activeState = states.get(activeState.transition(input[index]));
            
            if(index == inStr.length() - 1) {
                System.out.println("Finished input");
                if(activeState.isEndState()) {
                    System.out.println(activeState.isErrorState() ? "Error!" : "Accepting State!");
                }
                else {
                    System.out.println("Reached end of input in non-end state!");
                }
                break;
            } 
            else {
                index++;
            }
        }
    }

    public void printMachine() {
        for (String key : states.keySet()) {
            System.out.println(key + ":");
            HashMap<Character, String> transitions = states.get(key).transitions;
            for (char c : transitions.keySet()) {
                System.out.println("  " + c + " -> " + transitions.get(c));
            }
        }
    }

    public void load(String path) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            String lastState = "";

            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    // COMMENT -> ignore
                    if (!line.startsWith("//")) { 
                        if (line.endsWith(":")) {
                            // STATE
                            String state = line.substring(0, line.length() - 1);
                            
                            // Set the last state seen in the file to know where to put the relevant transitions
                            lastState = state; 

                            states.put(state, new State(state.startsWith("%"), false));
                            if(state.startsWith("$")) {
                                startState = states.get(state);
                            }
                        } else {
                            // See if a state is at the far right of the line delimited by a '>'
                            char[] chars = line.toCharArray();
                            for (int i = line.length() - 1; i >= 0; i--) {
                                if (chars[i] == ';') {
                                    break;
                                } else if (chars[i] == '>') {
                                    // TRANSITION
                                    String targetState = line.substring(i + 1, line.length());
    
                                    for (int j = 0; j < chars.length - targetState.length() - 1; j++) {
                                        switch (chars[j]) {
                                            case ';':
                                                // Yes that means ';' are actually optional, but it's nicer to read
                                                break;
                                            
                                            default:
                                                states.get(lastState).addTransition(chars[j], targetState);
                                                break;
                                        }
                                    }
    
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}