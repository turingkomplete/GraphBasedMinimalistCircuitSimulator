package simulator.control;

import simulator.gates.sequential.Clock;
import simulator.gates.combinational.Explicit;
import simulator.network.Link;
import simulator.network.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Circuit implements Runnable {
    private List<Clock> clocks;
    private List<List<Node>> netList;
    private Map<Link, List<Node>> removed;
    private Thread thread;

    public Circuit() {
        removed = new HashMap<>();
        netList = new ArrayList<>();
        netList.add(new ArrayList<>());
        clocks = new ArrayList<>();
        thread = new Thread(this);
    }

    public void addNode(Node node) {
        if(node instanceof Explicit || node instanceof Clock) {
            netList.get(0).add(node);
        }

        if (node instanceof Clock) {
            clocks.add((Clock) node);
        }
    }

    public void startCircuit(String mode) {
        removeLoop();
        if (mode.equals("REAL") || mode.equals("Real") || mode.equals("real")) {
            realModeInitializeNetList();
        } else {
            initializeNetList();
        }
        addLoop();
        startClocks();
        Simulator.debugger.startDebugger();
        thread.start();
    }

    public void startCircuit() {
        removeLoop();
        initializeNetList();
        addLoop();
        startClocks();
        Simulator.debugger.startDebugger();
        thread.start();
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
        boolean loopDetected;
        node.setVisited(true);

        for (Link link: node.getOutputs()) {
            for (int i = 0; i < link.getDestinations().size(); ++i) {
                if (link.getDestinations().get(i).isVisited()) {
                    if (!removed.containsKey(link)) {
                        removed.put(link, new ArrayList<>());
                    }
                    removed.get(link).add(link.getDestinations().get(i));
                    link.getDestinations().get(i).getInputs().remove(link);
                    link.getDestinations().remove(i);
                    node.setVisited(false);
                    return true;
                }
                loopDetected = depthFirstSearch(link.getDestinations().get(i));
                if (loopDetected) {
                    node.setVisited(false);
                    return true;
                }
            }
        }
        node.setVisited(false);
        return false;
    }

    private void removeLoop() {
        boolean loopDetected = true;

        while (loopDetected) {
            loopDetected = false;
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
                    boolean flag = true;
                    for (Link innerLink : innerNode.getInputs()) {
                        if (!innerLink.isValid()) {
                            flag = false;
                        }
                    }

                    if (flag) {
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

    private void realModeInitializeNetList() {
        int level = 0;
        while (netList.size() >= level + 1) {
            realModeInitializeLevel(level++);
        }
    }

    private void realModeInitializeLevel(int level) {
        for (Node node: netList.get(level)) {
            for (Link link: node.getOutputs()) {
                for (Node innerNode : link.getDestinations()) {
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

    private void evaluateNetList() {
        for (List<Node> list: netList) {
            for (Node node : list) {
                node.evaluate();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            evaluateNetList();
            Simulator.debugger.run();
        }
    }
}
