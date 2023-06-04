package karagulov.sultan.whac_a_mole.ui.screens.gameScreen

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import karagulov.sultan.whac_a_mole.R
import karagulov.sultan.whac_a_mole.databinding.FragmentGameBinding
import karagulov.sultan.whac_a_mole.ui.screens.Data.COUNT_COLUMN
import karagulov.sultan.whac_a_mole.ui.screens.Data.COUNT_ROW
import karagulov.sultan.whac_a_mole.ui.screens.Data.LAST_SCORE
import karagulov.sultan.whac_a_mole.ui.screens.Data.RECORD_SCORE
import kotlin.random.Random


class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var timer: CountDownTimer
    private lateinit var tableLayout: TableLayout
    private lateinit var mediaPlayer: MediaPlayer
    private var score = 0
    private var recordScore = 0
    private val arrayImages: Array<Array<FrameLayout?>> = Array(COUNT_COLUMN) { arrayOfNulls(COUNT_ROW) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tableLayout = binding.tblHoles

        timer = object : CountDownTimer(30000, 500) {
            override fun onTick(time: Long) {
            startGame(time)
            }
            override fun onFinish() {
                val sharedPreferences = activity?.getSharedPreferences("Record_Score", 0)
                val pref = sharedPreferences?.edit()
                pref?.putString(LAST_SCORE, score.toString())
                pref?.apply()
                findNavController().navigate(R.id.action_gameFragment_to_resultFragment)
            }
        }.start()

    }

    private fun startGame(timeToFinish: Long){

        val sharedPreferences = activity?.getSharedPreferences("Record_Score", 0)
        binding.tvTimer.text = (timeToFinish / 1000).toString()
        binding.tvRecordScore.text = sharedPreferences?.getString("Record_Score", "0")
        recordScore = sharedPreferences?.getString(RECORD_SCORE, "0")?.toInt() ?: 0

        val randomRow = Random.nextInt(COUNT_ROW)
        val randomColumn = Random.nextInt(COUNT_COLUMN)
        for(row in 0 until COUNT_ROW){
            for(column in 0 until COUNT_COLUMN){
                val frameLayout = FrameLayout(requireContext())
                val imageView = ImageView(requireContext())
                val params = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1.0F
                )
                params.setMargins(5)
                frameLayout.layoutParams = params
                frameLayout.setBackgroundResource(R.drawable.hole)

                if (row == randomRow && column == randomColumn) {
                    imageView.setImageResource(R.drawable.mole)
                    imageView.setOnClickListener {
                        score++
                        binding.tvScore.text = score.toString()
                        if(score > recordScore) {
                            val pref = sharedPreferences?.edit()
                            pref?.putString(RECORD_SCORE, score.toString())
                            pref?.apply()
                            binding.tvRecordScore.text = sharedPreferences?.getString(RECORD_SCORE, "0")
                        }
                        imageView.setImageResource(R.drawable.whack)
                        imageView.isEnabled = false
                        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.whack)
                        mediaPlayer.start()
                    }
                }
                frameLayout.addView(imageView)
                arrayImages[row][column] = frameLayout
            }
        }

        tableLayout.removeAllViews()
        for (row in 0 until COUNT_ROW) {
            val tableRow = TableRow(requireContext())
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
            )
            tableRow.gravity = Gravity.CENTER
            for (column in 0 until COUNT_COLUMN) {
                tableRow.addView(arrayImages[row][column], column)
            }
            tableLayout.addView(tableRow, row)
        }

    }


}