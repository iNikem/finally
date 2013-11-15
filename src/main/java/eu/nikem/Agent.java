package eu.nikem;

import java.lang.instrument.Instrumentation;

public class Agent {

  public static void premain(String agentArgs, Instrumentation inst) { // NO_UCD
    inst.addTransformer(new BaseClassFileTransformer());
  }
}
