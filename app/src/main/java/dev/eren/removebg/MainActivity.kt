package dev.eren.removebg

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import dev.eren.removebg.ui.theme.RemovebgTheme
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RemovebgTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RemoveBackground()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoveBackground() {
    val context = LocalContext.current

    val outputImage: MutableState<Bitmap?> = remember {
        mutableStateOf<Bitmap?>(null)
    }

    var loading: Boolean by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = Unit) {
        val bitmap = BitmapFactory.decodeStream(context.assets.open("remove_test.jpg"))
        val remover = RemoveBg(context)
        remover.clearBackground(bitmap)
            .onStart {
                loading = true
            }
            .onCompletion {
                loading = false
            }.collect { output ->
                outputImage.value = output
            }
    }

    Scaffold { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (loading) {
                CircularProgressIndicator()
            }
            if (outputImage.value != null) {
                Image(
                    bitmap = outputImage.value!!.asImageBitmap(),
                    contentDescription = "",
                    Modifier.fillMaxWidth()
                )
            }
        }
    }
}