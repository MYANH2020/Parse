package Parse;
/*
 * Class:       CS 4308 Section n
 * Term:        Fall 2021
 * Name:       <My Anh Huynh>
 * Instructor:   Sharon Perry
 * Project:     Deliverable 2 Parser
 */

import java.io.*;



public class ParseMain {
    public static void main(String[] args) throws FileNotFoundException
    {

        Parse.token_dict[Parse.INT_LIT] = "INT_LIT\t";
        Parse.token_dict[Parse.IDENT] = "IDENT\t";
        Parse.token_dict[Parse.ASSIGN_OP] = "ASSIGN_OP";
        Parse.token_dict[Parse.ADD_OP] = "ADD_OP\t";
        Parse.token_dict[Parse.SUB_OP] = "SUB_OP\t";
        Parse.token_dict[Parse.MULT_OP] = "MULT_OP\t";
        Parse.token_dict[Parse.DIV_OP] = "DIV_OP\t";
        Parse.token_dict[Parse.LEFT_PAREN] = "LEFT_PAREN";
        Parse.token_dict[Parse.RIGHT_PAREN] = "RIGHT_PAREN";
        Parse.token_dict[Parse.END_OF_FILE] = "END_OF_FILE";

          Reader file = new FileReader("/Users/anhhuynh/Desktop/Test2fix.jl");
       Reader file2 = new FileReader("/Users/anhhuynh/Desktop/Test2fix.jl");
       BufferedReader reader =null;
       BufferedReader r2 = null;
        reader = new BufferedReader(file);
        r2 = new BufferedReader(file2);

        if (file == null){
            System.out.println("ERROR - cannot open statements \n");
        }else{
            try {
                while(reader.ready()){
                    Parse.read = 0;
                    String nextLine = r2.readLine();
                    String notLine = nextLine;
                    for(int i = 0; i<notLine.length() ; i++)
                    {
                        Parse.newChar[i] = notLine.charAt(i);
                        if (i== notLine.length()-1)
                            Parse.endChar = notLine.charAt(i);
                    }
                    System.out.println("*********************************************************************");
                    System.out.println("Parsing the statement: " + nextLine +"\n");
                    getChar();
                    do
                    {
                        lex();
                        Parse.read++;
                    }
                    while(Parse.nextToken != Parse.END_OF_FILE);
                    System.out.println("");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("Next token is: END_OF_FILE     Next lexeme is EOF");
    }



   /* The following looks up the operators and parentheses with in the
   provided "statements.txt file and returns their token value*/

    static int lookup(char ch){

        switch (ch) {
            case '(':
                addChar();
                Parse.nextToken = Parse.LEFT_PAREN;
                break;

            case ')':
                addChar();
                Parse.nextToken = Parse.RIGHT_PAREN;
                break;

            case '+':
                addChar();
                Parse.nextToken = Parse.ADD_OP;
                break;

            case '-':
                addChar();
                Parse.nextToken = Parse.SUB_OP;
                break;

            case '*':
                addChar();
                Parse.nextToken = Parse.MULT_OP;
                break;

            case '/':
                addChar();
                Parse.nextToken = Parse.DIV_OP;
                break;

            case '=':
                addChar();
                System.out.println("Enter <assign>");
                Parse.nextToken = Parse.ASSIGN_OP;
                break;

            default:
                Parse.lexeme[0] = 'E';
                Parse.lexeme[1] = 'O';
                Parse.lexeme[2] = 'F';
                Parse.lexeme[3] = 0;
                Parse.lexLen = 4;
                Parse.nextToken = Parse.END_OF_FILE;
                break;
        }
        return Parse.nextToken;
    }



    /*adds next char to lexeme*/
    static void addChar(){

        if(Parse.lexLen <= (Parse.MAX_LEXEME_LENGTH-2)){
            Parse.lexeme[Parse.lexLen++] = Parse.nextChar;
            Parse.lexeme[Parse.lexLen] = 0;
        }else{
            System.out.println("ERROR - lexeme is too long \n");
        }
    }


   /*The following gets the next char of an input
   and determines its character class*/

    static void getChar(){

        char c = 0;
        try {
            c = (char)Parse.reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parse.nextChar = c;
        if((int)Parse.nextChar != 0){
            if(isalpha(Parse.nextChar)){
                Parse.charClass = Parse.LETTER;
            }else if(isdigit(Parse.nextChar)){
                Parse.charClass = Parse.DIGIT;
            }else{ Parse.charClass = Parse.UNKNOWN;}
        }else{
            Parse.charClass = Parse.END_OF_FILE;
        }
    }


   /* The following calls the getChar function
   until it returns a non-whitespace character */

    static void getNonBlank(){

        while(isspace(Parse.nextChar)){
            getChar();
        }
    }

   /* The following is a lexical analyzer method
      for arithmetic expressions */

    static int lex(){

        Parse.lexLen = 0;
        getNonBlank();
        switch (Parse.charClass){

            /* Determines parse identifiers */
            case Parse.LETTER:
                addChar();
                getChar();
                while(Parse.charClass == Parse.LETTER || Parse.charClass == Parse.DIGIT){
                    addChar();
                    getChar();
                }
                Parse.nextToken = Parse.IDENT;
                break;

            /* Determines parse integer literals */
            case Parse.DIGIT:
                addChar();
                getChar();
                while(Parse.charClass == Parse.DIGIT){
                    addChar();
                    getChar();
                }
                Parse.nextToken = Parse.INT_LIT;
                break;

            /* Determines parentheses and operators */
            case Parse.UNKNOWN:
                lookup(Parse.nextChar);
                getChar();
                break;

            /* Determines the end of the file*/
            case Parse.END_OF_FILE:
                Parse.nextToken = Parse.END_OF_FILE;;
                Parse.lexeme[0] = 'E';
                Parse.lexeme[1] = 'O';
                Parse.lexeme[2] = 'F';
               Parse. lexeme[3] = 0;
                Parse.lexLen = 4;
                break;
        }

        /* The following is the output calls for parser,
         * which in turn, defines when to insert either
         * <assign> <expr> <term> and/or <factor> */

        String s = new String(Parse.lexeme);
        s = s.substring(0,Parse.lexLen);
        char lexLast = s.charAt(s.length()-1);
        if(Parse.nextToken != 98)
            System.out.printf("Next token is: %s\t\t Next lexeme is %s\n", Parse.token_dict[Parse.nextToken], s);
        if(Parse.token_dict[Parse.nextToken].equals("LEFT_PAREN"))
        {
            System.out.println("Enter <expr>");
            System.out.println("Enter <term>");
            System.out.println("Enter <factor>");
        }

        if(Parse.nextToken == 11 && Parse.read !=0 && lexLast != Parse.endChar )
        {
            System.out.println("Enter <expr>");
            System.out.println("Enter <term>");
            System.out.println("Enter <factor>");
        }

        if(Parse.nextToken == 21 && lexLast != Parse.endChar)
        {
            System.out.println("Exit <factor>");
            System.out.println("Exit <term>");
        }

        if(Parse.nextToken == 10 && lexLast != Parse.endChar)
        {
            System.out.println("Enter <term>");
            System.out.println("Enter <factor>");
        }

        if(Parse.nextToken == 26 && lexLast != Parse.endChar)
        {
            System.out.println("Exit <factor>");
            System.out.println("Exit <term>");
            System.out.println("Exit <expr>");
        }

        if(Parse.nextToken == 24 && lexLast != Parse.endChar)
        {
            System.out.println("Exit <factor>");
        }
        if(Parse.nextToken == 98)
        {
            System.out.println("Enter <factor>");
            System.out.println("Exit <factor>");
            System.out.println("Exit <term>");
            System.out.println("Exit <expr>");
            System.out.println("Exit <assign>");
        }
        return Parse.nextToken;
    }

    /* The following returns true if the char is a letter */

    static boolean isalpha(char c){

        int ascii = (int) c;
        if((ascii > 64 && ascii < 91) || (ascii > 96 && ascii < 123)){
            return true;
        }else {return false;}
    }

    /* The following returns true if the char is a digit */

    static boolean isdigit(char c){

        int ascii = (int) c;
        if(ascii > 47 && ascii < 58){
            return true;
        }else {return false;}
    }

    /* The following returns true if the char is a space*/

    static boolean isspace(char c){

        int ascii = (int) c;
        if(ascii == 32){
            return true;
        }else {return false;}
    }
}


