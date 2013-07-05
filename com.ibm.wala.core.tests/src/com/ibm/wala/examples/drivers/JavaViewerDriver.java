/*******************************************************************************
 * Copyright (c) 2013 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.wala.examples.drivers;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;

import com.ibm.wala.core.tests.callGraph.CallGraphTestUtil;
import com.ibm.wala.ipa.callgraph.AnalysisCache;
import com.ibm.wala.ipa.callgraph.AnalysisOptions;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.CallGraphBuilderCancelException;
import com.ibm.wala.ipa.callgraph.Entrypoint;
import com.ibm.wala.ipa.callgraph.impl.Util;
import com.ibm.wala.ipa.callgraph.propagation.PointerAnalysis;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.util.config.AnalysisScopeReader;
import com.ibm.wala.util.io.CommandLine;
import com.ibm.wala.util.io.FileProvider;
import com.ibm.wala.viz.viewer.WalaViewer;

/**
 * Allows viewing the ClassHeirarcy, CallGraph and Pointer Analysis built from a
 * given classpath.
 * 
 * @author yinnonh
 * 
 */
public class JavaViewerDriver {
  public static void main(String[] args) throws ClassHierarchyException, IOException, CallGraphBuilderCancelException {
    Properties p = CommandLine.parse(args);
    validateCommandLine(p);
    run(p.getProperty("appClassPath"), p.getProperty("exclusionFile", CallGraphTestUtil.REGRESSION_EXCLUSIONS));
  }

  public static void validateCommandLine(Properties p) {
    if (p.get("appClassPath") == null) {
      throw new UnsupportedOperationException("expected command-line to include -appClassPath");
    }
  }

  private static void run(String classPath, String exclusionFilePath) throws IOException, ClassHierarchyException,
      CallGraphBuilderCancelException {

    File exclusionFile = (new FileProvider()).getFile(exclusionFilePath);
    AnalysisScope scope = AnalysisScopeReader.makeJavaBinaryAnalysisScope(classPath, exclusionFile != null ? exclusionFile
        : new File(CallGraphTestUtil.REGRESSION_EXCLUSIONS));

    ClassHierarchy cha = ClassHierarchy.make(scope);

    Iterable<Entrypoint> entrypoints = com.ibm.wala.ipa.callgraph.impl.Util.makeMainEntrypoints(scope, cha);
    AnalysisOptions options = new AnalysisOptions(scope, entrypoints);

    // //
    // build the call graph
    // //
    com.ibm.wala.ipa.callgraph.CallGraphBuilder builder = Util.makeZeroCFABuilder(options, new AnalysisCache(), cha, scope);
    final CallGraph cg = builder.makeCallGraph(options, null);

    final PointerAnalysis pa = builder.getPointerAnalysis();
    JFrame frame = new JFrame() {
      {
        setSize(600, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent event) {
            System.exit(0);
          }
        });
        setTitle("Wala viewer");
        setLayout(new BorderLayout());
        add(new WalaViewer(cg, pa), BorderLayout.CENTER);
        pack();
        setVisible(true);
      }
    };

  }
}
