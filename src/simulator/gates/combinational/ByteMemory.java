package simulator.gates.combinational;

import simulator.network.Link;
import simulator.network.Node;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* a byte-addressable memory with 4byte-word with 16bit address bus
 *   in:
 *       0 : write signal
 *       1 -> 16: address
 *       17 -> 48: data in
 *   out:
 *       0 -> 31 : data out */
public class ByteMemory extends Node {
    private Boolean[][] memory;
    private List<Link> memIn;

    public ByteMemory(String label, Link... links) {
        super(label, links);
        memory = new Boolean[65536][8];
        memIn = new ArrayList<>();
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
        int address = address();

        for (int i = 0; i < 4; ++i) {
            if (address + i <= 65535) {
                for (int j = 0; j < 8; ++j) {
                    memory[address + i][j] = getInput(i * 8 + j + 17).getSignal();
                }
            }
        }
    }

    private void memoryRead(){
        int address = address();

        for (int i = 0; i < 4; ++i) {
            if (address + i <= 65535) {
                for (int j = 0; j < 8; ++j) {
                    getOutput(i * 8 + j).setSignal(memory[address + i][j]);
                }
            }
        }
    }

    public List<Link> getMemIn() {
        return memIn;
    }

    @Override
    public Link getInput(int index) {
        return memIn.get(index);
    }

    @Override
    public void evaluate() {
        if (getInput(0).getSignal())
            memoryWrite();
        memoryRead();
    }

    public void setMemory(Boolean[][] memory) {
        this.memory = memory;
    }

    public Boolean[][] getMemory() {
        return memory;
    }
}