package com.spencerbarton.acousticbarcodes.decoder;

import com.musicg.wave.Wave;

import java.io.Console;
import java.io.File;
import java.util.Arrays;

/**
 * Contains acoustic barcode algorithm
 *
 * Created by Spencer on 3/22/2015.
 */
public class AcousticBarcodeDecoder {

    private static final String TAG = "AcousticBarcodeDecoder";

    // Consts
    private static final int ENCODING_UNIT_LEN_ONE = 1;
    private static final int ENCODING_UNIT_LEN_ZERO = 2;

    // Params
    private int mCodeLen;
    private int[] mStartBits;
    private int[] mStopBits;

    // Components
    private final Transform mTransform;
    private final TransientDetector mTransientDetector;
    
    public AcousticBarcodeDecoder(int codeLen, int[] startBits, int[] stopBits) {
        mCodeLen = codeLen;
        mStartBits = startBits;
        mStopBits = stopBits;
        mTransform = new Transform();
        mTransientDetector = new TransientDetector();
    }

    public int[] decode(File file) {
        Wave recording = new Wave(file.getAbsolutePath());

        // Prefilter
        double[] data = mTransform.filter(recording);
        
        Main.drawPlot("Sum Spec", data);

        // Transient Detection
        double[] transientLocs = mTransientDetector.detect(data);

        // Decoding

        // Error Detection

        return mStartBits;
    }

}
