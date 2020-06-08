//Dedicated to Goli

package simulator;

import simulator.control.Simulator;
import simulator.gates.sequential.Clock;
import simulator.gates.combinational.ExplicitInput;
import simulator.gates.sequential.flipflops.DFlipFlop;

public class Main {

    public static void main(String[] args) {
        Clock clock = new Clock("CLOCK", 1000);
        ExplicitInput trueInput = new ExplicitInput("TRUE", true);
        DFlipFlop dFlipFlop1 = new DFlipFlop("D1", clock.getOutput(0), trueInput.getOutput(0));
        DFlipFlop dFlipFlop2 = new DFlipFlop("D2", clock.getOutput(0), dFlipFlop1.getOutput(0));

        Simulator.circuit.startCircuit();

        //debug code
        while (true) {
            System.out.println(dFlipFlop1.getOutput(0).getValue() + "   " + dFlipFlop2.getOutput(0).getValue());
            try {
                Thread.sleep(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
