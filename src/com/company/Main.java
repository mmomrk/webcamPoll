package com.company;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static sun.misc.IOUtils.readFully;



public class Main
{
    public static int delaySec;
    private static final String outputArgumentL = "-o";
    private static final String outputArgumentH = "-O";
    private static final String every = "every";
    private static final String mL = "m";
    private static final String mH = "M";
    private static final String min = "min";
    private static final String sL = "s";
    private static final String sH = "S";
    private static final String sec = "sec";


    private static boolean everyIsSet = false;

    private static String prefix = "";

    private static int minutes;
    private static int seconds;
    private static int milliseconds;

    static
    {
        try

        {
            System.load("C:/cv/opencv_java300.dll");
        } catch (
                UnsatisfiedLinkError e
                )

        {
            System.err.println("Native code library failed to load.\n" + e);
            System.exit(1);
        }
    }

    private static void showHelp()
    {
        System.out.println("A tool for polling a camera.\nUsage:\nscriptName [-o filename] every [minutes] m [seconds] s" +
                "\n\n-o\tOptional. Set a prefix for filename. Date and time will be automatically added after" +
                "\nevery\tif this is not specified the program will poll a camera every 1 minute. Otherwise time is taken by values of minutes and seconds.\nTry now. GL");
    }


    public static void main(String[] args)
    {

        if (args.length < 2)
        {
            showHelp();
            return;
        }
        int index = 0;
        for (String argument : args)
        {
            if (argument.equals("-h"))
            {
                showHelp();
                return;
            }
            if (argument.equals(outputArgumentH) | argument.equals(outputArgumentL))
            {
                prefix = args[index + 1];
                System.out.println("Recognized command set prefix " + prefix);
            }
            if (argument.equals(every))
            {
                everyIsSet = true;
            }
            if (everyIsSet & (argument.equals(mL) | argument.equals(mH) | argument.equals(min)))
            {
                minutes = Integer.parseInt(args[index - 1]);
            }
            if (everyIsSet & (argument.equals(sL) | argument.equals(sH) | argument.equals(sec)))
            {
                seconds = Integer.parseInt(args[index - 1]);
                minutes += seconds/60;
                seconds = seconds%60;
            }
            index++;
        }
        if (everyIsSet)
        {
            System.out.println("Read delay " + minutes + " minutes and " + seconds + " seconds.");
        } else
        {
            System.out.println("Setting delay to default 1 minute. See syntax if you are not satisfied with this");
            minutes = 1;
            seconds = 0;
        }

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

//        loadLibrary();

        CamCapture cam = new CamCapture();
        Mat image = cam.getCamMat();
        milliseconds = (seconds + minutes*60)*1000;

        String filename;

        while (true)
        {
            filename = prefix.equals("") ? "" : (prefix + "-") + new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date()) + ".png";
            System.out.println("Writing to file " + filename);
            if (!Imgcodecs.imwrite(filename, image))
            {
                System.out.println("ERROR: not written!");
            }
            try
            {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            image = cam.getCamMat();
        }
    }

    private static void loadLibrary()
    {
        try
        {
            InputStream in = null;
            File fileOut = null;
            String osName = System.getProperty("os.name");
            System.out.println(Main.class + osName);

//            int bitness = Integer.parseInt(System.getProperty("sun.arch.data.model"));
//            if (bitness == 32)
//            {

//                in = File.
            in = Main.class.getResourceAsStream("C:\\cv\\opencv_java300.dll");
            fileOut = File.createTempFile("lib", ".dll");
//            }

            OutputStream out = new FileOutputStream(fileOut);


            byte[] inFully;
            //            IOUtils.copy(in, out);
            inFully = readFully(in, 0, true);
            System.out.println(inFully.length + "lenght");
            out.write(inFully);


//            byte[] buffer = new byte[1024]; // Adjust if you want
//            int bytesRead;
//            System.out.println("my read buffer here");
//            while ((bytesRead = in.read(buffer)) != -1)
//            {
//                out.write(buffer, 0, bytesRead);
//            }
            System.out.println("close");
            out.close();
            in.close();
            System.load(fileOut.toString());
        } catch (Exception e)
        {
            throw new RuntimeException("Failed to load opencv native library", e);
        }
    }
}
