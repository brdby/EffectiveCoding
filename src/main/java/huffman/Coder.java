package huffman;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Coder {
    private PriorityQueue<Node> nodesQueue = new PriorityQueue<>();
    private HashSet<Node> allNodes = new HashSet<>();
    private Graph graph;

    public Coder(TreeMap<Character, Integer> map) {
        graph = new SingleGraph("Huffman");
        map.forEach((k, v) -> {
            Node node = new Node(k, v);
            nodesQueue.add(node);
            allNodes.add(node);
            graph.addNode(node.toString());
        });
        code();
    }

    private void code() {
        while (nodesQueue.size() > 1) {
            Node a = nodesQueue.poll();
            Node b = nodesQueue.poll();
            a.setCode('1');
            b.setCode('0');
            Node parent = new Node(a.getWeight() + b.getWeight(), a.getName() + b.getName());
            a.setParent(parent);
            b.setParent(parent);
            nodesQueue.add(parent);
            graph.addNode(parent.toString());
            graph.addEdge(a.toString() + parent.toString(), a.toString(), parent.toString());
            graph.addEdge(b.toString() + parent.toString(), b.toString(), parent.toString());
        }
    }

    public TreeMap<Character, String> getCodes() {
        TreeMap<Character, String> map = new TreeMap<>();

        for (Node a : allNodes) {
            Node temp = a;
            StringBuilder code = new StringBuilder("");
            while (temp.getParent() != null) {
                code.append(temp.getCode());
                temp = temp.getParent();
            }
            map.put(a.getChar(), code.toString());
        }
        return map;
    }

    public Graph getGraph() {
        return graph;
    }
}
