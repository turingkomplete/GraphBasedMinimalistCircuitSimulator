//Dedicated to Goli

package simulator;

import simulator.control.Simulator;
import simulator.gates.combinational.And;
import simulator.gates.combinational.Explicit;

public class Main {

    public static void main(String[] args) {
        //sample code
        Explicit trueValue = new Explicit("TRUE", true);
        Explicit falseValue = new Explicit("FALSE", false);
        And a1 = new And("AND1", trueValue.getOutput(0), trueValue.getOutput(0));
        And a2 = new And("AND2", trueValue.getOutput(0), a1.getOutput(0));
        And a3 = new And("AND3", falseValue.getOutput(0), a2.getOutput(0));
        //a1.addInput(a2.getOutput(0));
        //a2.addInput(a3.getOutput(0));

        Simulator.circuit.startCircuit();

        //debug code
        while (true) {
            System.out.println(
                    a1.getOutput(0).getValue() + "   " +
                    a2.getOutput(0).getValue() + "   " +
                    a3.getOutput(0).getValue()
            );

            try {
                Thread.sleep(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
