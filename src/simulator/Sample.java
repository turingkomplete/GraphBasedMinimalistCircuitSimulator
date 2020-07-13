//Dedicated to Goli

package simulator;

import simulator.control.Simulator;
import simulator.gates.combinational.Not;
import simulator.gates.sequential.Clock;
import simulator.gates.sequential.flipflops.DFlipFlop;
import simulator.wrapper.wrappers.RealDFlipFlop;

public class Sample {
    public static void main(String[] args) {
        //sample circuit
        Clock clock = new Clock("CLOCK", 1000);

        DFlipFlop d1 = new DFlipFlop("D1", clock.getOutput(0));
        DFlipFlop d2 = new DFlipFlop("D2", clock.getOutput(0));
        DFlipFlop d3 = new DFlipFlop("D3", clock.getOutput(0));
        DFlipFlop d4 = new DFlipFlop("D4", clock.getOutput(0));

        Not n1 = new Not("NOT", d4.getOutput(0));


        d1.addInput(n1.getOutput(0));
        d2.addInput(d1.getOutput(0));
        d3.addInput(d2.getOutput(0));
        d4.addInput(d3.getOutput(0));

        Simulator.debugger.addTrackItem(clock, d1, d2, d3, d4);
        Simulator.debugger.setDelay(200);

        Simulator.circuit.startCircuit();
    }
}