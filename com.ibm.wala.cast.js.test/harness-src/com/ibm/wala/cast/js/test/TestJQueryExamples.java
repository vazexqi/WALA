package com.ibm.wala.cast.js.test;

import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ibm.wala.cast.ipa.callgraph.CAstCallGraphUtil;
import com.ibm.wala.cast.js.html.JSSourceExtractor;
import com.ibm.wala.cast.js.ipa.callgraph.JSCFABuilder;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.WalaException;

public class TestJQueryExamples extends TestJSCallGraphShape {

  public static void main(String[] args) {
    justThisTest(TestJQueryExamples.class);
  }
    
  @Before
  public void config() {
    JSSourceExtractor.USE_TEMP_NAME = false;
    JSSourceExtractor.DELETE_UPON_EXIT = false;
  }

  @Ignore("This tries to analyze unmodified jquery, which we can't do yet")
  @Test public void testEx1() throws IOException, IllegalArgumentException, CancelException, WalaException {
    URL url = getClass().getClassLoader().getResource("pages/jquery/ex1.html");
    JSCFABuilder builder = JSCallGraphBuilderUtil.makeHTMLCGBuilder(url);
    CallGraph CG = builder.makeCallGraph(builder.getOptions());
    CAstCallGraphUtil.dumpCG(builder.getPointerAnalysis(), CG);
  }
}
