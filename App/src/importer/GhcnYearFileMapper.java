package importer;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class GhcnYearFileMapper extends Mapper<LongWritable, Text, StationDateWritable, TypeValueWritable> {
	
	protected DateFormat df = new SimpleDateFormat("yyyyMMdd");
	
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, StationDateWritable, TypeValueWritable>.Context context)
			throws IOException, InterruptedException {
	
		String[] fields = value.toString().split(",");
		
		if (fields.length < 4) {
			return;
		}
		
		try {
			String station = fields[0];
			Date date = df.parse(fields[1]);
			
			String type = fields[2];
			int val = Integer.parseInt(fields[3]);
			
			context.write(new StationDateWritable(station, date), new TypeValueWritable(type, val));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
