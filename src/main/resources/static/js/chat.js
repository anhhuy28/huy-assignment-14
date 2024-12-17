document.addEventListener('DOMContentLoaded', function () {
    const channelId = document.getElementById('channel-id').value;
    const userId = document.getElementById('user-id').value;
    const messageInput = document.getElementById('message-input');
    const sendButton = document.getElementById('send-button');
    const messagesContainer = document.getElementById('messages');

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
        chatElement.className = chat.user.id === userId ? 'chat self' : 'chat';
        chatElement.classList.add(`user-${(chat.user.id % 4) + 1}`);
        chatElement.innerHTML = `<strong>${chat.user.username}:</strong> ${chat.content}`;
        chatElement.style.opacity = '0';
        messagesContainer.appendChild(chatElement);

        chatElement.offsetHeight;

        chatElement.style.opacity = '1';
    }

    function fetchChats() {
        fetch(`/channels/${channelId}/messages?after=${lastMessageId}`)
            .then((response) => response.json())
            .then((chats) => {
                let newMessages = false;
                chats.forEach((chat) => {
                    if (!seenMessages.has(chat.id)) {
                        appendChatToContainer(chat);
                        seenMessages.add(chat.id);
                        lastMessageId = Math.max(lastMessageId, chat.id);
                        newMessages = true;
                    }
                });
                if (newMessages) {
                    messagesContainer.scrollTop = messagesContainer.scrollHeight; // Auto-scroll
                }
            })
            .catch((error) => console.error('Error:', error));
    }

    function postMessage() {
        const content = messageInput.value;
        if (content.trim() !== '') {
            fetch(`/channels/${channelId}/messages`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ content: content }),
            })
                .then((response) => response.json())
                .then(() => {
                    messageInput.value = '';
                    fetchChats();
                })
                .catch((error) => console.error('Error:', error));
        }
    }

    fetchChats();
    setInterval(fetchChats, 2500);
});