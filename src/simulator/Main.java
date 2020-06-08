//Dedicated to Goli

package simulator;

import simulator.control.Circuit;
import simulator.control.Simulator;
import simulator.gates.combinational.And;
import simulator.gates.sequential.Clock;
import simulator.gates.combinational.ExplicitInput;
import simulator.gates.sequential.flipflops.DFlipFlop;

public class Main {

    public static void main(String[] args) {
        ExplicitInput trueValue = new ExplicitInput("TRUE", true);
        ExplicitInput falseValue = new ExplicitInput("FALSE", false);
        And a1 = new And("AND1", trueValue.getOutput(0), trueValue.getOutput(0));
        And a2 = new And("AND2", trueValue.getOutput(0), a1.getOutput(0));
        And a3 = new And("AND3", trueValue.getOutput(0), a2.getOutput(0));
        a1.addInput(a3.getOutput(0));

        Simulator.circuit.startCircuit();

        //debug code
        while (true) {
            System.out.println(a3.getOutput(0).getValue());
            try {
                Thread.sleep(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
