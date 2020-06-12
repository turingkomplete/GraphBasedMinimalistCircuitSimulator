package simulator.wrapper.wrappers;

import simulator.gates.combinational.And;
import simulator.gates.combinational.Xor;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

public class HalfAdder extends Wrapper {
    public HalfAdder(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        And and1 = new And("AND1", getInput(0), getInput(1));
        Xor xor1 = new Xor("XOR1", getInput(0), getInput(1));

        addOutput(and1.getOutput(0), xor1.getOutput(0));
    }
}
