package processor;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class TwoDimensionsSortComparator extends WritableComparator {

	public TwoDimensionsSortComparator() {
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
		int compareDim2 = o1.getDim2().compareTo(o2.getDim2());
		if (compareDim2 != 0) {
			return compareDim2;
		}
		return Integer.compare(o1.getValue(), o2.getValue());
	}
	
}
