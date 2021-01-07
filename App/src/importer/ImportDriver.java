package importer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class ImportDriver extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
		
		Job job = Job.getInstance();
		
		job.setJarByClass(GhcnYearFileMapper.class);
		job.setJobName("Import");
		
		job.setMapOutputKeyClass(StationDateWritable.class);
		job.setMapOutputValueClass(TypeValueWritable.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		FileInputFormat.addInputPath(job, new Path("/data/by_year"));
		
		job.setMapperClass(GhcnYearFileMapper.class);
		TableMapReduceUtil.initTableReducerJob("ghcn", HBaseReducer.class, job);
		
		job.setNumReduceTasks(10);
		
		job.waitForCompletion(true);
		
		return 0;
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		System.exit(ToolRunner.run(conf, new ImportDriver(), args));
	}

}
