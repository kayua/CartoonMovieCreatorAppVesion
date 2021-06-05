import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import cafe.adriel.androidaudioconverter.AndroidAudioConverter
import cafe.adriel.androidaudioconverter.callback.IConvertCallback
import cafe.adriel.androidaudioconverter.model.AudioFormat
import com.example.aedesdetector.R
import com.example.aedesdetector.spec.MFCC
import com.example.aedesdetector.spec.RTEvaluator
import com.example.aedesdetector.spec.WavFile
import com.example.aedesdetector.ui.report_screen.ReportScreenActivity
import com.example.aedesdetector.utils.AlertUtils
import kotlinx.coroutines.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.*
import java.lang.Float.max
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer


class RecorderFragment : Fragment() {

    private val mainLooper = Looper.getMainLooper()


    private val REQ_CODE_PICK_SOUNDFILE = 12345
    private lateinit var recorderViewModel: RecorderViewModel

    //audio converter
    private lateinit var audioConverterCallback: IConvertCallback

    //audio recorder
    private var isRecording = false

    private lateinit var recordButton: Button

    private lateinit var loaderView: ConstraintLayout
    private lateinit var loaderTextView: TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        recorderViewModel =
                ViewModelProviders.of(this).get(RecorderViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_recorder, container, false)

        loaderView = root.findViewById(R.id.loader_view)
        loaderTextView = root.findViewById(R.id.loader_text)
        loaderView.visibility = View.GONE

        val openFileButton: Button = root.findViewById(R.id.open_file_button)
        openFileButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "audio/wav|audio/m4a|audio/mp3"
            startActivityForResult(
                    Intent.createChooser(
                            intent,
                            getString(R.string.select_audio_file_title)
                    ),
                    REQ_CODE_PICK_SOUNDFILE
            )
        }

        recordButton = root.findViewById(R.id.record_button)
        recordButton.setOnClickListener {
            recordAudio()
        }

        audioConverterCallback = object : IConvertCallback {
            override fun onSuccess(convertedFile: File) {
                // audio converted!
                val audioBytes = convertedFile.inputStream()
                readAudioBytesAndEvaluate(audioBytes)
            }

            override fun onFailure(error: java.lang.Exception) {
                Log.d("ERROR", error.toString())
                // audio failed to convert
            }
        }

        return root
    }

    private fun recordAudio() {
        if (!isRecording) {
            if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.RECORD_AUDIO
                    ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                val permissions = arrayOf(
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                ActivityCompat.requestPermissions(requireActivity(), permissions, 0)
            } else {
                startRecording()
            }
        } else {
            stopRecording()
        }
    }

    private fun startRecording() {
        isRecording = true
        recordButton.setText("GRAVANDO")

        //RTEvaluator.startRecording(requireContext())
        //WavRecorder.getInstance().startRecording(requireContext())
    }

    private fun stopRecording() {
        RTEvaluator.stopRecording()
        //val fileName = WavRecorder.getInstance().stopRecording()
        recordButton.setText("GRAVAR")
        isRecording = false
        //Log.d("FILENAME", fileName)
        try {
            //val audioBytes = File(fileName).inputStream()
            //readAudioBytesAndEvaluate(audioBytes)
        } catch (e: Exception) {
            Log.d("EXCEPTION", e.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_PICK_SOUNDFILE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                try {
                    val uri = data.data
                    if (uri != null) {
                        val contentResolver: ContentResolver = this.context!!.contentResolver
                        val audioType = uri.let { contentResolver.getType(it) }
                        //convert here?
                        //audio/mp4
                        if (audioType == "audio/wav" || audioType == "audio/x-wav") {
                            //supported format
                            val audioBytes = uri.let { contentResolver.openInputStream(it) }
                            readAudioBytesAndEvaluate(audioBytes)
                            return
                        } else {
                            //unsuported file format, will try to convert
                            val path = uri.path
                            if (path != null) {
                                val audioFile = File(path)
                                AndroidAudioConverter.with(this.context!!).setFile(audioFile).setFormat(
                                        AudioFormat.WAV
                                ).setCallback(audioConverterCallback).convert()
                            }

                        }
                    } else {
                        //null uri
                        Log.d("EXCEPTION", "Null URI")
                    }

                } catch (e: Exception) {
                    Log.d("EXCEPTION", e.toString())
                }
            }
        }
    }

    private fun readAudioBytesAndEvaluate(audioBytes: InputStream?) {
        GlobalScope.launch {
            Handler(mainLooper).post {
                showLoader()
            }
            val result = extractFeaturesAndRunEvaluation(audioBytes)
            if (result) {
                Handler(mainLooper).post {
                    dismissLoader()
                    positiveAedesIdentification()
                }
            } else {
                Handler(mainLooper).post {
                    dismissLoader()
                    negativeAedesIdentification()
                }
            }
        }
    }

    private fun extractFeaturesAndRunEvaluation(audioBytes: InputStream?): Boolean {
        try {
            val mNumFrames: Int
            val mChannels: Int

            updateLoader("Abrindo arquivo...")

            var predictedResult: Float = 0.0F

            var wavFile: WavFile?
            wavFile = WavFile.openWavFile(audioBytes)
            mNumFrames = wavFile.numFrames.toInt()
            mChannels = wavFile.numChannels
            val buffer = Array(mChannels) { DoubleArray(mNumFrames) }
            wavFile.readFrames(buffer, mNumFrames, 0)
            val mfccConvert = MFCC()

            for (channel in buffer) {
                updateLoader("Processando áudio...")
                val mfccInput = mfccConvert.processBulkSpectrograms(channel, 40)
                updateLoader("Avaliando o áudio...")
                for (element in mfccInput) {
                    val flattenedSpec = flattenSpectrogram(element)
                    predictedResult =
                            max(loadModelAndMakePredictions(flattenedSpec), predictedResult)
                }
            }
            Log.d("MFCC R", predictedResult.toString())
            Log.d("MFCC", "Finished")
            return predictedResult > 0.95
        } catch (e: Exception) {
            Log.d("EXCEPTION", e.toString())
            return false
        }
    }

    private fun flattenSpectrogram(input: Array<FloatArray>): FloatArray {
        var output: ArrayList<Float> = ArrayList<Float>();
        input[0].indices.forEach { i ->
            input.indices.forEach { j ->
                output.add(input[j][i])
            }
        }
        return output.toFloatArray()
    }

    private fun positiveAedesIdentification() {
        AlertUtils.displayAlert(requireContext(),
                "Aedes!",
                "Identificamos um Aedes aegypti. Gostaria de compartilhar no mapa?",
                "Sim",
                DialogInterface.OnClickListener { _, _ ->
                    // User clicked OK button
                    startActivity(Intent(requireContext(), ReportScreenActivity::class.java))
                },
                "Não",
                DialogInterface.OnClickListener { _, _ ->
                    // User clicked OK button
                })
    }

    private fun negativeAedesIdentification() {
        AlertUtils.displayAlert(
                requireContext(),
                "Não é Aedes!",
                "Não identificamos um aedes na sua gravação.",
                "Ok",
                DialogInterface.OnClickListener { _, _ ->
                    // User clicked OK button
                },
                null,
                null
        )
    }

    protected fun loadModelAndMakePredictions(meanMFCCValues: FloatArray): Float {
        //load the TFLite model in 'MappedByteBuffer' format using TF Interpreter
        val tfliteModel: MappedByteBuffer =
                FileUtil.loadMappedFile(requireContext(), getModelPath())
        val tflite: Interpreter

        /** Options for configuring the Interpreter.  */
        val tfliteOptions =
                Interpreter.Options()
        tfliteOptions.setNumThreads(2)
        tflite = Interpreter(tfliteModel, tfliteOptions)

        //obtain the input and output tensor size required by the model
        val imageTensorIndex = 0
        val imageShape =
                tflite.getInputTensor(imageTensorIndex).shape()
        val imageDataType: DataType = tflite.getInputTensor(imageTensorIndex).dataType()
        val probabilityTensorIndex = 0
        val probabilityShape =
                tflite.getOutputTensor(probabilityTensorIndex).shape()
        val probabilityDataType: DataType =
                tflite.getOutputTensor(probabilityTensorIndex).dataType()

        //need to transform the MFCC 1d float buffer into 1x60x40 dimension tensor using TensorBuffer
        val inBuffer: TensorBuffer = TensorBuffer.createDynamic(imageDataType)
        inBuffer.loadArray(meanMFCCValues, imageShape)
        val inpBuffer: ByteBuffer = inBuffer.buffer
        val outputTensorBuffer: TensorBuffer =
                TensorBuffer.createFixedSize(probabilityShape, probabilityDataType)
        //run the predictions with input and output buffer tensors to get probability values
        tflite.run(inpBuffer, outputTensorBuffer.buffer)

        val result = outputTensorBuffer.floatArray.first()
        val secondResult = outputTensorBuffer.floatArray[1]
        Log.d("RESULT", "")
        Log.d("0", result.toString())
        Log.d("1", secondResult.toString())

        return result
    }

    fun getModelPath(): String {
        return "aedes_converted.tflite"
    }

    private fun showLoader() {
        Handler(mainLooper).post {
            loaderView.visibility = View.VISIBLE
            requireActivity().window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    private fun dismissLoader() {
        Handler(mainLooper).post {
            loaderView.visibility = View.GONE
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    private fun updateLoader(loaderMessage: String) {
        Handler(mainLooper).post {
            loaderTextView.text = loaderMessage
        }
    }
}