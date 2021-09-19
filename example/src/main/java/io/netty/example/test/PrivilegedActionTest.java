package io.netty.example.test;

import io.netty.util.internal.PlatformDependent0;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * 测试PrivilegedAction在netty中的用法
 */
public class PrivilegedActionTest {

    public static class Employ {
        public Employ(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        private String name;
        private Integer age;
    }

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        final Employ employ = new Employ("张三", 18);

//        通过特权方法获取employ的name字段
        Object mayBeNameField = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    Field nameField = employ.getClass().getDeclaredField("name");
                    return nameField;
                } catch (Throwable cause) {
                    return cause;
                }
            }
        });

//        Object mayBeAgeField = AccessController.doPrivileged(new PrivilegedAction<Object>() {
//            @Override
//            public Object run() {
//                try {
//                    Field ageField = employ.getClass().getDeclaredField("age");
//                    return ageField;
//                } catch (Throwable cause) {
//                    return cause;
//                }
//            }
//        });

        Field nameField = (Field) mayBeNameField;

        // 通过特权方法获取age Field
//        Field ageField = (Field) mayBeAgeField;
        // 实测不通过特权方法也是可以的
        Field ageField = employ.getClass().getDeclaredField("age");

        long fieldOffset = PlatformDependent0.objectFieldOffset(nameField);
        String name = (String) PlatformDependent0.getObject(employ, fieldOffset);

        fieldOffset = PlatformDependent0.objectFieldOffset(ageField);
        Integer age = (Integer) PlatformDependent0.getObject(employ, fieldOffset);
        System.out.println(name + " " + age);
    }
}
