package dev.eren.removebg

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
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

    val inputImage: MutableState<Bitmap?> = remember {
        mutableStateOf(null)
    }

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                inputImage.value =
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        })

    var loading: Boolean by remember {
        mutableStateOf(false)
    }

    var isReal: Boolean by remember {
        mutableStateOf(false)
    }

    val remover = remember {
        RemoveBg(context = context)
    }

    LaunchedEffect(key1 = inputImage.value) {
        inputImage.value?.let { image ->
            remover.clearBackground(image)
                .onStart {
                    loading = true
                }
                .onCompletion {
                    loading = false
                }.collect { output ->
                    outputImage.value = output
                }
        }
    }

    Scaffold { paddingValues ->
        Box(modifier = Modifier.background(Color.White)) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
            ) {
                Button(onClick = {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) {
                    Text(text = "Open Gallery")
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (loading) {
                    CircularProgressIndicator()
                }
                if (outputImage.value != null && inputImage.value != null) {
                    Image(
                        bitmap = if (!isReal) outputImage.value!!.asImageBitmap() else inputImage.value!!.asImageBitmap(),
                        contentDescription = "",
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                isReal = !isReal
                            }
                    )
                }
            }
        }
    }
}