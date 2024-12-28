document.addEventListener('DOMContentLoaded', function () {
    const channelId = document.getElementById('channel-id').value;
    const userId = sessionStorage.getItem('userId');
    const messageInput = document.getElementById('message-input');
    const sendButton = document.getElementById('send-button');
    const messagesContainer = document.getElementById('messages');

    if (!userId) {
        window.location.href = '/';
    }

    let lastMessageId = 0;
    let seenMessages = new Set();

    messageInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            postMessage();
        }
    });

    sendButton.addEventListener('click', postMessage);

    function appendChatToContainer(chat) {
        const chatElement = document.createElement('div');
        chatElement.className = chat.user.id == userId ? 'chat self' : 'chat';
        chatElement.innerHTML = `<strong>${chat.user.username}:</strong> ${chat.content}`;
        messagesContainer.appendChild(chatElement);
        messagesContainer.scrollTop = messagesContainer.scrollHeight;
    }

    function fetchChats() {
        fetch(`/channels/${channelId}/messages?after=${lastMessageId}`)
            .then((response) => response.json())
            .then((chats) => {
                chats.forEach((chat) => {
                    if (!seenMessages.has(chat.id)) {
                        appendChatToContainer(chat);
                        seenMessages.add(chat.id);
                        lastMessageId = Math.max(lastMessageId, chat.id);
                    }
                });
            })
            .catch((error) => console.error('Error fetching messages:', error));
    }

    function postMessage() {
        const content = messageInput.value;

        if (content.trim() !== '') {
            fetch(`/channels/${channelId}/messages?userId=${userId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ content: content }),
            })
                .then((response) => {
                    if (!response.ok) throw new Error('Failed to send message');
                    return response.json();
                })
                .then(() => {
                    messageInput.value = '';
                    fetchChats();
                })
                .catch((error) => console.error('Error posting message:', error));
        }
    }

    fetchChats();
    setInterval(fetchChats, 2500);
});
