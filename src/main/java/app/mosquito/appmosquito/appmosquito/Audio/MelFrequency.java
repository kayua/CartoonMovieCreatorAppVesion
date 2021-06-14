package app.mosquito.appmosquito.appmosquito.Audio;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.util.Arrays;
import static java.lang.Double.max;

public class MelFrequency {

    private final static int numberWindows = 1024;
    private final static int successiveNumberFrames = 256;
    private final static double sampleRate = 8000.0;
    private final static int melResolution = 60;
    private final static double minFrequency = 0.0;
    private final static double maxFrequency = sampleRate/2.0;
    
    ShortTransformed shortTransformed = new ShortTransformed();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public float[][][] processBulkSpectrogram(double[] signalWave, int sizeFrame) {

        int sizeWindows =  successiveNumberFrames * (sizeFrame - 1);
        int numberOfSpectrogram = signalWave.length / sizeWindows;
        final float[][][] spectrogramBuffer = new float[numberOfSpectrogram * 2][][];
        int start = 0;
        int end = sizeWindows;

        for(int i = 0; i < numberOfSpectrogram * 2; i++) {

            spectrogramBuffer[i] = convert(powerToDb(melSpectrogram(Arrays.copyOfRange(signalWave, start, end))));
            start += sizeWindows / 2;
            end += sizeWindows / 2;

        }

        return spectrogramBuffer;
    }

    public float[][] convert(double[][] doubleInput) {

        float[][] floatArray = new float[doubleInput.length][];

        for (int i = 0 ; i < doubleInput.length; i++)
        {

            floatArray[i] = new float[doubleInput[i].length];

            for (int j = 0; j < doubleInput[i].length; j++) {

                floatArray[i][j] = (float) ((doubleInput[i][j] / 80 ) + 1);

            }

        }

        return floatArray;

    }


    private double[][] melSpectrogram(double[] y){

        double[][] melBasis = melFilter();
        double[][] spectrogram = stftMagSpec(y);
        double[][] melS = new double[melBasis.length][spectrogram[0].length];

        for (int i = 0; i < melBasis.length; i++){

            for (int j = 0; j < spectrogram[0].length; j++){

                for (int k = 0; k < melBasis[0].length; k++){

                    melS[i][j] += melBasis[i][k]*spectrogram[k][j];

                }

            }

        }

        return melS;
    }


    private double[][] stftMagSpec(double[] y){

        final double[] fftwin = getWindow();
        double[] ypad = new double[numberWindows +y.length];

        for (int i = 0; i < numberWindows /2; i++){

            ypad[(numberWindows /2)-i-1] = y[i+1];
            ypad[(numberWindows /2)+y.length+i] = y[y.length-2-i];

        }

        for (int j = 0; j < y.length; j++){

            ypad[(numberWindows /2)+j] = y[j];

        }

        final double[][] frame = yFrame(ypad);
        double[][] fftmagSpec = new double[1+ numberWindows /2][frame[0].length];
        double[] fftFrame = new double[numberWindows];

        for (int k = 0; k < frame[0].length; k++){

            for (int l = 0; l < numberWindows; l++){

                fftFrame[l] = fftwin[l]*frame[l][k];

            }

            double[] magSpec = magSpectrogram(fftFrame);

            for (int i = 0; i < 1+ numberWindows /2; i++){

                fftmagSpec[i][k] = magSpec[i];

            }

        }

        return fftmagSpec;
    }

    private double[] magSpectrogram(double[] frame){

        double[] magSpec = new double[frame.length];
        shortTransformed.getTransformed(frame);

        for (int m = 0; m < frame.length; m++) {

            magSpec[m] = shortTransformed.realPhase[m] * shortTransformed.realPhase[m] + shortTransformed.imaginaryPhase[m] * shortTransformed.imaginaryPhase[m];

        }

        return magSpec;
    }


    private double[] getWindow(){

        double[] win = new double[numberWindows];

        for (int i = 0; i < numberWindows; i++){

            win[i] = 0.5 - 0.5 * Math.cos(2.0*Math.PI*i/ numberWindows);

        }

        return win;
    }

    private double[][] yFrame(double[] ypad){

        final int n_frames = 1 + (ypad.length - numberWindows) / successiveNumberFrames;
        double[][] winFrames = new double[numberWindows][n_frames];

        for (int i = 0; i < numberWindows; i++){

            for (int j = 0; j < n_frames; j++){

                winFrames[i][j] = ypad[j* successiveNumberFrames +i];

            }

        }

        return winFrames;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private double[][] powerToDb(double[][] melS){

        double[][] log_spec = new double[melS.length][melS[0].length];
        double maxValue = -100;
        double thisMax = -1000;

        for (int i = 0; i < melS.length; i++) {

            for (int j = 0; j < melS[0].length; j++) {

                thisMax = max(thisMax, melS[i][j]);

            }

        }

        for (int i = 0; i < melS.length; i++){

            for (int j = 0; j < melS[0].length; j++){

                double magnitude = Math.abs(melS[i][j]);

                if (magnitude > 1e-10){

                    log_spec[i][j]=10.0*log10(magnitude);
                    log_spec[i][j] = log_spec[i][j] -10.0 * log10(max(magnitude, thisMax));

                }else{

                    log_spec[i][j]=10.0*(-10);

                }

                if (log_spec[i][j] > maxValue){

                    maxValue = log_spec[i][j];

                }
            }
        }

        for (int i = 0; i < melS.length; i++){

            for (int j = 0; j < melS[0].length; j++){

                if (log_spec[i][j] < maxValue - 80.0){

                    log_spec[i][j] = maxValue - 80.0;

                }

            }
        }

        return log_spec;
    }


    private double[][] melFilter(){

        final double[] fftFreqs = fftFreq();
        final double[] melF = melFreq(melResolution +2);
        double[] fdiff = new double[melF.length-1];

        for (int i = 0; i < melF.length-1; i++){

            fdiff[i] = melF[i+1]-melF[i];

        }

        double[][] ramps = new double[melF.length][fftFreqs.length];

        for (int i = 0; i < melF.length; i++){

            for (int j = 0; j < fftFreqs.length; j++){

                ramps[i][j] = melF[i]-fftFreqs[j];

            }
        }

        double[][] weights = new double[melResolution][1+ numberWindows /2];

        for (int i = 0; i < melResolution; i++){

            for (int j = 0; j < fftFreqs.length; j++){

                double lowerF = -ramps[i][j] / fdiff[i];
                double upperF = ramps[i+2][j] / fdiff[i+1];

                if (lowerF > upperF && upperF>0){

                    weights[i][j] = upperF;

                }else if (lowerF > upperF && upperF<0){

                    weights[i][j] = 0;

                }else if (lowerF < upperF && lowerF>0){

                    weights[i][j] =lowerF;

                }else if (lowerF < upperF && lowerF<0){

                    weights[i][j] = 0;

                }else {}
            }
        }

        double enorm[] = new double[melResolution];

        for (int i = 0; i < melResolution; i++){

            enorm[i] = 2.0 / (melF[i+2]-melF[i]);

            for (int j = 0; j < fftFreqs.length; j++){

                weights[i][j] *= enorm[i];

            }

        }

        return weights;

        }

    private double[] fftFreq() {

        double[] freqs = new double[1+ numberWindows /2];

        for (int i = 0; i < 1+ numberWindows /2; i++){

            freqs[i] = 0 + (sampleRate/2)/(numberWindows /2) * i;

        }

        return freqs;
    }


    private double[] melFreq(int numMels) {

        double[] LowFFreq = new double[1];
        double[] HighFFreq = new double[1];
        LowFFreq[0] = minFrequency;
        HighFFreq[0] = maxFrequency;
        final double[] melFLow    = freqToMel(LowFFreq);
        final double[] melFHigh   = freqToMel(HighFFreq);
        double[] mels = new double[numMels];

        for (int i = 0; i < numMels; i++) {

            mels[i] = melFLow[0] + (melFHigh[0] - melFLow[0]) / (numMels-1) * i;

        }

        return melToFreq(mels);

    }


    private double[] melToFreq(double[] mels) {

        final double f_min = 0.0;
        final double f_sp = 200.0 / 3;
        double[] freqs = new double[mels.length];
        final double min_log_hz = 1000.0;
        final double min_log_mel = (min_log_hz - f_min) / f_sp;
        final double logstep = Math.log(6.4) / 27.0;

        for (int i = 0; i < mels.length; i++) {

            if (mels[i] < min_log_mel){

                freqs[i] =  f_min + f_sp * mels[i];

            }else{

                freqs[i] = min_log_hz * Math.exp(logstep * (mels[i] - min_log_mel));

            }

        }

        return freqs;

    }

    protected double[] freqToMel(double[] freqs) {

        final double f_min = 0.0;
        final double f_sp = 200.0 / 3;
        double[] mels = new double[freqs.length];
        final double min_log_hz = 1000.0;
        final double min_log_mel = (min_log_hz - f_min) / f_sp ;
        final double logstep = Math.log(6.4) / 27.0;

        for (int i = 0; i < freqs.length; i++) {

            if (freqs[i] < min_log_hz){

                mels[i] = (freqs[i] - f_min) / f_sp;

            }else{

                mels[i] = min_log_mel + Math.log(freqs[i]/min_log_hz) / logstep;

            }

        }

        return mels;

    }

    private double log10(double value) {

        return Math.log(value) / Math.log(10);

    }
}