package app.mosquito.appmosquito.appmosquito.Audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReaderWav
{
    private enum IOState {READING};
    private final static int BUFFER_SIZE = 4096;
    private final static int FMT_CHUNK_ID = 0x20746D66;
    private final static int DATA_CHUNK_ID = 0x61746164;


    private File file;
    private IOState ioState;
    private int bytesPerSample;
    private long numFrames;
    private FileOutputStream oStream;
    private FileInputStream iStream;
    private double floatScale;
    private double floatOffset;
    private boolean wordAlignAdjust;
    private int numChannels;
    private long sampleRate;
    private int blockAlign;
    private int validBits;
    private byte[] buffer;
    private int bufferPointer;
    private int bytesRead;
    private long frameCounter;

    private ReaderWav()
    {
        buffer = new byte[BUFFER_SIZE];
    }

    public int getNumChannels()
    {
        return numChannels;
    }

    public static ReaderWav openWavFile(File file) throws IOException
    {

        ReaderWav wavFile = new ReaderWav();
        wavFile.file = file;
        wavFile.iStream = new FileInputStream(file);
        int bytesRead = wavFile.iStream.read(wavFile.buffer, 0, 12);
        long riffChunkID = getLE(wavFile.buffer, 0, 4);
        long chunkSize = getLE(wavFile.buffer, 4, 4);
        long riffTypeID = getLE(wavFile.buffer, 8, 4);


        boolean foundFormat = false;
        boolean foundData = false;

        while (true)
        {
            bytesRead = wavFile.iStream.read(wavFile.buffer, 0, 8);

            long chunkID = getLE(wavFile.buffer, 0, 4);
            chunkSize = getLE(wavFile.buffer, 4, 4);
            long numChunkBytes = (chunkSize%2 == 1) ? chunkSize+1 : chunkSize;

            if (chunkID == FMT_CHUNK_ID)
            {
                foundFormat = true;
                bytesRead = wavFile.iStream.read(wavFile.buffer, 0, 16);
                int compressionCode = (int) getLE(wavFile.buffer, 0, 2);
                wavFile.numChannels = (int) getLE(wavFile.buffer, 2, 2);
                wavFile.sampleRate = getLE(wavFile.buffer, 4, 4);
                wavFile.blockAlign = (int) getLE(wavFile.buffer, 12, 2);
                wavFile.validBits = (int) getLE(wavFile.buffer, 14, 2);
                wavFile.bytesPerSample = (wavFile.validBits + 7) / 8;
                numChunkBytes -= 16;
                if (numChunkBytes > 0) wavFile.iStream.skip(numChunkBytes);

            }
            else if (chunkID == DATA_CHUNK_ID)
            {
                wavFile.numFrames = chunkSize / wavFile.blockAlign;
                foundData = true;
                break;
            }
            else
            {
                wavFile.iStream.skip(numChunkBytes);
            }
        }

        if (wavFile.validBits > 8)
        {

            wavFile.floatOffset = 0;
            wavFile.floatScale = 1 << (wavFile.validBits - 1);
        }
        else
        {
            wavFile.floatOffset = -1;
            wavFile.floatScale = 0.5 * ((1 << wavFile.validBits) - 1);
        }

        wavFile.bufferPointer = 0;
        wavFile.bytesRead = 0;
        wavFile.frameCounter = 0;
        wavFile.ioState = IOState.READING;

        return wavFile;
    }

    private static long getLE(byte[] buffer, int pos, int numBytes)
    {
        numBytes --;
        pos += numBytes;

        long val = buffer[pos] & 0xFF;
        for (int b=0 ; b<numBytes ; b++) val = (val << 8) + (buffer[--pos] & 0xFF);

        return val;
    }

    private long readSample() throws IOException
    {
        long val = 0;

        for (int b=0 ; b<bytesPerSample ; b++)
        {
            if (bufferPointer == bytesRead)
            {
                int read = iStream.read(buffer, 0, BUFFER_SIZE);
                bytesRead = read;
                bufferPointer = 0;
            }

            int v = buffer[bufferPointer];
            if (b < bytesPerSample-1 || bytesPerSample == 1) v &= 0xFF;
            val += v << (b * 8);

            bufferPointer ++;
        }

        return val;
    }

    public int readFrames(double[] sampleBuffer, int numFramesToRead) throws IOException
    {
        return readFrames(sampleBuffer, 0, numFramesToRead);
    }

    public int readFrames(double[] sampleBuffer, int offset, int numFramesToRead) throws IOException
    {

        for (int f=0 ; f<numFramesToRead ; f++)
        {
            if (frameCounter == numFrames) return f;

            for (int c=0 ; c<numChannels ; c++)
            {
                sampleBuffer[offset] = floatOffset + (double) readSample() / floatScale;
                offset ++;
            }

            frameCounter ++;
        }

        return numFramesToRead;
    }

}