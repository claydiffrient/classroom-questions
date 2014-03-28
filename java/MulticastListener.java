/**
 * MulticastListener.java
 * Copyright (c) 2008 by Dr. Herong Yang, http://www.herongyang.com/
 */
import java.io.*;
import java.net.*;
public class MulticastListener {
   public static void main(String[] args) {
      if (args.length < 2) {
         System.out.println
            ("Usage: java MulticastListener 224.0.0.1 Herong");
         return;
      }
      String ip = args[0];
      String name = args[1];;
      int port = 6789;
      try {
         InetAddress group = InetAddress.getByName(ip);
         MulticastSocket s = new MulticastSocket(port);
         s.joinGroup(group);

         String msg = "Greeting from "+name;
         System.out.println("Sending out: "+msg);
         DatagramPacket data = new DatagramPacket(
            msg.getBytes(), msg.length(), group, port);
         s.send(data);

         byte[] buffer = new byte[10*1024];
         data = new DatagramPacket(buffer, buffer.length);
         while (true) {
            s.receive(data);
            System.out.println("Received: "+
               (new String(buffer, 0, data.getLength())));
         }
      } catch (IOException e) {
         System.out.println(e.toString());
      }
   }
}
