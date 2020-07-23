package simulator.wrapper.wrappers;

import simulator.control.Simulator;
import simulator.gates.combinational.And;
import simulator.gates.combinational.Xor;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

/*a half adder
 * in:
 *   0 : first bit
 *   1 : second bit
 * out:
 *   0: carry
 *   1: summation */
public class HalfAdder extends Wrapper {
    public HalfAdder(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        And a1 = new And("AND1");
        Xor xor1 = new Xor("XOR1");

        a1.addInput(getInput(0), getInput(1));
        xor1.addInput(getInput(0), getInput(1));

        addOutput(a1.getOutput(0), xor1.getOutput(0));
    }
}
