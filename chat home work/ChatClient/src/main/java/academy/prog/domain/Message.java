package academy.prog.domain;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class Message {
	@Expose
	private Date date = new Date();
	@Expose
	private UserOnlyLogin from;
	@Expose
	private UserOnlyLogin to;
	@Expose
	private String text;

	public Message(UserOnlyLogin from, UserOnlyLogin to,String text) {
		this.from = from;
		this.to = to;
		this.text = text;
	}

	public Message() {}
	
	@Override
	public String toString() {
		return new StringBuilder().append("[").append(date)
				.append(", From: ").append(from.getLogin()).append(", To: ").append(to.getLogin())
				.append("] ").append(text)
                .toString();
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || this.getClass() != obj.getClass()) return false;
		Message message = (Message) obj;
		return (date != null && (date.compareTo(message.getDate()) == 0)) &&
				(from != null && (from.equals(message.getFrom()))) &&
				(to != null && (to.equals(message.getTo()))) &&
				(text != null && (text.equals(message.getText())));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (date == null ? 0 : date.hashCode());
		result = prime * result + (from == null ? 0 : from.hashCode());
		result = prime * result + (to == null ? 0 : to.hashCode());
		result = prime * result + (text == null ? 0 : text.hashCode());
		return result;
	}



	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTo(UserOnlyLogin to) {
		this.to = to;
	}

	public UserOnlyLogin getTo() {
		return to;
	}

	public UserOnlyLogin getFrom() {
		return from;
	}

	public void setFrom(UserOnlyLogin from) {
		this.from = from;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
