package simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Circuit {
    private static List<Link> allLinks = new ArrayList<>();
    private static List<Node> allNodes = new ArrayList<>();
    private static List<Clock> clocks = new ArrayList<>();
    private static List<List<Node>> netList = new ArrayList<>();
    private static Map<Link, List<Node>> removed = new HashMap<>();

    public static void addNode(Node node) {
        if(node instanceof ExplicitInput) {
            if (netList.size() == 0) {
                netList.add(new ArrayList<>());
            }
            netList.get(0).add(node);
        }

        if (node instanceof Clock) {
            clocks.add((Clock) node);
        }

        allNodes.add(node);
    }

    public static void start() {
        deleteLoop();
        initializeNetList();
        addLoop();
        evaluateNetList();
    }

    private static void addLoop() {
        for (Link link: removed.keySet()) {
            for (Node node: removed.get(link)) {
                link.addDestination(node);
                node.addInput(link);
            }
        }
    }

    public static Boolean DepthFirstSearch(Node node) {
        boolean loopDetected;
        node.setVisitCondition(true);

        for (Link link: node.getOutputs()) {
            for (int i = 0; i < link.getDestinations().size(); ++i) {
                if (link.getDestinations().get(i).isVisited()) {
                    if (!removed.containsKey(link)) {
                        removed.put(link, new ArrayList<>());
                    }
                    removed.get(link).add(link.getDestinations().get(i));
                    link.getDestinations().get(i).getInputs().remove(link);
                    link.getDestinations().remove(i);
                    node.setVisitCondition(false);
                    return true;
                }
                loopDetected = DepthFirstSearch(link.getDestinations().get(i));
                if (loopDetected) {
                    node.setVisitCondition(false);
                    return true;
                }
            }
        }
        node.setVisitCondition(false);
        return false;
    }

    public static void deleteLoop() {
        boolean loopDetected = true;

        while (loopDetected) {
            loopDetected = false;
            for (Node node: netList.get(0)) {
                loopDetected = DepthFirstSearch(node);
            }
        }
    }

    public static void initializeNetList() {
        int level = 0;
        while (netList.size() >= level + 1) {
            initializeLevel(level++);
        }
    }

    public static void initializeLevel(int level) {
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

    public static void evaluateNetList() {
        for (List<Node> list: netList) {
            for (Node node: list) {
                node.evaluate();
            }
        }
    }
}
