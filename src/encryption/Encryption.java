/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author Kaila
 */
public class Encryption {

    /**
     * @param args the command line arguments
     * args 0 = encryption
     * args 1 is to encrypt or decrypt == should be the same for both
     * args 2 is file in
     * args 3 is file out
     * args 4 is key
     */
    
    public static void main(String[] args) {
        if(args.length > 4)
        {
            if(args[1].equalsIgnoreCase("ENCRYPT") || args[1].equalsIgnoreCase("DECRYPT"))
            {
                try (DataInputStream dataIn = new DataInputStream(new FileInputStream(args[2]));
                        DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(args[3])))
                {
                    while(dataIn.available() > 0)
                    {
                        int pswdLength = args[4].length();
                        byte []bites = new byte[pswdLength];
                        int whatever = dataIn.read(bites);
                        if(whatever != -1) //if it is -1 then it has reached the end of the file
                        {
                            dataOut.write(encrypt(bites, args[4]));
                        }
                    }
                }
                catch(Exception exc) //could not open input file
                {
                    System.out.println(exc.getMessage() + "\nNot a legitimate file. Could not open.");
                }
            }
            else //args[1] isnt encrypt or decrypt
            {
                System.out.println("If you don't want me to encrypt or decrypt, "
                        + "then what on earth did you call me for?");
            }
        }
        else //less args than required
        {
            System.out.println("You did not provide the parameters I require to work");
        } 
    }

    private static byte[] encrypt(byte[] bites, String key)
    {
        byte []nibbles = key.getBytes();
        int xor;
        byte []all = new byte[nibbles.length];
        for(int i = 0; i < nibbles.length; ++i)
        {
            xor = bites[i] ^ nibbles[i];
            all[i] = (byte) xor; 
        }
        return all;
    }  
}
