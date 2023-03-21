package sung.gyun.composenoteapp.data


data class NoteEntity(
    val id: Long? = null,
    val title: String = "",
    val content: String = "",
    val priority: Int = 0,
    val createAt: String = "" // yyyy-MM-dd HH:mm:ss
) {
    fun createdDay() = createAt.split(" ")[0]
}