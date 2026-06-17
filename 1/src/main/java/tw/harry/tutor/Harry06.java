package tw.harry.tutor;

import org.mindrot.jbcrypt.BCrypt;

import tw.harry.dao.MemberDao;
import tw.harry.entity.Member;
import tw.harry.entity.MemberInfo;

// Harry06：新增一個會員。重點 1：密碼用 BCrypt 雜湊再存；重點 2：一對一靠 cascade 把 memberinfo 一起存進去
public class Harry06 {
	public static void main(String[] args) {
		MemberDao dao = new MemberDao();

		Member member = new Member();
		member.setAccount("a1");
		// BCrypt.gensalt() 產生隨機鹽(salt)；hashpw(明碼, 鹽) 把 "123456" 雜湊成一長串，
		// 存進 DB 的是這串雜湊、不是明碼 123456
		member.setPasswd(BCrypt.hashpw("123456", BCrypt.gensalt()));

		MemberInfo info = new MemberInfo();
		info.setBirthday("1999-01-02");
		info.setMale(true);
		info.setTel("123");

		// setMemberinfo 內部會幫你接好 info.setMember(this)（雙向一致）；
		// 少了這步，下面 cascade 存進去時 memberinfo 的外鍵會是 null
		member.setMemberinfo(info);

		// 只呼叫一次 addMember(member)，但 Member.memberinfo 是 cascade = ALL
		// → member 和 memberinfo 會「兩條 INSERT」一起寫進去
		dao.addMember(member);
	}
}

/*
===== 自己看過 =====
1) 同一個密碼 "123456"，每次跑 BCrypt 雜湊出來都不一樣 —— 因為 gensalt() 每次給不同的隨機鹽，這是故意的。
   那登入時怎麼驗證密碼對不對？→ 用 BCrypt.checkpw(輸入的明碼, DB 存的雜湊)，不是自己再 hash 一次去比對。
2) 跑完看主控台：應該是「兩條 INSERT」(member 一條、memberinfo 一條)，對上你 MemberDao.addMember 裡那條自己看過。
3) 你種子資料 member 1~15 的 passwd 是明碼(pw001…)，這支存進去的是雜湊 —— 兩種混在同一欄。
   自己想：實務上密碼欄該不該允許明碼存在？
*/
