package com.lin.learn.structure;

import com.lin.learn.java.structure.graph.MyGraph;
import com.lin.learn.java.structure.graph.MyGraphDijikstra;
import com.lin.learn.java.structure.graph.MyGraphKruskal;
import com.lin.learn.java.structure.graph.MyGraphPrim;

import org.junit.Test;

public class Graph {
    @Test
    public void testGraph(){
        MyGraph.test();
    }

    @Test
    public void testPrim(){
        MyGraphPrim.test();
    }

    @Test
    public void testKruskal(){
        MyGraphKruskal.test();
    }

    @Test
    public void testDijikstra(){
        MyGraphDijikstra.test();
    }
}
