package processor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

public class TwoDimensionsWritable implements WritableComparable<TwoDimensionsWritable> {

	protected String dim1;
	protected String dim2;
	protected int value;
		
	public TwoDimensionsWritable() {
	}
	
	public TwoDimensionsWritable(String dim1, String dim2, int value) {
		this.dim1 = dim1;
		this.dim2 = dim2;
		this.value = value;
	}
	
	public String getDim1() {
		return dim1;
	}

	public void setDim1(String dim1) {
		this.dim1 = dim1;
	}

	public String getDim2() {
		return dim2;
	}

	public void setDim2(String dim2) {
		this.dim2 = dim2;
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		dim1 = WritableUtils.readString(in);
		dim2 = WritableUtils.readString(in);
		
		value = WritableUtils.readVInt(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		WritableUtils.writeString(out, dim1);
		WritableUtils.writeString(out, dim2);
		
		WritableUtils.writeVInt(out, value);
	}

	@Override
	public int compareTo(TwoDimensionsWritable o) {
		int compareDim1 = dim1.compareTo(o.getDim1());
		if (compareDim1 != 0) {
			return compareDim1;
		}
		int compareDim2 = dim2.compareTo(o.getDim2());
		if (compareDim2 != 0) {
			return compareDim2;
		}
		return Integer.compare(value, o.getValue());
	}

	@Override
	public boolean equals(Object o) {
        if (o instanceof TwoDimensionsWritable) {
        	return dim1.equals(((TwoDimensionsWritable) o).getDim1()) && dim2.equals(((TwoDimensionsWritable) o).getDim2()) && value == ((TwoDimensionsWritable) o).getValue();
        }
        return false;
	}
	
	@Override
	public int hashCode() {
		return value * 37 + dim1.hashCode() * 31 + dim2.hashCode();
	}
}
