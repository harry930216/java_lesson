package tw.org.iii.practice.interfaces;

// 題 8 — 方法參數抽象化（避免重載地獄）
// 目標：對比「具體型別當參數要重載 N 個方法」vs「抽象型別當參數一個方法吃全部」。
//
// 情境：
//   Eater08 介面：void eat();
//   三個實作：
//     Dog08  → eat() 印 "狗啃骨頭"
//     Cat08  → eat() 印 "貓吃魚"
//     Bird08 → eat() 印 "鳥啄蟲"
//
//   寫法 A（重載地獄）：在 Ex08 類別內寫三個 static 方法
//     feedBadly(Dog08 d)   → d.eat()
//     feedBadly(Cat08 c)   → c.eat()
//     feedBadly(Bird08 b)  → b.eat()
//
//   寫法 B（抽象參數）：在 Ex08 類別內寫一個 static 方法
//     feedNicely(Eater08 e) → e.eat()
//
//   在 main：
//     - 各 new 一隻動物
//     - 用寫法 A 餵三隻
//     - 用寫法 B 餵同樣三隻
//     - 觀察兩種寫法的結果是否一樣
//
//   進階觀察：
//     假設業主突然要你加一個新動物 Fish08 implements Eater08（eat 印 "魚吃藻"）
//     - 寫法 A 需要做什麼？
//     - 寫法 B 需要做什麼？
//
// 觀察重點（寫完回答）：
//   1. 兩種寫法輸出一樣嗎？為什麼？
//   2. 加 Fish08 後,哪種寫法不用改原本的 feed 方法？為什麼？
//   3. 寫法 B 為什麼能「一個方法吃多種動物」？編譯期看誰、執行期看誰？
//   4. 用一句話說明「方法參數寫介面型別」解鎖了什麼能力。

public class Ex08_ParameterAbstraction {

	public static void main(String[] args) {

	}

	// 寫法 A：重載地獄（請補三個 feedBadly 方法）

	// 寫法 B：抽象參數（請補一個 feedNicely 方法）
}

interface Eater08 {

}

class Dog08 implements Eater08 {

}

class Cat08 implements Eater08 {

}

class Bird08 implements Eater08 {

}

// 進階：請自己加一個 Fish08 implements Eater08

/* === 觀察結果（寫完填這裡）===
寫法 A 三隻動物輸出：

寫法 B 三隻動物輸出：

加 Fish08 後寫法 A 要改：
加 Fish08 後寫法 B 要改：

Q1 兩種寫法輸出一樣嗎為什麼：

Q2 加 Fish08 後哪種寫法不用改 feed：

Q3 寫法 B 為什麼能吃多種動物（編譯期 vs 執行期）：

Q4 一句話：方法參數寫介面型別解鎖什麼能力：

*/
