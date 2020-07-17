package simulator.wrapper.wrappers;

import simulator.gates.combinational.And;
import simulator.gates.combinational.Not;
import simulator.gates.combinational.Or;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

public class Mux4x1 extends Wrapper {
    public Mux4x1(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        Not not0 = new Not("N0", getInput(0));
        Not not1 = new Not("N1", getInput(1));

        And[] ands = new And[4];
        for (int i = 0; i < 4; ++i)
            ands[i] = new And("A" + i, getInput(i + 2));
        ands[0].addInput(not0.getOutput(0), not1.getOutput(0));
        ands[1].addInput(getInput(0), not1.getOutput(0));
        ands[2].addInput(not0.getOutput(0), getInput(1));
        ands[3].addInput(getInput(0), getInput(1));

        Or or = new Or("Or");
        for (int i = 0; i < 4; ++i)
            or.addInput(ands[i].getOutput(0));

            addOutput(or.getOutput(0));
    }
}
