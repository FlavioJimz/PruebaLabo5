package com.falconteam.laboratorio_5.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.falconteam.laboratorio_5.data.database.Entity.Post
import com.falconteam.laboratorio_5.ui.components.PostComponent
import com.falconteam.laboratorio_5.ui.theme.Laboratorio5Theme
import com.falconteam.laboratorio_5.ui.theme.Typography
import com.falconteam.laboratorio_5.utils.BlogsitoViewModel


@Composable
fun MainScreen(
    viewModel: BlogsitoViewModel,
    onClick: () -> Unit
) {
    val listPosts = viewModel.listPosts.collectAsState()

    if (viewModel.showModal.value) {
        AlertDialog(
            onDismissRequest = {
                viewModel.showModal()
                               },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.addNewPost()
                        viewModel.showModal()
                    }
                ) {
                    Text(text = "Guardar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.showModal()
                    }
                ) {
                    Text(text = "Cancelar")
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "NewPost"
                )
            },
            title = {
                Text(text = "Redacta una publicacion!")
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = viewModel.newPostTitle.value,
                        onValueChange = {
                            viewModel.newPostTitle.value = it
                        },
                        label = {
                            Text(text = "titulo")
                        }
                    )

                    OutlinedTextField(
                        value = viewModel.newPostDescription.value,
                        onValueChange = {
                            viewModel.newPostDescription.value = it
                        },
                        label = {
                            Text(text = "Descripcion")
                        }
                    )
                }
            }
        )
    }

    Laboratorio5Theme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { viewModel.showModal() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                when {
                    listPosts.value.isEmpty() -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Se el primero en escribir una publicación!",
                                style = Typography.displayMedium
                            )
                        }
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            items(listPosts.value) {
                                PostComponent(
                                    id = it.id.toString(),
                                    title = it.title,
                                    description = it.description,
                                    author = it.author,
                                    onPostChange = { title, description, id ->
                                        viewModel.updatePost(title, description, id)
                                    },
                                    onDeletePost = {
                                        viewModel.deletePost(it.id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}