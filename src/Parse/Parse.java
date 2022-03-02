package Parse;
/*
 * Class:       CS 4308 Section n
 * Term:        Fall 2021
 * Name:       <My Anh Huynh>
 * Instructor:   Sharon Perry
 * Project:     Deliverable 2 Parser
 */
import java.io.*;


public class Parse {
    static final int MAX_LEXEME_LENGTH = 100;
    static final int MAX_TOKEN_LENGTH = 100;
    static int charClass;
    static char[] lexeme = new char[MAX_LEXEME_LENGTH];
    static char nextChar;
    static int lexLen;
    static int token;
    static String thisLine;
    static int nextToken;
    static int read;
    static char endChar;
    static char[] newChar = new char[100];
    static String nextLine;
    static FileReader file;
    static FileReader file2;
    static BufferedReader reader;
    static BufferedReader r2;
    /*Character classes*/
    static final int LETTER = 0;
    static final int DIGIT = 1;
    static final int UNKNOWN = 99;
    /*Token codes*/
    static String[] token_dict = new String[MAX_TOKEN_LENGTH];
    static final int INT_LIT = 10;
    static final int IDENT = 11;
    static final int ASSIGN_OP = 20;
    static final int ADD_OP = 21;
    static final int SUB_OP = 22;
    static final int MULT_OP = 23;
    static final int DIV_OP = 24;
    static final int LEFT_PAREN = 25;
    static final int RIGHT_PAREN = 26;
    static final int END_OF_FILE = 98;

}
