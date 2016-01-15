package cascalogSentenceReader;

import java.io.IOException;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat; 
import org.apache.hadoop.mapred.FileSplit; 
import org.apache.hadoop.mapred.InputSplit; 
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader; 
import org.apache.hadoop.mapred.Reporter;

public class SentenceInputFormat extends FileInputFormat {

	@Override
	public RecordReader getRecordReader(InputSplit input, JobConf job,
			Reporter reporter) throws IOException { reporter.setStatus(input.toString());
		return new SentenceRecordReader((FileSplit) input, job);
	}

	protected boolean isSplitable(FileSystem fs, Path file) { 
		return false;
	} 
}


