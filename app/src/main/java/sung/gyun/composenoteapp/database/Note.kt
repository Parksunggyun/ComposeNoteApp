package sung.gyun.composenoteapp.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "note_list")
@Parcelize
class Note(
    @PrimaryKey(autoGenerate = true)
    var itemId: Long?,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "content")
    var content: String,
    @ColumnInfo(name = "priority")
    var priority: Int,
    @ColumnInfo(name = "create_at")
    var createAt: String
): Parcelable {
    companion object {
        val EMPTY = Note(
            -1L,
            "",
            "",
            1,
            ""
        )
    }
}