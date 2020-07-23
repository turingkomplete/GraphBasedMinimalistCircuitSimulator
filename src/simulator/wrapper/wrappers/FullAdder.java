package simulator.wrapper.wrappers;

import simulator.gates.combinational.Or;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

/*a half adder
 * in:
 *   0 : first bit
 *   1 : second bit
 *   2 : third bit
 * out:
 *   0 : carry
 *   1 : summation */
public class FullAdder extends Wrapper {
    public FullAdder(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        HalfAdder ha1 = new HalfAdder("HALFADDER1", "2X2", getInput(0), getInput(1));
        HalfAdder ha2 = new HalfAdder("HALFADDER2", "2X2", ha1.getOutput(1), getInput(2));
        Or or1 = new Or("OR1", ha2.getOutput(0), ha1.getOutput(0));

        addOutput(or1.getOutput(0), ha2.getOutput(1));
    }
}
