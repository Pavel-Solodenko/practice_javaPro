import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Train {
	private String from;
	private String to;
	private Date date;
	private Date departure;
	private int id;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	private SimpleDateFormat time_sdf = new SimpleDateFormat("kk:mm");
	
	public Train(String from,String to,Date date,Date departure) {
		this.from = from;
		this.to = to;
		this.date = date;
		this.departure = departure;
	}
	
	public Train(String from,String to,String date,String departure,int id) {
		this.setFrom(from);
		this.setTo(to);
		this.setId(id);
		try {
			this.date = sdf.parse(date);
			this.departure = time_sdf.parse(departure);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "id: "+id+"\tfrom: "+from+"\tto: "+to+"\tdate: "+getDate()+"\tdeparture: "+getDeparture()+"\r\n";
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getDate() {
		return sdf.format(date);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDeparture() {
		return time_sdf.format(departure);
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
