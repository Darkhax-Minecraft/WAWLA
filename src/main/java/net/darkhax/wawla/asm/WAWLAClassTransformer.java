package net.darkhax.wawla.asm;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

/**
 * A simple Class Transformer that allows for the results of a string translation to be captured.
 * 
 * @author Ghostrec35
 **/

public class WAWLAClassTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] classBytes) {

        if ((name.equals("net.minecraft.util.StringTranslate") || name.equals("dd")) && WAWLAConfiguration.TRANSLATION_CLASS_TRANSFORM.getBoolean(true)) {

            return injectStrTransHook(classBytes);
        }

        return classBytes;
    }

    private byte[] injectStrTransHook(byte[] classBytes) {

        boolean isInjected = false;

        ClassReader cr = new ClassReader(classBytes);
        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);

        for (MethodNode node : cn.methods) {

            if ((node.name.equals("tryTranslateKey") || node.name.equals("func_135064_c")) && node.desc.equals("(Ljava/lang/String;)Ljava/lang/String;")) {

                for (int i = 0; i < node.instructions.size(); i++) {

                    if (node.instructions.get(i).getOpcode() == Opcodes.IFNONNULL && !isInjected) {

                        AbstractInsnNode location = node.instructions.get(i - 4);
                        InsnList list = new InsnList();
                        LabelNode l0 = new LabelNode();
                        list.add(l0);
                        list.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        list.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/darkhax/wawla/handler/TranslationHooks", "tryTranslateKey", "(Ljava/lang/String;Ljava/lang/String;)V"));
                        node.instructions.insert(location, list);
                        isInjected = true;
                        break;
                    }
                }
            }
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
        cn.accept(writer);
        return writer.toByteArray();
    }
}
