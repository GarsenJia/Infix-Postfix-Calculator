public class MyStack<AnyType> implements Stack{

    URLinkedList list = new URLinkedList();

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void push(Object x) {
        list.addFirst(x);
    }

    @Override
    public AnyType pop() {
        if(!isEmpty()){
            return (AnyType) list.pollFirst();
        }
        return null;
    }

    @Override
    public AnyType peek() {
        if(!isEmpty()){
            return (AnyType) list.peekFirst();
        }
        return null;
    }

    public static void main(String[] args){
        MyStack stack = new MyStack();
        System.out.print(stack.isEmpty());
    }
}
