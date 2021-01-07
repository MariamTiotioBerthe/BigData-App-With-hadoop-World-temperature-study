package processor;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ProcessorDriver extends Configured implements Tool {

	 @Override
	public int run(String[] args) throws Exception {
	
		 Job job = Job.getInstance();
		 
		 job.setJarByClass(GhcnMapper.class);
		 job.setJobName("processor");
		 
		 Scan scan = new Scan();
		 
		 TableMapReduceUtil.initTableMapperJob("ghcn", scan, GhcnMapper.class, TwoDimensionsWritable.class, IntWritable.class, job);
	 
		 job.setReducerClass(AggregatorReducer.class);
		 job.setOutputFormatClass(TextOutputFormat.class);
		 FileOutputFormat.setOutputPath(job, new Path("/data/aggregated_data"));
		 
		 job.setCombinerClass(MyCombiner.class);
		 
		 job.setGroupingComparatorClass(TwoDimensionsGroupingComparator.class);
		 job.setPartitionerClass(MyPartitioner.class);
		 job.setSortComparatorClass(TwoDimensionsSortComparator.class);
		 
	     job.setNumReduceTasks(10);
	     
	     job.waitForCompletion(true);
	     
		 return 0;
	}

	 public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		System.exit(ToolRunner.run(conf, new ProcessorDriver(), args));
	}
	 
}
