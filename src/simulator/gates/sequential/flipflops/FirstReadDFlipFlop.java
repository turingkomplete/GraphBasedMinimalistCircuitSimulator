package simulator.gates.sequential.flipflops;

import simulator.network.Link;
import simulator.network.Node;

public class FirstReadDFlipFlop extends Node implements FlipFlop {
    private Boolean memory;
    private Boolean edgeFlag;

    public FirstReadDFlipFlop(String label, Link... links) {
        super(label, links);
        edgeFlag = true;
        memory = false;
        addOutputLink(false);
    }

    @Override
    public void setOutput() {
        outputs.get(0).setSignal(memory);
    }

    @Override
    public void loadMemory() {
        memory = getInput(1).getSignal();
    }

    @Override
    public void evaluate() {
        if(getInput(0).getSignal() && edgeFlag) {
            loadMemory();
            setOutput();
            edgeFlag = false;
        } else if(!getInput(0).getSignal() && !edgeFlag) {
            edgeFlag = true;
        }
    }
}