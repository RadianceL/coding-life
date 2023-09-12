package cn.fuxi.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @author eddie.lys
 * @since 2023/9/11
 */
public class PerformanceAgent {
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        ClassFileTransformer transformer = new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classFileBuffer) {
                if (className != null && className.startsWith("com/example/")) {
                    // You can specify the package or class names to instrument here
                    long startTime = System.nanoTime();
                    byte[] transformedBytes = instrumentClass(classFileBuffer);
                    long endTime = System.nanoTime();
                    long executionTime = endTime - startTime;
                    System.out.println("Execution time of " + className + ": " + executionTime + " ns");
                    return transformedBytes;
                }

                return classFileBuffer; // Return the original bytecode
            }
        };

        instrumentation.addTransformer(transformer);
    }

    private static byte[] instrumentClass(byte[] originalBytes) {
        // Implement your bytecode manipulation logic here (e.g., adding timers)
        // This is a simplified example, and actual instrumentation may be more complex
        return originalBytes;
    }
}
