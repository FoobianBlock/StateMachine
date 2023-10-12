import java.util.HashMap;

public class State {
    private final boolean endState;
    private final boolean errorState;
    public HashMap<Character, String> transitions = new HashMap<>();

    public State(boolean end, boolean error) {
        endState = end;
        errorState = error;
    }

    public void addTransition(Character c, String targetState) {
        transitions.put(c, targetState);
    }

    public String transition(char c) {
        String target = transitions.get(c);
        return target == null ? "ERROR" : target;
    }

    public boolean isEndState() {
        return endState;
    }

    public boolean isErrorState() {
        return errorState;
    }
}
