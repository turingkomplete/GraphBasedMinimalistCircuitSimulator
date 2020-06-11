//Dedicated to Goli

package simulator;

import simulator.control.Simulator;
import simulator.gates.combinational.Nand;
import simulator.gates.combinational.Xor;
import simulator.gates.sequential.Clock;
import simulator.gates.sequential.flipflops.JKFlipFlop;
import simulator.wrappers.FullAdder;

public class Sample {
    public static void main(String[] args) {
        //sample circuit
        Clock ck1 = new Clock("CK1", 1000);

        Nand nand1 = new Nand("NAND1");
        Nand nand2 = new Nand("NAND2");
        Nand nand3 = new Nand("NAND3");
        Nand nand4 = new Nand("NAND4");
        Nand nand5 = new Nand("NAND5");
        Nand nand6 = new Nand("NAND6");

        Xor xor = new Xor("XOR");

        nand1.addInput(nand4.getOutput(0), nand2.getOutput(0));
        nand2.addInput(nand1.getOutput(0), ck1.getOutput(0));
        nand3.addInput(nand2.getOutput(0), ck1.getOutput(0), nand4.getOutput(0));
        nand4.addInput(nand3.getOutput(0), xor.getOutput(0));
        nand5.addInput(nand2.getOutput(0), nand6.getOutput(0));
        nand6.addInput(nand3.getOutput(0), nand5.getOutput(0));
        xor.addInput(nand5.getOutput(0), Simulator.trueLogic);


        Simulator.circuit.startCircuit("REAL");

        Simulator.debugger.addTrackItem(nand5);
        Simulator.debugger.startDebugger();
    }
}