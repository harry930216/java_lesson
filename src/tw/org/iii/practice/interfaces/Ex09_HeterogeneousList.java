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
		List<Shape09> shapes = new ArrayList<>();
		shapes.add(new Circle09(3)); 
		shapes.add(new Square09(3)); 
		shapes.add(new Triangle09(3,3)); 
		
		for (Shape09 s : shapes) {
			System.out.printf("印出面積: %f\n",s.area());
		}
		
	}
}

interface Shape09 {
	double area();
	
}

class Circle09 implements Shape09 {
	double radius;
	
	public Circle09(double r) {
		radius = r;
	}
	
	@Override
	public double area() {
		// TODO 自動產生的方法 Stub
		return radius * radius * 3.14;
	}
}

class Square09 implements Shape09 {
	double side;
	
	public Square09(double s) {
		side = s;
	}
	
	@Override
	public double area() {
		// TODO 自動產生的方法 Stub
		return side * side;
	}
}

class Triangle09 implements Shape09 {
	double base;
	double height;
	
	public Triangle09(double b,double h) {
		base = b;
		height = h;
	}
	
	@Override
	public double area() {
		// TODO 自動產生的方法 Stub
		return base * height / 2;
	}
}

/* === 觀察結果 ===
三種形狀各自面積：
   Circle09(3)     → 28.26
   Square09(3)     → 9.00
   Triangle09(3,3) → 4.50
總面積：41.76

加 Pentagon09 後 main 的 for 要改嗎：不用 List處理
加 Pentagon09 後三個原本形狀要改嗎： 不用

Q1 為什麼一個 for 能跑三種 area（編譯 vs 執行）：
   因為動態分發

   [Claude 補充] ✅ 抓到關鍵字 — 完整展開：
   - 編譯期：for 看 s 的宣告型別 = Shape09，編譯器確認 Shape09 介面有 area() → 通過
   - 執行期：JVM 看 s 實際指向的物件（Circle09 / Square09 / Triangle09）
                 → 透過 itable 找該物件 class 的 area() 實作 → 跑那份
   - 每次循環 s 指向不同物件 → 跑不同 area() = 多型威力
   → 跟 Ex08 feedNicely(Eater08 e) 是同一個機制，
     只是場景從「方法參數」搬到「容器元素」。

Q2 List<Circle09> 能塞 Square09 嗎為什麼：
   N 因為宣告型別無關係

   [Claude 補充] ✅ 對 — 用詞精準化：
   List<Circle09> 宣告 = 「這個 list 只能裝 Circle09 或其 subtype」
   Square09 ≠ Circle09 的 subtype（兩者都是 Shape09 的兄弟，沒有父子關係）
   → 編譯器擋住

Q3 List+ArrayList 跟 Shape09+Circle09 為什麼是同一件事：
   同一件事不太懂 但是我知道這樣做很省事 因為是型別在編譯時is-a關係

   [Claude 解答] 「同一件事」= 完全一模一樣的「interface + 實作」設計模式：

   | 你今天寫的                              | Java 標準庫的                            |
   |----------------------------------------|------------------------------------------|
   | interface Shape09                      | interface List                           |
   | class Circle09 implements Shape09      | class ArrayList implements List          |
   | class Square09 implements Shape09      | class LinkedList implements List         |
   | class Triangle09 implements Shape09    | class Vector implements List             |
   | Shape09 s = new Circle09(3);           | List<X> list = new ArrayList<>();        |
   | 變數型別 = interface                    | 變數型別 = interface                      |
   | 創造時 = 具體實作                        | 創造時 = 具體實作                          |

   → 完全相同的模式：「對抽象寫程式，不對具體寫程式」(Program to interface)
   → 省事的原因：未來想換 ArrayList 為 LinkedList 只要改 new 那邊：
        List<Shape09> shapes = new ArrayList<>();    // 原本
        List<Shape09> shapes = new LinkedList<>();   // 換實作，其他全部不動
   → 跟你 Shape09 場景一樣 — 加 Pentagon09 不用改 for 迴圈
   → Java Collections Framework 整套就建立在這個模式上
     List 介面已經有 17 個實作 class（ArrayList, LinkedList, Vector, CopyOnWriteArrayList...）

   ⚠️ 用詞提醒：你寫「型別在編譯時 is-a 關係」— 還記得 Ex08 查證過 「is-a」的兩種用法？
              嚴格講這層應該叫 subtype 關係（JLS §4.10.2 規範術語）。
              意思一樣，但用詞精準會減少未來自我混淆。

Q4 加 Pentagon09 後哪幾行要改：
   shapes.add
   以及Pentagon09類別

   [Claude 補充] ✅ 對 — 完整對照表：

   | 要改                                            | 不用改                                        |
   |------------------------------------------------|---------------------------------------------|
   | 新增 class Pentagon09 implements Shape09       | Shape09 介面（契約沒變）                       |
   | （含 area()）                                   |                                              |
   | main 加一行 shapes.add(new Pentagon09(...))    | Circle09/Square09/Triangle09（其他形狀不影響） |
   |                                                | for 迴圈（自動處理新形狀）                     |
   |                                                | List 宣告（List<Shape09> 已涵蓋 Pentagon09）  |

   → OCP 原則展現：對擴充開放（加 Pentagon09），對修改封閉（既有 code 不動）
*/
