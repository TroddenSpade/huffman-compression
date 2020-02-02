package sample;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Huffman {

    private final static Integer asciI = 256; // ASCI Interval

    public void compress(String from,String to) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(from)));
        char[] Bytes = text.toCharArray();
        Integer[] frequency = new Integer[asciI];
        for (int i = 0; i < asciI; i++) {
            frequency[i] = 0;
        }
        for (int i = 0; i < Bytes.length; i++) {
            frequency[(int)Bytes[i]]++;
        }

        Node root = constructTrie(frequency);   //Create Trie Via Frequency Array.
        Map< Character,String> hm = new HashMap<>();
        makeCode(hm, root, "");       //Assign Code For Every Elements.



        String bin = convertToBinString(hm,text);
        writeFile(to,hm,bin);

    }

    private Node constructTrie(Integer [] frequency) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (char i = 0; i < asciI; i++) {
            if (frequency[i] > 0) {
                pq.offer(new Node(frequency[i], i, null, null));

            }
        }
        while (pq.size() > 1) {
            Node right = pq.remove();
            Node left = pq.remove();
            Node parent = new Node(right.getFreq()+left.getFreq(), null, right, left);
            pq.offer(parent);
        }
        return pq.remove();
    }

    private void makeCode(Map<Character,String> hm , Node root, String temp){
        if(!root.isLeaf()){
            makeCode(hm, root.getLeft(), temp + "0");
            makeCode(hm, root.getRight(), temp + "1");
        }
        else
            hm.put(root.getCh(),temp);
    }

    private void writeFile(String src, Map<Character,String >hm,String bin){
        try {
            RandomAccessFile raf = new RandomAccessFile(src, "rw");
            raf.writeInt(hm.size());


            for (Character c: hm.keySet()) {
                raf.writeChar(c);
            }

            for(Character c: hm.keySet()){
                raf.writeBytes(hm.get(c));
                raf.writeChar('\n');
            }
            int extra = (8 - bin.length()%8)%8;
            raf.writeInt(extra);
//            System.out.println(extra);
            for(int i=0; i<extra; i++){
                bin+='0';
            }
            byte b = 0;
            for(int i=0;i<bin.length();i++){
                b += bin.charAt(i) == '1' ? 1 : 0;
                if((i+1)%8 == 0){
                    raf.writeByte(b);
//                    System.out.println(String.format("%8s", Integer.toBinaryString(b & 0xFF))
//                        .replace(' ', '0'));
                    b = 0;
                }
                b = (byte)(b<<1);

            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private String readFile(String src,Map<Character,String> hm){
        String bin = "";
        try {
            RandomAccessFile raf = new RandomAccessFile(src, "rw");
            int length = raf.readInt();

            char[] chars = new char[length];

            for (int i=0;i<length;i++){
                chars[i] = raf.readChar();
            }

            for(int i=0;i<length;i++){
                String s = raf.readLine();
                hm.put(chars[i],s.substring(0, s.length() - 1));
            }

            int extra = raf.readInt();
            while (true){
                try {
                    byte b = raf.readByte();
                    bin += String.format("%8s", Integer.toBinaryString(b & 0xFF))
                            .replace(' ', '0');
//                    System.out.println(String.format("%8s", Integer.toBinaryString(b & 0xFF))
//                            .replace(' ', '0'));
                }catch (EOFException e){
                    break;
                }
            }

            bin = bin.substring(0,bin.length()-extra);

//            System.out.println("read bin :"+bin);

            return bin;

        }catch (Exception e){
            System.out.println(e);
        }

        return "";
    }

    private String convertToBinString(Map<Character,String >hm,String string){
        String str = "";

        for (int i=0;i<string.length();i++){
            str += hm.get(string.charAt(i));
        }

//        System.out.println("write bin :"+str);

        return str;
    }

    public void extract(String from,String to){
        String text = "";
        Map< Character,String> readHM = new HashMap<>();
        String readBIN = readFile(from,readHM);

        int start = 0;
        for(int i=1;i<readBIN.length()+1;i++){
            for(Character c : readHM.keySet()){
                if(readHM.get(c).equals(readBIN.substring(start,i))){
                    text+= c;
                    start = i;
                }
            }
        }

        try{
            RandomAccessFile raf = new RandomAccessFile(to, "rw");
            raf.writeBytes(text);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

}