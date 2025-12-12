package com.example.jikanapp.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.jikanapp.domain.model.Anime
import com.example.jikanapp.presentation.viewmodels.AnimeListScreenState
import com.example.jikanapp.presentation.viewmodels.AnimeViewModel
import com.example.jikanapp.ui.theme.*



@Composable
fun AnimeListScreen(
    viewModel: AnimeViewModel,
    onAnimeClick: (Int) -> Unit,
    onDismiss : () -> Unit
) {
    val state by viewModel.screenState.collectAsState()
    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RichBlack)
    ) {
        when (state) {
            is AnimeListScreenState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = ElectricBlue,
                        strokeWidth = 3.dp
                    )
                }
            }

            is AnimeListScreenState.Error -> {
                val error = state as AnimeListScreenState.Error

                if (error.isNoInternet) {
                    AlertDialog(
                        onDismissRequest = {
                            onDismiss()
                        },
                        title = { Text("No Internet", color = PureWhite) },
                        text = { Text("Please check your connection and try again.", color = SilverGray) },
                        confirmButton = {
                            TextButton(onClick = { viewModel.getAnimeList() }) {
                                Text("Retry", color = ElectricBlue, fontWeight = FontWeight.SemiBold)
                            }
                        },
                        containerColor = DeepCharcoal
                    )
                } else {
                    LaunchedEffect(error.message) {
                        Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            is AnimeListScreenState.Success -> {
                val list = (state as AnimeListScreenState.Success).animeList

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(list.size) { index ->
                        AnimeItemCard(
                            anime = list[index],
                            onClick = { onAnimeClick(list[index].mal_id) },
                            index = index
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeItemCard(
    anime: Anime,
    onClick: () -> Unit,
    index: Int
) {
    val context = LocalContext.current
    val imageUrl = anime.images.jpg.large_image_url.ifBlank { anime.images.jpg.image_url }
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .crossfade(true)
            .build()
    )
    val state = painter.state

    val accentColor = when (index % 4) {
        0 -> ElectricBlue
        1 -> AmberGlow
        2 -> EmeraldGreen
        else -> CrimsonRed
    }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(145.dp),
        colors = CardDefaults.cardColors(
            containerColor = DeepCharcoal
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
        ) {
            Card(
                modifier = Modifier
                    .width(90.dp)
                    .fillMaxHeight(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        imageUrl.isBlank() || state is AsyncImagePainter.State.Error -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(accentColor.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.ImageNotSupported,
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp),
                                    tint = accentColor.copy(alpha = 0.4f)
                                )
                            }
                        }
                        state is AsyncImagePainter.State.Loading -> {
                            CircularProgressIndicator(
                                color = accentColor,
                                modifier = Modifier.size(28.dp),
                                strokeWidth = 2.5.dp
                            )
                        }
                        state is AsyncImagePainter.State.Success -> {
                            Image(
                                painter = painter,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = anime.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (anime.year != null && anime.year != 0) {
                            Text(
                                text = anime.year.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = SilverGray
                            )
                            Text(
                                text = "•",
                                color = accentColor.copy(alpha = 0.8f),
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "${anime.episodes} Episodes",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = SilverGray
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = anime.status,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        color = SilverGray.copy(alpha = 0.8f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(
                                accentColor.copy(alpha = 0.12f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = anime.score?.toString() ?: "N/A",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = accentColor
                        )
                    }
                    Text(
                        text = "Rank #${anime.rank}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = SilverGray.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

