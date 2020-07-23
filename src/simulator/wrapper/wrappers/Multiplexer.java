package simulator.wrapper.wrappers;

import simulator.gates.combinational.And;
import simulator.gates.combinational.Not;
import simulator.gates.combinational.Or;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.List;

/* a generic multiplexer
 * for n inputs, m + (2 pow m) = n:
 *   in:
 *       0 -> m-1 : select (highest order bit in 0)
 *       m -> (2 pow m) + m - 1 : signals to select
 *   out:
 *   0 : selected signals*/
public class Multiplexer extends Wrapper {
    public Multiplexer(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    private int getSelectSize() {
        int inSize = getInputSize();
        int i = 1;
        while (i + Math.pow(2, i) < inSize) {
            i++;
        }
        return i;
    }

    @Override
    public void initialize() {
        int selectSize = getSelectSize();
        int andsCount = (int) Math.pow(2, selectSize);

        List<Not> nots = new ArrayList<>();
        List<And> ands = new ArrayList<>();
        Or or = new Or("OR");

        for (int i = 0; i < selectSize; ++i)
            nots.add(new Not("NOT" + i, getInput(i)));

        ands.add(new And("AND0"));
        ands.get(0).addInput(getInput(selectSize));
        for (int i = 0; i < selectSize; ++i)
            ands.get(0).addInput(nots.get(i).getOutput(0));

        for (int i = 1; i < andsCount; ++i) {
            ands.add(new And("AND" + i));
            ands.get(i).addInput(getInput(i + selectSize));
            int temp = i;
            for (int position = selectSize - 1; position >= 0; --position) {
                int base = (int) Math.pow(2, position);
                if (temp >= base) {
                    ands.get(i).addInput(getInput(selectSize - position - 1));
                    temp -= base;
                } else {
                    ands.get(i).addInput(nots.get(selectSize - position - 1).getOutput(0));
                }
            }
        }

        for (int i = 0; i < andsCount; ++i) {
            or.addInput(ands.get(i).getOutput(0));
        }

        addOutput(or.getOutput(0));
    }
}