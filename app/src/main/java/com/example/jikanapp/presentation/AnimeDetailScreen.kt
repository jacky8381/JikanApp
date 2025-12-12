package com.example.jikanapp.presentation

import androidx.compose.ui.platform.LocalContext
import com.example.jikanapp.domain.model.Anime
import com.example.jikanapp.domain.model.Genre
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jikanapp.presentation.viewmodels.AnimeDetailViewmodel
import com.example.jikanapp.ui.theme.*
import com.example.jikanapp.utils.Constants


@Composable
fun AnimeDetailScreen(
    detailViewmodel: AnimeDetailViewmodel,
    navHostController: NavHostController
) {
    val uiState by detailViewmodel.uiState.collectAsState()

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(RichBlack),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = ElectricBlue)
            }
        }

        uiState.errorMessage.isNotEmpty() -> {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(RichBlack),
                contentAlignment = Alignment.Center
            ) {

                if (uiState.errorMessage == Constants.NO_INTERNET_CONNECTION) {
                    AlertDialog(
                        onDismissRequest = {
                            navHostController.popBackStack()
                            detailViewmodel.updateErrorMessage("")
                        },
                        title = { Text("No Internet", color = PureWhite) },
                        text = { Text("Please check your connection and try again.", color = SilverGray) },
                        confirmButton = {
                            TextButton(onClick = { detailViewmodel.animeId?.let {
                                detailViewmodel.getAnimeDetails(
                                    it
                                )
                            } }) {
                                Text("Retry", color = ElectricBlue, fontWeight = FontWeight.SemiBold)
                            }
                        },
                        containerColor = DeepCharcoal
                    )
                }else{
                    Text(
                        text = uiState.errorMessage,
                        color = CrimsonRed
                    )
                }

            }
        }

        uiState.anime != null -> {
            uiState.anime?.let { anime ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(RichBlack)
                ) {
                    item {
                        AnimePosterHeader(anime)
                    }
                    item {
                        QuickStatsSection(anime)
                    }
                    item {
                        GenresSection(anime.genres)
                    }
                    item {
                        DetailsSection(anime)
                    }
                    item {
                        SynopsisSection(anime.synopsis)
                    }
                    item {
                        Spacer(Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun AnimePosterHeader(anime: Anime) {
    val context = LocalContext.current
    val posterUrl = anime.images.jpg.large_image_url.ifBlank {
        anime.images.jpg.image_url
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        if (posterUrl.isBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DeepCharcoal),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = anime.title,
                    modifier = Modifier.size(80.dp),
                    tint = SilverGray.copy(alpha = 0.3f)
                )
            }
        } else {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(posterUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = anime.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.4f),
                            RichBlack.copy(alpha = 0.95f)
                        ),
                        startY = 150f
                    )
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = anime.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite,
                    lineHeight = 32.sp
                )

                Spacer(Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (anime.year != null && anime.year != 0) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = ElectricBlue.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = anime.year.toString(),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = ElectricBlue
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = EmeraldGreen.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = anime.status,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = EmeraldGreen
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuickStatsSection(anime: Anime) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            icon = Icons.Rounded.Star,
            label = "Rating",
            value = anime.score?.toString() ?: "N/A",
            accentColor = AmberGlow,
            modifier = Modifier.weight(1f)
        )

        StatCard(
            icon = Icons.Rounded.TrendingUp,
            label = "Rank",
            value = "#${anime.rank}",
            accentColor = ElectricBlue,
            modifier = Modifier.weight(1f)
        )

        StatCard(
            icon = Icons.Rounded.PlayArrow,
            label = "Episodes",
            value = anime.episodes.toString(),
            accentColor = EmeraldGreen,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(
    icon: ImageVector,
    label: String,
    value: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = DeepCharcoal
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = accentColor,
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = PureWhite
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = SilverGray
            )
        }
    }
}

@Composable
fun GenresSection(genres: List<Genre>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "Genres",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = PureWhite
        )

        Spacer(Modifier.height(12.dp))

        GenreChips(genres)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GenreChips(genres: List<Genre>) {
    val chipColors = listOf(
        ElectricBlue,
        AmberGlow,
        EmeraldGreen,
        CrimsonRed
    )

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        genres.forEachIndexed { index, genre ->
            val color = chipColors[index % chipColors.size]
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = color.copy(alpha = 0.15f)
            ) {
                Text(
                    text = genre.name,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = color
                )
            }
        }
    }
}

@Composable
fun DetailsSection(anime: Anime) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DeepCharcoal
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Details",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = PureWhite
            )

            Spacer(Modifier.height(16.dp))

            DetailRow("Type", anime.type ?: "Unknown")
            DetailRow("Episodes", anime.episodes.toString())
            DetailRow("Status", anime.status)
            if (anime.year != null && anime.year != 0) {
                DetailRow("Year", anime.year.toString())
            }
            DetailRow("Score", "⭐ ${anime.score}")
            DetailRow("Rank", "#${anime.rank}")
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = SilverGray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = PureWhite
        )
    }
}

@Composable
fun SynopsisSection(synopsis: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = DeepCharcoal
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Overview",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = PureWhite
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = synopsis,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 22.sp,
                fontWeight = FontWeight.Normal,
                color = SilverGray
            )
        }
    }
}