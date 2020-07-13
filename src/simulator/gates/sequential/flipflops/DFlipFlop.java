package simulator.gates.sequential.flipflops;

import simulator.network.Link;
import simulator.network.Node;

public class DFlipFlop extends Node implements FlipFlop {
    private Boolean memory;
    private Boolean edgeFlag;

    public DFlipFlop(String label, Link... links) {
        super(label, links);
        edgeFlag = true;
        memory = false;
        addOutputLink(false);
        addOutputLink(true);
    }

    @Override
    public void setOutput() {
        outputs.get(0).setSignal(memory);
        outputs.get(1).setSignal(!memory);
    }

    @Override
    public void loadMemory() {
        memory = getInput(1).getSignal();
    }

    @Override
    public void evaluate() {
        if(getInput(0).getSignal() && edgeFlag) {
            if (getInput(1).getSource() instanceof FlipFlop) {
                setOutput();
                loadMemory();
            } else {
                loadMemory();
                setOutput();
            }
            edgeFlag = false;
        } else if(!getInput(0).getSignal() && !edgeFlag) {
            edgeFlag = true;
        }
    }
}