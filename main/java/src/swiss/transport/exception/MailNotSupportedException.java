package swiss.transport.exception;

public class MailNotSupportedException extends Exception {

	private static final long serialVersionUID = -5778687528139086704L;

	public MailNotSupportedException() {
		super("Dieser Computer untestütz mail nicht.");
	}
}
