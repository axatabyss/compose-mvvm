package com.axat.composemvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axat.composemvvm.model.Post
import com.axat.composemvvm.ui.theme.ComposeMVVMTheme
import com.axat.composemvvm.util.ApiState
import com.axat.composemvvm.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMVVMTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    callPostApi(mainViewModel)
                }
            }
        }
    }
}


@Composable
fun EachRow(post : Post) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = post.title, modifier = Modifier.padding(10.dp), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = post.body, modifier = Modifier.padding(10.dp), fontSize = 14.sp)
        }
    }
}

@Composable
fun callPostApi(mainViewModel: MainViewModel) {

    when (val result = mainViewModel.response.value) {

        is ApiState.Success -> {
            LazyColumn{
                items(result.data) { response ->
                    EachRow(post = response)
                }
            }
        }

        is ApiState.Failure -> {
            Text(text = "${result.msg}")
        }

        ApiState.Loading -> {
            CircularProgressIndicator()
        }

        ApiState.Empty -> {
            Text(text = "There is no Data")
        }

    }

}