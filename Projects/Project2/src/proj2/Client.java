package proj2;

public class Client {
    public static void main(String[] args) {
        Sequence s = new Sequence();
        s.addAfter("A");
        s.addAfter("B");
        s.clear();
        System.out.println(s.size());
        System.out.println(s.isCurrent());

        Sequence t = new Sequence(10);
        t.addAfter("X");
        t.addAfter("Y");
        t.trimToSize();
        System.out.println(t.getCapacity());

    }

}