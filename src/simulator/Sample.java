//Dedicated to Goli

package simulator;

import simulator.control.Simulator;
import simulator.wrapper.wrappers.FullAdder;

public class Sample {
    public static void main(String[] args) {
        //sample circuit
        FullAdder fullAdder1 = new FullAdder("FULLADDER1", "3X2",
                Simulator.falseLogic, Simulator.trueLogic, Simulator.trueLogic);

        Simulator.debugger.addTrackItem(fullAdder1);

        Simulator.circuit.startCircuit("FAST");
    }
}