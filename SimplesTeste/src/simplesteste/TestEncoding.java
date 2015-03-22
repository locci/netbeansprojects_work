/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simplesteste;

import org.omg.CORBA.ORB;

import java.io.PrintStream;

/**
 * This source file is like Test88.java but is stored in UTF-16 encoding.
 *
 * @version     1.14, 05/11/10
 * @since       JDK1.2
 */

public interface TestEncoding
{
    String getCommandName();

    public final static boolean unicodeEscape\u20AC = true;
    public final static boolean unicode€  = false;

    void printCommandHelp(PrintStream out, boolean helpType);

    public final static boolean parse1Error = true;
    public final static boolean command2Done = false;

    boolean processCommand(String[] cmd, ORB orb, PrintStream out);
}