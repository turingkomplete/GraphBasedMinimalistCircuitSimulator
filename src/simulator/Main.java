//Dedicated to Goli

package simulator;

import simulator.control.Circuit;
import simulator.control.Simulator;
import simulator.gates.sequential.flipflops.Clock;
import simulator.gates.combinational.ExplicitInput;
import simulator.gates.sequential.flipflops.DFlipFlop;

public class Main {

    public static void main(String[] args) {
//        Bit trueBit = new Bit("True",true);
//        Bit falseBit = new Bit("False",false);
//
//        And a1 = new And("AND1", trueBit.getOutput(0), trueBit.getOutput(0));
//        And a2 = new And("AND2", a1.getOutput(0), trueBit.getOutput(0));
//        //a1.addInput(a2.getOutput(0));
//
//        Circuit.start();
//
//        System.out.println(a1.getOutput(0).getValue() + " " + a2.getOutput(0).getValue());

        Circuit c = Simulator.circuit;

        Clock clock = new Clock("CLOCK", 1000);
        DFlipFlop dFlipFlop1 = new DFlipFlop("", clock.getOutput(0), new ExplicitInput("", true).getOutput(0));
        DFlipFlop dFlipFlop2 = new DFlipFlop("", clock.getOutput(0), dFlipFlop1.getOutput(0));

        Simulator.circuit.startCircuit();

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
