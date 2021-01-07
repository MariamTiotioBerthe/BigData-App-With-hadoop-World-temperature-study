package importer;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableUtils;


public class TypeValueWritable implements Writable {

	protected String type;
	protected int value;
	
	public TypeValueWritable() {
	}
	
	public TypeValueWritable(String type, int value) {
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		type = WritableUtils.readString(in);
		value = in.readInt();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		WritableUtils.writeString(out, type);
		out.writeInt(value);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof TypeValueWritable) {
			return type.equals(((TypeValueWritable) o).getType()) && value == (((TypeValueWritable) o).getValue());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return type.hashCode() * 31 + value;
	}
	
}
