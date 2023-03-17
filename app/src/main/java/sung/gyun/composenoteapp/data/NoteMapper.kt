package sung.gyun.composenoteapp.data

import sung.gyun.composenoteapp.database.Note

fun NoteEntity.toNote(): Note {
    return Note(
        itemId = id,
        title = title,
        content = content,
        priority = priority,
        createAt = createAt
    )
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = itemId,
        title = title,
        content = content,
        priority = priority,
        createAt = createAt
    )
}

fun List<Note>.toEntities(): List<NoteEntity> {
    return map {
        NoteEntity(
            id = it.itemId,
            title = it.title,
            content = it.content,
            priority = it.priority,
            createAt = it.createAt
        )
    }
}