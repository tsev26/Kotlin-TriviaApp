package cz.utb.fai.trivia.result

import android.content.ClipData
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import cz.utb.fai.trivia.R
import cz.utb.fai.trivia.databinding.FragmentResultBinding
import cz.utb.fai.trivia.score.Score
import cz.utb.fai.trivia.score.ScoreApplication
import cz.utb.fai.trivia.score.ScoreViewModel
import cz.utb.fai.trivia.score.ScoreViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


class ResultFragment : Fragment() {

  private val viewModel: ResultViewModel by lazy {
    ViewModelProvider(this).get(ResultViewModel::class.java)
  }

  private val scoreViewModel: ScoreViewModel by viewModels {
    ScoreViewModelFactory(
      (activity?.application as ScoreApplication).repository
    )
  }

  lateinit var score: Score

  private var mCorrectAnswers : Int = 0
  private var mUserName : String? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    arguments = getArguments()
    mUserName = arguments?.getString("USER")
    mCorrectAnswers = arguments?.getInt("CORRECT_ANSWERS")!!

    val binding = FragmentResultBinding.inflate(inflater)
    binding.lifecycleOwner = this
    binding.viewModel = viewModel

    viewModel.showResult(mUserName!!,mCorrectAnswers)

    score = Score(name = mUserName!!, score = mCorrectAnswers, date = getCurrentDate())
    scoreViewModel.insert(score)

    binding.btnFinish.setOnClickListener {
      it.findNavController().navigate(R.id.action_resultFragment_to_welcomeFragment)
    }

    return binding.root
  }

  private fun getCurrentDate():String{
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    return sdf.format(Date())
  }



}