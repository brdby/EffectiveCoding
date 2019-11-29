package huffman;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Coder {
    private PriorityQueue<Node> nodesQueue = new PriorityQueue<>();
    private HashSet<Node> allNodes = new HashSet<>();
    private mxGraph graph;

    public Coder(TreeMap<Character, Integer> map) {
        //Creating graph
        graph = new mxGraph();
        Object grParent = graph.getDefaultParent();
        graph.getModel().beginUpdate();

        try {
            map.forEach((k, v) -> {
                Node node = new Node(k, v);
                nodesQueue.add(node);
                allNodes.add(node);
                node.setGraphObject(graph.insertVertex(grParent, null, node.toString(), 0, 0, 100, 40));
            });
            code();

            mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
            layout.setUseBoundingBox(false);
            layout.execute(grParent);
        } finally {
            graph.getModel().endUpdate();
        }
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

            Object grParent = graph.getDefaultParent();
            Object parentVer = graph.insertVertex(grParent, null, parent.toString(), 0, 0, 100, 40);
            Object aVer = a.getGraphObject();
            Object bVer = b.getGraphObject();

            parent.setGraphObject(parentVer);

            graph.insertEdge(grParent, null, "1", parentVer, aVer);
            graph.insertEdge(grParent, null, "0", parentVer, bVer);
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
            map.put(a.getChar(), new StringBuilder(code.toString()).reverse().toString());
        }
        return map;
    }

    public JFrame getHuffmanFrame(){
        return new HuffmanFrame(graph);
    }
}
