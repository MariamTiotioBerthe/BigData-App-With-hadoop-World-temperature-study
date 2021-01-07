package processor;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MyCombiner extends
		Reducer<TwoDimensionsWritable, IntWritable, TwoDimensionsWritable, IntWritable> {

	public enum Operator {
		SUM, AVG, MIN, MAX, RANGE,
	}
	
	protected Operator op;
	protected IntWritable intWritable = new IntWritable();
	
	protected Text text = new Text();
	
	@Override
	protected void setup(
			Reducer<TwoDimensionsWritable, IntWritable, TwoDimensionsWritable, IntWritable>.Context context)
			throws IOException, InterruptedException {
		op = Operator.valueOf(context.getConfiguration().get("operator"));
	}
	
	@Override
	protected void reduce(
			TwoDimensionsWritable key,
			Iterable<IntWritable> values,
			Reducer<TwoDimensionsWritable, IntWritable, TwoDimensionsWritable, IntWritable>.Context context)
			throws IOException, InterruptedException {
		
		int result = 0; 
		
		switch (op) {
		case SUM:
			System.out.println("------");
			for (IntWritable value : values) {
				System.out.println("Je rajoute : " + value.get() + " => " + result);
				result += value.get();
			}
			break;
		case AVG:
			int totalValue = 0;
			int countValues = 0;
			System.out.println("------");
			for (IntWritable value : values) {
				totalValue += value.get();
				System.out.println("Je rajoute : " + value.get() + " => " + totalValue);
				countValues++;
			}
			result = totalValue / countValues;
			break;
		case MIN:
			result = values.iterator().next().get();
			break;
		case MAX:
			for (IntWritable value : values) {
				result = value.get();
			}
			break;
		case RANGE:
			int min = values.iterator().next().get();
			int max = min;
			for (IntWritable value : values) {
				max = value.get();
			}
			result = max - min;
			break;

		default:
		}
		
		intWritable.set(result);
		
		context.write(key, intWritable);
		
	}
	
}