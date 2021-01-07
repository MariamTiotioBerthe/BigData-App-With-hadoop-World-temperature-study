package importer;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;


public class HBaseReducer extends TableReducer<StationDateWritable, TypeValueWritable, NullWritable> {

	protected static int DATE_MASK = 99999999;
	protected DateFormat df = new SimpleDateFormat("yyyyMMdd");
	
	@Override
	protected void reduce(StationDateWritable key, Iterable<TypeValueWritable> values,
			Reducer<StationDateWritable, TypeValueWritable, NullWritable, Mutation>.Context context)
			throws IOException, InterruptedException {
		
		String station =  key.getStation();
		Date date =  key.getDate();
		
		Put put = new Put(Bytes.toBytes(station + "_" + (99999999 - Integer.parseInt(df.format(date)))));
		
		put.addColumn(Bytes.toBytes("date"), Bytes.toBytes(""), Bytes.toBytes(df.format(date)));
		
		for (TypeValueWritable value : values) {
			String measure = value.getType();
			int val = value.getValue();
			put.addColumn(Bytes.toBytes("measures"), Bytes.toBytes(measure), Bytes.toBytes(val));
		}
		
		context.write(NullWritable.get(), put);
				
	}
	
}
