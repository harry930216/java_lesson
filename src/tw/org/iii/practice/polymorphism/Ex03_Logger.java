package tw.org.iii.practice.polymorphism;

// 題 3 — 多型 + 參數（進階）
// 目標：方法接收父類參數，搭配陣列統一處理多個不同子類物件。
// 情境：Logger 是父類，FileLogger 和 ConsoleLogger 繼承它，
// App 類別有 logAll(Logger[] loggers) 方法，統一呼叫每個 logger 的 log() 方法。

public class Ex03_Logger {

    public static void main(String[] args) {
    	Logger[] loggers = {new FileLogger(), new ConsoleLogger()};
    	(new App()).logAll(loggers);;
    }
}

abstract class Logger {
	abstract void log();
}

class FileLogger extends Logger {
	void log() {
		System.out.println("檔案輸出");
	};
}

class ConsoleLogger extends Logger {
	void log() {
		System.out.println("主機輸出");
	};
}

class App {
	// 參數是父類陣列，傳進任何 Logger 子類組合都能統一處理
	void logAll(Logger[] loggers){
		for (Logger s : loggers) {
			s.log();
		}
	}
}
