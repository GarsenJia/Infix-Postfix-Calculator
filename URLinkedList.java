/*
Name: Shengyi Jia
Netid: sjia6
Lab Time: TR 1650-1805
 */
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class URLinkedList<E> implements URList {

    int size = 0;
    URNode head;
    URNode tail;

    public URLinkedList() {
        head = new URNode(null,null,null);
        tail = new URNode(null, null, null);
    }

    @Override
    public boolean add(Object o) {
        addLast((E) o);
        return true;
    }

    @Override
    public void add(int index, Object element) {
        if(index > size()||index < 0){
            throw new IndexOutOfBoundsException();
        }else{
            if(index == size()){
                add(element);
            }else if(index == 0){
                addFirst((E) element);
            }else{
                URNode temp = head.next();
                for(int i = 0; i < index; i++){
                    temp = temp.next();
                }
                URNode newNode = new URNode(element, temp.prev(), temp);
                temp.prev().setNext(newNode);
                temp.setPrev(newNode);
                size++;
            }

        }
    }

    @Override
    public boolean addAll(Collection c) {
        for(Object element : c){
            add(element);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        if(index > size()||index < 0){
            throw new IndexOutOfBoundsException();
        }
        int temp = index;
        if(index == size()){
            addAll(c);
        }
        for(Object element : c){
            if(temp == 0){
                addFirst((E) element);
            }else{
                add(temp, element);
            }
            temp++;
        }
        return true;
    }

    @Override
    public void clear() {
        head.setNext(null);
        tail.setPrev(null);
        size = 0;
    }

    @Override
    public boolean contains(Object o) {
        if(head.next() == null){
            return false;
        }
        for(URNode temp = head.next(); temp != tail; temp = temp.next()){
            if(temp.element()==o){
                return true;
            }
        }
        return false;
    }

    @Override
    public Object get(int index) {
        if(index >= size()||index < 0){
            throw new IndexOutOfBoundsException();
        }else{
            URNode temp = head.next();
            for(int i = 0; i < index; i++){
                temp = temp.next();
            }
            return temp.element();
        }
    }

    @Override
    public int indexOf(Object o) {
        if(head.next() == null){
            throw new NoSuchElementException();
        }else{
            int cnt = 0;
            URNode temp = head.next();
            while(true){
                if(temp.element()==o){
                    return cnt;
                }
                if(temp == tail){
                    throw new NoSuchElementException();
                }
                temp = temp.next();
                cnt++;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        if(size == 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index <= size() - 1;
            }

            @Override
            public E next() {
                index++;
                return (E) get(index - 1);
            }
        };
    }

    @Override
    public Object remove(int index) {
        if(index >= size()){
            throw new IndexOutOfBoundsException();
        }else{
            URNode temp = head.next();
            for(int i = 0; i < index; i ++){
                temp = temp.next();
            }
            temp.prev().setNext(temp.next());
            temp.next().setPrev(temp.prev());
            size--;
            return temp.element();
        }
    }

    @Override
    public boolean remove(Object o) {
        if(!contains(o)){
            return false;
        }
        remove(indexOf(o));
        return true;
    }

    @Override
    public Object set(int index, Object element) {
        if(index >= size()||index < 0){
            throw new IndexOutOfBoundsException();
        }else{
            URNode temp = head.next();
            for(int i = 0; i < index; i++){
                temp = temp.next();
            }
            temp.setElement(element);
            return element;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public URList subList(int fromIndex, int toIndex) {
        if(fromIndex < 0 || toIndex >= size() || fromIndex > toIndex){
            throw new IndexOutOfBoundsException();
        }else{
            URLinkedList temp = this;
            URNode from = temp.head.next();
            URNode to = temp.head.next();
            for(int i = 0; i < fromIndex; i++){
                from = from.next();
            }
            for(int i = 0; i < toIndex; i++){
                to = to.next();
            }
            to = to.prev();
            temp.head.setNext(from);
            from.setPrev(temp.head);
            temp.tail.setPrev(to);
            to.setNext(temp.tail);
            return temp;
        }
    }

    @Override
    public Object[] toArray() {
        Object[] temp = new Object[size()];
        for(int i = 0; i < size(); i++){
            temp[i] = get(i);
        }
        return temp;
    }

    @Override
    public boolean removeAll(Collection c) {
        if(!contains(c)){
            return false;
        }
        Iterator it = c.iterator();
        while(it.hasNext()){
            remove(it.next());
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection c) {
        for(Object element : c){
            if(!contains(element)){
                return false;
            }
        }
        return true;
    }

    public void addFirst(E e){
        if(head.next() == null){
            URNode newNode = new URNode(e, head, tail);
            head.setNext(newNode);
            tail.setPrev(newNode);
            size++;
        }else{
            URNode newNode = new URNode(e, head, head.next());
            head.next().setPrev(newNode);
            head.setNext(newNode);
            size++;
        }
    }

    public void addLast(E e){
        if(head.next() == null){
            URNode newNode = new URNode(e, head, tail);
            head.setNext(newNode);
            tail.setPrev(newNode);
            size++;
        }else{
            URNode newNode = new URNode(e, tail.prev(), tail);
            tail.prev().setNext(newNode);
            tail.setPrev(newNode);
            size++;
        }
    }

    public E peekFirst(){
        if(head.next() == null) return null;
        return (E) head.next().element();
    }

    public E peekLast(){
        if(head.next() == null) return null;
        return (E) tail.prev().element();
    }

    public E pollFirst(){
        E temp = peekFirst();
        remove(0);
        return temp;
    }

    public E pollLast(){
        E temp = peekLast();
        remove(size() - 1);
        return temp;
    }

    class URNode<E> {

        private E e;
        private URNode<E> n;
        private URNode<E> p;

        public URNode(E it, URNode<E> inp, URNode<E> inn) { e = it; p = inp; n = inn; }
        public URNode(URNode<E> inp, URNode<E> inn) { p = inp; n = inn; }

        public E element() { return e; }
        public E setElement(E it) { return e = it; }
        public URNode<E> next() { return n; }
        public URNode<E> setNext(URNode<E> nextval) { return n = nextval; }
        public URNode<E> prev() { return p; }
        public URNode<E> setPrev(URNode<E> prevval) { return p = prevval; }
    }

    public static void main(String[] args){
        URLinkedList list = new URLinkedList();
        System.out.println(list.isEmpty());
    }
}
