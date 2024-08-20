package com.ar.practice.presentation.story.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ar.practice.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



val imageList = listOf<Int>(
    R.drawable.ic_cards,
    R.drawable.ic_cards,
    R.drawable.ic_cards,
    R.drawable.ic_cards,
    R.drawable.ic_cards,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoryScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val pagerState = rememberPagerState(pageCount = { imageList.size })
        val coroutineScope = rememberCoroutineScope()

        var currentPage by remember {
            mutableStateOf(0)
        }

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            StoryImage(pagerState = pagerState, imageList = imageList)

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (currentPage > 0) {
                            currentPage--
                        }
                        pagerState.animateScrollToPage(currentPage)
                    }
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.2f)
                    .align(Alignment.CenterStart),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Transparent
                )
            ) {}

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (currentPage < imageList.size - 1) {
                            currentPage++
                        }
                        pagerState.animateScrollToPage(currentPage)
                    }
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.2f)
                    .align(Alignment.CenterEnd),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Transparent
                )
            ) {}

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.padding(4.dp))

                for (index in imageList.indices) {
                    HorizontalProgressIndicator(
                        modifier = Modifier.weight(1f),
                        startProgress = index == currentPage,
                        index = index,
                        currentPage = currentPage
                    ) {
                        coroutineScope.launch {
                            if (currentPage < imageList.size - 1) {
                                currentPage++
                            }
                            pagerState.animateScrollToPage(currentPage)
                        }
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoryImage(pagerState: PagerState, imageList: List<Int>) {
    HorizontalPager(state = pagerState, userScrollEnabled = false) {
        Image(
            painter = painterResource(id = imageList[it]),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun HorizontalProgressIndicator(
    modifier: Modifier,
    startProgress: Boolean = false,
    currentPage: Int = 0,
    index: Int = 0,
    onAnimationEnd: () -> Unit,
) {

    var progress by remember {
        if (index > currentPage){
            mutableFloatStateOf(0.0f)
        }else{
            if(startProgress){
                mutableFloatStateOf(0.0f)
            }
            else{
                mutableFloatStateOf(1.0f)
            }
        }
    }

    LaunchedEffect(currentPage){
        progress = if (index > currentPage){
            0.0f
        }else{
            if(startProgress){
                0.0f
            }
            else{
                1.0f
            }
        }
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    if (startProgress) {
        LaunchedEffect(Unit) {
            while (progress < 1f) {
                progress += 0.01f
                delay(20)
            }
            onAnimationEnd()
        }
    }

    LinearProgressIndicator(
        progress = { animatedProgress },
        color = Color.White,
        trackColor = Color.LightGray,
        modifier = modifier
            .padding(top = 12.dp, bottom = 12.dp)
            .clip(RoundedCornerShape(12.dp))
    )
}

@Preview
@Composable
private fun StoryScreenPreview() {
    Surface {
        StoryScreen()
    }
}