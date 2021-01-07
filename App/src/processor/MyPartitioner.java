package processor;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class MyPartitioner extends Partitioner<TwoDimensionsWritable, IntWritable> {

	@Override
	public int getPartition(TwoDimensionsWritable key, IntWritable value,
			int numPartitions) {
		
		return ((key.getDim1().hashCode() * 31 + key.getDim2().hashCode()) & Integer.MAX_VALUE) % numPartitions;
		
	}
	
}
