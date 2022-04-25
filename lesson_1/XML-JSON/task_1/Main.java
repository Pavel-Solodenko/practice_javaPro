import java.io.File;

public class Main {
	public static void main(String args[]) {
		String time_interval = "15:00-19:00";
		String path = "C:\\eclipse_present_projects\\pro-lesson_1-XML-trains\\timetable.txt";
		Trains trains = TrainsWorkerXml.getTrainsFromXML(new File(path));
		System.out.println(trains);
		trains.printTrainsInTimeInterval(time_interval);
		Train train1 = new Train("Mykolaiv","Rivne","19.12.2013","13:00",3);
		Train train2 = new Train("Uzhorod","Dnipro","21.04.2022","19:00",4);
		Train train3 = new Train("Poltava","Kharkiv","08.03.2022","15:00",5);
		Train train4 = new Train("Cherkassi","Vinnitsa","25.04.2022","17:35",6);
		trains.addTrain(train1);
		trains.addTrain(train2);
		trains.addTrain(train3);
		trains.addTrain(train4);
		TrainsWorkerXml.saveToXML(trains, path);
	}
}
