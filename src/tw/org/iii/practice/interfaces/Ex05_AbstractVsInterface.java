package tw.org.iii.practice.interfaces;

// 題 5 — 同需求兩種寫法（決策練習）
// 目標：同一個需求分別用 abstract class 和 interface 各寫一遍，比較差異。
// 情境：需求是「Shape 形狀有 area() 方法計算面積，Circle 和 Square 是具體形狀」。
//
//   寫法 A（abstract class）：
//     abstract class ShapeA 有：
//       - double size 欄位
//       - 建構子 ShapeA(double size)
//       - abstract double area()
//     CircleA extends ShapeA，area() = size * size * 3.14
//     SquareA extends ShapeA，area() = size * size
//
//   寫法 B（interface）：
//     interface ShapeB 有：
//       - double area();
//     CircleB implements ShapeB，自己宣告 size 欄位、自己寫建構子、area() = size * size * 3.14
//     SquareB implements ShapeB，同理
//
// 兩種寫法都在 main 各 new 一次計算面積，印出結果。
//
// 觀察重點（寫完回答）：
//   1. 哪一種寫法重複程式碼比較多？為什麼？
//   2. 如果需求變成「Circle 和 Square 還要能 implements Drawable」，哪種寫法比較靈活？
//   3. 結論：什麼情況該選 abstract、什麼情況該選 interface？

public class Ex05_AbstractVsInterface {

	public static void main(String[] args) {
		
		System.out.printf("CircleA : %f\n",new CircleA(10).area());
		System.out.printf("SquareA : %f\n",new SquareA(10).area());
		System.out.printf("CircleB : %f\n",new CircleB(10).area());
		System.out.printf("SquareB : %f\n",new SquareB(10).area());

	}
}

// === 寫法 A：abstract class ===
abstract class ShapeA {
	protected double size;

	ShapeA(double size) {
		this.size = size;
	}

	abstract double area();
}

class CircleA extends ShapeA {
	public CircleA(double size) {
		super(size);
	}

	double area() {
		return size * size * 3.14;
	}

}

class SquareA extends ShapeA {
	public SquareA(double size) {
		super(size);
	}

	double area() {
		return size * size;
	}
}

// === 寫法 B：interface ===
// 自己宣告 size 欄位、自己寫建構子、area() = size * size * 3.14
interface ShapeB {
	double area();
}

class CircleB implements ShapeB {
	double size;
	
	public CircleB(double size) {
		this.size = size;
	}
	
	public double area() {
		return size * size * 3.14;
	}
}

class SquareB implements ShapeB {
double size;
	
	public SquareB(double size) {
		this.size = size;
	}
	
	public double area() {
		return size * size ;
	}
}

/*
 * === 觀察結果 ===
 * CircleA 面積 = 314.000000
 * SquareA 面積 = 100.000000
 * CircleB 面積 = 314.000000
 * SquareB 面積 = 100.000000
 * → A 跟 B 輸出完全一樣，差別不在「能不能算出結果」，而在「設計表達力」。
 *
 * Q1 哪種寫法重複程式碼比較多：
 *    interface
 *
 *    [Claude 補充] ✅ 對 — 補充重複了什麼
 *    B 寫法每個子類都要重抄：
 *      - double size;                                    （field）
 *      - public XxxB(double size) { this.size = size; }  （constructor）
 *    A 寫法把這兩個寫在 ShapeA 一次，子類只負責自己獨特的 area() 算法。
 *    → abstract class 的存在意義就是「抽出共用 state + constructor」。
 *
 * Q2 如果還要能 implements Drawable，哪種比較靈活：
 *    abstract 不知道 就直覺 應該是分開處理繼承跟實作 方法不用選擇
 *
 *    [Claude 撤回先前指正] ✅ 你選 A 是對的，是我答錯
 *    這題 wording 下兩個寫法都能 implements Drawable：
 *      A: class CircleA extends ShapeA implements Drawable   ✅
 *      B: class CircleB implements ShapeB, Drawable          ✅
 *    所以「flexibility 沒差別」。
 *
 *    A 反而拿到 best of both worlds：
 *      - extends ShapeA   → 共用 state（size 寫一次）+ 共用 constructor
 *      - implements Drawable → 加上一個 capability
 *    這是 OOP 教科書標準設計：abstract 處理「is-a 同類東西」+ interface 處理「can-do 能力」
 *
 *    「interface 更靈活」cliché 真正適用的是另一場景：
 *      想 extends 兩個 abstract class（例如 ShapeA + Saveable abstract）
 *        → class 單繼承限制，編譯錯
 *      改用 interface（implements ShapeB, Saveable）→ 想塞幾個都行
 *    但本題沒到那種程度，所以你 A 寫法完全 OK。
 *
 * Q3 結論（什麼時候用 abstract、什麼時候用 interface）：
 *    我沒有很懂 但是如果是需要有物件的 而且物件同質性高 那應該用abstract
 *    但如果只是欄位需要一樣 需要多介面合併 那應該用interface
 *
 *    [Claude 補充] 前半對、後半「欄位需要一樣 → interface」反了，要修
 *
 *    interface 的「field」自動變 public static final（= 常量、所有實作者共用唯一一份、不可變），
 *    根本「不能放 instance state」。所以「欄位要一樣（每個子類自己一份）」反而必須用 abstract class。
 *
 *    完整選擇表：
 *    | 場景                                       | 選             |
 *    |-------------------------------------------|----------------|
 *    | 子類有共用 state（每物件一份的 field）        | abstract class |
 *    | 子類有共用實作邏輯                          | abstract class（或 interface 的 default method） |
 *    | 子類本質「是一種同類東西」(IS-A 強烈)         | abstract class |
 *    | 純行為契約，沒共用 state                    | interface      |
 *    | 一個 class 想擁有多種能力（多重實作）         | interface      |
 *    | 跨繼承樹的能力（Bird 跟 Plane 都會 fly）     | interface      |
 *    | 想 extends 多個 abstract 父（單繼承卡死時）  | interface      |
 *
 *    口訣：
 *      abstract = 「這些東西本質一樣」（IS-A，狀態 + 行為都共用）
 *      interface = 「這些東西有同樣能力」（CAN-DO，純契約，跨繼承樹）
 *      實務上常常兩個合用：abstract 給共用骨架 + interface 給能力擴充
 */
