package simulator.gates.combinational;

import simulator.network.Link;
import simulator.network.Node;

public class Memory extends Node {
    private Boolean[] memory;

    public Memory(String label, Link... links) {
        super(label, links);
        memory = new Boolean[65536];
        for (int i = 0; i < 32; ++i) {
            addOutputLink(false);
        }
    }

    private int address() {
        int temp = 0;
        for (int i = 1; i < 17; ++i) {
            if(inputs.size() > i) {
                if(getInput(i).getSignal()) {
                    temp += Math.pow(2, 16 - i);
                }
            }
        }
        return temp;
    }

    private void memoryWrite() {
        for(int i = 17; i < 49; ++i) {
            memory[address() + i - 17] = getInput(i).getSignal();
        }
    }

    private void memoryRead(){
        for (int i = 0; i < 32; ++i) {
            getOutput(i).setSignal(memory[address() + i]);
        }
    }

    @Override
    public void evaluate() {
        if (getInput(0).getSignal()) {
            memoryWrite();
        } else {
            memoryRead();
        }
    }

    public void setMemory(Boolean[] memory) {
        this.memory = memory;
    }

    public Boolean[] getMemory() {
        return memory;
    }
}
