package labs.khobfa.movie

import android.util.Log
import androidx.compose.animation.animatedFloat
import androidx.compose.animation.asDisposableClock
import androidx.compose.animation.core.TargetAnimation
import androidx.compose.foundation.animation.FlingConfig
import androidx.compose.foundation.animation.defaultFlingConfig
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.ScrollableController
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonConstants
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.AnimationClockAmbient
import androidx.compose.ui.platform.ConfigurationAmbient
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor
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
    val screenWidth = configuration.screenWidthDp.dp
    val posterWidthDp = screenWidth * 0.6f
    val posterSpacing = posterWidthDp + 20.dp

    var currentMovie by remember { mutableStateOf(0) }


    Box {
        Carousel(
            movies,
            selectedIndex = currentMovie,
            spacing = posterSpacing,
            backgroundContent = { _, movie ->
                CoilImage(
                    data = movie.bgUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(posterAspectRatio)
                )
            },
            foregroundContent = { _, movie ->
                MoviePoster(
                    movie = movie,
                    modifier = Modifier.width(posterWidthDp)
                )
            },
            onSelectedIndexChange = { index -> currentMovie = index }
        )

        BuyTicket(
            onClick = {
                Log.e("Current Movie", "is >>>>> $currentMovie")
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(posterWidthDp)
                .padding(20.dp)
        )
    }
}

@Composable
fun <T> Carousel(
    items: List<T>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    spacing: Dp,
    backgroundContent: @Composable (Int, T) -> Unit,
    foregroundContent: @Composable (Int, T) -> Unit,
) {
    val animatedOffset = animatedFloat(initVal = 0f)
    val spacingPx = with(DensityAmbient.current) { spacing.toPx() }
    val flingConfig = defaultFlingConfig {
        Log.e("Details", "It >>> $it >>>> spacingPx: $spacing")
        TargetAnimation((it / spacingPx).roundToInt() * spacingPx)
    }

    val upperBound = 0f
    val lowerBound = -1f * (movies.size - 1) * spacingPx

    val scrollController = rememberScrollableController(flingConfig) { delta ->
        val target = animatedOffset.value + delta
        when {
            target > upperBound -> {
                val consumed = upperBound - animatedOffset.value
                animatedOffset.snapTo(upperBound)
//                offset = upperBound
                consumed
            }

            target < lowerBound -> {
                val consumed = lowerBound - animatedOffset.value
//                offset = lowerBound
                animatedOffset.snapTo(lowerBound)
                consumed
            }

            else -> {
//                offset = target
                animatedOffset.snapTo(target)
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
        items.forEachIndexed { index, item ->
            Column(
                modifier = Modifier
                    .carouselBaclground(index) {
                        -1 * animatedOffset.value / spacingPx
                    }
                    .fillMaxSize()
            ) {
                backgroundContent(index, item)
            }
            //onSelectedIndexChange(index)
        }

        Spacer(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .verticalGradient(0f to Color.Transparent, 0.3f to Color.White, 1f to Color.White)
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
        )



        items.forEachIndexed { index, item ->
            val center = spacingPx * index
            Column(
                Modifier
                    .offset(
                        getX = {
                            center + animatedOffset.value
                        },
                        getY = {
                            val distFromCenter = abs(animatedOffset.value + center) / spacingPx
                            lerp(0f, 50f, distFromCenter)
                        }
                    )
                    .align(Alignment.BottomCenter)
            ) {
                foregroundContent(index, item)
            }

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
fun MoviePoster(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(20.dp)
            .padding(bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilImage(
            data = movie.posterUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(posterAspectRatio)
                .clip(RoundedCornerShape(10.dp))
        )

        Text(
            text = movie.title,
            color = Color.Black,
            fontSize = 24.sp
        )

        Row {
            for (chip in movie.chips) {
                Chip(label = chip)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        StarRating(9.0f)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

fun Modifier.carouselBaclground(
    index: Int,
    getIndexFraction: () -> Float
) = this then object : DrawLayerModifier {

    override val alpha: Float
        get() {
            val indexFraction = getIndexFraction()
            val leftIndex = floor(indexFraction).toInt()
            val rightIndex = ceil(indexFraction).toInt()
            return if (index == leftIndex || index == rightIndex) 1f else 0f
        }

    override val shape: Shape
        get() {
//            val indexFraction = -1 * offset / spacingPx
            val indexFraction = getIndexFraction()
            val leftIndex = floor(indexFraction).toInt()
            val rightIndex = ceil(indexFraction).toInt()
            return when (index) {
                leftIndex -> {
                    val fraction = indexFraction - index
                    FractionalRectangleShape(fraction, 1f)
                }
                rightIndex -> {
                    val fraction = indexFraction - index + 1
                    FractionalRectangleShape(0f, fraction)
                }
                else -> RectangleShape
            }

        }
    override val clip: Boolean
        get() = super.clip

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
fun BuyTicket(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(vertical = 16.dp),
        colors = ButtonConstants.defaultButtonColors(backgroundColor = Color.DarkGray)
    ) {
        Text(
            "BUY TICKET",
            Modifier,
            Color.White,
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
    ): MeasureResult {
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
            drawRect(brush = brush, alpha = 1f)
        }
    }

