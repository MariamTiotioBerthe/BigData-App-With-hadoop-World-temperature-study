package processor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos;
import org.apache.hadoop.hbase.util.Base64;
import org.apache.hadoop.hbase.util.Bytes;

public class GenerateScanString {

	protected static byte[] MEASURES = Bytes.toBytes("measures");
	protected static byte[] DATE = Bytes.toBytes("date");
	protected static byte[] NULL = Bytes.toBytes("");

	
	public static void main(String[] args) throws IOException {
		
		Scan scan = new Scan();
		
		if (args.length >= 1 && args[0] != null && !("".equals(args[0]))) {
			Filter filter = new PrefixFilter(Bytes.toBytes(args[0]));
			scan.setFilter(filter);
		}
		
		if (args.length >= 2) {
			scan.addColumn(DATE, NULL);
			scan.addColumn(MEASURES, Bytes.toBytes(args[1]));
		}
		
		ClientProtos.Scan proto = ProtobufUtil.toScan(scan);
		String outputScan = Base64.encodeBytes(proto.toByteArray());

        File file = new File(System.getProperty("oozie.action.output.properties"));
        Properties props = new Properties();
        props.setProperty("scan", outputScan);

        OutputStream os = new FileOutputStream(file);
        props.store(os, "");
        os.close();

	}

}
