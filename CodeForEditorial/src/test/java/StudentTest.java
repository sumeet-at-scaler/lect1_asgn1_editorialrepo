import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    private Student student;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        student = new Student();
    }

    @Test
    void ageExists(){
        try {
            Field ageField = student.getClass().getDeclaredField("age");
            assertEquals(ageField.getType().toString(), "int");
        } catch (NoSuchFieldException e) {
            fail("age data member of type int not found");
        }
    }

    @Test
    void nameExists(){
        try {
            Field nameField = student.getClass().getDeclaredField("name");
            assertEquals(nameField.getType().toString(), "class java.lang.String");
        } catch (NoSuchFieldException e) {
            fail("name data member of type String not found");
        }
    }

    @Test
    void displayMethodExists(){
        try {
            Method displayMethod = student.getClass().getDeclaredMethod("display");
        } catch (NoSuchMethodException e) {
            fail("display method not found");
        }
    }

    @Test
    void displayMethodSignatureCheck() throws NoSuchMethodException {
        Method displayMethod = student.getClass().getDeclaredMethod("display");
        assertEquals(displayMethod.toString(), "void Student.display()");
    }

    @Test
    void sayHelloMethodExists(){
        try {
            Method sayHelloMethod = student.getClass().getDeclaredMethod("sayHello", String.class);
        } catch (NoSuchMethodException e) {
            fail("sayHello method not found");
        }
    }

    @Test
    void sayHelloMethodSignatureCheck() throws NoSuchMethodException {
        Method sayHelloMethod = student.getClass().getDeclaredMethod("sayHello", String.class);
        assertEquals(sayHelloMethod.toString(), "void Student.sayHello(java.lang.String)");
    }

    @Test
    void displayCheck() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // student.age = 10
        Field ageField = student.getClass().getDeclaredField("age");
        ageField.setAccessible(true);
        ageField.set(student, 10);

        // student.name = "Test"
        Field nameField = student.getClass().getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(student, "Test");

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // student.display()
        Method displayMethod = student.getClass().getDeclaredMethod("display");
        displayMethod.invoke(student);

        String expected = "My name is Test. I am 10 years old\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void sayHelloCheck() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // student.name = "Test"
        Field nameField = student.getClass().getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(student, "Test");

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // student.sayHello("Test2");
        Method sayHelloMethod = student.getClass().getDeclaredMethod("sayHello", String.class);
        sayHelloMethod.invoke(student, "Test2");

        String expected = "Test says hello to Test2\n";
        assertEquals(expected, outContent.toString());
    }
}