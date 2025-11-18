package com.anthonycr.mezzanine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.anthonycr.mezzanine.library.Test2Json

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = { Text(stringResource(R.string.app_name)) }
                    )
                }
            ) { innerPadding ->
                Column {
                    Test1JsonContent(modifier = Modifier.padding(innerPadding))
                    Test2JsonContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Test1JsonContent(modifier: Modifier = Modifier) {
    val test1Json = mezzanine<Test1Json>()
    Text(
        text = test1Json.readJson(),
        fontFamily = FontFamily.Monospace,
        modifier = modifier
    )
}

@Composable
fun Test2JsonContent(modifier: Modifier = Modifier) {
    val fileReader = mezzanine<Test2Json>()
    Text(
        text = fileReader.readJson(),
        fontFamily = FontFamily.Monospace,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun Test1JsonContentPreview() {
    Test1JsonContent()
}

@Preview(showBackground = true)
@Composable
fun Test2JsonContentPreview() {
    Test2JsonContent()
}
