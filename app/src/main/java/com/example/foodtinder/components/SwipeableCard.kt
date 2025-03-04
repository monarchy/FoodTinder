import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.foodtinder.data.Dish
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt


@Composable
fun SwipeableCard(
    dish: Dish,
    onLike: () -> Unit,
    onDislike: () -> Unit,
    onAnimationComplete: () -> Unit
) {
    val animatedOffsetX = remember { Animatable(0f) }
    val animatedRotation = remember { Animatable(0f) }
    val animatedScale = remember { Animatable(1f) }
    val coroutineScope = rememberCoroutineScope()

    val likeThreshold = -200f
    val dislikeThreshold = 200f

    var showHint by remember { mutableStateOf(false) }
    var hintMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset { IntOffset(animatedOffsetX.value.roundToInt(), 0) }
            .rotate(animatedRotation.value)
            .scale(animatedScale.value)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consumePositionChange()

                        coroutineScope.launch {
                            animatedOffsetX.snapTo(animatedOffsetX.value + dragAmount.x)
                            animatedRotation.snapTo(-animatedOffsetX.value / 1000)
                            animatedScale.snapTo(1f - (abs(animatedOffsetX.value) / 1000))
                        }

                        showHint = when {
                            animatedOffsetX.value < likeThreshold -> {
                                hintMessage = "Лайк"
                                true
                            }
                            animatedOffsetX.value > dislikeThreshold -> {
                                hintMessage = "Дизлайк"
                                true
                            }
                            else -> false
                        }
                    },
                    onDragEnd = {
                        coroutineScope.launch {
                            when {
                                animatedOffsetX.value < likeThreshold -> {
                                    launchSwipeAnimation(
                                        animatedOffsetX,
                                        animatedRotation,
                                        animatedScale,
                                        -800f,
                                        onLike
                                    ) {
                                        coroutineScope.launch {
                                            resetCard(animatedOffsetX, animatedRotation, animatedScale)
                                            onAnimationComplete()
                                        }
                                    }
                                }
                                animatedOffsetX.value > dislikeThreshold -> {
                                    launchSwipeAnimation(
                                        animatedOffsetX,
                                        animatedRotation,
                                        animatedScale,
                                        800f,
                                        onDislike
                                    ) {
                                        coroutineScope.launch {
                                            resetCard(animatedOffsetX, animatedRotation, animatedScale)
                                            onAnimationComplete()
                                        }
                                    }
                                }
                                else -> {
                                    resetCard(animatedOffsetX, animatedRotation, animatedScale)
                                }
                            }
                        }
                    }
                )
            }
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = dish.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = dish.name,
                    fontSize = 24.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = dish.description,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        if (showHint) {
            Box(
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 32.dp)
            ) {
                Surface(
                    color = if (hintMessage == "Лайк") Color.Green else Color.Red,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = hintMessage,
                        color = Color.White,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

private suspend fun resetCard(
    offset: Animatable<Float, AnimationVector1D>,
    rotation: Animatable<Float, AnimationVector1D>,
    scale: Animatable<Float, AnimationVector1D>
) {
    val animationSpec = tween<Float>(durationMillis = 200, easing = FastOutSlowInEasing)
    offset.snapTo(0f)
    rotation.snapTo(0f)
    scale.snapTo(1f)
}

private suspend fun launchSwipeAnimation(
    offset: Animatable<Float, AnimationVector1D>,
    rotation: Animatable<Float, AnimationVector1D>,
    scale: Animatable<Float, AnimationVector1D>,
    targetOffset: Float,
    onSwipe: () -> Unit,
    onAnimationComplete: () -> Unit
) {
    val animationSpec = tween<Float>(durationMillis = 300, easing = FastOutSlowInEasing)
    offset.animateTo(targetOffset, animationSpec)
    rotation.animateTo(if (targetOffset < 0) 5f else -5f, animationSpec)
    scale.animateTo(0.7f, animationSpec)
    onSwipe()
    onAnimationComplete()
}

