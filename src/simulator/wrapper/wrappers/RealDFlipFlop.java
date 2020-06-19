package simulator.wrapper.wrappers;

import simulator.gates.combinational.Nand;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

public class RealDFlipFlop extends Wrapper {
    public RealDFlipFlop(String label, String stream, Link... links) {
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

        n1.addInput(n4.getOutput(0), n2.getOutput(0));
        n2.addInput(getInput(0), n1.getOutput(0));
        n3.addInput(n2.getOutput(0), getInput(0), n4.getOutput(0));
        n4.addInput(n3.getOutput(0), getInput(1));
        n5.addInput(n2.getOutput(0), n6.getOutput(0));
        n6.addInput(n3.getOutput(0), n5.getOutput(0));

        addOutput(n5.getOutput(0), n6.getOutput(0));
    }
}
