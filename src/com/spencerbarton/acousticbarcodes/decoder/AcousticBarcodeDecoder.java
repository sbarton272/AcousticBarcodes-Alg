package com.spencerbarton.acousticbarcodes.decoder;

import com.musicg.wave.Wave;

import java.io.Console;
import java.io.File;
import java.util.Arrays;

/**
 * Contains acoustic barcode algorithm
 * 
 * TODO
 * consts obj
 * different decoders
 * settings control consts
 * change record btn
 * check audio works
 *
 * Created by Spencer on 3/22/2015.
 */
public class AcousticBarcodeDecoder {

    private static final String TAG = "AcousticBarcodeDecoder";

    // Consts
    private static final double ENCODING_UNIT_LEN_ONE = 1;
    private static final double ENCODING_UNIT_LEN_ZERO = 1.8;

    // Components
    private final Transform mTransform;
    private final TransientDetector mTransientDetector;
    private final Decoder mDecoder;
    private final ErrorChecker mErrorChecker;
    
    public AcousticBarcodeDecoder(int codeLen, int[] startBits, int[] stopBits) {
        mTransform = new Transform();
        mTransientDetector = new TransientDetector();
        mDecoder = new Decoder(ENCODING_UNIT_LEN_ONE, ENCODING_UNIT_LEN_ZERO, startBits, stopBits);
        mErrorChecker = new ErrorChecker(codeLen, startBits, stopBits);
    }

    public int[] decode(File file) {
        Wave recording = new Wave(file.getAbsolutePath());

        // Prefilter
        double[] data = mTransform.filter(recording);
        
        Main.drawPlot("Sum Spec", data);

        // Transient Detection
        int[] transientLocs = mTransientDetector.detect(data);
        
        // Decoding
        int[] code = mDecoder.decode(transientLocs);
        
        System.out.println("Decoder " + Arrays.toString(code));

        // Error Detection
        if (mErrorChecker.check(code)) {
        	return null;
        }

        return code;
    }

}
