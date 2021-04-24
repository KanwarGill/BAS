package exception;

@SuppressWarnings("serial")
public class StaffNotAuthorizedException extends Exception {

	public StaffNotAuthorizedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public StaffNotAuthorizedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public StaffNotAuthorizedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public StaffNotAuthorizedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
