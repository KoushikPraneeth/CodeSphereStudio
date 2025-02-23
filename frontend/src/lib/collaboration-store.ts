import { create } from "zustand";

interface ChatMessage {
  username: string;
  message: string;
  timestamp: number;
}

interface ExecutionResult {
  output: string;
  error: string;
  status: string;
  executionTime: number;
  timestamp: number;
}

interface CollaborationState {
  sessionId: string | null;
  activeUsers: string[];
  chatMessages: ChatMessage[];
  executionResults: ExecutionResult[];
  setSessionId: (id: string | null) => void;
  setActiveUsers: (users: string[]) => void;
  addChatMessage: (username: string, message: string) => void;
  addExecutionResult: (result: ExecutionResult) => void;
}

export const useCollaborationStore = create<CollaborationState>((set) => ({
  sessionId: null,
  activeUsers: [],
  chatMessages: [],
  executionResults: [],
  setSessionId: (id) => set({ sessionId: id }),
  setActiveUsers: (users) => set({ activeUsers: users }),
  addChatMessage: (username, message) =>
    set((state) => ({
      chatMessages: [
        ...state.chatMessages,
        { username, message, timestamp: Date.now() },
      ],
    })),
  addExecutionResult: (result) =>
    set((state) => ({
      executionResults: [
        ...state.executionResults,
        { ...result, timestamp: Date.now() },
      ],
    })),
}));
