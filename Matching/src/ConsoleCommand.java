import java.util.Arrays;



public interface ConsoleCommand {
	/**
	 * input 을 해석하는 공통 인터페이스.
	 * @param input {@code String} 타입의 입력 문자열
	 * @throws CommandParseException 입력 규칙에 맞지 않는 입력이 들어올 경우 발생
	 */
	void parse(String input) throws CommandParseException;

	/**
	 * 명령을 MovieDB 에 적용하고 결과를 출력하는 인터페이스를 정의한다.
	 * @param db 조작할 DB 인스턴스
	 * @throws Exception 일반 오류
	 */
	void apply(MovieDB db) throws Exception;
}

abstract class AbstractConsoleCommand implements ConsoleCommand {
	/**
	 * 공통 명령 해석 규칙을 담고 있다. {@code input} 을 분해하여 String[] 으로 만들고, 
	 * {@link AbstractConsoleCommand.parseArguments} 로 인자를 전달한다.
	 * 
	 * 만약 어떤 명령이 별도의 해석 규칙이 필요한 경우 이 메소드를 직접 오버라이드하면 된다. 
	 */
	@Override
	public void parse(String input) throws CommandParseException {
		String[] args = input.split(" *% *%? *");
		if (input.isEmpty())
			args = new String[0];
		// TIP: eclipse 에서 parseArguments 위에 커서를 올리고 Ctrl+T 를 누르면 해당 인터페이스를 
		// 실제로 구현하고 있는 클래스들의 목록을 확인할 수 있고, 바로 이동할 수 있다.
		parseArguments(args);
	}

	/**
	 * {@link AbstractConsoleCommand.parse} 메소드에서 분해된 문자열 배열(String[]) 을 이용해 
	 * 인자를 해석하는 추상 메소드. 
	 * 
	 * 자식 클래스들은 parse 메소드가 아니라 이 메소드를 오버라이드하여
	 * 각 명령에 맞는 규칙으로 인자를 해석한다.
	 *   
	 * @param args 규칙에 맞게 분해된 명령 인자
	 * @throws CommandParseException args가 명령의 규약에 맞지 않을 경우
	 */
	protected abstract void parseArguments(String[] args) throws CommandParseException;
}

/*  < (FILENAME)
*/
class ReadCmd extends AbstractConsoleCommand {
	private String genre;
	private String movie;

	public void parseArguments(String[] args) throws CommandParseException {
		if (args.length != 3)
			throw new CommandParseException(
					"DELETE", Arrays.toString(args), "insufficient argument");
		this.genre = args[1];
		this.movie = args[2];
	}

	@Override
	public void apply(MovieDB db) throws Exception {
		db.delete(new MovieDBItem(genre, movie));
	}
}

/*  @ (INDEX NUMBER)
*/
class PrintCmd extends AbstractConsoleCommand {
	private String genre;
	private String movie;

	public void parseArguments(String[] args) throws CommandParseException {
		if (args.length != 3)
			throw new CommandParseException(
					"DELETE", Arrays.toString(args), "insufficient argument");
		this.genre = args[1];
		this.movie = args[2];
	}

	@Override
	public void apply(MovieDB db) throws Exception {
		db.delete(new MovieDBItem(genre, movie));
	}
}

/*  ? (PATTERN)
*/
class PatternCmd extends AbstractConsoleCommand {
	private String genre;
	private String movie;

	public void parseArguments(String[] args) throws CommandParseException {
		if (args.length != 3)
			throw new CommandParseException(
					"DELETE", Arrays.toString(args), "insufficient argument");
		this.genre = args[1];
		this.movie = args[2];
	}

	@Override
	public void apply(MovieDB db) throws Exception {
		db.delete(new MovieDBItem(genre, movie));
	}
}

@SuppressWarnings("serial")
class ConsoleCommandException extends Exception {
	public ConsoleCommandException(String msg) {
		super(msg);
	}

	public ConsoleCommandException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

/******************************************************************************
 * 명령 파싱 과정에서 발견된 오류상황을 서술하기 위한 예외 클래스 
 */
@SuppressWarnings("serial")
class CommandParseException extends ConsoleCommandException {
	private String command;
	private String input;

	public CommandParseException(String cmd, String input, String cause) {
		super(cause, null);
		this.command = cmd;
		this.input = input;
	}

	public String getCommand() {
		return command;
	}

	public String getInput() {
		return input;
	}

}

/******************************************************************************
 * 존재하지 않는 명령을 사용자가 요구하는 경우를 서술하기 위한 예외 클래스 
 */
class CommandNotFoundException extends ConsoleCommandException {
	private String command;

	public CommandNotFoundException(String command) {
		super(String.format("input command: %s", command));
		this.command = command;
	}

	private static final long serialVersionUID = 1L;

	public String getCommand() {
		return command;
	}
}


