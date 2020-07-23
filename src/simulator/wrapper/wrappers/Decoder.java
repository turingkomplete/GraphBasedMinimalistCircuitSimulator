package simulator.wrapper.wrappers;

import simulator.gates.combinational.And;
import simulator.gates.combinational.Not;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.List;

/* a generic decoder
 * for n inputs:
 *   in:
 *       0 -> n-1 : code (highest order bit in 0)
 *   out:
 *       0 : code = 0
 *       1 : code = 1
 *       .
 *       .
 *       .
 *       2 pow n : code 2 pow n */
public class Decoder extends Wrapper {
    public Decoder(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        int inSize = getInputSize();
        int andsCount = (int) Math.pow(2, getInputSize());

        List<Not> nots = new ArrayList<>();
        List<And> ands = new ArrayList<>();

        for (int i = 0; i < inSize; ++i)
            nots.add(new Not("NOT" + i, getInput(i)));

        ands.add(new And("AND0"));
        for (int i = 0; i < inSize; ++i)
            ands.get(0).addInput(nots.get(i).getOutput(0));

        for (int i = 1; i < andsCount; ++i) {
            ands.add(new And("AND" + i));
            int temp = i;
            for (int position = inSize - 1; position >= 0; --position) {
                int base = (int) Math.pow(2, position);
                if (temp >= base) {
                    ands.get(i).addInput(getInput(inSize - position - 1));
                    temp -= base;
                } else {
                    ands.get(i).addInput(nots.get(inSize - position - 1).getOutput(0));
                }
            }
        }

        for (int i = 0; i < andsCount; ++i) {
            addOutput(ands.get(i).getOutput(0));
        }
    }
}