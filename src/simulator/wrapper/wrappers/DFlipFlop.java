package simulator.wrapper.wrappers;

import simulator.gates.combinational.Nand;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

/*a data flip-flop
 * in:
 *   0 : clock signal
 *   1 : data signal
 * out:
 *   0 : q
 *   1 : q-bar */
public class DFlipFlop extends Wrapper {
    public DFlipFlop(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        Nand n1 = new Nand("NAND1");
        Nand n2 = new Nand("NAND2");
        Nand n3 = new Nand("NAND3");
        Nand n4 = new Nand("NAND4");
        Nand n5 = new Nand("NAND5");
        Nand n6 = new Nand("NAND6");

        n1.setLatch(true);
        n2.setLatch(true);
        n3.setLatch(true);
        n4.setLatch(true);
        n5.setLatch(true);
        n6.setLatch(true);

        n1.addInput(n4.getOutput(0), n2.getOutput(0));
        n2.addInput(getInput(0), n1.getOutput(0));
        n3.addInput(n2.getOutput(0), getInput(0), n4.getOutput(0));
        n4.addInput(n3.getOutput(0), getInput(1));
        n5.addInput(n2.getOutput(0), n6.getOutput(0));
        n6.addInput(n3.getOutput(0), n5.getOutput(0));

        addOutput(n5.getOutput(0), n6.getOutput(0));
    }
}
