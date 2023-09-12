package cn.fuxi.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @author eddie.lys
 * @since 2023/9/12
 */
public class ClassLoaderAgent {

    // 通过java agent方式获取通过PlatformClassLoader初始化的方法
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        ClassFileTransformer transformer = new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classFileBuffer) {
                if (loader != null && loader.getClass().getName().equals("sun.misc.Launcher$PlatformClassLoader")) {
                    // This class is loaded by PlatformClassLoader
                    System.out.println("PlatformClassLoader loaded: " + className);
                }
                return classFileBuffer; // Return the original bytecode
            }
        };
        instrumentation.addTransformer(transformer);
    }
}
