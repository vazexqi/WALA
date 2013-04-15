package com.ibm.wala.viz.viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.propagation.PointerAnalysis;

/**
 * Viewer for ClassHeirarcy, CallGraph and Pointer Analysis results. A driver
 * for example can be found in com.ibm.wala.js.rhino.vis.JsViewer.
 * 
 * @author yinnonh
 * 
 */
public class WalaViewer extends JPanel {

  protected static final String DefaultMutableTreeNode = null;

  public WalaViewer(CallGraph cg, PointerAnalysis pa) {
    setLayout(new BorderLayout());
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.add("Call Graph", new CgPanel(cg));
    tabbedPane.add("Class Hierarchy", new ChaPanel(cg.getClassHierarchy()));
    PaPanel paPanel = createPaPanel(cg, pa);
    paPanel.init();
    tabbedPane.add("Pointer Analysis", paPanel);
    add(tabbedPane, BorderLayout.CENTER);
    setMaximumSize(new Dimension(800, 800));
  }

  protected PaPanel createPaPanel(CallGraph cg, PointerAnalysis pa) {
    return new PaPanel(cg, pa);
  }

  public static void setNativeLookAndFeel() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}