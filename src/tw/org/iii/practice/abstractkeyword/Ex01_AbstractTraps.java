package tw.org.iii.practice.abstractkeyword;

// 題 1 — abstract 踩雷集合（abstract 完整邊界）
// 目標：撞編譯錯,內化 abstract 的所有規則邊界,釐清 abstract class 跟 interface 的差別。
//
// 情境：
//   下面 BadAbstract01 故意踩三個雷,目前全部註解掉。
//   一個一個取消註解,觀察編譯訊息,記下來再註解回去。
//
//   雷 1：abstract private void x();        ← abstract 跟 private 衝突
//   雷 2：abstract void y() { ... }         ← abstract 方法不能有 body
//   雷 3：abstract final void z();          ← abstract 跟 final 衝突
//
//   然後寫正確版 AnimalA1：
//     abstract class AnimalA1：
//       - String name 欄位                 ← abstract class 可以有 instance 欄位
//       - 建構子 AnimalA1(String name) { this.name = name; }   ← abstract class 可以有建構子
//       - abstract void eat();              ← 抽象方法,強迫子類實作
//       - void sleep() { 印 name + " 睡覺" } ← 一般方法,子類繼承後可直接用
//
//   寫 DogA1 extends AnimalA1：
//     - 建構子用 super(name) 把名字丟給父類
//     - 實作 eat() 印 name + " 吃骨頭"
//
//   在 main：
//     - 嘗試 new AnimalA1("XX");           ← 觀察編譯訊息
//     - new DogA1("小白").sleep();
//     - new DogA1("小白").eat();
//
// 觀察重點（寫完回答）：
//   1. 雷 1 的編譯訊息？為什麼 abstract 不能搭 private？（提示：private 不能被子類看到）
//   2. 雷 2 的編譯訊息？為什麼 abstract 方法不能有 body？（提示：abstract 的意思）
//   3. 雷 3 的編譯訊息？為什麼 abstract 不能搭 final？（提示：final 的意思）
//   4. new AnimalA1("XX") 的編譯訊息？為什麼 abstract class 不能 new？
//   5. AnimalA1 有建構子,但既然不能 new,這建構子是給誰用的？
//   6. abstract class 可以完全沒有 abstract 方法嗎？（試試看：把 eat() 拿掉 abstract 改成空 body,看編譯過嗎）
//   7. 比較 abstract class 跟 interface：兩個都不能 new,差別是？（從 instance state 角度回答）

public class Ex01_AbstractTraps {

	public static void main(String[] args) {
		
//		new AnimalA1("XX");
		new DogA1("小白").sleep();
		new DogA1("小白").eat();
	}
}

abstract class BadAbstract01 {

	// === 雷 1 === 取消註解觀察,觀察完再註解回去
//	 abstract private void x();

	// === 雷 2 === 取消註解觀察,觀察完再註解回去
//	 abstract void y() { System.out.println("我有 body"); }

	// === 雷 3 === 取消註解觀察,觀察完再註解回去
//	 abstract final void z();
}

abstract class AnimalA1 {
	String name;
	
	public AnimalA1(String n) {
		name = n;
	}
	
	abstract void eat();
	
	void sleep() {
		System.out.println(name + " 睡覺" );
	}
}

class DogA1 extends AnimalA1 {
	public DogA1(String name) {
		super(name);
	}
	
	@Override
	void eat() {
		System.out.println(name + " 吃骨頭");
	}
	
}

/* === 觀察結果（寫完填這裡）===
雷 1 編譯訊息：The abstract method x in type BadAbstract01 
can only set a visibility modifier, one of public or protected

雷 2 編譯訊息：Abstract methods do not specify a body

雷 3 編譯訊息：The abstract method z in type BadAbstract01 can 
only set a visibility modifier, one of public or protected

new AnimalA1("XX") 編譯訊息： Cannot instantiate the type AnimalA1
new DogA1("小白").sleep() 輸出：
new DogA1("小白").eat()   輸出：

Q1 abstract 不能搭 private 為什麼：
	只能在class中 那誰要幫你實作?
Q2 abstract 方法不能有 body 為什麼：
	很矛盾 你就是虛擬方法了 但還實作幹嘛?
	但是我覺得還是沒很通 請你多解釋
Q3 abstract 不能搭 final 為什麼：
	方法不能改 又沒實作方法 等叫到方法就炸了
Q4 abstract class 不能 new 為什麼：
	裡面沒實作的方法就炸了
Q5 AnimalA1 建構子既然不能 new 是給誰用的：
	給子代用的
Q6 abstract class 可以完全沒有 abstract 方法嗎：
	可以
Q7 abstract class 跟 interface 兩個都不能 new 差別在哪（從 instance state 角度）：
	abstract class不能new是因為怕方法沒能實作
	interface 則是忘了
	幫我複習
*/

/* === [Claude 補充] Q1-Q7 review（不動你上面原答案，這裡另寫，你自己對照）===

【先抓一個共通病】Q3、Q4 你都寫「炸了」。這兩個雷都是「編譯期」就被擋 ——
javac / Eclipse 檢查時就拒絕，.class 根本生不出來，不是「跑起來才炸」。
compile-time（編譯期檢查）≠ runtime（JVM 執行期）。Q2 也會用到這個分界。

--- Q1（對）---
「誰要幫你實作」抓到核心。精準版：abstract 要靠子類 override 補實作；
private 對子類不可見，子類就算寫同名方法也只是「另一個新方法」、不算 override。
一個方法同時「必須被 override」又「不可能被 override」→ 矛盾 → 編譯擋下。

--- Q2（你點名要的詳解：abstract 方法為何不能有 body）---
你卡在「你就是虛擬方法了，還實作幹嘛」。病根：你把「虛擬」和「abstract」當同一件事。
其實是兩條獨立的軸：
  軸一 = 這方法可否被子類覆寫（這叫「虛擬」）
  軸二 = 這方法「這一層」有沒有寫 body
看 AnimalA1.sleep()：它可被覆寫（是虛擬的）、又有 body。
→ 證明「虛擬」和「有 body」可同時成立、不互斥。所以 abstract ≠ 虛擬。

abstract 的精準意思是：「我這一層拒絕寫 body，把實作推給子類。」
那 abstract + body 就字面打架：abstract 說「這層沒實作」、body 說「這層就是實作」。

JVM 視角（真正的底）：.class 檔裡每個方法是一筆 method_info。
  一般方法：method_info 帶一個 Code 屬性（裡面是 bytecode）。
  abstract 方法：設 ACC_ABSTRACT 旗標、且「沒有 Code 屬性」。
這兩種在檔案格式上互斥 —— 一筆方法要嘛帶 Code、要嘛標 abstract，不可能都有。
你寫 abstract void y(){...}，等於叫 javac 產出「又標 ACC_ABSTRACT 又帶 Code」的東西，
格式上做不出來，所以擋你。
延伸：invokevirtual 呼叫方法時，JVM 要跳進那方法的 bytecode 去跑。abstract 方法
沒有 bytecode，所以它永遠不會被「直接執行」—— 真正跑的是子類那個有 body 的
override 版。這就是為什麼 abstract 一定要有人 override。

--- Q3（方向偏了，自己重寫；只給提示）---
「等叫到方法就炸了」—— 上面說過，它編譯就不過，輪不到「叫」。
提示：把 final、abstract 對「同一個方法」下的命令並排念一次 ——
final 下的命令是什麼？abstract 下的命令是什麼？兩個擺一起，矛盾在哪？
（跟 Q1 同一種結構：又要、又不准。）想清楚把 Q3 重寫。

--- Q4（不完整，跟 Q6 一起看；只給提示）---
「裡面沒實作的方法就炸了」——「炸了」同上。更關鍵：看你自己的 Q6。
你 Q6 說 abstract class「可以完全沒有 abstract 方法」，對。
那一個「沒有任何 abstract 方法」的 abstract class，照你 Q4 的理由應該能 new 才對 ——
但它還是不能。所以擋 new 的不是「有沒有沒實作的方法」。
提示：那個沒有 abstract 方法的 class，跟一般 class 唯一差在哪？那個「差別」就是答案。
想清楚把 Q4 重寫。

--- Q5（對）---
「給子代用」對。精準：子類 DogA1 建構子第一行 super(name) 呼叫它，
作用是初始化 AnimalA1 這層的 name 欄位。它不是被 new 呼叫，是被「子類建構子鏈」呼叫。

--- Q6（對）---
對。而這正是 Q4 的鑰匙：沒有任何 abstract 方法、它「還是」不能 new ——
證明擋 new 的是 class 上的 abstract 關鍵字本身。

--- Q7（你點名要的複習：abstract class vs interface，instance state 角度）---
instance state = 每個物件各自擁有的欄位值。
  abstract class =「蓋到一半的 class」：有 instance 欄位（AnimalA1 的 String name 就是）、
    有建構子初始化它、有一般方法。它有 per-object 狀態，只是某些方法留 abstract。
  interface =「純規格」：沒有 instance 欄位 —— interface 裡寫的欄位一律是
    public static final 常數（全類共用一份，不是每物件各一份）、沒有建構子。
    （Java 8+ 可有 default 方法，但仍沒有 instance 欄位。）
→「從 instance state 角度」的答案：abstract class 有 instance state、interface 沒有。
延伸（接你學過的 interface 鑽石問題）：class 只能 extends 一個 abstract class、
卻能 implements 多個 interface —— 就是因為 state。兩個父類都有 name 欄位會衝突，
所以帶 state 的繼承鏈只准一條；interface 沒 state，多接也不打架。

--- 你還有兩格沒填 ---
line 97-98 sleep()、eat() 的輸出你留白了 —— 跑一下自己填，那是你的觀察，我不代填。
*/
