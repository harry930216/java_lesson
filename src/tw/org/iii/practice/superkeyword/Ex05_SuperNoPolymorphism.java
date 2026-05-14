package tw.org.iii.practice.superkeyword;

// 題 5 — super.method() 不會多型（永遠跑父類版本,即使有孫類覆寫）
// 目標：親手證明 super.method() 是「靜態綁定」,不會走動態分派。
//
// 情境（三層繼承）：
//   GrandPa05 有方法 hi() 印 "我是阿公"
//
//   Father05 extends GrandPa05：
//     覆寫 hi() 印 "我是爸爸"
//     另外多一個方法 callSuperHi() { super.hi(); }
//     ← 注意：這裡的 super 永遠是 Father05 的父類,也就是 GrandPa05
//
//   Son05 extends Father05：
//     覆寫 hi() 印 "我是兒子"
//     不覆寫 callSuperHi()
//
//   在 main：
//     Son05 s = new Son05();
//     s.hi();              ← 觀察跑誰
//     s.callSuperHi();     ← 觀察跑誰
//
//     對照：
//     Father05 f = new Father05();
//     f.hi();
//     f.callSuperHi();
//
// 觀察重點（寫完回答）：
//   1. s.hi() 跑誰？為什麼？（提示：多型 / 動態分派）
//   2. s.callSuperHi() 內部的 super.hi() 跑誰？預期是「爸爸」還是「阿公」？
//      ⚠️ 這題的核心：實際物件是 Son05,但 callSuperHi 在 Father05 裡寫的 super,
//          所以 super 指 Father05 的父類 = GrandPa05,**跟實際物件是誰無關**。
//   3. 一句話：this.method() 跟 super.method() 的綁定方式分別是？
//   4. 為什麼 Java 不允許 super.super.method()？如果 Son05 想直接呼叫 GrandPa05.hi(),怎麼做？
//   5. 連結今天 interface 講解：super 是「多型的後門」,什麼意思？

public class Ex05_SuperNoPolymorphism {

	public static void main(String[] args) {

	}
}

class GrandPa05 {

}

class Father05 extends GrandPa05 {

}

class Son05 extends Father05 {

}

/* === 觀察結果（寫完填這裡）===
s.hi()           輸出：
s.callSuperHi()  輸出：
f.hi()           輸出：
f.callSuperHi()  輸出：

Q1 s.hi() 跑誰為什麼：

Q2 s.callSuperHi() 跑誰（預期爸爸還是阿公）為什麼：

Q3 this.method 跟 super.method 綁定方式分別：

Q4 為什麼不允許 super.super,Son05 要叫到 GrandPa05.hi 怎麼辦：

Q5 super 是「多型的後門」什麼意思：

*/
