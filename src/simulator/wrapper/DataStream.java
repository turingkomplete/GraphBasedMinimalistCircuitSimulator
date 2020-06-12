package simulator.wrapper;

import simulator.network.Link;
import simulator.network.Node;

public class DataStream extends Node {
    public DataStream(String label, int size, Link... links) {
        super(label, links);
        for (int i = 0; i < size; ++i) {
            addOutputLink(false);
        }
    }

    @Override
    public void evaluate() {
        for (int i = 0; i < getInputs().size(); ++i) {
            getOutput(i).setSignal(getInput(i).getSignal());
        }
    }
}
