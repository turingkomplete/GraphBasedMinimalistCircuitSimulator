package simulator.procedures;

import simulator.gates.combinational.And;
import simulator.network.Link;

public class HalfAdder extends Procedure {
    public HalfAdder(String label, Link... links) {
        super(label, links);
    }

    @Override
    public void initialize() {
        And a1 = new And("AND1", getInput(0), getInput(1));
        addOutput(a1.getOutput(0));
    }
}
