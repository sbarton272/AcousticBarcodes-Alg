package com.spencerbarton.acousticbarcodes.decoder;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.musicg.graphic.GraphicRender;
import com.musicg.wave.Wave;
import com.musicg.wave.extension.Spectrogram;
import com.spencerbarton.acousticbarcodes.decoder.AcousticBarcodeDecoder;

public class Main {

	public static void main(String[] args) {
		int[] BITS = { 1, 1 };
		int LEN = 8;
		File wav = new File("data/test.wav");
		AcousticBarcodeDecoder decoder = new AcousticBarcodeDecoder(LEN, BITS,
				BITS);
		System.out.println("Decoded " + Arrays.toString(decoder.decode(wav)));

		Wave recording = new Wave(wav.getAbsolutePath());
		//System.out.println(recording.toString());
		
		System.out.println("Done");
		
	}

	public static void drawPlot(String title, int[] intData) {
		double[] data = new double[intData.length];
		for (int i = 0; i < intData.length; i++) {
			data[i] = (double)intData[i];
		}
		drawPlot(title, data);
	}
	
	public static void drawPlot(String title, double[] data) {
		LineChart chart = new LineChart(title, title, data);
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}
	
	public static void drawSpectrogram(Spectrogram spectrogram) {
		String fileName = "spectrogram.jpg";
        GraphicRender render = new GraphicRender();
        double[][] data = spectrogram.getAbsoluteSpectrogramData();
        render.renderSpectrogramData(data, fileName);
        
        System.out.println("SpecSz: " + data.length + " " + data[0].length);
        
	}

}

class LineChart extends ApplicationFrame {
	private static final long serialVersionUID = 1L;

	public LineChart(String applicationTitle, String chartTitle, double[] data) {
		super(applicationTitle);
		JFreeChart lineChart = ChartFactory.createXYLineChart(
	            chartTitle,      // chart title
	            "X",                      // x axis label
	            "Y",                      // y axis label
	            createDataset(data),                  // data
	            PlotOrientation.VERTICAL,
	            false,                     // include legend
	            false,                     // tooltips
	            false                     // urls
	        );

		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);
	}

	private XYSeriesCollection createDataset(double[] data) {
		XYSeries series = new XYSeries("Data");
		double d;
		for(int i = 0; i < data.length; i++) {
			d = data[i];
			series.add(i, d);
		}
		
		XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
		return dataset;
	}
}