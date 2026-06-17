package tw.harry.tutor;

import tw.harry.dao.MemberDao;
import tw.harry.entity.Member;
import tw.harry.entity.MemberInfo;

// Harry07：用主鍵把會員撈出來 → 一對一導覽到 memberinfo → 改生日後更新（沒有 memberinfo 就新建一筆掛上去）
public class Harry07 {
	public static void main(String[] args) {
		MemberDao dao = new MemberDao();

		Member member = dao.findById(1);  // 用主鍵 id=1 撈一個 member（你種子資料裡的 harry）

		// ===== 自己看過（順序 bug，老師原碼就長這樣）=====
		// 這行在「if (member != null)」檢查之前就用了 member.getAccount()。
		// 萬一 id=1 不存在，member 會是 null → 這行先丟 NullPointerException，
		// 根本走不到最下面那個印 "Member ID NOT EXIST" 的 else。順序反了。
		// 你 DB 有 id=1 所以這次不會炸，但邏輯是錯的 —— 自己想：這行應該搬到哪裡才對？
		// （老師後來把印的內容改成 id:account，但「印在 null 檢查之前」這個順序問題沒修，bug 還在）
		System.out.println(member.getId() + ":" + member.getAccount());

		if (member != null) {
			// ===== 自己看過（一對一的載入時機）=====
			// findById 裡的 session 在 return 當下就被 try-with-resources 關掉了，
			// 為什麼這裡還能 member.getMemberinfo() 拿到資料？
			// → @OneToOne 預設 fetch = EAGER：session 還開著時 get() 就把 memberinfo 一起查回來了。
			//   看主控台 SQL，是不是撈 member 的同時就把 memberinfo 也 select 出來。
			//   若改成 fetch = LAZY，這行會在 session 關閉後才想載入 → 丟 LazyInitializationException。
			MemberInfo info = member.getMemberinfo();
			if (info != null) {
				System.out.printf("MemberInfo: %s\n", info.getBirthday());
				info.setBirthday("2000-01-02");  // 改的是記憶體裡這個物件的生日（還沒進 DB）
			} else {
				// 沒有 memberinfo 就新建一筆，掛回 member 身上
				info = new MemberInfo();
				info.setBirthday("1999-03-04");
				info.setMale(false);
				info.setTel("333");
				member.setMemberinfo(info);
			}
			// ===== 自己看過 =====
			// 只 updateMember(member)，沒單獨 update info。因為 cascade = ALL，
			// merge member 時會連帶把改過的 memberinfo 一起 UPDATE（上面 else 那種新建的則是 INSERT）。
			dao.updateMember(member);
		} else {
			System.out.println("Member ID NOT EXIST");
		}
	}
}
