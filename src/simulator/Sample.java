//Dedicated to Goli

package simulator;

import simulator.control.Simulator;
import simulator.wrapper.wrappers.FullAdder;
import simulator.wrapper.wrappers.HalfAdder;

public class Sample {
    public static void main(String[] args) {
        //sample circuit
        FullAdder fullAdder1 = new FullAdder("FULLADDER1", "3X2", Simulator.falseLogic, Simulator.falseLogic, Simulator.trueLogic);

        Simulator.circuit.startCircuit("FAST");

        Simulator.debugger.addTrackItem(fullAdder1);
        Simulator.debugger.startDebugger();
    }
}