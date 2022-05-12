package agenda.exceptions;

@SuppressWarnings("serial")
public class SQLProblemException extends RuntimeException{

	public SQLProblemException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SQLProblemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public SQLProblemException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SQLProblemException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SQLProblemException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
