package karagulov.sultan.whac_a_mole.ui.screens.resultScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import karagulov.sultan.whac_a_mole.R
import karagulov.sultan.whac_a_mole.databinding.FragmentResultBinding
import karagulov.sultan.whac_a_mole.ui.screens.Data.LAST_SCORE
import karagulov.sultan.whac_a_mole.ui.screens.Data.RECORD_SCORE

class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val sharedPreferences = activity?.getSharedPreferences(RECORD_SCORE, 0)
        binding.tvResultBestScore.text = sharedPreferences?.getString(RECORD_SCORE, "-1")
        binding.tvScoreResult.text = sharedPreferences?.getString(LAST_SCORE, "-1")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMenu.setOnClickListener{
            findNavController().navigate(R.id.action_resultFragment_to_menuFragment)
        }
        binding.btnPlayAgain.setOnClickListener{
            findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }
    }

}