package cascalogSentenceReader;
import static java.lang.Character.isLowerCase;
import java.io.BufferedReader; 
import java.io.IOException;

public class SentenceReaderAlgorithm {

	private boolean isNextCharTerminatorFollowedBySpaceAndNBS(BufferedReader br, char d) throws IOException {
		br.mark(3);
		char a = ((char) br.read());
		if (a == d) {
			char b = ((char) br.read());
			if (b == '\u0020') {// regular space
				int cint = br.read();
				char c = ((char) cint);
				if (c == '\u00A0') { // non-breaking space
					return true; 
				} else {
					br.reset();
					return false; 
				}
			} else { 
				br.reset();
				return false; }
		} else { 
			br.reset();
			return false; 
		}
	}

private boolean isNextCharTerminatorFollowedBySpace(BufferedReader br, char c)
	throws IOException {
	br.mark(2);
	char a = ((char) br.read());
	if (a == c) {
		char b = ((char) br.read());
		if (b == '\u0020') {
			return true; 
		} else {
			br.reset();
			return false; 
		}
	} else { 
		br.reset();
		return false; 
	}
}

private boolean isNextCharTerminator(BufferedReader br, char c) throws IOException {
	return isNextCharTerminatorFollowedBySpaceAndNBS(br, c) || 
	isNextCharTerminatorFollowedBySpace(br, c);
}

public boolean isNextCharEOF(BufferedReader br) throws IOException { 
	// flag the reset point
	br.mark(1);
	if (br.read() == -1) {
		br.reset();
		return true; 
	} else {
		br.reset();
		return false; 
	}
}

private boolean isNextCharLowercase(BufferedReader br) throws IOException {
	br.mark(1);
	if (isLowerCase( ((char) br.read()))) {
		br.reset();
		return true; 
	} else { 
		br.reset();
		return false; 
	}
}

private boolean isNextTwoCharsLineFeedNewlineWithNoTrailingLowerCase( BufferedReader br) 
	throws IOException {

	br.mark(3);
	if (((char) br.read()) == '\r')
		if (((char) br.read()) == '\n') 
			if (!isNextCharLowercase(br))
				return true; 
			else {
				br.reset();
				return false; 
			} 
		else { 
			br.reset();
			return false; 
		} 
	else { 
		br.reset();
		return false; 
	}
}

private boolean isNextCharNewlineWithNoTrailingLowerCase(BufferedReader br) throws IOException {
	br.mark(2);
	if (((char) br.read()) == '\n')
		if (!isNextCharLowercase(br)) 
			return true;
		else { 
			br.reset();
			return false; 
		}
	else { 
		br.reset();
		return false; 
	}
}

private boolean isNextTwoCharsSpaceNBS(BufferedReader br) throws IOException {
	br.mark(2);
	if (((char) br.read()) == '\u0020')
		if (((char) br.read()) == '\u00A0') 
			return true;
		else { 
			br.reset();
			return false; 
		}
	else { 
		br.reset();
		return false; 
	}
}

private boolean isNextTwoCharsLineFeedNewline(BufferedReader br) throws IOException {
	br.mark(2);
	if (((char) br.read()) == '\r')
		if (((char) br.read()) == '\n') 
			return true;
		else { 
			br.reset();
			return false; 
		}
	else { 
		br.reset();
		return false; 
	}
}

public String readSentence(BufferedReader br) throws IOException { 
	String result = "";
	while (true) {

		// Handle termination condition
		if (isNextCharTerminator(br, '.') ) {
			result += ".";
			break; 
		}

		if (isNextCharTerminator(br, '?') ) { 
			result += "?";
			break; 
		}

		if (isNextCharEOF(br)
		//|| isNextCharRealFullStop(br)
			|| isNextTwoCharsLineFeedNewlineWithNoTrailingLowerCase(br)
			|| isNextCharNewlineWithNoTrailingLowerCase(br)
		) 
			break;

		if (!isNextTwoCharsSpaceNBS(br) ) 
			result += (char) br.read();
		//else
		// br.read();
		if (isNextTwoCharsLineFeedNewline(br)) 
			result += " ";
		}

		//trim whitespace
		result = result.replaceAll("\u00A0", "");
		result = result.replaceAll("\u00C2", "");
		result = result.replaceAll(""+Character.toChars(239)[0],""); 
		result = result.replaceAll(""+Character.toChars(187)[0],""); 
		result = result.replaceAll(""+Character.toChars(191)[0],"");
		//put m-dashes back in
		result = result.replaceAll(""+Character.toChars(151)[0]," - ");
		return result; 
	}
}


