package tw.org.iii.practice.interfaces;

// 題 6 — 混合骨幹模式（Skeletal Implementation Pattern）
// 目標：學 Java 標準庫到處用的「interface + abstract 骨幹 + concrete」三層架構。
//
// 真實對應：List → AbstractList → ArrayList
//          Map  → AbstractMap  → HashMap
// 你寫完這題，看 Java 源碼會直接懂為什麼這樣排。
//
// 情境：
//   第 1 層 Animal2 介面（純契約）：
//     - String getName();
//     - void eat();
//     - void sleep();
//
//   第 2 層 AbstractAnimal 抽象類（骨幹：把共通行為先寫好）：
//     - implements Animal2
//     - 有 name 欄位 + 建構子收 name
//     - 實作 getName() 回傳 name
//     - 實作 sleep() 印 "name 睡覺了"   ← 所有動物睡覺都長一樣，先寫好
//     - eat() 保持 abstract           ← 每種動物吃的東西不同，留給子類
//
//   第 3 層 Dog2 具體類：
//     - extends AbstractAnimal
//     - 建構子 Dog2(String name) 用 super(name)
//     - eat() 印 "name 吃骨頭"
//
// 在 main：new Dog2("小白")，呼叫 getName()、eat()、sleep()。
//
// 觀察重點（寫完回答）：
//   1. Dog2 只寫了一個 eat() 方法，為什麼還能 getName() 和 sleep()？
//   2. 如果不用 AbstractAnimal 骨幹，直接 Dog2 implements Animal2，會多寫什麼？
//   3. 為什麼介面定契約、抽象類提供骨幹、具體類補空缺，是「最少重複 + 最大彈性」的組合？

public class Ex06_HybridPattern {

	public static void main(String[] args) {

	}
}

interface Animal2 {

}

abstract class AbstractAnimal implements Animal2 {

}

class Dog2 extends AbstractAnimal {

}

/* === 觀察結果（寫完填這裡）===
getName() =
eat()     =
sleep()   =

Q1 Dog2 只寫一個 eat() 為什麼還能用其他方法：

Q2 不用 AbstractAnimal 骨幹會多寫什麼：

Q3 三層架構為什麼是「最少重複 + 最大彈性」：

*/
