package no.oslomet.cs.algdat.Oblig3;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {                      //  Hentet fra kompendiet

        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel
        // System.out.println("::: " + verdi);
        while (p != null)       // fortsetter til p er ute av treet
        {
            // System.out.println("- " + p);
            q = p;                                 // q er forelder til p
            cmp = comp.compare(verdi,p.verdi);     // bruker komparatoren
            // System.out.println("oo " + cmp);
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<T>(verdi, q);                  // oppretter en ny node

        if (q == null){
            // System.out.println(111);
            rot = p;                  // p blir rotnode
        } 
        else if (cmp < 0){
            // System.out.println(222);
            q.venstre = p;         // venstre barn til q
        } 
        else{
            // System.out.println(333);
            q.høyre = p;                        // høyre barn til q
        } 

        antall++;                                // én verdi mer i treet
        return true;  
    }

    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int antall(T verdi) {
        if (verdi == null) return 0;
        int ant = 0;
        Node<T> node = rot;
        while (node != null){
            int cmp = comp.compare(verdi, node.verdi);
            if (cmp < 0) node = node.venstre;
            else if (cmp > 0) node = node.høyre;
            else{
                ant += 1;
                node = node.høyre;
            } 
        }
        return ant;
    }

    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        Node<T> node = p;
        while (node != null){
            // System.out.println(node.verdi);
            if (node.venstre != null){
                // System.out.println("Left");
                node = node.venstre;
            }
            else if (node.høyre != null){
                // System.out.println("Høyre");
                node = node.høyre;
            }
            else{
                // System.out.println("Break");
                break;
            }
        }
        // System.out.println("Første postorden: " + node.verdi);
        return node;
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {               //  nesten som en iterator
        Node<T> node = p;
        Node<T> prevNode = null;
        if (node.høyre != null){
            prevNode = node.høyre;
        }

        Node<T> firstP = førstePostorden(p);
        // System.out.println("Start: " + p.verdi + " prevNode: " + prevNode + " førstePost: " + firstP);
        while (node != null){
            // System.out.println(node.venstre + "--- " + node.verdi + " ---" + node.høyre);
            if (node == firstP || node == p){
                // System.out.println("Hopp opp med en gang");
                prevNode = node;
                node = node.forelder;
            }
            else{
                if (node.høyre != null && node.høyre == prevNode){
                    return node;
                    // node = node.forelder;
                }
                else if (node.venstre != null && node.venstre != null && node.venstre != prevNode){
                    // System.out.println("Gå venstre");
                    prevNode = node;
                    node = node.venstre;
                }
                else if (node.høyre != null && node.høyre != firstP){
                    // System.out.println("Gå høyre");
                    prevNode = node;
                    node = node.høyre;
                }
                else if (node.høyre == null){
                    return node;
                }
            }
        }
        return null;
    }

    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> node = førstePostorden(rot);
        
        while (node != null){
            System.out.println(node.verdi);
            oppgave.utførOppgave(node.verdi);
            node = nestePostorden(node);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(førstePostorden(rot), oppgave);              //  Må endres
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if (p == null){
            return;
        }
        oppgave.utførOppgave(p.verdi);
        postordenRecursive(nestePostorden(p), oppgave);


    }

    public void testE(Node<T> p) {
        if (p != null){
            System.out.print(p.verdi + " ");
            testE(nestePostorden(p));
        }
        
    }

    public ArrayList<T> serialize() {                                   //  Basert på kode fra forelesing
        ArrayList<T> liste = new ArrayList<T>();
        ArrayDeque<Node<T>> kø = new ArrayDeque<Node<T>>();
        kø.add(rot);
        
        while (!kø.isEmpty()){
            System.out.println("---- " + kø);
            Node<T> current = kø.removeFirst();   //  Tar ut første fra køen
            if (current.venstre != null){
                kø.addLast(current.venstre);      //  Legger til venstrebarn, hvis det finnes
            }
            if (current.høyre != null){
                kø.addLast(current.høyre);        //  Legger til høyrebarn, hvis det finnes
            }
            System.out.println(kø + " :: " + current.verdi);
            liste.add(current.verdi);
        }
        System.out.println("iiii " + liste);
        return liste;
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        SBinTre<K> nyTre = new SBinTre<K>(c);
        for (K k : data) {
            nyTre.leggInn(k);
        }
        return nyTre;
    }

    public static void main(String[] args) {
        System.out.println(234);
        Integer[] a = {4,9,2,3,9,9,19,8,4,9}; 
        for (Integer integer : a) {
            System.out.print(integer + " ");
        }
        System.out.println("");
        SBinTre<Integer> tre = new SBinTre<>(Comparator.naturalOrder()); 
        for (int verdi : a) {tre.leggInn(verdi); } 
        System.out.println(tre.antall());  // Utskrift: 10 
        for (Integer integer : a) {
            System.out.print(integer + " ");
        }
        System.out.println("");
        System.out.println("antall av 9: " + tre.antall(9));
        System.out.println(førstePostorden(tre.rot));
        System.out.println("---------");
        Node<Integer> n = nestePostorden(førstePostorden(tre.rot));
        System.out.println(n);
        n = nestePostorden(n);
        System.out.println(n);
        n = nestePostorden(n);
        System.out.println(n);
        n = nestePostorden(n);
        System.out.println(n);
        n = nestePostorden(n);
        System.out.println(n);
        n = nestePostorden(n);
        System.out.println(n);
        n = nestePostorden(n);
        System.out.println(n);
        n = nestePostorden(n);
        System.out.println(n);
        n = nestePostorden(n);
        System.out.println(n);
        n = nestePostorden(n);
        System.out.println(n);
        System.out.println("--------");
        Node<Integer> nodee = førstePostorden(tre.rot);
        
        while (nodee != null){
            System.out.print(nodee.verdi + " ");
            nodee = nestePostorden(nodee);
        }
        System.out.println("");

        tre.testE(førstePostorden(tre.rot));
        System.out.println("");
        System.out.println(tre.toStringPostOrder());
        SBinTre<Integer> tre3 =
                new SBinTre<>(Comparator.naturalOrder());

        int[] aa = {10, 14, 6, 8, 1, 12, 7, 3, 11, 9, 13, 5, 2, 4};
        for (int verdi : aa) tre3.leggInn(verdi);
        System.out.println("----");
        ArrayList<Integer> data = tre3.serialize();
        System.out.println(tre3.toStringPostOrder());
        SBinTre<Integer> tre2 = SBinTre.deserialize(data, Comparator.naturalOrder());
        System.out.println(tre2.toStringPostOrder());
    }


} // ObligSBinTre
