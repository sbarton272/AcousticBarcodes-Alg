package com.spencerbarton.acousticbarcodes.decoder;

/**
 * Created by Spencer on 4/20/2015.
 */
public class TransientDetector {

    private static final int FLT_LEN = 4;
    private static final double FLT_SIGMA = 2;

    private final GaussianFilter mGaussFlt = new GaussianFilter(FLT_LEN, FLT_SIGMA);;

    public double[] detect(double[] data) {

        // Lowpass filter
        double[] fltData = mGaussFlt.filter(data);
        
        Main.drawPlot("Filtered ", fltData);

        // Find transient peaks
        double[] peakLocs = findPeaks(fltData);

        return fltData;
    }

    private double[] findPeaks(double[] data) {
        // TODO
        return new double[0];
    }


}
