package tw.org.iii;

public class PokerV2doWhile {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		int nums = 52;
		int[] poker = new int[nums];
		for (int i = 0; i < poker.length; i++) {

			int temp;
			boolean isRepeat;
			do {
				temp = (int) (Math.random() * nums); // 0-51

				// 檢查機制
				isRepeat = false;
				for (int j = 0; j < i; j++) {
					if (temp == poker[j]) {
						// 重複了
						isRepeat = true;
						break;
					}
				}
			} while (isRepeat);

			poker[i] = temp;
		}
		long end = System.currentTimeMillis();
		for (int card : poker) {
			System.out.println(card);
		}
		System.out.println("--------------------------------------");
		System.out.println(end - start);
	}

}
