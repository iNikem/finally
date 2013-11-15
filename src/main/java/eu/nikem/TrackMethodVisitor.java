package eu.nikem;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;

import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;

public class TrackMethodVisitor extends MethodVisitor {

  private static String trackerClassName = Type.getInternalName(Track.class);
  private GeneratorAdapter mv;

  public TrackMethodVisitor(GeneratorAdapter mv) {
    super(Opcodes.ASM4, mv);
    this.mv = mv;
  }

  public void visitMethodInsn(int opcode, String owner, String name, String signature) {
    if(opcode == INVOKEVIRTUAL || opcode == INVOKESTATIC || opcode == INVOKEINTERFACE || opcode == INVOKESPECIAL){
      mv.visitMethodInsn(INVOKESTATIC, trackerClassName, "before", "()V");

      Label l0 = new Label();
      Label l1 = new Label();
      Label l2 = new Label();
      mv.visitTryCatchBlock(l0, l1, l2, null);

      mv.visitLabel(l0);
      super.visitMethodInsn(opcode, owner, name, signature);
      mv.visitLabel(l1);
      mv.visitMethodInsn(INVOKESTATIC, trackerClassName, "after", "()V");

      Label l4 = new Label();
      mv.visitJumpInsn(GOTO, l4);
      mv.visitLabel(l2);
      int var = mv.newLocal(Type.getType(Object.class));
      mv.storeLocal(var);
      mv.visitMethodInsn(INVOKESTATIC, trackerClassName, "thrown", "()V");
      mv.loadLocal(var);
      mv.throwException();
      mv.visitLabel(l4);
      return;
    }
    super.visitMethodInsn(opcode, owner, name, signature);
  }
}
