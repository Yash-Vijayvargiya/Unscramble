package com.example.unscramble

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.unscramble.databinding.FragmentGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class GameFragment() : Fragment(){

    private lateinit var _binding: FragmentGameBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGameBinding.inflate(inflater, container, false)
                // Log.d("Create","1")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup a click listener for the Submit and Skip buttons.
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
        // Update the UI
        updateNextWordOnScreen()
        binding.score.text = getString(R.string.score, 0)
        binding.wordCount.text = getString(
            R.string.word_count, 1, MAX_NO_OF_WORDS)
    }


    private fun onSubmitWord() {
        val userWord= binding.textInputEditText.text.toString()
        if(viewModel.checkWord(userWord)) {
            if (viewModel.nextWord()) {
                setErrorTextField(false)
                updateNextWordOnScreen()

            }
            else
                showFinalScoreDialog()
        }

        else{
            setErrorTextField(true)
        }




        //Log.i("Create","1")

    }


    private fun onSkipWord() {
        if( viewModel.nextWord()) {
            setErrorTextField(false)
            updateNextWordOnScreen()
        }
        else
            showFinalScoreDialog()
    }


    private fun restartGame() {
        setErrorTextField(false)
        viewModel.reset()
        updateNextWordOnScreen()

    }
    private fun exitGame() {
        activity?.finish()
    }

    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }


    private fun updateNextWordOnScreen() {
        binding.textViewUnscrambledWord.text = viewModel.currentScrambleWord
        binding.wordCount.text = getString(R.string.word_count, viewModel.currentWordCount, MAX_NO_OF_WORDS)
        binding.score.text = getString(R.string.score, viewModel.score)
    }
    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.you_scored, viewModel.score))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                restartGame()
            }
            .show()

    }

    private val viewModel: GameViewModel by  viewModels()



    }

