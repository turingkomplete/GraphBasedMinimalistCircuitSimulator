//Dedicated to Goli

package simulator;

import simulator.control.Simulator;
import simulator.gates.sequential.Clock;
import simulator.gates.sequential.flipflops.JKFlipFlop;
import simulator.wrappers.FullAdder;

public class Sample {
    public static void main(String[] args) {
        //sample circuit
        Clock ck1 = new Clock("CK1", 1000);
        JKFlipFlop jk1 = new JKFlipFlop("JK1", ck1.getOutput(0), Simulator.trueLogic, Simulator.trueLogic);
        FullAdder fa1 = new FullAdder("FA1", Simulator.falseLogic, Simulator.falseLogic, jk1.getOutput(0));

        Simulator.circuit.startCircuit();

        Simulator.debugger.addTrackItem(fa1);
        Simulator.debugger.startDebugger();
    }
}