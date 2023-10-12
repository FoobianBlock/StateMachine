public class Program {
    public static void main(String[] args) {
        System.out.print("Input file path to machine: ");
        StateMachine sm = new StateMachine(System.console().readLine());
        sm.printMachine();
        System.out.println("");
        System.out.print("Input string: ");
        sm.run(System.console().readLine());
    }
}
