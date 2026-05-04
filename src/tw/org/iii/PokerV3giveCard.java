/*
 * Fisher-Yates Shuffle（洗牌法 / Knuth Shuffle）
 *
 * 核心思想：
 *   1) 先排好 0~51（每張牌恰好一次，零重複問題）
 *   2) 每輪「釘」一格、跟「未釘區」任一格交換
 *   3) 已釘格不再參與隨機 → 自然零重抽、時間複雜度 O(n)
 *
 * 「釘」的真正意思：
 *   不是「鎖定這格不動」，而是「決定這格最終放哪張牌」。
 *   那張牌可以來自 0 ~ 釘的位置（含釘的位置自己）。
 *   「來自自己」= 「牌留原位」= 也是合法的洗牌結果，
 *   必須給它機率，不然就偏差。
 *
 * off-by-one 兩個 -1 不能放錯（這題最容易踩的坑）：
 *   - 釘的位置 ：poker[length - 1 - j]       ← 要 -1（索引從 0 起算）
 *   - 隨機範圍 ：Math.random() * (length - j) ← 不能 -1（要含自己）
 *   口訣：「位置」減 1，「範圍」不減。
 *
 * 範圍多寫了 -1 會發生什麼：
 *   - 變成 Sattolo 演算法（不是 Fisher-Yates）
 *   - 「每張牌保證離開原位」→ poker[51] 永遠不會是 51
 *   - 52! 種排列裡只能產生 51! 種，98% 的洗牌結果產生不出
 *   - 跑得起來但統計偏差，是「隱性 bug」（最危險的那種）
 *
 * 驗證方法：跑 100 萬次，數 poker[51] == 51 的次數
 *   - 正解 ≈ 19,230 次（1/52 ≈ 1.92%）
 *   - 寫錯永遠是 0 次
 *
 * 對稱對比四種「不重複 52 張」演算法：
 *   重抽法    ：抽到撞了重抽         O(n²) 期望差，越後面越慢
 *   標記法    ：boolean[] used 記錄  O(n²) 期望好，仍有重抽
 *   候選池法  ：抽走就消失           O(n²) （ArrayList.remove 慢）
 *   洗牌法★  ：先排好再亂排         O(n)  零重抽、零額外空間
 */
package tw.org.iii;

public class PokerV3giveCard {

	public static void main(String[] args) {
		int[] poker = new int[52];
		for (int i = 0; i < poker.length; i++) poker[i] = i+1;
		
		for (int j = 0; j < poker.length - 1; j++) {
			int random = (int)(Math.random() * (poker.length - j));
			int bridge = poker[poker.length-j-1];
			poker[poker.length-j-1] = poker[random];
			poker[random] = bridge;
		}
//		for (int card : poker) {
//			System.out.println(card);
//			
//		}
		
		int[][] players = new int[4][13];
		
		for (int i = 0; i < poker.length; i++) {
			players[i%4][i/4] = poker[i];
		}
		for (int[] player: players) {
			for (int card : player) {
				System.out.print(card + " ");
			}
			System.out.println();
		}
	}

}
