package app.mosquito.appmosquito.appmosquito.Audio;

public class ShortTransformed {

    public double[] realPhase;
    public double[] imaginaryPhase;

    public void getTransformed(double[] signalWav) {

        final int signalSize = signalWav.length;
        realPhase = signalWav;
        imaginaryPhase = new double[signalSize];
        final int numStagesWav = (int) (Math.log(signalSize) / Math.log(2));
        final int shiftSignalSize = signalSize >> 1;

        copyWavToList(signalSize, shiftSignalSize);

        for (int valueSFTF = 1; valueSFTF <= numStagesWav; valueSFTF++) {
            int signalLeft = 1;
            for (int i = 0; i < valueSFTF; i++) {
                signalLeft <<= 1;
            }
            final int tempSignalLeft = signalLeft >> 1;
            double UR = 1;
            double UI = 0;

            final double angleCosSignal =  Math.cos(Math.PI / tempSignalLeft);
            final double angleSinSignal = -Math.sin(Math.PI / tempSignalLeft);

            for (int partialTransform = 1; partialTransform <= tempSignalLeft; partialTransform++) {

                for (int valueDFT = partialTransform - 1; valueDFT <= signalSize - 1; valueDFT += signalLeft) {

                    int ip = valueDFT + tempSignalLeft;
                    double tempReal = (double) (realPhase[ip] * UR - imaginaryPhase[ip] * UI);
                    double tempImag = (double) (realPhase[ip] * UI + imaginaryPhase[ip] * UR);
                    realPhase[ip] = realPhase[valueDFT] - tempReal;
                    imaginaryPhase[ip] = imaginaryPhase[valueDFT] - tempImag;
                    realPhase[valueDFT] += tempReal;
                    imaginaryPhase[valueDFT] += tempImag;
                }

                double tempConstant = UR;
                UR = tempConstant * angleCosSignal - UI * angleSinSignal;
                UI = tempConstant * angleSinSignal + UI * angleCosSignal;
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