//Dedicated to Goli

package simulator;

import simulator.control.Simulator;
import simulator.gates.combinational.Not;
import simulator.gates.sequential.Clock;
import simulator.wrapper.wrappers.RealDFlipFlop;

public class Sample {
    public static void main(String[] args) {
        //sample circuit
        Clock clock = new Clock("CLOCK", 1000);

        RealDFlipFlop d1 = new RealDFlipFlop("D1", "2X2", clock.getOutput(0));
        RealDFlipFlop d2 = new RealDFlipFlop("D2", "2X2", clock.getOutput(0));
        RealDFlipFlop d3 = new RealDFlipFlop("D3", "2X2", clock.getOutput(0));
        RealDFlipFlop d4 = new RealDFlipFlop("D4", "2X2", clock.getOutput(0));

        Not n1 = new Not("NOT", d4.getOutput(0));


        d1.addInput(n1.getOutput(0));
        d2.addInput(d1.getOutput(0));
        d3.addInput(d2.getOutput(0));
        d4.addInput(d3.getOutput(0));

        Simulator.debugger.addTrackItem(clock, d1, d2, d3, d4);
        Simulator.debugger.setDelay(500);

        Simulator.circuit.startCircuit();
    }
}