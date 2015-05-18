package epharma.utils;

public class AppointmentNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppointmentNotFoundException() {
		super();
	}

	public AppointmentNotFoundException(String s) {
		super(s);
	}

	public AppointmentNotFoundException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public AppointmentNotFoundException(Throwable throwable) {
		super(throwable);
	}

}
