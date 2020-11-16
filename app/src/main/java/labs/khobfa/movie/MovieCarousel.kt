package labs.khobfa.movie

import android.widget.Toast
import androidx.compose.animation.asDisposableClock
import androidx.compose.animation.core.TargetAnimation
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.animation.FlingConfig
import androidx.compose.foundation.animation.defaultFlingConfig
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.ScrollableController
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AnimationClockAmbient
import androidx.compose.ui.platform.ConfigurationAmbient
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

data class Movie(
    val title: String,
    val posterUrl: String,
    val bgUrl: String,
    val color: Color,
    val chips: List<String>,
    val actors: List<MovieActor> = emptyList(),
    val introduction: String = ""
)

data class MovieActor(
    val name: String,
    val image: String
)

val movies = listOf(
    Movie(
        title = "Good Boys",
        posterUrl = "https://m.media-amazon.com/images/M/MV5BMTc1NjIzODAxMF5BMl5BanBnXkFtZTgwMTgzNzk1NzM@._V1_.jpg",
        bgUrl = "https://m.media-amazon.com/images/M/MV5BMTc1NjIzODAxMF5BMl5BanBnXkFtZTgwMTgzNzk1NzM@._V1_.jpg",
        color = Color.Red,
        chips = listOf("Action", "Drama", "History"),
        actors = listOf(
            MovieActor(
                "Jaoquin Phoenix",
                "https://image.tmdb.org/t/p/w138_and_h175_face/nXMzvVF6xR3OXOedozfOcoA20xh.jpg"
            ),
            MovieActor(
                "Robert De Niro",
                "https://image.tmdb.org/t/p/w138_and_h175_face/cT8htcckIuyI1Lqwt1CvD02ynTh.jpg"
            ),
            MovieActor(
                "Zazie Beetz",
                "https://image.tmdb.org/t/p/w138_and_h175_face/sgxzT54GnvgeMnOZgpQQx9csAdd.jpg"
            )
        ),
        introduction = "During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure."
    ),
    Movie(
        title = "Joker",
        posterUrl = "https://i.etsystatic.com/15963200/r/il/25182b/2045311689/il_794xN.2045311689_7m2o.jpg",
        bgUrl = "https://images-na.ssl-images-amazon.com/images/I/61gtGlalRvL._AC_SY741_.jpg",
        color = Color.Blue,
        chips = listOf("Action", "Drama", "History"),
        actors = listOf(
            MovieActor(
                "Jaoquin Phoenix",
                "https://image.tmdb.org/t/p/w138_and_h175_face/nXMzvVF6xR3OXOedozfOcoA20xh.jpg"
            ),
            MovieActor(
                "Robert De Niro",
                "https://image.tmdb.org/t/p/w138_and_h175_face/cT8htcckIuyI1Lqwt1CvD02ynTh.jpg"
            ),
            MovieActor(
                "Zazie Beetz",
                "https://image.tmdb.org/t/p/w138_and_h175_face/sgxzT54GnvgeMnOZgpQQx9csAdd.jpg"
            )
        ),
        introduction = "During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure."
    ),
    Movie(
        title = "The Hustle",
        posterUrl = "https://m.media-amazon.com/images/M/MV5BMTc3MDcyNzE5N15BMl5BanBnXkFtZTgwNzE2MDE0NzM@._V1_.jpg",
        bgUrl = "https://m.media-amazon.com/images/M/MV5BMTc3MDcyNzE5N15BMl5BanBnXkFtZTgwNzE2MDE0NzM@._V1_.jpg",
        color = Color.Yellow,
        chips = listOf("Action", "Drama", "History"),
        actors = listOf(
            MovieActor(
                "Jaoquin Phoenix",
                "https://image.tmdb.org/t/p/w138_and_h175_face/nXMzvVF6xR3OXOedozfOcoA20xh.jpg"
            ),
            MovieActor(
                "Robert De Niro",
                "https://image.tmdb.org/t/p/w138_and_h175_face/cT8htcckIuyI1Lqwt1CvD02ynTh.jpg"
            ),
            MovieActor(
                "Zazie Beetz",
                "https://image.tmdb.org/t/p/w138_and_h175_face/sgxzT54GnvgeMnOZgpQQx9csAdd.jpg"
            )
        ),
        introduction = "During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure."
    )
)

val posterAspectRatio = .674f

@Composable
fun Screen() {
    val configuration = ConfigurationAmbient.current
    val density = DensityAmbient.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenWidthPx = with(density) { screenWidth.toPx() }
    val screenHeight = configuration.screenHeightDp.dp
    val screenHeightPx = with(density) { screenHeight.toPx() }

    var offset by remember { mutableStateOf(0f) }

    val posterWidthDp = screenWidth * 0.6f
    val posterSpacingPx = with(density) { posterWidthDp.toPx() + 50.dp.toPx() }
    val indexFraction = -1 * offset / posterSpacingPx


    val flingConfig =
        defaultFlingConfig { TargetAnimation((it / posterSpacingPx).roundToInt() * posterSpacingPx) }

    val upperBound = 0f
    val lowerBound = -1f * (movies.size - 1) * posterSpacingPx

    val scrollController = rememberScrollableController(flingConfig) { delta ->
        val target = offset + delta
        when {
            target > upperBound -> {
                val consumed = upperBound - offset
                offset = upperBound
                consumed
            }

            target < lowerBound -> {
                val consumed = lowerBound - offset
                offset = lowerBound
                consumed
            }

            else -> {
                offset = target
                delta
            }
        }
    }

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .scrollable(
                Orientation.Horizontal,
                controller = scrollController
            )
    ) {
        movies.forEachIndexed { index, movie ->

            val isInRange = (index >= indexFraction - 1 && indexFraction + 1 > index)
            val opacity = if (isInRange) 1f else 0f
            val shape = when {
                !isInRange -> RectangleShape
                index < indexFraction -> {
                    val fraction = indexFraction - index
                    FractionalRectangleShape(fraction, 1f)
                }
                else -> {
                    val fraction = indexFraction - index + 1
                    FractionalRectangleShape(0f, max(fraction, Float.MIN_VALUE))
                }
            }
            CoilImage(
                data = movie.bgUrl,
                modifier = Modifier
                    // .drawOpacity(opacity)
                    .drawLayer(
                        alpha = opacity,
                        shape = shape,
                        clip = true
                    )
                    .fillMaxWidth()
                    .aspectRatio(posterAspectRatio)
            )
        }

        Spacer(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .verticalGradient(0f to Color.Transparent, 0.3f to Color.White, 1f to Color.White)
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
        )

        movies.forEachIndexed { index, movie ->
            val center = posterSpacingPx * index
            val distFromCenter = abs(center + offset) / screenWidthPx
            MoviePoster(
                index = index,
                screenSize = screenWidth,
                movie = movie,
                modifier = Modifier
                    .offset(getX = { center + offset }, getY = { lerp(0f, 50f, distFromCenter) })
                    .width(posterWidthDp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun rememberScrollableController(
    flingConfig: FlingConfig = defaultFlingConfig(),
    consumeScrollDelta: (Float) -> Float
): ScrollableController {
    val clocks = AnimationClockAmbient.current.asDisposableClock()
    return remember(clocks, flingConfig) {
        ScrollableController(consumeScrollDelta, flingConfig, clocks)
    }
}

fun FractionalRectangleShape(startFraction: Float, endFraction: Float) = object : Shape {
    override fun createOutline(size: Size, density: Density): Outline =
        Outline.Rectangle(
            Rect(
                top = 0f,
                left = startFraction * size.width,
                bottom = size.height,
                right = endFraction * size.width
            )
        )
}

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}

@Composable
fun MoviePoster(index: Int, screenSize: Dp, movie: Movie, modifier: Modifier = Modifier) {
    val context = ContextAmbient.current
    Column(
        modifier = modifier
            .width(screenSize * 0.7f)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilImage(
            data = movie.posterUrl,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .width(screenSize * 0.6f)
                .aspectRatio(posterAspectRatio)
                .clip(RoundedCornerShape(10.dp))
        )

        Text(text = movie.title, style = TextStyle(color = Color.Black, fontSize = 24.sp))

        Row {
            for (chip in movie.chips) {
                Chip(label = chip)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        StarRating(9.0f)
        Spacer(modifier = Modifier.height(8.dp))
        BuyTicket(onClick = {
            Toast.makeText(context, "Index >> $index", Toast.LENGTH_LONG).show()
        })
    }
}

@Composable
fun Chip(label: String) {
    Text(
        text = label,
        style = TextStyle(fontSize = 9.sp),
        color = Color.Gray,
        modifier = Modifier
            .padding(2.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(50))
            .padding(horizontal = 8.dp, vertical = 2.dp),
    )
}

@Composable
fun StarRating(value: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(Icons.Default.Star)
        }
    }
}

@Composable
fun BuyTicket(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.5f),
        backgroundColor = Color.DarkGray,
        elevation = 2.dp
    ) {
        Text(
            text = "Buy Ticket",
            color = Color.White
        )
    }
}

fun Modifier.offset(
    getX: () -> Float,
    getY: () -> Float,
    rtlAware: Boolean = true
) = this then object : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureScope.MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            if (rtlAware) {
                placeable.placeRelative(getX().roundToInt(), getY().roundToInt())
            } else {
                placeable.place(getX().roundToInt(), getY().roundToInt())
            }
        }
    }
}

fun Modifier.verticalGradient(vararg colors: ColorStop) =
    this then object : DrawModifier {

        // naive cache outline calculation if size is the same
        private var lastSize: Size? = null
        private var lastBrush: Brush? = null

        override fun ContentDrawScope.draw() {
            drawRect()
            drawContent()
        }

        private fun ContentDrawScope.drawRect() {
            var brush = lastBrush
            if (size != lastSize || brush == null) {
                brush = VerticalGradient(
                    *colors,
                    startY = 0f,
                    endY = size.height
                )
                lastSize = size
                lastBrush = brush
            }

            brush.let { drawRect(brush = brush, alpha = 1f) }
        }
    }

