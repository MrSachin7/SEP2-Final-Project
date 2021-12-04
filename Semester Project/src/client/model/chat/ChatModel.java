package client.model.chat;

import shared.utils.Request;
import shared.utils.chat.Message;

public interface ChatModel {
    Request sendMessage(Message message);
}
