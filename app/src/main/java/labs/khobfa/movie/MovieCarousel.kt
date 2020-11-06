package labs.khobfa.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.ui.tooling.preview.Preview
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun Screen() {
    Column(
        Modifier.background(Color.Black)
            .fillMaxSize()
    ) {
        MoviePoster()
    }
}

@Composable
fun MoviePoster(modifier: Modifier = Modifier) {
    Column(
        modifier
            .background(Color.White)
            .padding(20.dp)
    ) {
        Image(
            asset = imageResource(id = R.drawable.joker_poster),
            modifier = modifier
                .size(570.dp, 845.dp)
        )
    }
}