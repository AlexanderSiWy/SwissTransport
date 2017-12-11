package swiss.transport.mail;

import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import swiss.transport.exception.MailNotSupportedException;

public class Email {

	private String subject;
	private String content;

	public Email(String subject, String content) {
		this.subject = subject;
		this.content = content;
	}

	public void open() throws IOException, URISyntaxException, MailNotSupportedException {
		Desktop desktop;
		if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
			URI mailto = new URI(String.format("mailto:?subject=%s&body=%s", encode(subject), encode(content)));
			desktop.mail(mailto);
		} else {
			throw new MailNotSupportedException();
		}
	}

	private String encode(String text) throws UnsupportedEncodingException {
		return URLEncoder.encode(text, "UTF-8").replace("+", "%20");
	}
}
