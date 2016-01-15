package cascalogSentenceReader;

import java.io.BufferedReader; 
import java.io.DataInputStream; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.nio.charset.Charset;
import org.apache.hadoop.conf.Configuration; 
import org.apache.hadoop.fs.FSDataInputStream; 
import org.apache.hadoop.fs.FileSystem; 
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable; 
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit; 
import org.apache.hadoop.mapred.RecordReader;

public class SentenceRecordReader implements RecordReader<Text, BytesWritable> {

	private FileSplit fileSplit; 
	private Configuration conf; 
	private boolean processed = false; 
	private Path file;
	private FSDataInputStream fsdis = null; 
	private BufferedReader brz;

	public SentenceRecordReader(FileSplit fileSplit, Configuration conf) { 
		this.fileSplit = fileSplit;
		this.conf = conf;
		file = fileSplit.getPath();
		FileSystem fs; 
		try {
			fs = file.getFileSystem(conf);
			fsdis = fs.open(file);
			DataInputStream dis = new DataInputStream(fsdis);
			brz = new BufferedReader(new InputStreamReader(dis,Charset.forName("ISO-8859-1"))); 
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}

	public boolean next(Text key, BytesWritable value) throws IOException { 
		if (!processed) {
			String fileName = file.getName(); 
			SentenceReaderAlgorithm sra = new SentenceReaderAlgorithm(); 
			try {
				String sentence = sra.readSentence(brz);
				byte[] contents = new byte[(int) sentence.length()]; 
				contents = sentence.getBytes();
				key.set(sentence);
			} catch (Exception e) {
			} finally { 
			}
			processed = sra.isNextCharEOF(brz); 
			return true;
		}
		return false; 
	}

	public Text createKey() { 
		return new Text();
	}
	public BytesWritable createValue() { 
		return new BytesWritable();
	}

	public long getPos() throws IOException {
		return processed ? fileSplit.getLength() : 0;
	}

	public float getProgress() throws IOException {
		return processed ? 1.0f : 0.0f;
	}

	public void close() throws IOException { 
		fsdis.close();
	} 
}


