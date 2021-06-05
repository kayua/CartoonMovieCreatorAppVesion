package app.mosquito.appmosquito.appmosquito.ui.Audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder.AudioSource;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class WavAudioRecorder {
    private final static int[] sampleRates = {44100, 22050, 11025, 8000};

    public static WavAudioRecorder getInstanse() {
        WavAudioRecorder result = null;
        int i=0;
        do {
            result = new WavAudioRecorder(AudioSource.MIC,
                    sampleRates[i],
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT);
        } while((++i<sampleRates.length) & !(result.getState() == WavAudioRecorder.State.INITIALIZING));
        return result;
    }


    public enum State {INITIALIZING, READY, RECORDING, ERROR, STOPPED};

    public static final boolean RECORDING_UNCOMPRESSED = true;
    public static final boolean RECORDING_COMPRESSED = false;


    private static final int TIMER_INTERVAL = 120;

    private AudioRecord     audioRecorder = null;

    private String          filePath = null;

    private State          	state;

    private RandomAccessFile randomAccessWriter;

    private short                    nChannels;
    private int                      sRate;
    private short                    mBitsPersample;
    private int                      mBufferSize;
    private int                      mAudioSource;
    private int                      aFormat;

    private int                      mPeriodInFrames;

    private byte[]                   buffer;

    private int                      payloadSize;

    public State getState() {
        return state;
    }


    private AudioRecord.OnRecordPositionUpdateListener updateListener = new AudioRecord.OnRecordPositionUpdateListener() {
        //	periodic updates on the progress of the record head
        public void onPeriodicNotification(AudioRecord recorder) {
            if (State.STOPPED == state) {
                Log.d(WavAudioRecorder.this.getClass().getName(), "recorder stopped");
                return;
            }
            int numOfBytes = audioRecorder.read(buffer, 0, buffer.length); // read audio data to buffer
//			Log.d(WavAudioRecorder.this.getClass().getName(), state + ":" + numOfBytes);
            try {
                randomAccessWriter.write(buffer); 		  // write audio data to file
                payloadSize += buffer.length;
            } catch (IOException e) {
                Log.e(WavAudioRecorder.class.getName(), "Error occured in updateListener, recording is aborted");
                e.printStackTrace();
            }
        }
        //	reached a notification marker set by setNotificationMarkerPosition(int)
        public void onMarkerReached(AudioRecord recorder) {
        }
    };


    public WavAudioRecorder(int audioSource, int sampleRate, int channelConfig, int audioFormat) {
        try {
            if (audioFormat == AudioFormat.ENCODING_PCM_16BIT) {
                mBitsPersample = 16;
            } else {
                mBitsPersample = 8;
            }

            if (channelConfig == AudioFormat.CHANNEL_IN_MONO) {
                nChannels = 1;
            } else {
                nChannels = 2;
            }

            mAudioSource = audioSource;
            sRate   = sampleRate;
            aFormat = audioFormat;

            mPeriodInFrames = sampleRate * TIMER_INTERVAL / 1000;
            mBufferSize = mPeriodInFrames * 2  * nChannels * mBitsPersample / 8;
            if (mBufferSize < AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)) {
                mBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);

                mPeriodInFrames = mBufferSize / ( 2 * mBitsPersample * nChannels / 8 );
                Log.w(WavAudioRecorder.class.getName(), "Increasing buffer size to " + Integer.toString(mBufferSize));
            }

            audioRecorder = new AudioRecord(audioSource, sampleRate, channelConfig, audioFormat, mBufferSize);

            if (audioRecorder.getState() != AudioRecord.STATE_INITIALIZED) {
                throw new Exception("AudioRecord initialization failed");
            }
            audioRecorder.setRecordPositionUpdateListener(updateListener);
            audioRecorder.setPositionNotificationPeriod(mPeriodInFrames);
            filePath = null;
            state = State.INITIALIZING;
        } catch (Exception e) {
            if (e.getMessage() != null) {
                Log.e(WavAudioRecorder.class.getName(), e.getMessage());
            } else {
                Log.e(WavAudioRecorder.class.getName(), "Unknown error occured while initializing recording");
            }
            state = State.ERROR;
        }
    }

    public void setOutputFile(String argPath) {
        try {
            if (state == State.INITIALIZING) {
                filePath = argPath;
            }
        } catch (Exception e) {
            if (e.getMessage() != null) {
                Log.e(WavAudioRecorder.class.getName(), e.getMessage());
            } else {
                Log.e(WavAudioRecorder.class.getName(), "Unknown error occured while setting output path");
            }
            state = State.ERROR;
        }
    }


    public void prepare() {
        try {
            if (state == State.INITIALIZING) {
                if ((audioRecorder.getState() == AudioRecord.STATE_INITIALIZED) & (filePath != null)) {

                    randomAccessWriter = new RandomAccessFile(filePath, "rw");
                    randomAccessWriter.setLength(0);
                    randomAccessWriter.writeBytes("RIFF");
                    randomAccessWriter.writeInt(0);
                    randomAccessWriter.writeBytes("WAVE");
                    randomAccessWriter.writeBytes("fmt ");
                    randomAccessWriter.writeInt(Integer.reverseBytes(16));
                    randomAccessWriter.writeShort(Short.reverseBytes((short) 1));
                    randomAccessWriter.writeShort(Short.reverseBytes(nChannels));
                    randomAccessWriter.writeInt(Integer.reverseBytes(sRate));
                    randomAccessWriter.writeInt(Integer.reverseBytes(sRate*nChannels*mBitsPersample/8));
                    randomAccessWriter.writeShort(Short.reverseBytes((short)(nChannels*mBitsPersample/8)));
                    randomAccessWriter.writeShort(Short.reverseBytes(mBitsPersample));
                    randomAccessWriter.writeBytes("data");
                    randomAccessWriter.writeInt(0);
                    buffer = new byte[mPeriodInFrames*mBitsPersample/8*nChannels];
                    state = State.READY;
                } else {
                    Log.e(WavAudioRecorder.class.getName(), "prepare() method called on uninitialized recorder");
                    state = State.ERROR;
                }
            } else {
                Log.e(WavAudioRecorder.class.getName(), "prepare() method called on illegal state");
                release();
                state = State.ERROR;
            }
        } catch(Exception e) {
            if (e.getMessage() != null) {
                Log.e(WavAudioRecorder.class.getName(), e.getMessage());
            } else {
                Log.e(WavAudioRecorder.class.getName(), "Unknown error occured in prepare()");
            }
            state = State.ERROR;
        }
    }

    public void release() {
        if (state == State.RECORDING) {
            stop();
        } else {
            if (state == State.READY){
                try {
                    randomAccessWriter.close(); // Remove prepared file
                } catch (IOException e) {
                    Log.e(WavAudioRecorder.class.getName(), "I/O exception occured while closing output file");
                }
                (new File(filePath)).delete();
            }
        }

        if (audioRecorder != null) {
            audioRecorder.release();
        }
    }


    public void start() {
        if (state == State.READY) {
            payloadSize = 0;
            audioRecorder.startRecording();
            audioRecorder.read(buffer, 0, buffer.length);
            state = State.RECORDING;
        } else {
            Log.e(WavAudioRecorder.class.getName(), "start() called on illegal state");
            state = State.ERROR;
        }
    }


    public void stop() {
        if (state == State.RECORDING) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            audioRecorder.stop();
            try {
                randomAccessWriter.seek(4);
                randomAccessWriter.writeInt(Integer.reverseBytes(36+payloadSize));

                randomAccessWriter.seek(40);
                randomAccessWriter.writeInt(Integer.reverseBytes(payloadSize));

                randomAccessWriter.close();
            } catch(IOException e) {
                Log.e(WavAudioRecorder.class.getName(), "I/O exception occured while closing output file");
                state = State.ERROR;
            }
            state = State.STOPPED;
        } else {
            Log.e(WavAudioRecorder.class.getName(), "stop() called on illegal state");
            state = State.ERROR;
        }

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }


    }


}