package tw.harry.tutor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import tw.harry.entity.Test;

// 介紹反射類別

public class Harry04 {

	public static void main(String[] args) {
		Class<String> strClass = String.class;  // 取得 String 的「架構圖」(Class 物件)；.class 就是拿架構圖的方式
		Class<Test> testClass = Test.class;  // 取得你自己 Test 的架構圖
		// 這裡的泛型 實際主角是class 
		// 說明書 Class	Class<Test>	描述 Test 這個型別
		
		System.out.println(strClass.getName());  // 印類別全名 → java.lang.String
		System.out.println(testClass.getName());  // → tw.harry.entity.Test
		
		// 反射「讀」①：列出 String 的所有建構子(constructor)
		for (Constructor<?> constructor :strClass.getDeclaredConstructors()) {
			System.out.println(constructor);
		}
		System.out.println("----");  // 分隔線
		// 反射「讀」②：列出 Test 的所有欄位(field)
		for(Field f : testClass.getDeclaredFields()) {
			System.out.println(f.getName()+":"+f.getModifiers()+":"+f.getType().getSimpleName());  // 印 欄位名:修飾子代碼:型別 (1=public 2=private…；連 private 都讀得到 → 反射能看穿封裝)
		}
	}

}

/*
 這支只示範反射的「讀」(看類別有哪些欄位、建構子)。
 Hibernate 在這基礎上再多做「寫」那一半：
   testClass.getDeclaredConstructor().newInstance()  → 動態 new 一個 Test
   field.set(該 Test, 資料庫那一格的值)               → 把每一欄塞進去
 → 合起來就是 Harry03 說的「Hibernate 拿 Test.class 架構圖 new 物件 + 塞欄位」。

 你的程度：知道「.class 拿架構圖、反射 = 照圖動態做事(讀+寫)」就夠，不用會自己手寫反射。
*/
