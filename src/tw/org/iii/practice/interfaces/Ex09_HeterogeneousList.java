package tw.org.iii.practice.interfaces;

import java.util.ArrayList;
import java.util.List;

// 題 9 — 用 List<介面> 裝異質物件（後端日常）
// 目標：學會用「介面當泛型參數」一次處理多種實作,for loop 自動多型。
//
// 情境：
//   Shape09 介面：double area();
//
//   三個實作：
//     Circle09   有 radius 欄位 + 建構子 + area() = radius * radius * 3.14
//     Square09   有 side 欄位 + 建構子 + area() = side * side
//     Triangle09 有 base + height 欄位 + 建構子 + area() = base * height / 2
//
//   在 main：
//     - 宣告 List<Shape09> shapes = new ArrayList<>();
//     - 加入三種不同形狀（各自 new 並帶入合理參數）
//     - 用 for 迴圈跑 shapes,印出每個形狀的面積
//     - 順便算總面積
//
//   進階觀察：
//     假設業主要加一個 Pentagon09（五角形）implements Shape09
//     - main 的 for 迴圈要改嗎？
//     - 三個原本的形狀類別要改嗎？
//
// 觀察重點（寫完回答）：
//   1. 為什麼一個 for 迴圈能自動跑三種形狀各自的 area()？編譯期看到什麼？執行期看到什麼？
//   2. 如果宣告寫 List<Circle09>,還能塞 Square09 進去嗎？為什麼？
//   3. 為什麼 List 介面 + ArrayList 實作這組合,跟你今天寫的 Shape09 介面 + Circle09 實作,是「同一件事」？
//   4. 加 Pentagon09 後,你的程式有哪幾行要改、哪幾行完全不用動？

public class Ex09_HeterogeneousList {

	public static void main(String[] args) {

	}
}

interface Shape09 {

}

class Circle09 implements Shape09 {

}

class Square09 implements Shape09 {

}

class Triangle09 implements Shape09 {

}

/* === 觀察結果（寫完填這裡）===
三種形狀各自面積：
總面積：

加 Pentagon09 後 main 的 for 要改嗎：
加 Pentagon09 後三個原本形狀要改嗎：

Q1 為什麼一個 for 能跑三種 area（編譯 vs 執行）：

Q2 List<Circle09> 能塞 Square09 嗎為什麼：

Q3 List+ArrayList 跟 Shape09+Circle09 為什麼是同一件事：

Q4 加 Pentagon09 後哪幾行要改：

*/
