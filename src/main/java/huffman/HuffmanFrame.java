package huffman;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;

class HuffmanFrame extends JFrame {

    HuffmanFrame(mxGraph graph){
        super("Huffman Tree");
        this.setSize(1900, 1000);
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
