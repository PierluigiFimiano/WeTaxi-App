package it.wetaxi.test.message

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import it.wetaxi.test.message.databinding.ActivityMainBinding
import it.wetaxi.test.message.util.observeEvent
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        adapter = MessageAdapter()
        binding.content.recyclerView.adapter = adapter
        binding.content.recyclerView.setHasFixedSize(true)

        binding.fab.setOnClickListener {
            viewModel.getMessage()
        }

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.w("getInstanceId failed", task.exception)
                    return@addOnCompleteListener
                }

                val token = task.result ?: return@addOnCompleteListener
                Timber.d(token)
            }

        lifecycleScope.launchWhenCreated {
            viewModel.messages.collectLatest { messages ->
                adapter.submitList(messages)
            }
        }

        viewModel.errorEvent.observeEvent(this) {
            Snackbar.make(
                binding.rootLayout,
                "API error",
                Snackbar.LENGTH_LONG
            ).show()
        }

        viewModel.successEvent.observeEvent(this) {
            Snackbar.make(
                binding.rootLayout,
                "API success",
                Snackbar.LENGTH_LONG
            ).show()
        }

        viewModel.dataLoading.observe(this) {
            binding.content.progressBar.isVisible = it
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                viewModel.markAllMessagesRead()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
