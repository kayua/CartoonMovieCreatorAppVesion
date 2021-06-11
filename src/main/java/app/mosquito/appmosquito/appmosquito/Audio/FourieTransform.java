package app.mosquito.appmosquito.appmosquito.Audio;

public class FourieTransform {

    public double[] realPhase;
    public double[] imaginaryPhase;
    private double constantPi = Math.PI;

    public void getTransform(double[] signalWav) {

        final int signalSize = signalWav.length;
        realPhase = signalWav;
        imaginaryPhase = new double[signalSize];


        final int numStagesWav = (int) (Math.log(signalSize) / Math.log(2));
        final int shiftSignalSize = signalSize >> 1;
        copyWavToList(signalSize, shiftSignalSize);

        for (int stage = 1; stage <= numStagesWav; stage++) {
            int LE = 1;
            for (int i = 0; i < stage; i++) {
                LE <<= 1;
            }
            final int LE2 = LE >> 1;
            double UR = 1;
            double UI = 0;

            final double angleCosSignal =  Math.cos(constantPi / LE2);
            final double angleSinSignal = -Math.sin(constantPi / LE2);

            for (int subDFT = 1; subDFT <= LE2; subDFT++) {

                for (int valueDFT = subDFT - 1; valueDFT <= signalSize - 1; valueDFT += LE) {

                    int ip = valueDFT + LE2;
                    double tempReal = (double) (realPhase[ip] * UR - imaginaryPhase[ip] * UI);
                    double tempImag = (double) (realPhase[ip] * UI + imaginaryPhase[ip] * UR);
                    realPhase[ip] = realPhase[valueDFT] - tempReal;
                    imaginaryPhase[ip] = imaginaryPhase[valueDFT] - tempImag;
                    realPhase[valueDFT] += tempReal;
                    imaginaryPhase[valueDFT] += tempImag;
                }

                double tempUR = UR;
                UR = tempUR * angleCosSignal - UI * angleSinSignal;
                UI = tempUR * angleSinSignal + UI * angleCosSignal;
            }
        }
    }

    public void copyWavToList(int signalSize, int shiftSignalSize){
        int k;
        int j = shiftSignalSize;
        for (int i = 1; i < signalSize - 2; i++) {

            if (i < j) {

                double tempReal = realPhase[j];
                double tempImag = imaginaryPhase[j];
                realPhase[j] = realPhase[i];
                imaginaryPhase[j] = imaginaryPhase[i];
                realPhase[i] = tempReal;
                imaginaryPhase[i] = tempImag;
            }
            k = shiftSignalSize;
            while (k <= j) {
                j -= k;
                k >>= 1;
            }
            j += k;
        }

    }
}