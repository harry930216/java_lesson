package tw.harry.tutor;

import java.util.List;

import tw.harry.dao.MemberDao;
import tw.harry.entity.Member;
import tw.harry.entity.MemberInfo;

// Harry08：用模糊查詢撈出「account 含 a」的會員清單，逐筆印出，並一對一帶出 memberinfo 一起印
public class Harry08 {

	public static void main(String[] args) {
		MemberDao dao = new MemberDao();

		// 撈 account「包含 a」的會員（你種子資料裡 amy/cathy/jack/karen… 都含 a；若跑過 Harry06 還有 a1）
		List<Member> members = dao.findByAccountLike("a");

		for (Member member : members) {
			System.out.printf("%s:\n", member.getAccount());

			// 一對一導覽：從 member 拿它的 memberinfo（跟 Harry07 同一招）
			MemberInfo info = member.getMemberinfo();
			if (info != null) {  // 防呆：萬一這個 member 沒有 memberinfo，跳過不印（不然 getTel() 會 NPE）
				// isMale() 是 boolean，用三元運算子轉成中文：true→男、false→女
				System.out.printf("%s:%s:%s\n",
						info.getTel(),
						info.getBirthday(),
						info.isMale() ? "男" : "女");
			}
			System.out.println("----");
		}
	}

}

/*
===== 自己看過 =====
這支撈出 N 個 member，迴圈裡每個又呼叫 member.getMemberinfo()。
跑起來去數主控台的 SELECT 共幾條：是「1 條（撈 member 時 join 帶出全部 memberinfo）」，
還是「1 + N 條（先撈清單，再一個一個去查 memberinfo）」？
後者就是後端很有名的「N+1 查詢問題」—— 清單一大就會送出爆量 SQL。
這題接你前面問的 @OneToOne EAGER：EAGER 到底怎麼撈，這裡用「清單」最看得出差別。先觀察，不要先記結論。
*/
