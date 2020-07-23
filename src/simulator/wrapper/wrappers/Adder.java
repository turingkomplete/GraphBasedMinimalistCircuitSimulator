package simulator.wrapper.wrappers;

import simulator.control.Simulator;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.List;

/* a generic adder
* for n inputs:
*   in:
*       0 -> n/2-1 : number1 (highest order bit in 0)
*       n/2 -> n-1 : number2 (highest order bit in n/2)
*   out:
*       0 : carry
*       1 -> n/2 summation */
public class Adder extends Wrapper {
    public Adder(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        /* you can also use getInputs().size() instead of getInputSize()
        * but to prevent several potential problems, always try not to use
        * collections explicitly. */
        int firstLowerBitIndex = (getInputSize() - 1) / 2;
        int secondLowerBitIndex = getInputSize() - 1;
        int fullAddersSize = getInputSize() / 2;

        List<FullAdder> fullAdders = new ArrayList<>();

        for (int i = 0; i < fullAddersSize; ++i)
            fullAdders.add(new FullAdder("FA" + i, "3X2",
                    getInput(firstLowerBitIndex - i),
                    getInput(secondLowerBitIndex - i)));

        fullAdders.get(0).addInput(Simulator.falseLogic);

        for (int i = 1; i < fullAddersSize; ++i)
            fullAdders.get(i).addInput(fullAdders.get(i - 1).getOutput(0));

        addOutput(fullAdders.get(fullAddersSize - 1).getOutput(0));

        for (int i = fullAddersSize - 1; i >= 0; --i)
            addOutput(fullAdders.get(i).getOutput(1));
    }
}