package simulator.wrapper.wrappers;

import simulator.gates.combinational.Or;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

public class FullAdder extends Wrapper {
    public FullAdder(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        HalfAdder halfAdder1 = new HalfAdder("HALFADDER1", "2X2", getInput(0), getInput(1));
        HalfAdder halfAdder2 = new HalfAdder("HALFADDER2", "2X2", halfAdder1.getOutput(1), getInput(2));
        Or or1 = new Or("OR1", halfAdder1.getOutput(0), halfAdder2.getOutput(0));

        addOutput(or1.getOutput(0), halfAdder2.getOutput(1));
    }
}
