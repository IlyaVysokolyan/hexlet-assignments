package exercise;

import main.java.exercise.model.Address;
import main.java.exercise.annotation.Inspect;

import java.lang.reflect.Method;

public class Application {
    public static void main(String[] args) {
        var address = new Address("London", 12345678);

        // BEGIN
        for (Method method : Address.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Inspect.class)) {
                String methodName = method.getName();
                Class<?> returnType = method.getReturnType();
                System.out.println("Method " + methodName + " returns a value of type " + returnType.getSimpleName()); // getSimpleName() выводит слово String а не java.lang.String
            }
        }
        // END
    }
}
