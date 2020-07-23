//Dedicated to Goli

package simulator;

import simulator.control.Simulator;
import simulator.gates.combinational.Memory;
import simulator.gates.combinational.Not;
import simulator.gates.sequential.Clock;
import simulator.wrapper.wrappers.*;

public class Sample {
    public static void main(String[] args) {
        //sample circuit
        Clock clock = new Clock("CLOCK", 1000);

        DFlipFlop d0 = new DFlipFlop("D0", "2X2", clock.getOutput(0));
        Not n0 = new Not("N0");

        n0.addInput(d0.getOutput(0));
        d0.addInput(n0.getOutput(0));

        Memory mem = new Memory("MEMORY");

        Boolean[] temp = new Boolean[65536];
        for (int i = 0; i < 32; ++i) {
            temp[i] = true;
        }
        for (int i = 32; i < 65536; ++i) {
            temp[i] = false;
        }

        mem.setMemory(temp);

        //write
        mem.addInput(n0.getOutput(0));

        //address
        for (int i = 0; i < 16; ++i) {
            mem.addInput(Simulator.falseLogic);
        }

        //input
        for (int i = 0; i < 16; ++i) {
            mem.addInput(Simulator.falseLogic);
        }
        for (int i = 16; i < 32; ++i) {
            mem.addInput(Simulator.trueLogic);
        }

        Simulator.debugger.addTrackItem(clock, mem);
        Simulator.debugger.setDelay(200);
        Simulator.circuit.startCircuit();
    }
}