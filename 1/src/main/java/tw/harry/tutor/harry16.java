package tw.harry.tutor;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import tw.harry.dao.SCDao;
import tw.harry.entity.Course;
import tw.harry.entity.Student;

// harry16：互動選課，把多對多跑起來。輸入學號 → 列出「還沒選的課」→ 選課 → 存。
// 自己看過：類別名 harry16 小寫開頭（鏡像老師的 brad16）。Java 慣例類別要大寫開頭(Harry16)，老師這個算 typo，先照他的。
public class harry16 {

	public static void main(String[] args) {
		SCDao dao = new SCDao();

		Student s1;
		Scanner scanner = new Scanner(System.in);
		System.out.print("Student ID: ");
		long sid = scanner.nextLong();

		s1 = dao.getStudentById(sid);
		if (s1 != null) {
			System.out.printf("Welcome, %s\n", s1.getSname());

			while (true) {
				System.out.println("----Course List-----");
				List<Course> courses = dao.getAllCourse();
				for (Course course : courses) {
					// 只列出「這個學生還沒選的課」：用 isExist 檢查該課的學生名單裡有沒有他
					if (!isExist(s1, course.getStudents())) {
						System.out.printf("%d: %s\n", course.getCid(), course.getCname());
					}
				}
				System.out.println("---");
				System.out.print("Which? (0: Quit)");
				long cid = scanner.nextLong();

				if (cid == 0) break;
				// ===== 自己看過（多對多寫入流程）=====
				// addCourse 把課加進 s1.courses（並雙向接好），再 update 存回 DB → sc 表多一列 (sid, cid)。
				// update 回傳重新查出的 s1，所以下一圈迴圈看到的選課狀態是最新的。
				s1.addCourse(dao.getCourseById(cid));
				s1 = dao.update(s1);
			}
		} else {
			System.out.println("ID NOT EXIST");
		}
	}

	// ===== 自己看過（Stream.anyMatch）=====
	// 判斷某課的學生集合裡，有沒有「sid 等於 s 的」那個學生 → 用來過濾掉「已選過的課」。
	static boolean isExist(Student s, Set<Student> students) {
		return students.stream().anyMatch(student -> student.getSid().equals(s.getSid()));
	}

}
