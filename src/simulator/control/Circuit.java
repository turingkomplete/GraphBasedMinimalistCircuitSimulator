package simulator.control;

import simulator.gates.combinational.ByteMemory;
import simulator.gates.combinational.Memory;
import simulator.gates.sequential.BigClock;
import simulator.gates.sequential.Clock;
import simulator.gates.combinational.Explicit;
import simulator.network.Link;
import simulator.network.Node;
import simulator.wrapper.DataStream;

import java.util.*;

public class Circuit implements Runnable {
    private List<DataStream> dataStreams;
    private List<Clock> clocks;
    private List<List<Node>> netList;
    private Map<Link, List<Node>> removed;
    private Thread thread;
    private List<BigClock> bigClocks;
    private int clockCount;
    private List<Memory> memories;
    private List<ByteMemory> byteMemories;

    public Circuit() {
        dataStreams = new ArrayList<>();
        removed = new HashMap<>();
        netList = new ArrayList<>();
        netList.add(new ArrayList<>());
        clocks = new ArrayList<>();
        thread = new Thread(this);
        bigClocks = new ArrayList<>();
        memories = new ArrayList<>();
        byteMemories = new ArrayList<>();
        clockCount = -1;
    }

    public void addNode(Node node) {
        if (node instanceof Memory) {
            memories.add((Memory) node);
        }

        if (node instanceof ByteMemory) {
            byteMemories.add((ByteMemory) node);
        }

        if(node instanceof DataStream) {
            dataStreams.add((DataStream) node);
        }

        if(node instanceof Explicit || node instanceof Clock || node instanceof BigClock) {
            netList.get(0).add(node);
        }

        if (node instanceof Clock) {
            clocks.add((Clock) node);
        }

        if (node instanceof BigClock) {
            bigClocks.add((BigClock) node);
        }
    }

    public void startCircuit() {
        removeDataStream();
        saveMemoryState();
        removeLoop();
        initializeNetList();
        addLoop();
        startClocks();
        Simulator.debugger.startDebugger();
        thread.start();
    }

    public void startCircuit(int clockCount) {
        removeDataStream();
        saveMemoryState();
        removeLoop();
        initializeNetList();
        addLoop();
        startClocks();
        Simulator.debugger.startDebugger();
        thread.start();
        this.clockCount = clockCount * 2;
    }

    private void saveMemoryState() {
        for (ByteMemory mem: byteMemories) {
            for (Link in: mem.getInputs()) {
                mem.getMemIn().add(in);
            }
        }

        for (Memory mem: memories) {
            for (Link in: mem.getInputs()) {
                mem.getMemIn().add(in);
            }
        }
    }

    private void removeDataStream() {
        int m = 0;
        outer: while (!dataStreams.isEmpty()) {
            if (m >= dataStreams.size()) {
                m = 0;
            }
            DataStream dataStream = dataStreams.get(m);
            for (int l = 0; l < dataStream.getInputs().size(); ++l) {
                if (dataStream.getInput(l).getSource() instanceof DataStream) {
                    m ++;
                    continue outer;
                }
            }
            for (int j = 0; j < dataStream.getOutputs().size(); ++j) {
                Link input = dataStream.getInput(j);
                Link output = dataStream.getOutput(j);
                Node source = input.getSource();
                int sourceIndex = source.getOutputs().indexOf(input);
                if (output.getDestinations().isEmpty()) {
                    source.getOutput(sourceIndex).getDestinations().remove(dataStream);
                } else {
                    for (int k = 0; k < output.getDestinations().size(); ++k) {
                        Node destination = output.getDestination(k);
                        int destinationIndex = destination.getInputs().indexOf(output);
                        source.getOutput(sourceIndex).getDestinations().remove(dataStream);
                        source.getOutput(sourceIndex).getDestinations().add(destination);
                        destination.getInputs().set(destinationIndex, source.getOutput(sourceIndex));
                    }
                }
                dataStream.setOutput(j, source.getOutput(sourceIndex));
            }
            dataStreams.remove(dataStream);
        }
    }

    private void startClocks() {
        for (Clock clock: clocks) {
            clock.startClock();
        }
    }

    private void addLoop() {
        for (Link link: removed.keySet()) {
            for (Node node: removed.get(link)) {
                node.addInput(link);
            }
        }
    }

    private Boolean depthFirstSearch(Node node) {
        Stack<StackFrame> stack = new Stack<>();
        stack.push(new StackFrame(node));
        StackFrame.returnValue = false;

        outer: while (stack.size() > 0) {
            Node thisNode = stack.peek().node;

            if (StackFrame.returnValue || !thisNode.getLoop()) {
                thisNode.setVisited(false);
                stack.pop();
                if (stack.size() > 0)
                    stack.peek().j++;
                continue;
            }

            thisNode.setVisited(true);

            while (stack.peek().i < thisNode.getOutputs().size()) {
                while (stack.peek().j < thisNode.getOutput(stack.peek().i).getDestinations().size()) {
                    if (thisNode.getOutput(stack.peek().i).getDestination(stack.peek().j).isVisited()) {
                        if (!removed.containsKey(thisNode.getOutput(stack.peek().i))) {
                            removed.put(thisNode.getOutput(stack.peek().i), new ArrayList<>());
                        }

                        removed.get(thisNode.getOutput(stack.peek().i)).add(thisNode.getOutput(stack.peek().i).getDestination(stack.peek().j));
                        thisNode.getOutput(stack.peek().i).getDestination(stack.peek().j).getInputs().remove(thisNode.getOutput(stack.peek().i));
                        thisNode.getOutput(stack.peek().i).getDestinations().remove(stack.peek().j);
                        thisNode.setVisited(false);
                        StackFrame.returnValue = true;
                        stack.pop();
                        continue outer;
                    }

                    stack.push(new StackFrame(thisNode.getOutput(stack.peek().i).getDestination(stack.peek().j)));
                    continue outer;
                }

                stack.peek().i++;
                stack.peek().j = 0;
            }

            thisNode.setVisited(false);
            thisNode.setLoop(false);
            stack.pop();
            if (stack.size() > 0)
                stack.peek().j++;
        }

        return StackFrame.returnValue;
    }

    private void removeLoop() {
        boolean loopDetected = true;

        while (loopDetected) {
            loopDetected = false;
            StackFrame.returnValue = false;
            for (Node node: netList.get(0)) {
                if (depthFirstSearch(node)) {
                    loopDetected = true;
                    break;
                }
            }
        }
    }

    private void initializeNetList() {
        int level = 0;
        while (netList.size() >= level + 1) {
            initializeLevel(level++);
        }
    }

    private void initializeLevel(int level) {
        for (Node node: netList.get(level)) {
            for (Link link: node.getOutputs()) {
                link.setValidity(true);
            }
        }

        for (Node node: netList.get(level)) {
            for (Link link: node.getOutputs()) {
                for (Node innerNode : link.getDestinations()) {
                    if (!innerNode.getLatch() && !node.getLatchValidity()) {
                        continue;
                    }

                    if (innerNode.getLatch()) {
                        boolean latchValid = true;
                        for (Link inputLink : innerNode.getInputs()) {
                            if (!inputLink.getSource().getLatchValidity()) {
                                latchValid = false;
                                break;
                            }
                        }

                        innerNode.setLatchValidity(latchValid);
                    }

                    boolean valid = true;
                    for (Link innerLink : innerNode.getInputs()) {
                        if (!innerLink.isValid()) {
                            valid = false;
                            break;
                        }
                    }

                    if (valid || innerNode.getLatch()) {
                        if (netList.size() < level + 2) {
                            netList.add(new ArrayList<>());
                        }

                        if (!netList.get(level + 1).contains(innerNode)) {
                            netList.get(level + 1).add(innerNode);
                        }
                    }
                }
            }
        }
    }

    private void evaluateNetList() {
        for (List<Node> list: netList) {
            for (Node node : list) {
                node.evaluate();
            }
        }
    }

    private void toggleBigClocks() {
        for (BigClock bigClock : bigClocks) {
            bigClock.toggle();
        }
    }

    @Override
    public void run() {
        int count = 0;
        while (count < clockCount || clockCount == -1) {
            toggleBigClocks();
            evaluateNetList();
            Simulator.debugger.run();
            count ++;
        }
    }
}

//    private void realModeInitializeNetList() {
//        int level = 0;
//        while (netList.size() >= level + 1) {
//            realModeInitializeLevel(level++);
//        }
//    }
//
//    private void realModeInitializeLevel(int level) {
//        for (Node node: netList.get(level)) {
//            for (Link link: node.getOutputs()) {
//                for (Node innerNode : link.getDestinations()) {
//                    if (netList.size() < level + 2) {
//                        netList.add(new ArrayList<>());
//                    }
//
//                    if (!netList.get(level + 1).contains(innerNode)) {
//                        netList.get(level + 1).add(innerNode);
//                    }
//                }
//            }
//        }
//    }

//    private Boolean depthFirstSearch(Node node) {
//        boolean loopDetected;
//
//        if (!node.getLoop())
//            return false;
//
//        node.setVisited(true);
//
//        for (Link link: node.getOutputs()) {
//            for (int i = 0; i < link.getDestinations().size(); ++i) {
//                if (link.getDestinations().get(i).isVisited()) {
//                    if (!removed.containsKey(link)) {
//                        removed.put(link, new ArrayList<>());
//                    }
//                    removed.get(link).add(link.getDestinations().get(i));
//                    link.getDestinations().get(i).getInputs().remove(link);
//                    link.getDestinations().remove(i);
//                    node.setVisited(false);
//                    return true;
//                }
//                loopDetected = depthFirstSearch(link.getDestinations().get(i));
//                if (loopDetected) {
//                    node.setVisited(false);
//                    return true;
//                }
//            }
//        }
//
//        node.setLoop(false);
//        node.setVisited(false);
//        return false;
//    }