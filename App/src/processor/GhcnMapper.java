package processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class GhcnMapper extends TableMapper<TwoDimensionsWritable, IntWritable> {

	protected DateFormat df = new SimpleDateFormat("yyyyMMdd");
	
	protected static byte[] DATE = Bytes.toBytes("date");
	protected static byte[] MEASURES = Bytes.toBytes("measures");
	protected static byte[] NULL = Bytes.toBytes("");
		
	public enum Dimension {
		STATION, MEASURE, WEEK, MONTH, YEAR, LONGITUDE, LATITUDE, VOID,
	}

	protected Dimension dim1;
	protected Dimension dim2;
	
	protected TwoDimensionsWritable twoDimensionsWritable = new TwoDimensionsWritable();
	protected IntWritable intWritable = new IntWritable();
	
	protected Map<String, float[]> stations = new HashMap<String, float[]>();
	
	@Override
	protected void setup(
			Mapper<ImmutableBytesWritable, Result, TwoDimensionsWritable, IntWritable>.Context context)
			throws IOException, InterruptedException {
		
		super.setup(context);
		
		dim1 = Dimension.valueOf(context.getConfiguration().get("dim1"));
		dim2 = Dimension.valueOf(context.getConfiguration().get("dim2"));
		
		FileSystem fs = FileSystem.get(context.getConfiguration());
		BufferedReader br = new BufferedReader(new InputStreamReader(
				fs.open(new Path("/data/stations.txt"))));
		String line;
		while ((line = br.readLine()) != null) {
			if (line.length() < 30) {
				continue;
			}
			float[] coord = { Float.parseFloat(line.substring(12, 20)), Float.parseFloat(line.substring(21, 30)) };
			stations.put(line.substring(0, 11), coord);
			
			context.getCounter("stations", "loaded").increment(1);
		}
		
		for (Entry<String, float[]> station : stations.entrySet()) {
			System.out.println(station.getKey() + " => " + station.getValue()[0] + "-" + station.getValue()[1]);
		}
		
	}
	
	@Override
	protected void map(
			ImmutableBytesWritable key,
			Result value,
			Mapper<ImmutableBytesWritable, Result, TwoDimensionsWritable, IntWritable>.Context context)
			throws IOException, InterruptedException {
		
		String station = Bytes.toStringBinary(key.get(), 0, 11);
		
		
		try {
			Date date = df.parse(Bytes.toString(value.getValue(DATE, NULL)));
		
			for (Cell cell : value.listCells()) {
				if (CellUtil.matchingFamily(cell, DATE)) {
					continue;
				}
				
				String measure = Bytes.toString(CellUtil.cloneQualifier(cell));
				int mValue = Bytes.toInt(CellUtil.cloneValue(cell));		
				
				String dim1value = getDimension(dim1, station, measure, date);
				String dim2value = getDimension(dim2, station, measure, date);
				
				twoDimensionsWritable.setDim1(dim1value);
				twoDimensionsWritable.setDim2(dim2value);
				twoDimensionsWritable.setValue(mValue);
				
				intWritable.set(mValue);
				
				context.write(twoDimensionsWritable, intWritable);
			}
		} catch (ParseException e) {
			context.getCounter("monapp", "date invalide");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected String getDimension(Dimension dimension, String station,
			String measure, Date date) {
		DateFormat df;
		switch (dimension) {
		case STATION:
			return station;
		case MEASURE:
			return measure;
		case LATITUDE:
			if(!stations.containsKey(station)) return "";
			return "" + Math.round(stations.get(station)[0] / 5) * 5;
		case LONGITUDE:
			if(!stations.containsKey(station)) return "";
			return "" + Math.round(stations.get(station)[1] / 5) * 5;
		case MONTH:
			df = new SimpleDateFormat("Y-M");
			return df.format(date);
		case WEEK:
			df = new SimpleDateFormat("Y-w");
			return df.format(date);
		case YEAR:
			df = new SimpleDateFormat("Y");
			return df.format(date);
		case VOID:
		default:
		}
		return "";
	}
	
}
