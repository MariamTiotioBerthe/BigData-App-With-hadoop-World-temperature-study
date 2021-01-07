package importer;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;


public class StationDateWritable implements WritableComparable<StationDateWritable> {

	protected String station;
	protected Date date;
	
	protected static String DATE_FORMAT = "yyyyMMdd";
	
	public StationDateWritable() {
	}
	
	public StationDateWritable(String station, Date date) {
		this.station = station;
		this.date = date;
	}
	
	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		station = WritableUtils.readString(in);
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		try {
			date = df.parse(WritableUtils.readString(in));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void write(DataOutput out) throws IOException {
		WritableUtils.writeString(out, station);
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		WritableUtils.writeString(out, df.format(date));
	}

	@Override
	public int compareTo(StationDateWritable o) {
		int compareStation = station.compareTo(o.getStation());
		if (compareStation != 0) {
			return compareStation;
		}
		return date.compareTo(o.getDate());
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof StationDateWritable) {
			return station.equals(((StationDateWritable) o).getStation()) && date.equals(((StationDateWritable) o).getDate());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return station.hashCode() * 31 + date.hashCode();
	}
	
}
