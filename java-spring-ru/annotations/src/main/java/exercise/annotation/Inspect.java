package main.java.exercise.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@interface определяет саму аннотацию
//@Retention определяет жизненный цикл аннотации, то есть указывает, как долго аннотация должна оставаться с кодом.
// В этом случае аннотация должна быть доступна в рантайме, потому что именно так мы будем ее обрабатывать
//@Target определяет, где мы будем применять аннотацию (например, в методах)

// BEGIN
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Inspect {
}
// END
