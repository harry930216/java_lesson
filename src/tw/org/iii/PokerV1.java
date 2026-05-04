package tw.org.iii;

public class PokerV1 {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		int nums = 52;
		int[] poker = new int[nums];
		for (int i = 0; i < poker.length; i++) {

			int temp = (int) (Math.random() * nums); // 0-51

			// 檢查機制
			boolean isRepeat = false;
			for (int j = 0; j < i; j++) {
				if (temp == poker[j]) {
					// 重複了
					isRepeat = true;
					break;
				}
			}
			if (isRepeat) {
				i--;
			} else {
				poker[i] = temp;
			}

		}
		long end = System.currentTimeMillis();
		for (int card : poker) {
			System.out.println(card);
		}
		System.out.println("--------------------------------------");
		System.out.println(end - start);
	}

}
