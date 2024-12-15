package backend.academy;

import backend.academy.records.Student;
import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Supplier;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
public class Benchmark {
    private Student student;
    private Method method;
    private MethodHandle handle;
    private Supplier<String> lambda;

    @Setup
    public void setup() throws Throwable {
        student = new Student("Vladislav", "Lunev");

        String methodName = "name";
        method = Student.class.getDeclaredMethod(methodName);

        MethodType methodType = MethodType.methodType(String.class);
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        handle = lookup.findVirtual(Student.class, methodName, methodType);

        CallSite site = LambdaMetafactory.metafactory(
            lookup,
            "get",
            MethodType.methodType(Supplier.class, Student.class),
            MethodType.methodType(Object.class),
            handle,
            MethodType.methodType(String.class)
        );

        lambda = (Supplier<String>) site.getTarget().invokeExact(student);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void reflection(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        String name = (String) method.invoke(student);
        bh.consume(name);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void methodHandlers(Blackhole bh) throws Throwable {
        String name = (String) handle.invoke(student);
        bh.consume(name);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void lambdaMetaFactory(Blackhole bh) {
        String name = lambda.get();
        bh.consume(name);
    }
}
