package simulator.wrappers;

import simulator.gates.combinational.And;
import simulator.gates.combinational.Xor;
import simulator.network.Link;

public class HalfAdder extends Wrapper {
    public HalfAdder(String label, Link... links) {
        super(label, links);
    }

    @Override
    public void initialize() {
        And a1 = new And("AND1", getInput(0), getInput(1));
        Xor xor1 = new Xor("XOR1", getInput(0), getInput(1));

        addOutput(a1.getOutput(0), xor1.getOutput(0));
    }
}
