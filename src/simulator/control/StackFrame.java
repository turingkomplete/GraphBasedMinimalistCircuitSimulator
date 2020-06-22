package simulator.control;

import simulator.network.Node;

public class StackFrame {
    public static boolean returnValue = false;
    public int i;
    public int j;
    public Node node;

    public StackFrame(Node node, int i, int j) {
        this.i = i;
        this.j = j;
        this.node = node;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StackFrame frame = (StackFrame) o;

        return node.equals(frame.node);
    }

    @Override
    public int hashCode() {
        return node.hashCode();
    }
}
