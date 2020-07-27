//Dedicated to Goli

package simulator;

import simulator.control.Simulator;
import simulator.gates.combinational.ByteMemory;
import simulator.gates.sequential.BigClock;
import simulator.gates.sequential.Clock;
import simulator.network.Link;
import simulator.wrapper.wrappers.*;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Sample {
    public static void main(String[] args) {
        //sample circuit
        BigClock clock = new BigClock("CLOCK");
        DFlipFlop shoift0 = new DFlipFlop("SHIFT0", "2X2", clock.getOutput(0), Simulator.falseLogic);
        DFlipFlop shoift1 = new DFlipFlop("SHIFT1", "2X2", clock.getOutput(0), shoift0.getOutput(0));

        DFlipFlop[] counter = new DFlipFlop[32];
        for (int i =0; i < 32; ++i) {
            counter[i] = new DFlipFlop("D" + i, "2X2", clock.getOutput(0));
        }
        Link[] counterInit = new Link[32];
        for (int i = 0; i < 32; ++i) {
            counterInit[i] = Simulator.falseLogic;
        }

        Adder adder = new Adder("ADDER", "64X33");
        for (int i =0; i < 32; ++i) {
            adder.addInput(counter[i].getOutput(0));
        }
        Link[] four = new Link[32];
        for (int i =0; i < 29; ++i) {
            four[i] = Simulator.falseLogic;
        }
        four[29] = Simulator.trueLogic;
        four[30] = Simulator.falseLogic;
        four[31] = Simulator.falseLogic;

        adder.addInput(four);

        Multiplexer[] counterSelect = new Multiplexer[32];
        for (int i =0; i < 32; ++i) {
            counterSelect[i] = new Multiplexer("M" + i, "3X1",
                    shoift1.getOutput(0), adder.getOutput(i + 1), counterInit[i]);
        }
        for (int i =0; i < 32; ++i) {
            counter[i].addInput(counterSelect[i].getOutput(0));
        }


        ByteMemory mem = new ByteMemory("MEMORY");

        Boolean[][] temp = new Boolean[65536][8];
        for (int i = 0; i < 65536; ++i) {
            for (int j = 0; j < 8; ++j) {
                temp[i][j] = false;
            }
        }
        for (int i = 0; i < 8; ++i) {
            temp[1][i] = true;
        }

        mem.setMemory(temp);

        //write
        mem.addInput(Simulator.falseLogic);

        //address
        for (int i = 0; i < 16; ++i) {
            mem.addInput(counter[i + 16].getOutput(0));
        }

        //input
        for (int i = 0; i < 16; ++i) {
            mem.addInput(Simulator.falseLogic);
        }
        for (int i = 16; i < 32; ++i) {
            mem.addInput(Simulator.trueLogic);
        }

        Simulator.debugger.addTrackItem(clock, mem);
        Simulator.debugger.setDelay(0);
        Simulator.circuit.startCircuit(10);
    }
}