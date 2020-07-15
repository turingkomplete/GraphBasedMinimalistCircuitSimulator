//Dedicated to Goli

package simulator;

import simulator.control.Simulator;
import simulator.wrapper.wrappers.*;

public class Sample {
    public static void main(String[] args) {
        //sample circuit
        Adder adder = new Adder("ADDER", "10X6",
                Simulator.falseLogic, Simulator.trueLogic, Simulator.trueLogic, Simulator.falseLogic, Simulator.trueLogic,
                Simulator.falseLogic, Simulator.trueLogic, Simulator.falseLogic, Simulator.falseLogic, Simulator.trueLogic);

        Simulator.debugger.addTrackItem(adder);
        Simulator.debugger.setDelay(200);
        Simulator.circuit.startCircuit();
    }
}