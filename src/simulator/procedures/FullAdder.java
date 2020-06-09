package simulator.procedures;

import simulator.gates.combinational.Or;
import simulator.network.Link;

public class FullAdder extends Procedure {
    public FullAdder(String label, Link... links) {
        super(label, links);
    }

    @Override
    public void initialize() {
        HalfAdder ha1 = new HalfAdder("HA1", getInput(0), getInput(1));
        HalfAdder ha2 = new HalfAdder("HA2", ha1.getOutput(1), getInput(2));
        Or or1 = new Or("OR1", ha1.getOutput(0), ha2.getOutput(0));

        addOutput(or1.getOutput(0), ha2.getOutput(1));
    }
}
