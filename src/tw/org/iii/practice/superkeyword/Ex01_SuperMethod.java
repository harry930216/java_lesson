package tw.org.iii.practice.superkeyword;

// 題 1 — super.method() 擴充父類
// 目標：子類覆寫父類方法，但要「先做父類的事再加自己的事」，不能把父類整個砍掉。
// 情境：BaseController 有 log(String msg) 方法印一行通用 log。
//       UserController 繼承後，希望 log() 先印通用 log，再多印一行「儲存到 DB」。
//
// 【用戶思路】外部方法要傳參 → 內先 super 傳參 → 再印儲存到 DB
// 【補充重點】super.method(arg) 可以「參數轉發」給父類版本，不是只能無參呼叫。
//             這是後端最常見的「保留父類行為 + 擴充自己邏輯」模式。

public class Ex01_SuperMethod {

	public static void main(String[] args) {
		UserController uc = new UserController();
		uc.log("登入");
		// 預期輸出：
		// [LOG] 登入
		// 儲存到 DB: 登入
	}
}

class BaseController {
	void log(String msg) {
		System.out.println("[LOG] " + msg);
	}
}

class UserController extends BaseController {
	@Override
	void log(String msg) {
		super.log(msg);                          // ← 參數轉發，先做父類的事
		System.out.println("儲存到 DB: " + msg); // 再加自己要做的事
	}
	// 實務模式：父類提供「通用骨幹」，子類用 super.x() 保留它再擴充。
	// 反例（壞寫法）：直接複製貼上父類的程式碼到子類 → 父類改一次要改兩個地方。
}
