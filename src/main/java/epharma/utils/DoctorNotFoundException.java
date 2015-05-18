package epharma.utils;

public class DoctorNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DoctorNotFoundException() {
		super();
	}

	public DoctorNotFoundException(String s) {
		super(s);
	}

	public DoctorNotFoundException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public DoctorNotFoundException(Throwable throwable) {
		super(throwable);
	}

}