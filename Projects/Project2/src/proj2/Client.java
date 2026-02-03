package proj2;

public class Client {
    public static void main(String[] args) {
//        Sequence s = new Sequence();
//        s.addAfter("A");
//        s.addAfter("B");
//        s.clear();
//        System.out.println(s.size());
//        System.out.println(s.isCurrent());
//
//        Sequence t = new Sequence(10);
//        t.addAfter("X");
//        t.addAfter("Y");
//        t.trimToSize();
//        System.out.println(t.getCapacity());
//
//        System.out.println(t);


        Sequence s = new Sequence();
        s.addAfter("A");
        s.addAfter("B");

        Sequence c = s.clone();
        System.out.println(s);
        System.out.println(c);

        c.addAfter("C");
        c.trimToSize();
        c.clear();
        System.out.println(s);
        System.out.println(c);


        Sequence a = new Sequence();
        a.addAfter("A"); a.addAfter("B");

        Sequence b = new Sequence();
        b.addAfter("X"); b.addAfter("Y");
        a.addAll(b);

        System.out.println(a);
        System.out.println(b);


    }

}