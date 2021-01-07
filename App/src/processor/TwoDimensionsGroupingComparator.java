package processor;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class TwoDimensionsGroupingComparator extends WritableComparator {

	public TwoDimensionsGroupingComparator() {
		super(TwoDimensionsWritable.class, true);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		TwoDimensionsWritable o1 = (TwoDimensionsWritable) a;
		TwoDimensionsWritable o2 = (TwoDimensionsWritable) b;
		
		int compareDim1 = o1.getDim1().compareTo(o2.getDim1());
		if (compareDim1 != 0) {
			return compareDim1;
		}
		return o1.getDim2().compareTo(o2.getDim2());
	}
	
}
