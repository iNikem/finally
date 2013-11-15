package eu.nikem;

import java.io.PrintWriter;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.TraceClassVisitor;

public class BaseClassFileTransformer implements ClassFileTransformer {

  @Override
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                          byte[] classfileBuffer) {
    try {
      if (className.equals("eu/nikem/Main")) {
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
        ClassVisitor adapter = new TrackClassVisitor(cw);
        cr.accept(adapter, ClassReader.EXPAND_FRAMES);
        byte[] byteArray = cw.toByteArray();

        cr = new ClassReader(byteArray);
        cr.accept(new CheckClassAdapter(new ClassWriter(0)), 0);
        cr.accept(new TraceClassVisitor(new PrintWriter(System.out)), 0);
        return byteArray;
      }
      return null;
    } catch (Throwable e) {
      e.printStackTrace();
      System.out.printf("Instrumentation of class %s failed. Using original class.", className);
      return null;
    }
  }

}
