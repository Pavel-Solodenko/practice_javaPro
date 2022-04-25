import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Trains {
	private ArrayList<Train> trains = new ArrayList<>();
	
	public void addTrain(Train train) {
		if (train == null) {
			throw new IllegalArgumentException("Null");
		}
		trains.add(train);
	}
	
	@Override
	public String toString() {
		String str = "";
		for (Train a : trains) {
			str += a.toString();
		}
		return str;
	}
	
	public void printTrainsInTimeInterval(String interval) {
		SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
		String[] str_inter = interval.split("-");
		Date from = null;
		Date to = null;
		try {
			from = sdf.parse(str_inter[0]);
			to = sdf.parse(str_inter[1]);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("Trains departure in time interval "+interval+"\n");
		for (Train a : trains) {
			if (from != null && to != null) {
				try {
					if (from.compareTo(sdf.parse(a.getDeparture())) <= 0 && to.compareTo(sdf.parse(a.getDeparture())) >= 0) {
						System.out.print(a);
					}
				}
				catch (Exception e) {
					System.out.println("Problem with time");
				}
			}
			else {
				throw new NullPointerException("Problem with time");
			}
		}
	}
	
	public ArrayList<Train> getTrains() {
		return trains;
	}
}
