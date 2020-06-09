package simulator.control;

import simulator.gates.combinational.Explicit;
import simulator.network.Link;

public class Simulator {
    public static Circuit circuit = new Circuit();
    public static Debugger debugger = new Debugger(100);
    public static Explicit trueGate = new Explicit("TRUE", true);
    public static Explicit falseGate = new Explicit("FALSE", false);
    public static Link trueLogic = trueGate.getOutput(0);
    public static Link falseLogic = falseGate.getOutput(0);
}
