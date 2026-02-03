package proj2;

public class Client {
    public static void main(String[] args) {
//        Sequence s1 = new Sequence();
//        s1.addAfter("A");
//        s1.addAfter("B");
//        s1.addAfter("C");
//        // s1 = {A, B, >C} (capacity = 10)
//
//        Sequence s2 = new Sequence(4);
//        s2.addBefore("X");
//        s2.addBefore("Y");
//        s2.addBefore("Z");
//
//        // s2 = {>Z, Y, X} (capacity = 4)
//
//        s1.addAll(s2);
//
//        // s1 = {A, B, >C, Z, Y, X}
//        // s2 = {>Z, Y, X}
//
//        s1.addBefore("Q");
//        // s1 = {A, B, >Q, C, Z, Y, X}

        Sequence s = new Sequence();
        s.size();
        s.isEmpty();
        s.isCurrent();
        s.getCurrent();
        s.getCapacity();
        s.start();
    }

}