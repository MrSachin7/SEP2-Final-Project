package server.model.chat;

import server.database.chat.ChatDAO;
import server.database.chat.ChatDAOImpl;
import shared.utils.Request;
import shared.utils.chat.Message;

public class ChatHandlerImp implements ChatHandler{

    private ChatDAO chatDAO;

    public ChatHandlerImp( ) {
    }

    @Override
    public Request sendMessage(Message message) {
        try {
            return ChatDAOImpl.getInstance().addMessage(message);
        } catch (Exception e) {
            return new Request(e.getMessage(),null);
        }
    }
}
