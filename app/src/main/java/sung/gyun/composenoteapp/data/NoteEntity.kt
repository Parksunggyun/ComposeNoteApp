package sung.gyun.composenoteapp.data


data class NoteEntity(
    val id: Long? = null,
    var title: String,
    var content: String,
    val priority: Int,
    val createAt: String // yyyy-MM-dd HH:mm:ss
) {
    companion object {
        val EMPTY_NOTE = NoteEntity(
            id = -1L,
            title = "",
            content = "",
            priority = 1,
            createAt = ""
        )
    }
}