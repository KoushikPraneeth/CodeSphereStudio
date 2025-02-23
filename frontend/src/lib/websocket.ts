import { Client, IMessage } from '@stomp/stompjs'
import { useAuth } from './auth'

class WebSocketService {
  private client: Client | null = null
  private subscriptions: { [key: string]: () => void } = {}

  connect(sessionId: string): Promise<void> {
    const token = useAuth.getState().token

    this.client = new Client({
      brokerURL: 'ws://localhost:8080/ws',
      connectHeaders: {
        Authorization: `Bearer ${token}`,
      },
      onConnect: () => {
        this.subscribeToSession(sessionId)
      },
      onDisconnect: () => {
        console.log('Disconnected from WebSocket')
      },
    })

    return new Promise((resolve) => {
      this.client?.activate()
      resolve()
    })
  }

  disconnect() {
    Object.values(this.subscriptions).forEach(unsubscribe => unsubscribe())
    this.subscriptions = {}
    this.client?.deactivate()
  }

  private subscribeToSession(sessionId: string) {
    if (!this.client?.connected) return

    // Subscribe to code changes
    this.subscriptions['code'] = this.client.subscribe(
      `/topic/session/${sessionId}`,
      (message: IMessage) => {
        const data = JSON.parse(message.body)
        if (data.type === 'CODE_CHANGE') {
          this.handlers.onCodeChange?.(data.content)
        }
      }
    ).unsubscribe

    // Subscribe to cursor positions
    this.subscriptions['cursors'] = this.client.subscribe(
      `/topic/session/${sessionId}/cursors`,
      (message: IMessage) => {
        const data = JSON.parse(message.body)
        this.handlers.onCursorMove?.(data)
      }
    ).unsubscribe

    // Subscribe to active users
    this.subscriptions['users'] = this.client.subscribe(
      `/topic/session/${sessionId}/users`,
      (message: IMessage) => {
        const users = JSON.parse(message.body)
        this.handlers.onUsersChange?.(users)
      }
    ).unsubscribe

    // Subscribe to chat messages
    this.subscriptions['chat'] = this.client.subscribe(
      `/topic/session/${sessionId}/chat`,
      (message: IMessage) => {
        const data = JSON.parse(message.body)
        this.handlers.onChatMessage?.(data)
      }
    ).unsubscribe

    // Join session
    this.client.publish({
      destination: '/app/session.join',
      body: sessionId,
    })
  }

  sendCodeChange(sessionId: string, content: string) {
    this.client?.publish({
      destination: '/app/collaborate',
      body: JSON.stringify({
        sessionId,
        type: 'CODE_CHANGE',
        content,
      }),
    })
  }

  sendCursorPosition(sessionId: string, position: number) {
    this.client?.publish({
      destination: '/app/collaborate',
      body: JSON.stringify({
        sessionId,
        type: 'CURSOR_MOVE',
        position,
      }),
    })
  }

  sendChatMessage(sessionId: string, message: string) {
    this.client?.publish({
      destination: '/app/collaborate',
      body: JSON.stringify({
        sessionId,
        type: 'CHAT',
        content: message,
      }),
    })
  }

  handlers = {
    onCodeChange: null as ((code: string) => void) | null,
    onCursorMove: null as ((data: { username: string, position: number }) => void) | null,
    onUsersChange: null as ((users: string[]) => void) | null,
    onChatMessage: null as ((data: { username: string, message: string }) => void) | null,
  }
}

export const websocketService = new WebSocketService()