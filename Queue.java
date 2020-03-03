/*
Name: Shengyi Jia
Netid: sjia6
Lab Time: TR 1650-1805
 */
public interface Queue<AnyType> {

    public boolean isEmpty();

    public void enqueue(AnyType x);

    public AnyType dequeue();

    public AnyType peek();

}
