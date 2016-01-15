package cascalogSentenceReader;

import java.io.IOException;
import org.apache.hadoop.io.BytesWritable; 
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector; 
import org.apache.hadoop.mapred.RecordReader;
import cascading.flow.FlowProcess; 
import cascading.scheme.Scheme; 
import cascading.scheme.SinkCall; 
import cascading.scheme.SourceCall; 
import cascading.tap.Tap;
import cascading.tuple.Fields; 
import cascading.tuple.Tuple;


public class SentenceFileCascading 
	extends
	Scheme<JobConf, RecordReader, OutputCollector, Object[], Object[]> {

public SentenceFileCascading(Fields fields) { 
	super(fields);
}

@Override
public void sink(FlowProcess<JobConf> arg0,
								SinkCall<Object[], OutputCollector> arg1) 
	throws IOException { 
		throw new UnsupportedOperationException("Not supported yet.");
}


@Override
public void sinkConfInit(FlowProcess<JobConf> arg0, 
	Tap<JobConf,RecordReader,OutputCollector> arg1, JobConf arg2) { 
	throw new UnsupportedOperationException("Not supported yet.");
}

@Override
public void sourcePrepare(FlowProcess<JobConf> flowProcess,
SourceCall<Object[], RecordReader> sourceCall) { 
	sourceCall.setContext(new Object[2]);
	sourceCall.getContext()[0] = sourceCall.getInput().createKey(); 
	sourceCall.getContext()[1] = sourceCall.getInput().createValue();
}

@Override
public boolean source(FlowProcess<JobConf> arg0,
	SourceCall<Object[], RecordReader> sourceCall) throws 
		IOException {

	Text key = (Text) sourceCall.getContext()[0];

	BytesWritable value = (BytesWritable) sourceCall.getContext()[1];
	boolean result = sourceCall.getInput().next(key, value);

	if (!result)
		return false;

	sourceCall.getIncomingEntry()
					.setTuple(new Tuple(key.toString(), value));
	return true;
}

@Override
public void sourceConfInit(FlowProcess<JobConf> arg0,
	Tap<JobConf,RecordReader,OutputCollector> arg1, JobConf conf) { 
		conf.setInputFormat(SentenceInputFormat.class);
	} 
}


