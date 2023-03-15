package karagulov.sultan.whac_a_mole.ui.screens.menuScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import karagulov.sultan.whac_a_mole.R
import karagulov.sultan.whac_a_mole.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val sharedPreferences = activity?.getSharedPreferences("Record_Score", 0)
        binding.tvBestScore.text = sharedPreferences?.getString("Record_Score", "-1")

        binding.btnPlay.setOnClickListener{
            findNavController().navigate(R.id.action_menuFragment_to_gameFragment)
        }
    }
}