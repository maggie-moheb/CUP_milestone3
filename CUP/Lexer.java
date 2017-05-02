import java.lang.System;
import java.io.*;
import java_cup.runtime.Symbol;
import java.util.Stack;


class Lexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

	//initialize  variables to be used by class
	Stack<Character> brackets = new Stack<Character>();
	int curlyCounter	= 0;
	int squareCounter	= 0;
	int paranCounter	= 0;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Lexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Lexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Lexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

	//Add code to be executed on initialization of the lexer
	brackets.add('$');
	}

	private boolean yy_eof_done = false;
	private final int curly = 1;
	private final int YYINITIAL = 0;
	private final int square = 2;
	private final int paran = 3;
	private final int yy_state_dtrans[] = {
		0,
		75,
		77,
		79
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NOT_ACCEPT,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NOT_ACCEPT,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NOT_ACCEPT,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NOT_ACCEPT,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NOT_ACCEPT,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NOT_ACCEPT,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NOT_ACCEPT,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"32:8,33:2,24,32:2,26,32:18,33,37,25,32:2,49,46,28,50,51,44,47,41,48,42,31,3" +
"0,29:9,43,40,38,36,39,32:2,35:26,54,27,55,32,35,23,2,17,3,15,6,12,5,20,7,34" +
",4,16,8,14,9,1,34,10,18,11,13,22,19,34,21,34,52,45,53,32:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,128,
"0,1,2,3,1,4,5,6,7:2,8,1:2,9,10,1,11,12,13,14,1:7,15,1,16,1:8,15:2,1:2,15:12" +
",1:6,17,18,19,1,20,21:2,22,23,1,24,25,1,26,27,28,29,30,31,32,33,34,35,36,37" +
",38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62" +
",63,64,65,66,67,68,69,70,71,72,73,74,75,76,15,77,15,78")[0];

	private int yy_nxt[][] = unpackFromString(79,56,
"1,2,126,103,126:2,104,61,126:2,121,105,89,126:2,127,126,114,122,126:3,90,3," +
"4,62,-1,68,72,5,63,6,72,7,126:2,8,9,10,64,11,12,13,14,15,16,17,18,19,20,21," +
"22,23,24,25,26,-1:57,126,123,126:20,-1:6,124:2,-1:3,126,124,-1:21,60:22,-1," +
"60:32,-1:29,5:2,-1:56,29,-1:57,7,-1:58,69,-1:55,69,-1,30,-1:9,31,-1:49,73,-" +
"1:49,33,-1:64,34,-1:56,35,-1:56,36,-1:56,37,-1:8,126:22,-1:6,124:2,-1:3,126" +
",124,-1:21,29:23,-1,29,-1,29:29,-1,60:22,40,60:32,-1,126:7,115,126:3,27,126" +
":10,-1:6,124:2,-1:3,126,124,-1:21,66:23,-1:2,66,70,66:28,-1:36,69,-1:2,32,-" +
"1:17,66:23,-1,40,66,70,66:28,-1,126:9,38,126:12,-1:6,124:2,-1:3,126,124,-1:" +
"22,28,-1:7,28:3,-1,28,-1:2,28,-1:4,28,-1:2,28,-1,28:2,-1:16,28,-1:11,66:23," +
"-1,65,66,70,66:28,-1,126:9,39,126:12,-1:6,124:2,-1:3,126,124,-1:62,41,-1:14" +
",126:5,42,126:16,-1:6,124:2,-1:3,126,124,-1:20,1,2,126,103,126:2,104,61,126" +
":2,121,105,89,126:2,127,126,114,122,126:3,90,3,4,62,-1,68,72,5,63,6,72,7,12" +
"6:2,8,9,10,64,11,12,13,14,15,16,17,18,19,20,21,54,23,55,25,56,-1,126:5,43,1" +
"26:16,-1:6,124:2,-1:3,126,124,-1:20,1,2,126,103,126:2,104,61,126:2,121,105," +
"89,126:2,127,126,114,122,126:3,90,3,4,62,-1,68,72,5,63,6,72,7,126:2,8,9,10," +
"64,11,12,13,14,15,16,17,18,19,20,21,54,23,57,25,58,-1,126:5,44,126:16,-1:6," +
"124:2,-1:3,126,124,-1:20,1,2,126,103,126:2,104,61,126:2,121,105,89,126:2,12" +
"7,126,114,122,126:3,90,3,4,62,-1,68,72,5,63,6,72,7,126:2,8,9,10,64,11,12,13" +
",14,15,16,17,18,19,20,21,59,23,57,25,56,-1,126:2,45,126:19,-1:6,124:2,-1:3," +
"126,124,-1:21,126:10,46,126:11,-1:6,124:2,-1:3,126,124,-1:21,126:3,47,126:1" +
"8,-1:6,124:2,-1:3,126,124,-1:21,126:10,48,126:11,-1:6,124:2,-1:3,126,124,-1" +
":21,126:13,49,126:8,-1:6,124:2,-1:3,126,124,-1:21,126:10,50,126:11,-1:6,124" +
":2,-1:3,126,124,-1:21,126:19,51,126:2,-1:6,124:2,-1:3,126,124,-1:21,126:5,5" +
"2,126:16,-1:6,124:2,-1:3,126,124,-1:21,126:10,53,126:11,-1:6,124:2,-1:3,126" +
",124,-1:21,126:8,67,126:3,94,126:9,-1:6,124:2,-1:3,126,124,-1:21,126,71,126" +
":20,-1:6,124:2,-1:3,126,124,-1:21,126:17,74,126:4,-1:6,124:2,-1:3,126,124,-" +
"1:21,126:17,76,126:4,-1:6,124:2,-1:3,126,124,-1:21,78,126:21,-1:6,124:2,-1:" +
"3,126,124,-1:21,126:13,80,126:8,-1:6,124:2,-1:3,126,124,-1:21,126:17,81,126" +
":4,-1:6,124:2,-1:3,126,124,-1:21,126,82,126:20,-1:6,124:2,-1:3,126,124,-1:2" +
"1,126:9,83,126:12,-1:6,124:2,-1:3,126,124,-1:21,126:9,84,126:12,-1:6,124:2," +
"-1:3,126,124,-1:21,126:2,85,126:19,-1:6,124:2,-1:3,126,124,-1:21,126:2,86,1" +
"26:19,-1:6,124:2,-1:3,126,124,-1:21,126:4,87,126:17,-1:6,124:2,-1:3,126,124" +
",-1:21,126:15,88,126:6,-1:6,124:2,-1:3,126,124,-1:21,126,91,126:6,106,126:1" +
"3,-1:6,124:2,-1:3,126,124,-1:21,126:15,92,126:6,-1:6,124:2,-1:3,126,124,-1:" +
"21,126:20,93,126,-1:6,124:2,-1:3,126,124,-1:21,126:13,95,126:8,-1:6,124:2,-" +
"1:3,126,124,-1:21,126:5,96,126:16,-1:6,124:2,-1:3,126,124,-1:21,126:8,97,12" +
"6:13,-1:6,124:2,-1:3,126,124,-1:21,126:12,98,126:9,-1:6,124:2,-1:3,126,124," +
"-1:21,126:12,99,126:9,-1:6,124:2,-1:3,126,124,-1:21,126:10,100,126:11,-1:6," +
"124:2,-1:3,126,124,-1:21,126,101,126:20,-1:6,124:2,-1:3,126,124,-1:21,126:1" +
"2,102,126:9,-1:6,124:2,-1:3,126,124,-1:21,126:9,107,126:12,-1:6,124:2,-1:3," +
"126,124,-1:21,108,126:21,-1:6,124:2,-1:3,126,124,-1:21,126:10,109,126:11,-1" +
":6,124:2,-1:3,126,124,-1:21,126:9,110,126:12,-1:6,124:2,-1:3,126,124,-1:21," +
"126:6,111,126:15,-1:6,124:2,-1:3,126,124,-1:21,126:3,112,126:18,-1:6,124:2," +
"-1:3,126,124,-1:21,126,113,126:20,-1:6,124:2,-1:3,126,124,-1:21,126:5,116,1" +
"26:16,-1:6,124:2,-1:3,126,124,-1:21,126:10,117,126:7,118,126:3,-1:6,124:2,-" +
"1:3,126,124,-1:21,126:2,119,126:19,-1:6,124:2,-1:3,126,124,-1:21,126:11,120" +
",126:10,-1:6,124:2,-1:3,126,124,-1:21,126:5,125,126:16,-1:6,124:2,-1:3,126," +
"124,-1:20");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

	if(brackets.peek()!='$'){
		return new Symbol(sym.EOF, ("There is some " + brackets.peek() + " that is not closed"));
	}else{
		return new Symbol(sym.EOF, ("Done"));
	}
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -3:
						break;
					case 3:
						{
  return new Symbol(sym.error, "Invalid input: " + yytext() + " in line " + (yyline + 1));
}
					case -4:
						break;
					case 4:
						{
}
					case -5:
						break;
					case 5:
						{
 return new Symbol(sym.INT_LIT, yytext());
}
					case -6:
						break;
					case 6:
						{
  return new Symbol(sym.SLASH, yytext());
}
					case -7:
						break;
					case 7:
						{
}
					case -8:
						break;
					case 8:
						{
  return new Symbol(sym.EQUAL, yytext());
}
					case -9:
						break;
					case 9:
						{
  return new Symbol(sym.EXCLAMATION, yytext());
}
					case -10:
						break;
					case 10:
						{
  return new Symbol(sym.REL_OP, yytext());
}
					case -11:
						break;
					case 11:
						{
  return new Symbol(sym.SEMI_COLON, yytext());
}
					case -12:
						break;
					case 12:
						{
  return new Symbol(sym.COMMA, yytext());
}
					case -13:
						break;
					case 13:
						{
  return new Symbol(sym.DOT, yytext());
}
					case -14:
						break;
					case 14:
						{
  return new Symbol(sym.COLON, yytext());
}
					case -15:
						break;
					case 15:
						{
  return new Symbol(sym.ASTRISK, yytext());
}
					case -16:
						break;
					case 16:
						{
  return new Symbol(sym.BAR, yytext());
}
					case -17:
						break;
					case 17:
						{
  return new Symbol(sym.AMBERSAND, yytext());
}
					case -18:
						break;
					case 18:
						{
  return new Symbol(sym.PLUS, yytext());
}
					case -19:
						break;
					case 19:
						{
  return new Symbol(sym.MINUS, yytext());
}
					case -20:
						break;
					case 20:
						{
  return new Symbol(sym.PERCENT, yytext());
}
					case -21:
						break;
					case 21:
						{
	paranCounter++;
	yybegin(paran);
	brackets.push('(');
  	return new Symbol(sym.OPEN_PARAN, yytext());
}
					case -22:
						break;
					case 22:
						{
	return new Symbol(sym.error, (") has no matching ( in line " + (yyline + 1)));
}
					case -23:
						break;
					case 23:
						{
	curlyCounter++;
	yybegin(curly);
	brackets.push('{');
	return new Symbol(sym.OPEN_CURLY, yytext());
}
					case -24:
						break;
					case 24:
						{
	return new Symbol(sym.error, ("} has no matching { in line " + (yyline + 1)));
}
					case -25:
						break;
					case 25:
						{
	squareCounter++;
	yybegin(square);
	brackets.push('[');
	return new Symbol(sym.OPEN_SQUARE, yytext());
}
					case -26:
						break;
					case 26:
						{
	return new Symbol(sym.error, ("] has no matching [ in line " + (yyline + 1)));
}
					case -27:
						break;
					case 27:
						{
  return new Symbol(sym.IF, yytext());
}
					case -28:
						break;
					case 28:
						{
}
					case -29:
						break;
					case 29:
						{
}
					case -30:
						break;
					case 30:
						{
  return new Symbol(sym.SHIFT_LEFT, yytext());
}
					case -31:
						break;
					case 31:
						{
  return new Symbol(sym.LESS_DASH, yytext());
}
					case -32:
						break;
					case 32:
						{
  return new Symbol(sym.SHIFT_RIGHT, yytext());
}
					case -33:
						break;
					case 33:
						{
  return new Symbol(sym.COLON_EQUAL, yytext());
}
					case -34:
						break;
					case 34:
						{
  return new Symbol(sym.OR_OP, yytext());
}
					case -35:
						break;
					case 35:
						{
  return new Symbol(sym.AND_OP, yytext());
}
					case -36:
						break;
					case 36:
						{
  return new Symbol(sym.INCREMENT, yytext());
}
					case -37:
						break;
					case 37:
						{
  return new Symbol(sym.DECREMENT, yytext());
}
					case -38:
						break;
					case 38:
						{
  return new Symbol(sym.FOR, yytext());
}
					case -39:
						break;
					case 39:
						{
  return new Symbol(sym.VAR, yytext());
}
					case -40:
						break;
					case 40:
						{
 return new Symbol(sym.STRING_LIT, yytext());
}
					case -41:
						break;
					case 41:
						{
  return new Symbol(sym.CDOTS, yytext());
}
					case -42:
						break;
					case 42:
						{
  return new Symbol(sym.CASE, yytext());
}
					case -43:
						break;
					case 43:
						{
  return new Symbol(sym.ELSE, yytext());
}
					case -44:
						break;
					case 44:
						{
  return new Symbol(sym.TYPE, yytext());
}
					case -45:
						break;
					case 45:
						{
  return new Symbol(sym.FUNC, yytext());
}
					case -46:
						break;
					case 46:
						{
  return new Symbol(sym.CONST, yytext());
}
					case -47:
						break;
					case 47:
						{
  return new Symbol(sym.BREAK, yytext());
}
					case -48:
						break;
					case 48:
						{
  return new Symbol(sym.IMPORT, yytext());
}
					case -49:
						break;
					case 49:
						{
  return new Symbol(sym.RETURN, yytext());
}
					case -50:
						break;
					case 50:
						{
  return new Symbol(sym.STRUCT, yytext());
}
					case -51:
						break;
					case 51:
						{
  return new Symbol(sym.SWITCH, yytext());
}
					case -52:
						break;
					case 52:
						{
  return new Symbol(sym.PACKAGE, yytext());
}
					case -53:
						break;
					case 53:
						{
  return new Symbol(sym.DEFAULT, yytext());
}
					case -54:
						break;
					case 54:
						{
	if(paranCounter == 0){
		return new Symbol(sym.error, (") has no matching ( in line " + (yyline + 1)));
	}else{
		return new Symbol(sym.error, ("You have a missing bracket in line " + (yyline + 1)));
	}
}
					case -55:
						break;
					case 55:
						{
	brackets.pop();
	curlyCounter--;
	switch(brackets.peek()){
		case '(': 
				yybegin(paran); break;
		case '[':
				yybegin(square); break;
		case '{':
				yybegin(curly); break;
		default:
				yybegin(YYINITIAL); break;
	}
	return new Symbol(sym.CLOSE_CURLY, yytext());
}
					case -56:
						break;
					case 56:
						{
	if(squareCounter == 0){
		return new Symbol(sym.error, ("] has no matching [ in line " + (yyline + 1)));
	}else{
		return new Symbol(sym.error, ("You have a missing bracket in line " + (yyline + 1)));
	}
}
					case -57:
						break;
					case 57:
						{
	if(curlyCounter == 0){
		return new Symbol(sym.error, ("} has no matching { in line " + (yyline + 1)));
	}else{
		return new Symbol(sym.error, ("You have a missing bracket in line " + (yyline + 1)));
	}
}
					case -58:
						break;
					case 58:
						{
	brackets.pop();
	squareCounter--;
	switch(brackets.peek()){
		case '(': 
				yybegin(paran); break;
		case '[':
			yybegin(square); break;
		case '{':
			yybegin(curly); break;
		default:
			yybegin(YYINITIAL); break;
	}
	return new Symbol(sym.CLOSE_SQUARE, yytext());
}
					case -59:
						break;
					case 59:
						{
	brackets.pop();
	paranCounter--;
	switch(brackets.peek()){
		case '(': 
				yybegin(paran); break;
		case '[':
				yybegin(square); break;
		case '{':
				yybegin(curly); break;
		default:
				yybegin(YYINITIAL); break;
	}
	return new Symbol(sym.CLOSE_PARAN, yytext());
}
					case -60:
						break;
					case 61:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -61:
						break;
					case 62:
						{
  return new Symbol(sym.error, "Invalid input: " + yytext() + " in line " + (yyline + 1));
}
					case -62:
						break;
					case 63:
						{
 return new Symbol(sym.INT_LIT, yytext());
}
					case -63:
						break;
					case 64:
						{
  return new Symbol(sym.REL_OP, yytext());
}
					case -64:
						break;
					case 65:
						{
 return new Symbol(sym.STRING_LIT, yytext());
}
					case -65:
						break;
					case 67:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -66:
						break;
					case 68:
						{
  return new Symbol(sym.error, "Invalid input: " + yytext() + " in line " + (yyline + 1));
}
					case -67:
						break;
					case 69:
						{
  return new Symbol(sym.REL_OP, yytext());
}
					case -68:
						break;
					case 71:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -69:
						break;
					case 72:
						{
  return new Symbol(sym.error, "Invalid input: " + yytext() + " in line " + (yyline + 1));
}
					case -70:
						break;
					case 74:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -71:
						break;
					case 76:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -72:
						break;
					case 78:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -73:
						break;
					case 80:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -74:
						break;
					case 81:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -75:
						break;
					case 82:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -76:
						break;
					case 83:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -77:
						break;
					case 84:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -78:
						break;
					case 85:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -79:
						break;
					case 86:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -80:
						break;
					case 87:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -81:
						break;
					case 88:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -82:
						break;
					case 89:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -83:
						break;
					case 90:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -84:
						break;
					case 91:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -85:
						break;
					case 92:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -86:
						break;
					case 93:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -87:
						break;
					case 94:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -88:
						break;
					case 95:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -89:
						break;
					case 96:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -90:
						break;
					case 97:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -91:
						break;
					case 98:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -92:
						break;
					case 99:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -93:
						break;
					case 100:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -94:
						break;
					case 101:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -95:
						break;
					case 102:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -96:
						break;
					case 103:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -97:
						break;
					case 104:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -98:
						break;
					case 105:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -99:
						break;
					case 106:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -100:
						break;
					case 107:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -101:
						break;
					case 108:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -102:
						break;
					case 109:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -103:
						break;
					case 110:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -104:
						break;
					case 111:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -105:
						break;
					case 112:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -106:
						break;
					case 113:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -107:
						break;
					case 114:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -108:
						break;
					case 115:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -109:
						break;
					case 116:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -110:
						break;
					case 117:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -111:
						break;
					case 118:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -112:
						break;
					case 119:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -113:
						break;
					case 120:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -114:
						break;
					case 121:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -115:
						break;
					case 122:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -116:
						break;
					case 123:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -117:
						break;
					case 124:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -118:
						break;
					case 125:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -119:
						break;
					case 126:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -120:
						break;
					case 127:
						{
  return new Symbol(sym.IDENTIFIER, yytext());
}
					case -121:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
