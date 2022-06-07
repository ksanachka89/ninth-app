class ChatService {
    private var chats = mutableListOf<Chat>()
    private var actualChatId = 1
    private var actualMessageId = 1

    override fun toString(): String {
        return "$chats, actualChatId = $actualChatId, actualMessageId = $actualMessageId"
    }

    fun createChat(chat: Chat): Int {
        val newChat = chat.copy(chatId = actualChatId)
        chats.add(newChat)
        actualChatId++
        return newChat.chatId
    }

    fun deleteChat(chatId: Int): Boolean = chats.removeIf { it.chatId == chatId }

    fun getChats(userId: Int): List<Chat> {
        val chatList = chats.filter { it.userId == userId }
        return if (chats.isNotEmpty()) {
            chatList
        } else {
            println("no messages")
            emptyList()
        }
    }

    fun createMessage(userId: Int, message: Message) {
        val newMessage = message.copy(senderId = userId, messageId = actualMessageId)
        if (message.chatId != 0) {
            val messageList = chats.filter { it.chatId == message.chatId }
            if (messageList.isEmpty()) {
                throw ChatNotFoundException("no actual chat")
            }
            messageList[0].messages.add(newMessage)
            actualMessageId++
        } else {
            val messageList: MutableList<Message> = mutableListOf(newMessage)
            newMessage.chatId = createChat(Chat(0, message.senderId, messageList))
            actualMessageId++
        }
    }

    fun editMessage(modifiedMessage: Message): Boolean { // редактировать записку
        chats.forEach { chat ->
            if (chat.chatId == modifiedMessage.chatId) {
                chat.messages.withIndex().forEach { (index, message) ->
                    if (message.messageId == modifiedMessage.messageId) {
                        chat.messages[index] = message.copy(text = modifiedMessage.text)
                        return true
                    }
                }
            }
        }
        return false
    }

    fun deleteMessage(message: Message) {
        val chat = chats.first { it.chatId == message.chatId }
        chat.messages.remove(message)
        chat.messages.ifEmpty { deleteChat(chat.chatId) }
    }

    fun getMessagesFromChat(chatId: Int, messageId: Int, amountOfMessages: Int): List<Message> {
        val result = chats.single { it.chatId == chatId }.messages
            .filter { it.messageId >= messageId }
            .take(amountOfMessages)
        chats
            .filter { chatId == it.chatId }
            .flatMap { it.messages }
            .forEach { it.get = true }
        return result
    }

    fun getUnreadChatsCount(userId: Int): Int { // количество непрочитанных чатов
        return chats.filter { it.userId == userId }
            .map { chat -> chat.messages.filter { !it.get && it.incoming } }
            .count { it.isNotEmpty() }
    }
}