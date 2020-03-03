/*
Name: Shengyi Jia
Netid: sjia6
Lab Time: TR 1650-1805
 */
public class MyQueue<AnyType> implements Queue {

    URLinkedList list = new URLinkedList();

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void enqueue(Object x) {
        list.addLast(x);
    }

    //return null if the queue is empty
    @Override
    public AnyType dequeue(){
        if(!isEmpty()){
            return (AnyType) list.pollFirst();
        }
        return null;
    }

    //return null instead of throw an exception when the queue is empty
    @Override
    public AnyType peek() {
        if(!isEmpty()){
            return (AnyType) list.peekFirst();
        }
        return null;
    }

}
