import org.junit.Test

import org.junit.Assert.*

class ChatServiceTest {

    @Test
    fun createChat() {
        val chat = ChatService()
        val result = chat.createChat(
            Chat(
                1, 1, mutableListOf()
            )
        )
        assertNotEquals(0, result)
    }

    @Test
    fun deleteChat_true() {
        val chat = ChatService()
        chat.createChat(
            Chat(
                1, 1, mutableListOf()
            )
        )
        val result = chat.deleteChat(1)
        assertTrue(result)
    }

    @Test
    fun deleteChat_false() {
        val chat = ChatService()
        chat.createChat(
            Chat(
                1, 1, mutableListOf()
            )
        )
        val result = chat.deleteChat(2)
        assertFalse(result)
    }

    @Test
    fun getChats() {
        val chat = ChatService()
        chat.createChat(
            Chat(
                1, 1, mutableListOf()
            )
        )
        val result = chat.getChats(1).isNotEmpty()
        assertTrue(result)
    }

    @Test
    fun getChats_no_chats() {
        val chat = ChatService()
        chat.createChat(
            Chat(
                1, 1, mutableListOf()
            )
        )
        val result = chat.getChats(2).isNotEmpty()
        assertFalse(result)
    }

    @Test
    fun createMessage() {
        val chat = ChatService()
        chat.createChat(
            Chat(
                1, 1, mutableListOf()
            )
        )
        val result = chat.createMessage(
            1, Message(
                1, 1, 1, 2, "Hello", get = false, incoming = false
            )
        )
        assertNotEquals(0, result)
    }

    @Test
    fun createMessage_create_new_chat() {
        val chat = ChatService()
        val result = chat.createMessage(
            1, Message(
                1, 0, 1, 2, "Hello", get = false, incoming = false
            )
        )
        assertNotEquals(chat.getChats(1), 1)
        assertNotEquals(chat.getMessagesFromChat(1, 1, 1), 1)
    }

    @Test(expected = ChatNotFoundException::class)
    fun createMessage_should_not_create() {
        val chat = ChatService()
        chat.createMessage(
            1, Message(
                1, 1, 1, 2, "Hello", get = false, incoming = false
            )
        )
    }

    @Test
    fun editMessage_true() {
        val chat = ChatService()
        chat.createChat(
            Chat(
                1, 1, mutableListOf()
            )
        )
        chat.createMessage(
            1, Message(
                1, 1, 1, 2, "Hello", get = false, incoming = false
            )
        )
        val result = chat.editMessage(
            Message(1, 1, 1, 2, "Bye", false, incoming = false)
        )
        assertTrue(result)
    }

    @Test
    fun editMessage_false() {
        val chat = ChatService()
        chat.createChat(
            Chat(
                1, 1, mutableListOf()
            )
        )
        chat.createMessage(
            1, Message(
                1, 1, 1, 2, "Hello", get = false, incoming = false
            )
        )
        val result = chat.editMessage(
            Message(2, 1, 1, 2, "Bye", false, incoming = false)
        )
        assertFalse(result)
    }

    @Test
    fun deleteMessage_should_delete() {
        val chat = ChatService()
        chat.createChat(
            Chat(
                1, 1, mutableListOf()
            )
        )
        chat.createMessage(
            1, Message(
                1, 1, 1, 2, "Hello", get = false, incoming = false
            )
        )
        val sizeBefore = chat.getMessagesFromChat(1, 1, 1).size
        chat.deleteMessage(
            Message(
                1, 1, 1, 2, "Hello", get = false, incoming = false
            )
        )
        val sizeAfter = chat.getMessagesFromChat(1, 1, 0).size
        assertEquals(sizeBefore - 1, sizeAfter)
    }

    @Test(expected = java.util.NoSuchElementException::class)
    fun deleteMessage_should_not_delete() {
        val chat = ChatService()
        chat.createChat(
            Chat(
                1, 1, mutableListOf()
            )
        )
        chat.createMessage(
            1, Message(
                1, 1, 1, 2, "Hello", get = false, incoming = false
            )
        )
        chat.deleteMessage(
            Message(
                1, 2, 1, 2, "Hello", get = false, incoming = false
            )
        )
    }

    @Test
    fun getMessagesFromChat_should_get() {
        val chat = ChatService()
        chat.createChat(
            Chat(
                1, 1, mutableListOf()
            )
        )
        chat.createMessage(
            1, Message(
                1, 1, 1, 2, "Hello", get = false, incoming = false
            )
        )
        val result = chat.getMessagesFromChat(1, 1, 1).isNotEmpty()
        assertTrue(result)
    }

    @Test(expected = NoSuchElementException::class)
    fun getMessagesFromChat_should_not_get() {
        val chat = ChatService()
        chat.getMessagesFromChat(1, 1, 1)
    }

    @Test
    fun getUnreadChatsCount() {
        val chat = ChatService()
        chat.createChat(
            Chat(
                1, 1, mutableListOf()
            )
        )
        chat.createMessage(
            1, Message(
                1, 1, 1, 2, "Hello", get = false, incoming = true
            )
        )
        val result = chat.getUnreadChatsCount(1)
        assertEquals(1, result)
    }

    @Test
    fun getUnreadChatsCount_no_unread_chats() {
        val chat = ChatService()
        chat.createChat(
            Chat(
                1, 1, mutableListOf()
            )
        )
        chat.createMessage(
            1, Message(
                1, 1, 1, 2, "Hello", get = true, incoming = true
            )
        )
        val result = chat.getUnreadChatsCount(1)
        assertEquals(0, result)
    }
}