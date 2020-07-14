//Dedicated to Goli

package simulator;

import simulator.control.Simulator;
import simulator.gates.combinational.Not;
import simulator.gates.sequential.Clock;
import simulator.gates.sequential.flipflops.DFlipFlop;
import simulator.wrapper.wrappers.FullAdder;
import simulator.wrapper.wrappers.RealDFlipFlop;

public class Sample {
    public static void main(String[] args) {
        //sample circuit
        FullAdder fullAdder1 = new FullAdder("FULLADDER1", "3X2", Simulator.falseLogic,
                Simulator.trueLogic);
        FullAdder fullAdder2 = new FullAdder("FULLADDER2", "3X2", Simulator.falseLogic,
                Simulator.falseLogic, fullAdder1.getOutput(1));

        fullAdder1.addInput(fullAdder2.getOutput(1));

        Simulator.debugger.addTrackItem(fullAdder1, fullAdder2);
        Simulator.debugger.setDelay(500);
        Simulator.circuit.startCircuit();
    }
}