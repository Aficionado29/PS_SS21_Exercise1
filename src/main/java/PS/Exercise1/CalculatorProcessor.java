package PS.Exercise1;

import PS.Exercise1.Exceptions.InvalidTopOfStack;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

public class CalculatorProcessor {

    protected static int operationMode = 0;
    protected static Stack<String> dataStack = new Stack<>();
    protected static String commandStream = "";
    protected static String[] dataRegister = new String[26];
    protected static final float epsilon = 0.0001f;
    protected static OutputStream os = System.out;
    protected static Scanner scanner = new Scanner(System.in);

    public CalculatorProcessor(String[] args) {
        try {
            os = new FileOutputStream(args[0]);
        } catch(FileNotFoundException e) {
            System.err.println("Could not create a specified output file");
        } catch(IndexOutOfBoundsException ignored) {
        }
        dataRegister[0] = "(Welcome, the program to calculate the surface area of a regular triangle given in 3D space by points A = (0,-0.47,3.86), B = (0,2,0) and C = (1,4,4) is stored in register X, \nthe program to compute n (5) of these triangles is stored in register Y and the program to calculate the octahedron surface is in register Z. \nTo run the programs load them from the appropriate register and write @ on the command stream to start the execution. \nPlease insert first command/entry:) \" '";

        // program to calculate surface area of regular triangle in 3D space, stored into register X
        dataRegister[23] = "(0 1 - 0 1 - * 2 4 - 2 4 - * + 0 4 - 0 4 - * + _ 0 1 - 0 1 - * 0.47 ~ 4 - 0.47 ~ 4 - * + 3.86 4 - 3.86 4 - * + _ 0 0 - 0 0 - * 0.47 ~ 2 - 0.47 ~ 2 - * + 3.86 0 - 3.86 0 - * + _ + + 2 / 0 1 - 0 1 - * 2 4 - 2 4 - * + 0 4 - 0 4 - * + _ 0 1 - 0 1 - * 0.47 ~ 4 - 0.47 ~ 4 - * + 3.86 4 - 3.86 4 - * + _ 0 0 - 0 0 - * 0.47 ~ 2 - 0.47 ~ 2 - * + 3.86 0 - 3.86 0 - * + _ + + 2 / 0 1 - 0 1 - * 2 4 - 2 4 - * + 0 4 - 0 4 - * + _ - 0 1 - 0 1 - * 2 4 - 2 4 - * + 0 4 - 0 4 - * + _ 0 1 - 0 1 - * 0.47 ~ 4 - 0.47 ~ 4 - * + 3.86 4 - 3.86 4 - * + _ 0 0 - 0 0 - * 0.47 ~ 2 - 0.47 ~ 2 - * + 3.86 0 - 3.86 0 - * + _ + + 2 / 0 1 - 0 1 - * 0.47 ~ 4 - 0.47 ~ 4 - * + 3.86 4 - 3.86 4 - * + _ - 0 1 - 0 1 - * 2 4 - 2 4 - * + 0 4 - 0 4 - * + _ 0 1 - 0 1 - * 0.47 ~ 4 - 0.47 ~ 4 - * + 3.86 4 - 3.86 4 - * + _ 0 0 - 0 0 - * 0.47 ~ 2 - 0.47 ~ 2 - * + 3.86 0 - 3.86 0 - * + _ + + 2 / 0 0 - 0 0 - * 0.47 ~ 2 - 0.47 ~ 2 - * + 3.86 0 - 3.86 0 - * + _ - * * * _)";

        // program to calculate surface area of n (in this case 5) regular triangles in 3D space, stored into register Y
        dataRegister[24] = "(x @ 5 *)";

        // program to calculate surface area of octahedron using two different formulas, stored into register Z
        dataRegister[25] = "((x @ 8 *) @ (2 3 _ 0 1 - 0 1 - * 2 4 - 2 4 - * + 0 4 - 0 4 - * + * *) @)";

        commandStream = dataRegister[0];
        dataRegister[0] = "(Insert next command/entry:) \" '";
    }

    public void start() throws InvalidTopOfStack {
        while(true) {
            try {
                this.run();
            } catch(EmptyStackException e) {
                this.printToOutputStream("Data stack does not have enough entries for the operation you requested");
            }
            this.printToOutputStream("Stack: " + dataStack);
            commandStream = dataRegister[0];
        }
    }

    private void run() throws InvalidTopOfStack {
        char inputCharacter;
        while(commandStream.length() != 0) {
            inputCharacter = commandStream.charAt(0);
            commandStream = commandStream.substring(1);
            if(operationMode == 0) {
                this.executeInputChar(inputCharacter);
            } else if(operationMode == -1) {
                this.constructWholeNum(inputCharacter);
            } else if(operationMode < -1) {
                this.constructDecimalNum(inputCharacter);
            } else {
                this.constructList(inputCharacter);
            }
        }
    }

    private void executeInputChar(char inputCharacter) {
        switch(inputCharacter) {
            case '0': case '1': case '2': case '3': case '4':
            case '5': case '6': case '7': case '8': case '9':
                int number = Character.getNumericValue(inputCharacter);
                float decimalNumber = (float) number;
                dataStack.push(Float.toString(decimalNumber));
                operationMode = -1;
                break;
            case 'a': case 'b': case 'c': case 'd': case 'e': case 'f': case 'g': case 'h': case 'i':
            case 'j': case 'k': case 'l': case 'm': case 'n': case 'o': case 'p': case 'q': case 'r':
            case 's': case 't': case 'u': case 'v': case 'w': case 'x': case 'y': case 'z':
                dataStack.push(dataRegister[this.getRegisterNo(inputCharacter)]);
                break;
            case 'A': case 'B': case 'C': case 'D': case 'E': case 'F': case 'G': case 'H': case 'I':
            case 'J': case 'K': case 'L': case 'M': case 'N': case 'O': case 'P': case 'Q': case 'R':
            case 'S': case 'T': case 'U': case 'V': case 'W': case 'X': case 'Y': case 'Z':
                dataRegister[this.getRegisterNo(inputCharacter)] = dataStack.pop();
                break;
            case '=': case '<': case '>':
                this.comparison(inputCharacter);
                break;
            case '?':
                this.check();
                break;
            case '.':
                dataStack.push("0.0");
                operationMode = -2;
                break;
            case '(':
                dataStack.push("(");
                operationMode = 1;
                break;
            case '+': case '-': case '*': case '/':
                this.arithmetic(inputCharacter);
                break;
            case '&': case '|':
                this.logic(inputCharacter);
                break;
            case '~':
                this.negation();
                break;
            case '%':
                this.rounding();
                break;
            case '_':
                this.squareRoot();
                break;
            case '!':
                this.copy();
                break;
            case '$':
                this.delete();
                break;
            case '@':
                this.applyImmediately();
                break;
            case '\\':
                this.applyLater();
                break;
            case '#':
                float numOfItems = (float) dataStack.size();
                dataStack.push(Float.toString(numOfItems));
            case '"':
                this.printToOutputStream(this.formatOutput(dataStack.pop()));
                break;
            case '\'':
                String input = scanner.nextLine();
                this.processInput(input);
                break;
            default:
        }
    }

    private void constructWholeNum(char inputCharacter) throws InvalidTopOfStack {
        switch(inputCharacter) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                String stackItem = dataStack.pop();
                if(!this.isWholeNumber(stackItem)) {
                    throw new InvalidTopOfStack("Expected Whole number");
                }
                float stackNumber = Float.parseFloat(stackItem) * 10;
                int number = Character.getNumericValue(inputCharacter);
                float decimalNumber = (float) number;
                dataStack.push(Float.toString(stackNumber + decimalNumber));
                break;
            case '.':
                operationMode = -2;
                break;
            default:
                operationMode = 0;
                this.executeInputChar(inputCharacter);
        }
    }

    private void constructDecimalNum(char inputCharacter) throws InvalidTopOfStack {
        switch(inputCharacter) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                String stackItem = dataStack.pop();
                if(!this.isDecimalNumber(stackItem)) {
                    throw new InvalidTopOfStack("Expected Decimal number");
                }
                float stackNumber = Float.parseFloat(stackItem);
                int number = Character.getNumericValue(inputCharacter);
                float decimalNumber = (float) number * (float) Math.pow(10, operationMode+1);
                dataStack.push(Float.toString(stackNumber + decimalNumber));
                operationMode--;
                break;
            case '.':
                dataStack.push("0.0");
                operationMode = -2;
                break;
            default:
                operationMode = 0;
                this.executeInputChar(inputCharacter);
        }
    }

    private void constructList(char inputCharacter) throws InvalidTopOfStack {
        String stackItem = dataStack.pop();
        if(!this.isList(stackItem)) {
            throw new InvalidTopOfStack("Expected List");
        }
        switch(inputCharacter) {
            case '(':
                stackItem = stackItem + "(";
                dataStack.push(stackItem);
                operationMode++;
                break;
            case ')':
                stackItem = stackItem + ")";
                dataStack.push(stackItem);
                operationMode--;
                break;
            default:
                stackItem = stackItem + inputCharacter;
                dataStack.push(stackItem);
        }
    }

    private void comparison(char inputCharacter) {
        String stackItemB = dataStack.pop();
        String stackItemA = dataStack.pop();
        switch(inputCharacter) {
            case '=':
                if(this.isList(stackItemA) && this.isList(stackItemB)) {
                    int comparisonRes = stackItemA.compareTo(stackItemB);
                    if(comparisonRes == 0) {
                        dataStack.push("1.0");
                    } else {
                        dataStack.push("0.0");
                    }
                } else if(this.isList(stackItemA) || this.isList(stackItemB)) {
                    dataStack.push("0.0");
                } else {
                    float biggerNum;
                    float numA = Float.parseFloat(stackItemA);
                    float numB = Float.parseFloat(stackItemB);
                    if(numA > numB) {
                        biggerNum = numA;
                        if((numA - numB) > (biggerNum * epsilon)) {
                            dataStack.push("0.0");
                        } else {
                            dataStack.push("1.0");
                        }
                    } else {
                        biggerNum = numB;
                        if((Float.parseFloat(stackItemB) - numA) > (biggerNum * epsilon)) {
                            dataStack.push("0.0");
                        } else {
                            dataStack.push("1.0");
                        }
                    }
                }
                break;
            case '<':
                if(this.isList(stackItemA) && this.isList(stackItemB)) {
                    int comparisonRes = stackItemA.compareTo(stackItemB);
                    if(comparisonRes < 0) {
                        dataStack.push("1.0");
                    } else {
                        dataStack.push("0.0");
                    }
                } else if(this.isList(stackItemA)) {
                    dataStack.push("0.0");
                } else if(this.isList(stackItemB)) {
                    dataStack.push("1.0");
                } else {
                    if(Float.parseFloat(stackItemA) < Float.parseFloat(stackItemB)) {
                        dataStack.push("1.0");
                    } else {
                        dataStack.push("0.0");
                    }
                }
                break;
            case '>':
                if(this.isList(stackItemA) && this.isList(stackItemB)) {
                    int comparisonRes = stackItemA.compareTo(stackItemB);
                    if(comparisonRes > 0) {
                        dataStack.push("1.0");
                    } else {
                        dataStack.push("0.0");
                    }
                } else if(this.isList(stackItemA)) {
                    dataStack.push("1.0");
                } else if(this.isList(stackItemB)) {
                    dataStack.push("0.0");
                } else {
                    if(Float.parseFloat(stackItemA) > Float.parseFloat(stackItemB)) {
                        dataStack.push("1.0");
                    } else {
                        dataStack.push("0.0");
                    }
                }
                break;
            default:
        }
    }

    private void check() {
        String stackItem = dataStack.pop();
        if(stackItem.equals("()")) {
            dataStack.push("1.0");
        } else if(this.isDecimalNumber(stackItem)) {
            float num = Float.parseFloat(stackItem);
            if(num <= epsilon && num >= epsilon*-1) {
                dataStack.push("1.0");
            } else {
                dataStack.push("0.0");
            }
        } else {
            dataStack.push("0.0");
        }
    }

    private void arithmetic(char inputCharacter) {
        String stackItemB = dataStack.pop();
        String stackItemA = dataStack.pop();
        if(this.isList(stackItemB) || this.isList(stackItemA)) {
            dataStack.push("()");
        } else {
            float numB = Float.parseFloat(stackItemB);
            float numA = Float.parseFloat(stackItemA);
            if(inputCharacter == '/' && numB == 0.0) {
                dataStack.push("()");
            } else {
                switch(inputCharacter) {
                    case '+':
                        dataStack.push(Float.toString(numA + numB));
                        break;
                    case '-':
                        dataStack.push(Float.toString(numA - numB));
                        break;
                    case '*':
                        dataStack.push(Float.toString(numA * numB));
                        break;
                    case '/':
                        dataStack.push(Float.toString(numA / numB));
                        break;
                    default:
                }
            }
        }
    }

    private void logic(char inputCharacter) {
        String stackItemB = dataStack.pop();
        String stackItemA = dataStack.pop();
        if(this.isList(stackItemB) || this.isList(stackItemA)) {
            dataStack.push("()");
        } else {
            float numB = Float.parseFloat(stackItemB);
            float numA = Float.parseFloat(stackItemA);
            boolean numBA, numBB;
            numBA = numA <= epsilon && numA >= epsilon * -1;
            numBB = numB <= epsilon && numB >= epsilon * -1;
            switch(inputCharacter) {
                case '&':
                    if(numBA || numBB) {
                        dataStack.push("0.0");
                    } else {
                        dataStack.push("1.0");
                    }
                    break;
                case '|':
                    if(numBA || numBB) {
                        dataStack.push("1.0");
                    } else {
                        dataStack.push("0.0");
                    }
                    break;
                default:
            }
        }
    }

    private void negation() {
        String stackItem = dataStack.pop();
        if(this.isDecimalNumber(stackItem)) {
            float negatedNum = Float.parseFloat(stackItem) * -1;
            dataStack.push(Float.toString(negatedNum));
        } else {
            dataStack.push("()");
        }
    }

    private void rounding() {
        String stackItem = dataStack.pop();
        if(this.isDecimalNumber(stackItem)) {
            float stackNum = Float.parseFloat(stackItem);
            float roundedNum = Math.round(stackNum);
            dataStack.push(stackItem);
            dataStack.push(Float.toString(roundedNum - stackNum));
        } else {
            dataStack.push("()");
        }
    }

    private void squareRoot() {
        String stackItem = dataStack.pop();
        if(this.isDecimalNumber(stackItem)) {
            float stackNumber = Float.parseFloat(stackItem);
            if(stackNumber > 0) {
                dataStack.push(Float.toString((float) Math.sqrt(stackNumber)));
            } else {
                dataStack.push(stackItem);
            }
        } else {
            dataStack.push(stackItem);
        }
    }

    private void copy() {
        String stackItem = dataStack.pop();
        if(this.isDecimalNumber(stackItem)) {
            int roundedNum = Math.round(Float.parseFloat(stackItem));
            int stackSize = dataStack.size() + 1;
            if(roundedNum <= stackSize && roundedNum != 1) {
                String copiedItem = dataStack.get(stackSize - roundedNum);
                dataStack.push(copiedItem);
            } else {
                dataStack.push(stackItem);
            }
        } else {
            dataStack.push(stackItem);
        }
    }

    private void delete() {
        String stackItem = dataStack.pop();
        if(this.isDecimalNumber(stackItem)) {
            int roundedNum = Math.round(Float.parseFloat(stackItem));
            int stackSize = dataStack.size();
            if(roundedNum <= stackSize) {
                dataStack.remove(stackSize - roundedNum);
            }
        }
    }

    private void applyImmediately() {
        String stackItem = dataStack.pop();
        if(this.isList(stackItem)) {
            String newCommands = stackItem.substring(1, stackItem.length()-1);
            commandStream = newCommands + commandStream;
        } else {
            dataStack.push(stackItem);
        }
    }

    private void applyLater() {
        String stackItem = dataStack.pop();
        if(this.isList(stackItem)) {
            String newCommands = stackItem.substring(1, stackItem.length()-1);
            commandStream = commandStream + newCommands;
        } else {
            dataStack.push(stackItem);
        }
    }

    private boolean isWholeNumber(String stackItem) {
        return stackItem.endsWith(".0");
    }

    private boolean isDecimalNumber(String stackItem) {
        return !stackItem.startsWith("(");
    }

    private boolean isList(String stackItem) {
        return stackItem.startsWith("(");
    }

    private int getRegisterNo(char registerName) {
        switch(registerName) {
            case 'a': case 'A': return 0;
            case 'b': case 'B': return 1;
            case 'c': case 'C': return 2;
            case 'd': case 'D': return 3;
            case 'e': case 'E': return 4;
            case 'f': case 'F': return 5;
            case 'g': case 'G': return 6;
            case 'h': case 'H': return 7;
            case 'i': case 'I': return 8;
            case 'j': case 'J': return 9;
            case 'k': case 'K': return 10;
            case 'l': case 'L': return 11;
            case 'm': case 'M': return 12;
            case 'n': case 'N': return 13;
            case 'o': case 'O': return 14;
            case 'p': case 'P': return 15;
            case 'q': case 'Q': return 16;
            case 'r': case 'R': return 17;
            case 's': case 'S': return 18;
            case 't': case 'T': return 19;
            case 'u': case 'U': return 20;
            case 'v': case 'V': return 21;
            case 'w': case 'W': return 22;
            case 'x': case 'X': return 23;
            case 'y': case 'Y': return 24;
            case 'z': case 'Z': return 25;
            default: return -1;
        }
    }

    private void printToOutputStream(String printText) {
        try {
            os.write(printText.getBytes());
            os.write("\n".getBytes());
        } catch(IOException e) {
            System.err.println("An error occurred when printing to OutputStream!");
        }
    }

    private String formatOutput(String stackItem) {
        if(this.isList(stackItem)) {
            return stackItem.substring(1, stackItem.length()-1);
        } else {
            return stackItem;
        }
    }

    private void processInput(String input) {
        try {
            int n = Integer.parseInt(input);
            dataStack.push((Float.toString((float) n)));
            return;
        } catch(NumberFormatException ignored) {}
        try {
            Float.parseFloat(input);
            dataStack.push(input);
            return;
        } catch(NumberFormatException ignored) {}
        if(input.startsWith("(") && input.endsWith(")") && StringUtils.countMatches(input, "(") == StringUtils.countMatches(input, ")")) {
            dataStack.push(input);
        } else if("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ=<>?+-*/&|~%_!$@\\@\"".contains(input)) {
            commandStream = input;
        } else {
            dataStack.push("()");
        }
    }

}
