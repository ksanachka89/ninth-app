data class Chat(
    val chatId: Int,
    val userId: Int,
    val messages: MutableList<Message>
)