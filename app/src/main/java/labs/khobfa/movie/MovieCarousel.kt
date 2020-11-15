package labs.khobfa.movie

import android.widget.Toast
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberScrollableController
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.VerticalGradient
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ConfigurationAmbient
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.accompanist.coil.CoilImage

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
    val screenSize = ConfigurationAmbient.current.screenWidthDp.dp
    var offset by remember { mutableStateOf(0f) }
    val scrollController = rememberScrollableController { delta ->
        offset += delta
        delta
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
            CoilImage(
                data = movie.bgUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(posterAspectRatio)
            )
        }

        Spacer(
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .background(VerticalGradient(listOf(Color.White, Color.Transparent), 0f, 1f))
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
        )

        movies.forEachIndexed { index, movie ->
            MoviePoster(
                index = index,
                screenSize = screenSize,
                movie = movie,
                modifier = Modifier
                    .offset(getX = { screenSize * index + offset.dp }, getY = { 0.dp })
                    .width(screenSize * .75f)
            )
        }
    }
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
                .fillMaxWidth()
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
        modifier = Modifier.fillMaxWidth(0.5f),
        backgroundColor = Color.DarkGray,
        elevation = 0.dp
    ) {
        Text(
            text = "Buy Ticket",
            color = Color.White
        )
    }
}

fun Modifier.offset(
    getX: () -> Dp,
    getY: () -> Dp,
    rtlAware: Boolean = true
) = this then object : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureScope.MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            if (rtlAware) {
                placeable.placeRelative(getX().toIntPx(), getY().toIntPx())
            } else {
                placeable.place(getX().toIntPx(), getY().toIntPx())
            }
        }
    }
}