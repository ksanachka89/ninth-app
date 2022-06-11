fun main() {
    val chat = ChatService()
    chat.createChat(Chat(1, 1, mutableListOf()))
    chat.createChat(Chat(2, 1, mutableListOf()))

    println(chat.getChats(1))

    chat.createChat(Chat(3, 1, mutableListOf()))
    println(chat)
    chat.deleteChat(3)
    println(chat)

    chat.createMessage(1, Message(1, 2, 1, 2, "Hello", get = false, incoming = false))
    chat.createMessage(1, Message(2, 0, 1, 3, "Hi", get = true, incoming = true)) // создался новый чат (id4)
    println(chat)

    chat.editMessage(Message(1, 2, 1, 2, "Hello", false, incoming = false), "Bye")
    println(chat)

    chat.createChat(Chat(5, 1, mutableListOf()))
    chat.createMessage(1, Message(3, 5, 1, 2, "Good morning", get = false, incoming = true))
    chat.deleteMessage(Message(3, 5, 1, 2, "Good morning", get = false, incoming = true))
    println(chat)
    println()

    println(chat.getMessagesFromChat(2, 1, 1))

    println(chat)

    chat.createMessage(1, Message(4, 2, 1, 2, "Good afternoon", get = false, incoming = true))
    chat.createMessage(1, Message(5, 2, 1, 2, "Good evening", get = false, incoming = true))
    println(chat.getUnreadChatsCount(1))
}

