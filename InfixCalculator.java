/*
Name: Shengyi Jia
Netid: sjia6
Lab Time: TR 1650-1805
 */
import java.io.*;
import java.util.*;

public class InfixCalculator<E> {

    public static ArrayList<String> read(String s){
        ArrayList<String> list = new ArrayList<>();
        try{
            File file = new File(s);
            String str = null;
            FileInputStream inputStream = new FileInputStream(s);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while((str = bufferedReader.readLine()) != null)
            {
                list.add(str);
            }
            inputStream.close();
            bufferedReader.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return list;
    }

    public static boolean isOperator(String s){
        switch(s){
            case "+":
            case "-":
            case "*":
            case "/":
            case "(":
            case ")":
            case ">":
            case "<":
            case "=":
            case "&":
            case "|":
            case "!":
            case "sin":
            case "cos":
            case "tan":
            case "^":
                return true;
            default:
                return false;
        }
    }

    public static int precedence(String s){
        switch(s){
            case "(":
                return 0;
            case ")":
                return 1;
            case "!":
                return 2;
            case "^":
                return 3;
            case "*":
            case "/":
            case "%":
                return 4;
            case "+":
            case "-":
                return 5;
            case ">":
            case "<":
                return 6;
            case "=":
                return 7;
            case "&":
                return 8;
            case "|":
                return 9;
            case"sin":
                return 11;
            case "cos":
                return 12;
            case "tan":
                return 13;
            default:
                return 10;
        }
    }

    public static ArrayList<String> split(String s){
        ArrayList<String> target = new ArrayList<>();
        int i = 0, j = i + 1;
        s = s.replace(" ", "");
        while(i < s.length()){
            if(isOperator(s.substring(i, j))){
                if(s.substring(i, j).equals("s") || s.substring(i, j).equals("c") || s.substring(i, j).equals("t")){
                    target.add(s.substring(i, i + 3));
                    i += 3;
                    j = i + 1;
                }else{
                    target.add(s.substring(i, j));
                    i++;
                    j++;
                }
            }else{
                for(j = i + 1; j < s.length(); j++){
                    if(isOperator(s.substring(j, j+1))){
                        break;
                    }
                }
                target.add(s.substring(i, j));
                i = j;
                j++;
            }
        }
        return target;
    }

    public static MyQueue convert(ArrayList<String> list){
        MyStack stack = new MyStack();
        MyQueue queue = new MyQueue();
        for(String value : list){
            if(!isOperator(value)){
                queue.enqueue(value);
            }else if(precedence(value) > 10){
                stack.push(value);
            }else if(precedence(value) < 10 && precedence(value) > 1) {
                if(stack.isEmpty()){
                    stack.push(value);
                }else{
                    while(!stack.isEmpty() && precedence((String)(stack.peek())) <= precedence(value) && precedence((String)(stack.peek())) != 0){
                        queue.enqueue(stack.pop());
                    }
                    stack.push(value);
                }
            }else if(precedence(value) == 0){
                    stack.push(value);
            }else if(precedence(value) == 1){
                while(!stack.isEmpty()){
                    while(precedence((String) stack.peek()) != 0){
                        queue.enqueue(stack.pop());
                    }
                    if(precedence((String) stack.peek()) == 0){
                        stack.pop();
                        if(!stack.isEmpty()){
                            if(precedence((String) stack.peek()) > 10){
                                queue.enqueue(stack.pop());
                            }
                        }
                        break;
                    }
                }
            }
        }
        while (!stack.isEmpty()) {
            queue.enqueue(stack.pop());
        }
        return queue;
    }

    public static ArrayList<Comparable> evaluate(ArrayList<MyQueue> list){
        ArrayList<Comparable> result = new ArrayList<>();
        for(MyQueue element : list){
            MyStack stack = new MyStack();
            while(!element.isEmpty()){
                if(!isOperator((String) element.peek())){
                    stack.push(element.dequeue());
                }else{
                    String s = (String) element.dequeue();
                    switch(s){
                        case "!":
                            if(Double.parseDouble((String) stack.peek()) == 1){
                                stack.pop();
                                stack.push("0");
                                break;
                            }else if(Double.parseDouble((String) stack.peek()) == 0){
                                stack.pop();
                                stack.push("1");
                                break;
                            }else{
                                throw new IllegalArgumentException("Illegal logic operation");
                            }
                        case "*":
                            try{
                                double a = Double.parseDouble((String) stack.pop());
                                double b = Double.parseDouble((String) stack.pop());
                                stack.push(String.valueOf(a * b));
                                break;
                            }catch(Exception e){
                                System.out.println("Illegal * operation ");
                                System.out.println(e);
                            }
                        case "/":
                            try{
                                double a = Double.parseDouble((String) stack.pop());
                                double b = Double.parseDouble((String) stack.pop());
                                stack.push(String.valueOf(b / a));
                                break;
                            }catch(Exception e){
                                System.out.println("Illegal / operation ");
                                System.out.println(e);
                            }
                        case "%":
                            try{
                                double a = Double.parseDouble((String) stack.pop());
                                double b = Double.parseDouble((String) stack.pop());
                                stack.push(String.valueOf(b % a));
                                break;
                            }catch(Exception e){
                                System.out.println("Illegal % operation ");
                                System.out.println(e);
                            }
                        case "+":
                            try{
                                double a = Double.parseDouble((String) stack.pop());
                                double b = Double.parseDouble((String) stack.pop());
                                stack.push(String.valueOf(a + b));
                                break;
                            }catch(Exception e){
                                System.out.println("Illegal + operation ");
                                System.out.println(e);
                            }
                        case "-":
                            try{
                                double a = Double.parseDouble((String) stack.pop());
                                double b = Double.parseDouble((String) stack.pop());
                                stack.push(String.valueOf(b - a));
                                break;
                            }catch(Exception e){
                                System.out.println("Illegal - operation ");
                                System.out.println(e);
                            }
                        case ">":
                            try{
                                Double a = Double.parseDouble((String) stack.pop());
                                Double b = Double.parseDouble((String) stack.pop());
                                if(b.compareTo(a) > 0){
                                    stack.push("1");
                                }else{
                                    stack.push("0");
                                }
                                break;
                            }catch(Exception e){
                                System.out.println("Illegal > operation ");
                                System.out.println(e);
                            }
                        case "<":
                            try{
                                Double a = Double.parseDouble((String) stack.pop());
                                Double b = Double.parseDouble((String) stack.pop());
                                if(b.compareTo(a) < 0){
                                    stack.push("1");
                                }else{
                                    stack.push("0");
                                }
                                break;
                            }catch(Exception e){
                                System.out.println("Illegal < operation ");
                                System.out.println(e);
                            }
                        case "=":
                            try{
                                Double a = Double.parseDouble((String) stack.pop());
                                Double b = Double.parseDouble((String) stack.pop());
                                if(b.compareTo(a) == 0){
                                    stack.push("1");
                                }else{
                                    stack.push("0");
                                }
                                break;
                            }catch(Exception e){
                                System.out.println("Illegal = operation ");
                                System.out.println(e);
                            }
                        case "&":
                            try{
                                double a = Double.parseDouble((String) stack.pop());
                                double b = Double.parseDouble((String) stack.pop());
                                if(a == 1 && b == 1){
                                    stack.push("1");
                                }else{
                                    stack.push("0");
                                }
                                break;
                            }catch(Exception e){
                                System.out.println("Illegal & operation ");
                                System.out.println(e);
                            }
                        case "|":
                            try{
                                double a = Double.parseDouble((String) stack.pop());
                                double b = Double.parseDouble((String) stack.pop());
                                if(a == 1 || b == 1){
                                    stack.push("1");
                                }else{
                                    stack.push("0");
                                }
                                break;
                            }catch(Exception e){
                                System.out.println("Illegal | operation ");
                                System.out.println(e);
                            }
                        case "sin":
                            try{
                                double a = Double.parseDouble((String) stack.pop());
                                stack.push(String.valueOf(Math.sin(a)));
                                break;
                            }catch(Exception e){
                                System.out.println("Illegal sin operation ");
                                System.out.println(e);
                            }
                        case "cos":
                            try{
                                double a = Double.parseDouble((String) stack.pop());
                                stack.push(String.valueOf(Math.cos(a)));
                                break;
                            }catch(Exception e){
                                System.out.println("Illegal cos operation ");
                                System.out.println(e);
                            }
                        case "tan":
                            try{
                                double a = Double.parseDouble((String) stack.pop());
                                stack.push(String.valueOf(Math.tan(a)));
                                break;
                            }catch(Exception e){
                                System.out.println("Illegal tan operation ");
                                System.out.println(e);
                            }
                        case "^":
                            try{
                                double a = Double.parseDouble((String) stack.pop());
                                double b = Double.parseDouble((String) stack.pop());
                                stack.push(String.valueOf(Math.pow(b, a)));
                                break;
                            }catch(Exception e){
                                System.out.println("Illegal ^ operation ");
                                System.out.println(e);
                            }
                        default:
                            throw new IllegalArgumentException("Unrecognizable operator");
                    }
                }
            }
            Double d = Double.parseDouble((String) stack.pop());
            result.add(d);
        }
        return result;
    }

    public static void write(ArrayList<Comparable> list, String s){
        try {
            File file = new File(s);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for(Comparable value : list){
                bw.write(String.format("%.2f%n", value));
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        ArrayList<String> list = read(args[0]);
        ArrayList<ArrayList<String>> target = new ArrayList<>();
        ArrayList<MyQueue> post = new ArrayList<>();
        ArrayList<Comparable> result;
        for(String element : list){
            target.add(split(element));
        }
        for(ArrayList element : target){
            post.add(convert(element));
        }
        result = evaluate(post);
        write(result, args[1]);
    }

}
