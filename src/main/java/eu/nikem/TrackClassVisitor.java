package eu.nikem;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.GeneratorAdapter;

public class TrackClassVisitor extends ClassVisitor {

  public TrackClassVisitor(ClassVisitor cv) {
    super(Opcodes.ASM4, cv);
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
    MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
    GeneratorAdapter adapter = new GeneratorAdapter(mv, access, name, desc);

    if (mv != null) {
      return new TrackMethodVisitor(adapter);
    }
    return mv;
  }

}