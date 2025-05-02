package com.savethekrakens.lab5_notesapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.savethekrakens.lab5_notesapp.NoteTopAppBar
import com.savethekrakens.lab5_notesapp.R
import com.savethekrakens.lab5_notesapp.data.Note
import com.savethekrakens.lab5_notesapp.ui.AppViewModelProvider
import com.savethekrakens.lab5_notesapp.ui.navigation.NavigationDestination
import com.savethekrakens.lab5_notesapp.ui.theme.Lab5_NotesAppTheme

// Navigation Destination for nav host controller
object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

// Start of the home screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToNoteEntry: () -> Unit,
    navigateToNoteUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NoteTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToNoteEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Item"
                )
            }
        }
    ) { innerPadding ->
        HomeBody(
            noteList = homeUiState.noteList,
            onItemClick = navigateToNoteUpdate,
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp, 16.dp),
            contentPadding = innerPadding
        )
    }
}

@Composable
private fun HomeBody(
    noteList: List<Note>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(contentPadding)
    ) {
        if (noteList.isEmpty()) {
            Text(
                text = "There are no notes right now. Use the button at the bottom to start creating notes.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding)
            )
        } else {
            NoteList(
                noteList = noteList,
                onItemClick = { onItemClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
private fun NoteList(
    noteList: List<Note>,
    onItemClick: (Note) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(items = noteList, key = { it.id }) { item ->
            NoteItem(
                note = item,
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { onItemClick(item) }
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier.padding(6.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Lab5_NotesAppTheme {
        HomeScreen(
            navigateToNoteEntry = {},
            navigateToNoteUpdate = {},
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteItemPreview() {
    Lab5_NotesAppTheme {
        NoteItem(
            Note(1, "Start here", "This is my crazy content description", 1)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    Lab5_NotesAppTheme {
        HomeBody(
            listOf(
                Note(1, "Start here", "This is my crazy content description", 1),
                Note(2, "Start here", "This is my crazy content description", 2)
            ), onItemClick = {})
    }
}