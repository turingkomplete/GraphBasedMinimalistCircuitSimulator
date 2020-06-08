package simulator;

public class Main {

    public static void main(String[] args) {
        Bit trueBit = new Bit("True",true);
        Bit falseBit = new Bit("False",false);

        And a1 = new And("AND1", trueBit.getOutput(0), trueBit.getOutput(0));
        And a2 = new And("AND2", a1.getOutput(0), trueBit.getOutput(0));
        //a1.addInput(a2.getOutput(0));

        Simulator.circuit.start();

        System.out.println(a1.getOutput(0).getValue() + " " + a2.getOutput(0).getValue());
    }
}
