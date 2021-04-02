package PS.Exercise1;

import PS.Exercise1.Exceptions.InvalidTopOfStack;

import java.util.Stack;

public class CalculatorProcessor {

    protected static int operationMode = 0;
    protected static Stack<String> dataStack = new Stack<String>();
    protected String commandStream = "";
    protected String[] dataRegister = new String[26];

    public CalculatorProcessor() {
        dataRegister[0] = "102.1((10).3).54321";
        commandStream = dataRegister[0];
    }

    public void run() throws InvalidTopOfStack {
        /*String s = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for(int i = 0; i < s.length(); i++) {
            System.out.println(this.getRegisterNo(s.charAt(i)));
        }*/
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
            } else if(operationMode > 0) {
                this.constructList(inputCharacter);
            }
        }
        System.out.println(dataStack.pop());
    }

    private void executeInputChar(char inputCharacter) {
        System.out.println("som v execute input character");
        switch(inputCharacter) {
            case '0': case '1': case '2': case '3': case '4':
            case '5': case '6': case '7': case '8': case '9':
                int number = Character.getNumericValue(inputCharacter);
                float decimalNumber = (float) number;
                dataStack.push(Float.toString(decimalNumber));
                operationMode = -1;
                break;
            case '.':
                dataStack.push("0.0");
                operationMode = -2;
                break;
            case '(':
                dataStack.push("(");
                operationMode = 1;
                break;
            default: return;
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
                    throw new InvalidTopOfStack("");
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
        String stackItem = dataStack.pop();
        if(!this.isDecimalNumber(stackItem)) {
            throw new InvalidTopOfStack("");
        }
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
            throw new InvalidTopOfStack("");
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

}
