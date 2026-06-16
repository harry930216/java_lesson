package tw.harry.tutor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import tw.harry.entity.Test;

// 介紹反射類別

public class Harry04 {

	public static void main(String[] args) {
		Class<String> strClass = String.class;
		Class<Test> testClass = Test.class;
		
		
		System.out.println(strClass.getName());
		System.out.println(testClass.getName());
		
		for (Constructor<?> constructor :strClass.getDeclaredConstructors()) {
			System.out.println(constructor);
		}
		System.out.println("----");
		for(Field f : testClass.getDeclaredFields()) {
			System.out.println(f.getName()+":"+f.getModifiers()+":"+f.getType().getSimpleName());
		}
	}

}
