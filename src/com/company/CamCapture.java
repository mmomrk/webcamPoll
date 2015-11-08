package com.company;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;



/**
 * Created by mrk on 10/14/2015.
 */

public class CamCapture
{
    private static String path = "/cv/";
    private static String outFileName = "omg.jpg";
    private String inFileName = "img.jpg";
    private static String cam = "camera_";
    private String samplePic = "utils/Pallet.jpg";

    private Mat imageIn;
    private Mat imageOut;
    private static Mat imageCam;

    static VideoCapture cap;
    static int device = 0;

    public static Size getSize()
    {
        return imageCam.size();
    }

    public CamCapture()
    {
        System.out.println("Inside Capturer constructor. opening image. Device is " + device);

        imageIn = Imgcodecs.imread(path + inFileName);
        imageOut = new Mat(imageIn.rows(), imageIn.cols(), imageIn.type());
        imageCam = new Mat(480, 640, imageIn.type());
        cap = new VideoCapture();
    }

    public static Mat getCamMat()
    {
        System.out.println("Captureitr.getCamMat");
        pollCam();
        return imageCam;
    }

    private static int pollCam()
    {
        System.out.println("Inside Capturer.pollCam");
        cap.open(device);
        cap.grab();
        cap.read(imageCam);
        Imgcodecs.imwrite(path + cam + outFileName, imageCam);
        cap.isOpened();

        if (cap.isOpened() == false)
        {
            System.out.println("cap is closed");
            return -1;
        }

        return 0;
    }

    public void run()
    {
        System.out.println("Inside Capturer.run ");

        pollCam();

    }

    public void finish()
    {
        cap.release();
    }


    public Mat readTestFile()
    {
        return Imgcodecs.imread(path + samplePic);
    }
}


