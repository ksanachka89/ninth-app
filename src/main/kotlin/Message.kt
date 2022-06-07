data class Message(
    val messageId: Int,
    var chatId: Int,
    val senderId: Int,
    val recipientId: Int,
    val text: String,
    var get: Boolean,
    val incoming: Boolean
)